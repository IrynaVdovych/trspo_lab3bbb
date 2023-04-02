package borakdmytro.trspo_lab3.service;

import borakdmytro.trspo_lab3.model.Book;
import borakdmytro.trspo_lab3.model.Borrowing;
import borakdmytro.trspo_lab3.model.BorrowingStatus;
import borakdmytro.trspo_lab3.model.Reader;
import borakdmytro.trspo_lab3.repository.BookRepository;
import borakdmytro.trspo_lab3.repository.BorrowingRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

@Service
public class BorrowingService {
    private final BorrowingRepository borrowingRepository;
    private final BookRepository bookRepository;

    @Autowired
    public BorrowingService(BorrowingRepository borrowingRepository, BookRepository bookRepository) {
        this.borrowingRepository = borrowingRepository;
        this.bookRepository = bookRepository;
    }

    public Borrowing getBorrowingById(int id) throws EntityNotFoundException {
        return borrowingRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Borrowing not found"));
    }

    public List<Borrowing> getAllBorrowings() {
        List<Borrowing> borrowings = borrowingRepository.findAll();
        borrowings.sort(Comparator.comparing(Borrowing::getStartDateTime).reversed());
        return borrowings;
    }

    public List<Borrowing> getNewBorrowings() {
        return borrowingRepository.findAllByStatus(BorrowingStatus.CREATED);
    }

    @Transactional
    public List<Borrowing> getActiveBorrowings() {
        List<Borrowing> confirmed = borrowingRepository.findAllByStatus(BorrowingStatus.CONFIRMED);
        List<Borrowing> expired = borrowingRepository.findAllByStatus(BorrowingStatus.EXPIRED);
        return Stream.of(confirmed, expired)
                .flatMap(List::stream)
                .toList();
    }

    @Transactional
    public List<Borrowing> getReaderActiveBorrowings(Reader reader) {
        List<Borrowing> created = borrowingRepository.findAllByReaderAndStatus(reader, BorrowingStatus.CREATED);
        List<Borrowing> confirmed = borrowingRepository.findAllByReaderAndStatus(reader, BorrowingStatus.CONFIRMED);
        List<Borrowing> expired = borrowingRepository.findAllByReaderAndStatus(reader, BorrowingStatus.EXPIRED);
        return Stream.of(expired, confirmed, created)
                .flatMap(List::stream)
                .toList();
    }

    @Transactional
    public List<Borrowing> getReaderBorrowingsHistory(Reader reader) {
        List<Borrowing> created = borrowingRepository.findAllByReaderAndStatus(reader, BorrowingStatus.NOT_CONFIRMED);
        List<Borrowing> confirmed = borrowingRepository.findAllByReaderAndStatus(reader, BorrowingStatus.CANCELED);
        List<Borrowing> expired = borrowingRepository.findAllByReaderAndStatus(reader, BorrowingStatus.FINISHED);
        return Stream.of(expired, confirmed, created)
                .flatMap(List::stream)
                .sorted(Comparator.comparing(Borrowing::getStartDateTime).reversed())
                .toList();
    }

    @Transactional
    public Borrowing createBorrowing(Borrowing borrowing) throws EntityExistsException {
        if (!borrowingRepository.existsById(borrowing.getId())) {
            throw new EntityExistsException("Borrowing with id " + borrowing.getId() + " already exist");
        }
        borrowing.setStatus(BorrowingStatus.CREATED);
        borrowing.setStartDateTime(LocalDateTime.now());
        Borrowing newBorrowing = borrowingRepository.save(borrowing);
        Book book = borrowing.getBook();
        book.setAvailableAmount(book.getAvailableAmount() - countBooksInUse(book.getId())); // recalculate booksInUse;
        bookRepository.save(book);
        return newBorrowing;
    }

    @Transactional
    public Borrowing updateBorrowing(Borrowing borrowing) throws EntityNotFoundException {
        if (!borrowingRepository.existsById(borrowing.getId())) {
            throw new EntityNotFoundException("Borrowing with id " + borrowing.getId() + " not found");
        }
        Borrowing newBorrowing = borrowingRepository.save(borrowing);
        Book book = borrowing.getBook();
        book.setAvailableAmount(book.getAvailableAmount() - countBooksInUse(book.getId())); // recalculate booksInUse;
        bookRepository.save(book);
        return newBorrowing;
    }

    protected int countBooksInUse(int bookId) {
        List<BorrowingStatus> statuses = List.of(BorrowingStatus.CONFIRMED, BorrowingStatus.EXPIRED);
        return borrowingRepository.countAllByBookIdAndStatusIn(bookId, statuses);
    }

    @Transactional
    protected void deleteAllReaderBorrowings(int readerId) {
        borrowingRepository.deleteAllByReaderIdAndStatus(readerId, BorrowingStatus.CREATED);
        borrowingRepository.updateBorrowingsStatusForReader(readerId, BorrowingStatus.CONFIRMED, BorrowingStatus.EXPIRED);
    }
}

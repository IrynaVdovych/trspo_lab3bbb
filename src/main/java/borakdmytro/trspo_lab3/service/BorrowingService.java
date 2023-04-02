package borakdmytro.trspo_lab3.service;

import borakdmytro.trspo_lab3.model.Borrowing;
import borakdmytro.trspo_lab3.model.BorrowingStatus;
import borakdmytro.trspo_lab3.model.Reader;
import borakdmytro.trspo_lab3.repository.BorrowingRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

@Service
public class BorrowingService {
    private final BorrowingRepository borrowingRepository;

    @Autowired
    public BorrowingService(BorrowingRepository borrowingRepository) {
        this.borrowingRepository = borrowingRepository;
    }

    public Borrowing getBorrowingById(int id) {
        return borrowingRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Borrowing not found"));
    }

    public List<Borrowing> getNewBorrowings() {
        return borrowingRepository.findAllByStatus(BorrowingStatus.CREATED);
    }

    public List<Borrowing> getActiveBorrowings() {
        List<Borrowing> confirmed = borrowingRepository.findAllByStatus(BorrowingStatus.CONFIRMED);
        List<Borrowing> expired = borrowingRepository.findAllByStatus(BorrowingStatus.EXPIRED);
        return Stream.of(confirmed, expired)
                .flatMap(List::stream)
                .toList();
    }

    public List<Borrowing> getAllBorrowings() {
        List<Borrowing> borrowings = borrowingRepository.findAll();
        borrowings.sort(Comparator.comparing(Borrowing::getStartDateTime).reversed());
        return borrowings;
    }

    public List<Borrowing> getReaderActiveBorrowings(Reader reader) {
        List<Borrowing> created = borrowingRepository.findAllByReaderAndStatus(reader, BorrowingStatus.CREATED);
        List<Borrowing> confirmed = borrowingRepository.findAllByReaderAndStatus(reader, BorrowingStatus.CONFIRMED);
        List<Borrowing> expired = borrowingRepository.findAllByReaderAndStatus(reader, BorrowingStatus.EXPIRED);
        return Stream.of(expired, confirmed, created)
                .flatMap(List::stream)
                .toList();
    }

    public List<Borrowing> getReaderBorrowingsHistory(Reader reader) {
        List<Borrowing> created = borrowingRepository.findAllByReaderAndStatus(reader, BorrowingStatus.NOT_CONFIRMED);
        List<Borrowing> confirmed = borrowingRepository.findAllByReaderAndStatus(reader, BorrowingStatus.CANCELED);
        List<Borrowing> expired = borrowingRepository.findAllByReaderAndStatus(reader, BorrowingStatus.FINISHED);
        return Stream.of(expired, confirmed, created)
                .flatMap(List::stream)
                .sorted(Comparator.comparing(Borrowing::getStartDateTime).reversed())
                .toList();
    }

    public Borrowing createBorrowing(Borrowing borrowing) {
        borrowing.setStatus(BorrowingStatus.CREATED);
        borrowing.setStartDateTime(LocalDateTime.now());
        return borrowingRepository.save(borrowing);
    }

    public Borrowing updateBorrowing(Borrowing borrowing) {
        if (!borrowingRepository.existsById(borrowing.getId())) {
            throw new EntityNotFoundException("Book with id " + borrowing.getId() + " not found");
        }
        return borrowingRepository.save(borrowing);
    }
}

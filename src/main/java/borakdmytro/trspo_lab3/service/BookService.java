package borakdmytro.trspo_lab3.service;

import borakdmytro.trspo_lab3.model.Book;
import borakdmytro.trspo_lab3.repository.BookRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final BorrowingService borrowingService;

    @Autowired
    public BookService(BookRepository bookRepository, BorrowingService borrowingService) {
        this.bookRepository = bookRepository;
        this.borrowingService = borrowingService;
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book getBookById(int id) throws EntityNotFoundException {
        return bookRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Book not found"));
    }

    public List<Book> findBooksByTitle(String title) {
        return bookRepository.findByTitleContainingIgnoreCase(title);
    }

    public List<Book> findBooksByAuthor(String author) {
        return bookRepository.findByAuthorContainingIgnoreCase(author);
    }

    @Transactional
    public Book createBook(Book book) throws EntityExistsException {
        if (bookRepository.existsById(book.getId())) {
            throw new EntityExistsException("Book with id " + book.getId() + " already exist");
        }
        book.setAvailableAmount(book.getTotalAmount());
        return bookRepository.save(book);
    }

    @Transactional
    public Book updateBook(Book book) throws EntityNotFoundException, IllegalArgumentException {
        if (!bookRepository.existsById(book.getId())) {
            throw new EntityNotFoundException("Book with id " + book.getId() + " not found");
        }
        int booksInUse = borrowingService.countBooksInUse(book.getId());
        if (book.getTotalAmount() < booksInUse || booksInUse < 0) {
            String message = String.format("The number of books(%d) is less than the number of books already in use(%d)",
                    book.getTotalAmount(), booksInUse);
            throw new IllegalArgumentException(message);
        }
        book.setAvailableAmount(book.getTotalAmount() - booksInUse);
        return bookRepository.save(book);
    }
}

package borakdmytro.trspo_lab3.service;

import borakdmytro.trspo_lab3.model.Book;
import borakdmytro.trspo_lab3.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book getBookById(int id) {
        return bookRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Book not found"));
    }

    public List<Book> findBooksByTitle(String title) {
        return bookRepository.findByTitleContainingIgnoreCase(title);
    }

    public List<Book> findBooksByAuthor(String author) {
        return bookRepository.findByAuthorContainingIgnoreCase(author);
    }

    public Book createBook(Book book) {
        return bookRepository.save(book);
    }

    public Book updateBook(int id, Book book) {
        Book existingBook = bookRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Book not found"));
        existingBook.setTitle(book.getTitle());
        existingBook.setAuthor(book.getAuthor());
        existingBook.setAnnotation(book.getAnnotation());
        existingBook.setPages(book.getPages());
        existingBook.setYear(book.getYear());
        existingBook.setTotalAmount(book.getTotalAmount());
        // todo calculate available amount
//        existingBook.setAvailableAmount(book.getAvailableAmount());
        return bookRepository.save(existingBook);
    }

    // не видаляти якщо є в позичанні
    public void deleteBook(int id) {
        bookRepository.deleteById(id);
    }
}


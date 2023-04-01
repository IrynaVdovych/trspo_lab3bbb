package borakdmytro.trspo_lab3.controller;

import borakdmytro.trspo_lab3.dto.BookDTO;
import borakdmytro.trspo_lab3.model.Book;
import borakdmytro.trspo_lab3.service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("")
    public ResponseEntity<List<BookDTO>> getAllBooks() {
        List<Book> books = bookService.getAllBooks();
        List<BookDTO> bookDTOs = books.stream().map(BookDTO::fromEntity).toList();
        return ResponseEntity.ok(bookDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable int id) {
        Book book = bookService.getBookById(id);
        BookDTO bookDTO = BookDTO.fromEntity(book);
        return ResponseEntity.ok(bookDTO);
    }

    @GetMapping("/title/{title}")
    public ResponseEntity<List<BookDTO>> findBooksByTitle(@PathVariable String title) {
        List<Book> books = bookService.findBooksByTitle(title);
        List<BookDTO> bookDTOs = books.stream().map(BookDTO::fromEntity).toList();
        return ResponseEntity.ok(bookDTOs);
    }

    @GetMapping("/author/{author}")
    public ResponseEntity<List<BookDTO>> findBooksByAuthor(@PathVariable String author) {
        List<Book> books = bookService.findBooksByAuthor(author);
        List<BookDTO> bookDTOs = books.stream().map(BookDTO::fromEntity).toList();
        return ResponseEntity.ok(bookDTOs);
    }

    @PostMapping
    public ResponseEntity<BookDTO> createBook(@RequestBody @Valid BookDTO bookDTO) {
        Book book = bookDTO.toEntity();
        book.setAvailableAmount(book.getTotalAmount());
        book = bookService.createBook(book);
        BookDTO createdBookDTO = BookDTO.fromEntity(book);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBookDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookDTO> updateBook(@PathVariable int id, @RequestBody @Valid BookDTO bookDTO) {
        Book book = bookDTO.toEntity();
        book.setId(id);
        book = bookService.updateBook(id, book);
        BookDTO updatedBookDTO = BookDTO.fromEntity(book);
        return ResponseEntity.ok(updatedBookDTO);
    }

//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteBookById(@PathVariable int id) {
//        bookService.deleteBook(id);
//        return ResponseEntity.noContent().build();
//    }
}

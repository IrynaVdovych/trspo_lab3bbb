package borakdmytro.trspo_lab3.controller;

import borakdmytro.trspo_lab3.dto.BorrowingDTO;
import borakdmytro.trspo_lab3.model.*;
import borakdmytro.trspo_lab3.service.BookService;
import borakdmytro.trspo_lab3.service.BorrowingService;
import borakdmytro.trspo_lab3.service.ReaderService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/borrowings")
public class BorrowingController {
    private final BorrowingService borrowingService;
    private final ReaderService readerService;
    private final BookService bookService;

    @Autowired
    public BorrowingController(BorrowingService borrowingService, ReaderService readerService, BookService bookService) {
        this.borrowingService = borrowingService;
        this.readerService = readerService;
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<List<BorrowingDTO>> getAllBorrowings() {
        List<Borrowing> borrowings = borrowingService.getAllBorrowings();
        List<BorrowingDTO> borrowingDTOs = borrowings.stream().map(BorrowingDTO::fromEntity).toList();
        return ResponseEntity.ok(borrowingDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BorrowingDTO> getBorrowingById(@PathVariable int id) {
        try {
            Borrowing borrowing = borrowingService.getBorrowingById(id);
            BorrowingDTO borrowingDTO = BorrowingDTO.fromEntity(borrowing);
            return ResponseEntity.ok(borrowingDTO);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/new")
    public ResponseEntity<List<BorrowingDTO>> getNewBorrowings() {
        List<Borrowing> borrowings = borrowingService.getNewBorrowings();
        List<BorrowingDTO> borrowingDTOs = borrowings.stream().map(BorrowingDTO::fromEntity).toList();
        return ResponseEntity.ok(borrowingDTOs);
    }

    @GetMapping("/active")
    public ResponseEntity<List<BorrowingDTO>> getActiveBorrowings() {
        List<Borrowing> borrowings = borrowingService.getActiveBorrowings();
        List<BorrowingDTO> borrowingDTOs = borrowings.stream().map(BorrowingDTO::fromEntity).toList();
        return ResponseEntity.ok(borrowingDTOs);
    }

    @GetMapping("/active/user/{id}")
    public ResponseEntity<List<BorrowingDTO>> getActiveReaderBorrowings(@PathVariable int id) {
        try {
            Reader reader = readerService.getReaderById(id);
            List<Borrowing> borrowings = borrowingService.getReaderActiveBorrowings(reader);
            List<BorrowingDTO> borrowingDTOs = borrowings.stream().map(BorrowingDTO::fromEntity).toList();
            return ResponseEntity.ok(borrowingDTOs);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/history/user/{id}")
    public ResponseEntity<List<BorrowingDTO>> getReaderBorrowingsHistory(@PathVariable int id) {
        try {
            Reader reader = readerService.getReaderById(id);
            List<Borrowing> borrowings = borrowingService.getReaderBorrowingsHistory(reader);
            List<BorrowingDTO> borrowingDTOs = borrowings.stream().map(BorrowingDTO::fromEntity).toList();
            return ResponseEntity.ok(borrowingDTOs);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<BorrowingDTO> creteBorrowing(@Valid @RequestBody BorrowingDTO borrowingDTO) {
        try {
            Reader reader = readerService.getReaderById(borrowingDTO.getReaderId());
            Book book = bookService.getBookById(borrowingDTO.getBookId());
            Borrowing borrowing = borrowingDTO.toEntity(reader, book);
            borrowing.setStartDateTime(LocalDateTime.now());
            borrowing.setStatus(BorrowingStatus.CREATED);
            borrowing = borrowingService.createBorrowing(borrowing);
            BorrowingDTO createdBorrowingDTO = BorrowingDTO.fromEntity(borrowing);
            URI location = URI.create("/borrowings/" + borrowing.getId());
            return ResponseEntity.created(location).body(createdBorrowingDTO);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (EntityExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<BorrowingDTO> cancelBorrowing(@PathVariable int id) {
        try {
            Borrowing borrowing = borrowingService.getBorrowingById(id);
            if (borrowing.getStatus() == BorrowingStatus.CREATED) {
                borrowing.setStatus(BorrowingStatus.CANCELED);
                borrowing = borrowingService.updateBorrowing(borrowing);
                BorrowingDTO borrowingDTO = BorrowingDTO.fromEntity(borrowing);
                return ResponseEntity.ok(borrowingDTO);
            }
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/reject")
    public ResponseEntity<BorrowingDTO> rejectBorrowing(@PathVariable int id) {
        try {
            Borrowing borrowing = borrowingService.getBorrowingById(id);
            if (borrowing.getStatus() == BorrowingStatus.CREATED) {
                borrowing.setStatus(BorrowingStatus.NOT_CONFIRMED);
                borrowing = borrowingService.updateBorrowing(borrowing);
                BorrowingDTO borrowingDTO = BorrowingDTO.fromEntity(borrowing);
                return ResponseEntity.ok(borrowingDTO);
            }
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/confirm")
    public ResponseEntity<BorrowingDTO> confirmBorrowing(@PathVariable int id) {
        try {
            Borrowing borrowing = borrowingService.getBorrowingById(id);
            if (borrowing.getStatus() == BorrowingStatus.CREATED) {
                borrowing.setStatus(BorrowingStatus.CONFIRMED);
                borrowing = borrowingService.updateBorrowing(borrowing);
                BorrowingDTO borrowingDTO = BorrowingDTO.fromEntity(borrowing);
                return ResponseEntity.ok(borrowingDTO);
            }
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/finish")
    public ResponseEntity<BorrowingDTO> finishBorrowing(@PathVariable int id) {
        try {
            Borrowing borrowing = borrowingService.getBorrowingById(id);
            if (borrowing.getStatus() == BorrowingStatus.CONFIRMED
                    || borrowing.getStatus() == BorrowingStatus.EXPIRED) {
                borrowing.setStatus(BorrowingStatus.FINISHED);
                borrowing = borrowingService.updateBorrowing(borrowing);
                BorrowingDTO borrowingDTO = BorrowingDTO.fromEntity(borrowing);
                return ResponseEntity.ok(borrowingDTO);
            }
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}

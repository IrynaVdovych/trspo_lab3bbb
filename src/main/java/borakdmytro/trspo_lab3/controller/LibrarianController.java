package borakdmytro.trspo_lab3.controller;

import borakdmytro.trspo_lab3.dto.LibrarianDTO;
import borakdmytro.trspo_lab3.model.Librarian;
import borakdmytro.trspo_lab3.service.LibrarianService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/librarians")
public class LibrarianController {

    private final LibrarianService librarianService;

    @Autowired
    public LibrarianController(LibrarianService librarianService) {
        this.librarianService = librarianService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<LibrarianDTO> getLibrarianById(@PathVariable int id) {
        try {
            Librarian librarian = librarianService.getLibrarianById(id);
            return ResponseEntity.ok(LibrarianDTO.fromEntity(librarian));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<LibrarianDTO>> getAllLibrarians() {
        List<Librarian> librarians = librarianService.getAllLibrarians();
        List<LibrarianDTO> librarianDTOs = librarians.stream()
                .map(LibrarianDTO::fromEntity)
                .toList();
        return ResponseEntity.ok(librarianDTOs);
    }

    @PostMapping
    public ResponseEntity<LibrarianDTO> addLibrarian(@RequestBody @Valid LibrarianDTO librarianDTO) {
        try {
            Librarian librarian = librarianDTO.toEntity();
            librarian = librarianService.createLibrarian(librarian);
            URI location = URI.create("/librarians/" + librarian.getId());
            LibrarianDTO createdLibrarianDTO = LibrarianDTO.fromEntity(librarian);
            return ResponseEntity.created(location).body(createdLibrarianDTO);
        } catch (EntityExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<LibrarianDTO> updateLibrarian(@PathVariable int id, @RequestBody @Valid LibrarianDTO librarianDTO) {
        try {
            Librarian librarian = librarianDTO.toEntity();
            librarian.setId(id);
            librarian = librarianService.updateLibrarian(librarian);
            LibrarianDTO updatedLibrarianDTO = LibrarianDTO.fromEntity(librarian);
            return ResponseEntity.ok(updatedLibrarianDTO);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLibrarian(@PathVariable int id) {
        try {
            librarianService.deleteLibrarian(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}


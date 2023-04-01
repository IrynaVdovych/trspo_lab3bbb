package borakdmytro.trspo_lab3.controller;

import borakdmytro.trspo_lab3.dto.ReaderDTO;
import borakdmytro.trspo_lab3.model.Reader;
import borakdmytro.trspo_lab3.service.ReaderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/readers")
public class ReaderController {

    private final ReaderService readerService;

    @Autowired
    public ReaderController(ReaderService readerService) {
        this.readerService = readerService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReaderDTO> getReaderById(@PathVariable int id) {
        Reader reader = readerService.getReaderById(id);
        return ResponseEntity.ok(ReaderDTO.fromEntity(reader));
    }

    @GetMapping
    public ResponseEntity<List<ReaderDTO>> getAllReaders() {
        List<Reader> readers = readerService.getAllReaders();
        List<ReaderDTO> readerDTOs = readers.stream()
                .map(ReaderDTO::fromEntity)
                .toList();
        return ResponseEntity.ok(readerDTOs);
    }

    @PostMapping
    public ResponseEntity<ReaderDTO> createReader(@Valid @RequestBody ReaderDTO readerDTO) {
        Reader reader = readerDTO.toEntity();
        reader = readerService.createReader(reader);
        URI location = URI.create("/readers/" + reader.getId());
        ReaderDTO createdReaderDTO = ReaderDTO.fromEntity(reader);
        return ResponseEntity.created(location).body(createdReaderDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReaderDTO> updateReader(@PathVariable int id, @Valid @RequestBody ReaderDTO readerDTO) {
        Reader reader = readerDTO.toEntity();
        reader.setId(id);
        reader = readerService.updateReader(reader);
        ReaderDTO updatedReaderDTO = ReaderDTO.fromEntity(reader);
        return ResponseEntity.ok(updatedReaderDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReader(@PathVariable int id) {
        readerService.deleteReaderById(id);
        return ResponseEntity.noContent().build();
    }
}


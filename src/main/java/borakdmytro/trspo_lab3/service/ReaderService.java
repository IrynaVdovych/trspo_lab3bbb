package borakdmytro.trspo_lab3.service;

import borakdmytro.trspo_lab3.model.Reader;
import borakdmytro.trspo_lab3.repository.ReaderRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReaderService {

    private final ReaderRepository readerRepository;

    @Autowired
    public ReaderService(ReaderRepository readerRepository) {
        this.readerRepository = readerRepository;
    }

    public List<Reader> getAllReaders() {
        return readerRepository.findAll();
    }

    public Reader getReaderById(int id) {
        return readerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Reader with id " + id + " not found"));
    }

    public Reader createReader(Reader reader) {
        return readerRepository.save(reader);
    }

    public Reader updateReader(Reader reader) {
        if (!readerRepository.existsById(reader.getId())) {
            throw new EntityNotFoundException("Reader with id " + reader.getId() + " not found");
        }
        return readerRepository.save(reader);
    }

    public void deleteReaderById(int id) {
        if (!readerRepository.existsById(id)) {
            throw new EntityNotFoundException("Reader with id " + id + " not found");
        }
        // todo видалити його нові замовнення
        readerRepository.deleteById(id);
    }
}


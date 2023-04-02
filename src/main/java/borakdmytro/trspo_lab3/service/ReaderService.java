package borakdmytro.trspo_lab3.service;

import borakdmytro.trspo_lab3.model.Reader;
import borakdmytro.trspo_lab3.repository.ReaderRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReaderService {

    private final ReaderRepository readerRepository;
    private final BorrowingService borrowingService;

    @Autowired
    public ReaderService(ReaderRepository readerRepository, BorrowingService borrowingService) {
        this.readerRepository = readerRepository;
        this.borrowingService = borrowingService;
    }

    public List<Reader> getAllReaders() {
        return readerRepository.findAll();
    }

    public Reader getReaderById(int id) throws EntityNotFoundException {
        return readerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Reader with id " + id + " not found"));
    }

    @Transactional
    public Reader createReader(Reader reader) throws EntityExistsException {
        if (readerRepository.existsById(reader.getId())) {
            throw new EntityExistsException("Reader with id " + reader.getId() + " not found");
        }
        return readerRepository.save(reader);
    }

    @Transactional
    public Reader updateReader(Reader reader) throws EntityNotFoundException {
        if (!readerRepository.existsById(reader.getId())) {
            throw new EntityNotFoundException("Reader with id " + reader.getId() + " not found");
        }
        return readerRepository.save(reader);
    }

    @Transactional
    public void deleteReaderById(int id) throws EntityNotFoundException {
        if (!readerRepository.existsById(id)) {
            throw new EntityNotFoundException("Reader with id " + id + " not found");
        }
        borrowingService.deleteAllReaderBorrowings(id);
        readerRepository.deleteById(id);
    }
}


package borakdmytro.trspo_lab3.service;

import borakdmytro.trspo_lab3.model.Librarian;
import borakdmytro.trspo_lab3.repository.LibrarianRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LibrarianService {
    private final LibrarianRepository librarianRepository;

    @Autowired
    public LibrarianService(LibrarianRepository librarianRepository) {
        this.librarianRepository = librarianRepository;
    }

    public List<Librarian> getAllLibrarians() {
        return librarianRepository.findAll();
    }

    public Librarian getLibrarianById(int id) throws EntityNotFoundException {
        return librarianRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Librarian with id " + id + " not found"));
    }

    @Transactional
    public Librarian createLibrarian(Librarian librarian) throws EntityExistsException {
        if (librarianRepository.existsById(librarian.getId())) {
            throw new EntityExistsException("Librarian with id " + librarian.getId() + " already exist");
        }
        return librarianRepository.save(librarian);
    }

    @Transactional
    public Librarian updateLibrarian(Librarian librarian) throws EntityNotFoundException {
        if (!librarianRepository.existsById(librarian.getId())) {
            throw new EntityNotFoundException("Librarian with id " + librarian.getId() + " not found");
        }
        return librarianRepository.save(librarian);
    }

    @Transactional
    public void deleteLibrarian(int id) throws EntityNotFoundException {
        if (!librarianRepository.existsById(id)) {
            throw new EntityNotFoundException("Librarian with id " + id + " not found");
        }
        librarianRepository.deleteById(id);
    }
}


package borakdmytro.trspo_lab3.repository;

import borakdmytro.trspo_lab3.model.Borrowing;
import borakdmytro.trspo_lab3.model.BorrowingStatus;
import borakdmytro.trspo_lab3.model.Reader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BorrowingRepository extends JpaRepository<Borrowing, Integer> {
    List<Borrowing> findAllByStatus(BorrowingStatus status);
    List<Borrowing> findAllByReaderAndStatus(Reader reader, BorrowingStatus status);
}

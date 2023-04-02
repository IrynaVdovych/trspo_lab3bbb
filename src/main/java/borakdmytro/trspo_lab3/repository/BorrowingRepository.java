package borakdmytro.trspo_lab3.repository;

import borakdmytro.trspo_lab3.model.Borrowing;
import borakdmytro.trspo_lab3.model.BorrowingStatus;
import borakdmytro.trspo_lab3.model.Reader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface BorrowingRepository extends JpaRepository<Borrowing, Integer> {
    List<Borrowing> findAllByStatus(BorrowingStatus status);
    List<Borrowing> findAllByReaderAndStatus(Reader reader, BorrowingStatus status);
    int countAllByBookIdAndStatusIn(int book_id, Collection<BorrowingStatus> status);
    void deleteAllByReaderIdAndStatus(int reader_id, BorrowingStatus status);
    @Modifying
    @Query("UPDATE Borrowing b SET b.status = :newStatus WHERE b.reader.id = :readerId and b.status = :oldStatus")
    void updateBorrowingsStatusForReader(@Param("readerId") int readerId, @Param("oldStatus") BorrowingStatus oldStatus, @Param("newStatus") BorrowingStatus newStatus);
}

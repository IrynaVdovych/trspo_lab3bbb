package borakdmytro.trspo_lab3.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Borrowing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "borrowing_id")
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reader_id", referencedColumnName = "reader_id")
    private Reader reader;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", referencedColumnName = "book_id")
    private Book book;

    @PastOrPresent
    @Column
    private LocalDateTime startDateTime;

    @Enumerated(EnumType.ORDINAL)
    @Column
    private BorrowingType type;

    @Enumerated(EnumType.ORDINAL)
    @Column
    private BorrowingStatus status;
}

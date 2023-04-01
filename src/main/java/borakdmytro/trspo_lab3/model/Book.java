package borakdmytro.trspo_lab3.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private int id;

    @NotBlank
    @Column(unique = true)
    private String title;

    @NotBlank
    @Column
    private String author;

    @Column
    private String annotation;

    @Positive
    @Column
    private int pages;

    @Positive
    @Column
    private int year;

    @PositiveOrZero
    @Column
    private int totalAmount;

    @PositiveOrZero
    @Column
    private int availableAmount;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Borrowing> borrowings;
}

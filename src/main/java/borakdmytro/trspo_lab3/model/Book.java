package borakdmytro.trspo_lab3.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
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
    private String title;

    @NotBlank
    private String author;

    private String annotation;

    @Positive
    private int pages;

    @Positive
    private int year;

    @OneToOne (cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
    private Catalog catalog;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Borrowing> borrowings;
}

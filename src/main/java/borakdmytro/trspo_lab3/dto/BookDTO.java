package borakdmytro.trspo_lab3.dto;

import borakdmytro.trspo_lab3.model.Book;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class BookDTO {

    @NotBlank
    private String title;

    @NotBlank
    private String author;

    private String annotation;

    @NotNull
    @Positive
    private Integer pages;

    @NotNull
    @Positive
    private Integer year;

    @PositiveOrZero
    @Column
    private int totalAmount;

    @PositiveOrZero
    @Column
    private int availableAmount;

    public static BookDTO fromEntity(Book book) {
        BookDTO dto = new BookDTO();
        dto.setTitle(book.getTitle());
        dto.setAuthor(book.getAuthor());
        dto.setAnnotation(book.getAnnotation());
        dto.setPages(book.getPages());
        dto.setYear(book.getYear());
        dto.setTotalAmount(book.getTotalAmount());
        dto.setAvailableAmount(book.getAvailableAmount());
        return dto;
    }

    public Book toEntity() {
        Book book = new Book();
        book.setTitle(this.getTitle());
        book.setAuthor(this.getAuthor());
        book.setAnnotation(this.getAnnotation());
        book.setPages(this.getPages());
        book.setYear(this.getYear());
        book.setTotalAmount(this.getTotalAmount());
        book.setAvailableAmount(this.getAvailableAmount());
        return book;
    }
}

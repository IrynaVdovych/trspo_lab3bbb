package borakdmytro.trspo_lab3.dto;

import borakdmytro.trspo_lab3.model.Book;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class BookDTO {
    @Null
    private Integer bookId;

    @NotBlank
    private String title;

    @NotBlank
    private String author;

    private String annotation;

    @NotNull
    @Positive
    private int pages;

    @NotNull
    @Positive
    private int year;

    @NotNull
    @PositiveOrZero
    private int totalAmount;

    @Null // only for output
    private Integer availableAmount;

    public static BookDTO fromEntity(Book book) {
        BookDTO dto = new BookDTO();
        dto.setBookId(book.getId());
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
        book.setTitle(this.title);
        book.setAuthor(this.author);
        book.setAnnotation(this.annotation);
        book.setPages(this.pages);
        book.setYear(this.year);
        book.setTotalAmount(this.totalAmount);
        book.setAvailableAmount(this.totalAmount);
        return book;
    }
}

package borakdmytro.trspo_lab3.dto;

import borakdmytro.trspo_lab3.model.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BorrowingDTO {
    @PositiveOrZero
    private int readerId;

    @PositiveOrZero
    private int bookId;

    @Null // only for output
    private LocalDateTime startDateTime;

    @NotBlank
    private String type;

    @Null // only for output
    private String status;

    public static BorrowingDTO fromEntity(Borrowing borrowing) {
        BorrowingDTO dto = new BorrowingDTO();
        dto.setReaderId(borrowing.getReader().getId());
        dto.setBookId(borrowing.getBook().getId());
        dto.setStartDateTime(borrowing.getStartDateTime());
        dto.setType(borrowing.getType().name());
        dto.setStatus(borrowing.getStatus().name());
        return dto;
    }

    public Borrowing toEntity(Reader reader, Book book) {
        Borrowing borrowing = new Borrowing();
        if (reader.getId() == this.readerId) {
            borrowing.setReader(reader);
        }
        if (book.getId() == this.bookId) {
            borrowing.setBook(book);
        }
        borrowing.setStartDateTime(LocalDateTime.now());
        borrowing.setType(BorrowingType.valueOf(this.getType()));
        borrowing.setStatus(BorrowingStatus.CREATED);
        return borrowing;
    }
}

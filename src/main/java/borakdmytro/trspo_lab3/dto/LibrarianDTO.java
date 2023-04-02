package borakdmytro.trspo_lab3.dto;

import borakdmytro.trspo_lab3.model.Librarian;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class LibrarianDTO {
    @Null
    private Integer librarianId;
    @NotBlank
    private String firstName;

    private String lastName;

    @NotBlank
    @Email
    private String email;

    @NotNull
    @Size(min = 6)
    private String password;

    public static LibrarianDTO fromEntity(Librarian librarian) {
        LibrarianDTO dto = new LibrarianDTO();
        dto.setLibrarianId(librarian.getId());
        dto.setFirstName(librarian.getFirstName());
        dto.setLastName(librarian.getLastName());
        dto.setEmail(librarian.getEmail());
        dto.setPassword(librarian.getPassword());
        return dto;
    }

    public Librarian toEntity() {
        Librarian librarian = new Librarian();
        librarian.setFirstName(this.firstName);
        librarian.setLastName(this.lastName);
        librarian.setEmail(this.email);
        librarian.setPassword(this.password);
        return librarian;
    }
}


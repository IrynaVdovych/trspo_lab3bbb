package borakdmytro.trspo_lab3.dto;

import borakdmytro.trspo_lab3.model.Librarian;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LibrarianDTO {
    private int id;

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
        dto.setId(librarian.getId());
        dto.setFirstName(librarian.getFirstName());
        dto.setLastName(librarian.getLastName());
        dto.setEmail(librarian.getEmail());
        dto.setPassword(librarian.getPassword());
        return dto;
    }

    public Librarian toEntity() {
        Librarian librarian = new Librarian();
        librarian.setId(this.id);
        librarian.setFirstName(this.firstName);
        librarian.setLastName(this.lastName);
        librarian.setEmail(this.email);
        librarian.setPassword(this.password);
        return librarian;
    }
}


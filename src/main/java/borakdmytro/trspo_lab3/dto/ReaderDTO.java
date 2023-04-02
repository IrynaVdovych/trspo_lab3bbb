package borakdmytro.trspo_lab3.dto;

import borakdmytro.trspo_lab3.model.Reader;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ReaderDTO {
    @Null
    private Integer readerId;

    @NotBlank
    private String name;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 6)
    private String password;

    public static ReaderDTO fromEntity(Reader reader) {
        ReaderDTO dto = new ReaderDTO();
        dto.setReaderId(reader.getId());
        dto.setName(reader.getName());
        dto.setEmail(reader.getEmail());
        dto.setPassword(reader.getPassword());
        return dto;
    }

    public Reader toEntity() {
        Reader reader = new Reader();
        reader.setName(this.name);
        reader.setEmail(this.email);
        reader.setPassword(this.password);
        return reader;
    }
}




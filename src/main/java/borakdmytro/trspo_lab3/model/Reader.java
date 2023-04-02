package borakdmytro.trspo_lab3.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Reader {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reader_id")
    private int id;

    @Column(unique = true)
    private String name;

    @NotBlank
    @Email
    @Column(unique = true)
    private String email;

    @NotNull
    @Size(min = 6)
    @Column
    private String password;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Borrowing> borrowings;
}

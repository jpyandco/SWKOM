package at.technikumwien.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "Title cannot be blank")
    @Size(max = 255, message = "Title cannot exceed 255 characters")
    private String title;

    @NotBlank(message = "Author cannot be blank")
    @Size(max = 255, message = "Author cannot exceed 255 characters")
    private String author;

    @NotBlank(message = "MinIO key cannot be blank")
    @Size(max = 255, message = "MinIO key cannot exceed 255 characters")
    private String minioKey;

    @Lob
    private String text;
}

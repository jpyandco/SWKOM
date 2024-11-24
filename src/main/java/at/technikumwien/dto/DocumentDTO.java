package at.technikumwien.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DocumentDTO {

    private int id;
    private String title;
    private String author;
    private String text;
}

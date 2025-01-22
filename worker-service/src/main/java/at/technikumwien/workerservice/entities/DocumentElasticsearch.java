package at.technikumwien.workerservice.entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DocumentElasticsearch {
    private String title;
    private String author;
    private String text;
    private byte[] data;
}

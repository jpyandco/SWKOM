package at.technikumwien.workerservice.entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WorkerDocument {
    private String title;
    private String author;
    private String minioKey;
    private String text;
}

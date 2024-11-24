package at.technikumwien.entity;

import at.technikumwien.dto.DocumentDTO;
import at.technikumwien.entities.Document;
import at.technikumwien.mapper.DocumentMapper;
import org.mapstruct.factory.Mappers;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class DocumentEntityTest {

    private final DocumentMapper mapper = Mappers.getMapper(DocumentMapper.class);

    @Test
    void createCorrectDocFromDTO() {
        DocumentDTO dto = new DocumentDTO();
        dto.setTitle("TestTitle");
        dto.setText("Secret Text");
        dto.setAuthor("Hansi Hinterseer");

        Document doc = mapper.toEntity(dto);
        assertNotNull(doc);
        assertEquals(dto.getText(), doc.getText());
        assertEquals(dto.getAuthor(), doc.getAuthor());
    }
}

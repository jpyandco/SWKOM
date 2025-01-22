package at.technikumwien.controller;

import at.technikumwien.dto.DocumentDTO;
import at.technikumwien.entities.Document;
import at.technikumwien.service.DocumentService;
import org.junit.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@Ignore
public class DocumentControllerTest {

    @Mock
    private DocumentService documentService;

    @InjectMocks
    private DocumentController documentController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllDocuments() {
        DocumentDTO doc1 = new DocumentDTO();
        doc1.setId(1);
        doc1.setTitle("Doc 1");
        doc1.setAuthor("Author 1");

        DocumentDTO doc2 = new DocumentDTO();
        doc2.setId(2);
        doc2.setTitle("Doc 2");
        doc2.setAuthor("Author 2");

        when(documentService.getAllDocuments()).thenReturn(List.of(doc1, doc2));

        List<DocumentDTO> result = documentController.getAllDocuments();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Doc 1", result.get(0).getTitle());
        verify(documentService, times(1)).getAllDocuments();
    }

    @Test
    public void testCreateDocument() {
        Document input = new Document();
        input.setTitle("Test Title");
        input.setAuthor("Test Author");

        Document savedDocument = new Document();
        savedDocument.setId(1);
        savedDocument.setTitle("Test Title");
        savedDocument.setAuthor("Test Author");

        /*
        when(documentService.saveDocument(any(Document.class))).thenReturn(savedDocument);

        ResponseEntity<DocumentDTO> response = documentController.createDocument(input);

        assertNotNull(response.getBody());
        assertEquals("Test Title", response.getBody().getTitle());
        verify(documentService, times(1)).saveDocument(any(Document.class));
        */
    }
}

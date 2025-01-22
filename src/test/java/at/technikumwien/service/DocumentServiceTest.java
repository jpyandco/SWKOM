package at.technikumwien.service;

import at.technikumwien.messaging.RabbitMQSender;
import at.technikumwien.repositories.DocumentRepository;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@Disabled
public class DocumentServiceTest {

    private DocumentService documentService;

    @Mock
    private DocumentRepository documentRepository;

    @Mock
    private RabbitMQSender sender;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        documentService = new DocumentService(documentRepository, validator, sender);
    }
/*
    @Test
    public void testSaveDocument_Valid() {

        Document document = new Document();
        document.setTitle("Valid Title");
        document.setAuthor("Valid Author");

        when(documentRepository.save(any(Document.class))).thenReturn(document);

        Document result = documentService.saveDocument(document);

        assertNotNull(result);
        assertEquals("Valid Title", result.getTitle());
        verify(documentRepository, times(1)).save(any(Document.class));
    }

    @Test
    public void testSaveDocument_Invalid() {

        Document document = new Document();
        document.setTitle("");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> documentService.saveDocument(document));

        //assertTrue(exception.getMessage().contains("Title cannot be blank"));
        verify(documentRepository, never()).save(any(Document.class));
    }
*/
}

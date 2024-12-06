package at.technikumwien.controller;

import at.technikumwien.dto.DocumentDTO;
import at.technikumwien.entities.Document;
import at.technikumwien.messenging.Sender;
import at.technikumwien.service.DocumentService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeoutException;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class DocumentController {

    private final DocumentService documentService;

    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    public DocumentController(DocumentService documentService) throws IOException, TimeoutException {
        this.documentService = documentService;
    }

    @GetMapping("/all")
    public List<DocumentDTO> getAllDocuments() {
        LOGGER.info("/all");
        return documentService.getAllDocuments();
    }

    @PostMapping("/document")
    public ResponseEntity<DocumentDTO> createDocument(@RequestBody Document document) {
        LOGGER.info("/document");
        try {
            Document savedDocument = documentService.saveDocument(document);
            DocumentDTO dto = new DocumentDTO();
            dto.setId(savedDocument.getId());
            dto.setTitle(savedDocument.getTitle());
            dto.setAuthor(savedDocument.getAuthor());
            dto.setText(savedDocument.getText());

            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @DeleteMapping("/document/{id}")
    public ResponseEntity<String> deleteDocument(@PathVariable Long id) {
        LOGGER.info("/delete");
        try {
            documentService.deleteDocumentById(Math.toIntExact(id));
            return ResponseEntity.ok("Document deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error deleting document: " + e.getMessage());
        }
    }
}

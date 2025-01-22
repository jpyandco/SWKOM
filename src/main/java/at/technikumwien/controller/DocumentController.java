package at.technikumwien.controller;

import at.technikumwien.dto.DocumentDTO;
import at.technikumwien.service.DocumentService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class DocumentController {

    private static final Logger LOGGER = LogManager.getLogger();
    private final DocumentService documentService;

    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @GetMapping("/all")
    public List<DocumentDTO> getAllDocuments() {
        LOGGER.info("Fetching all documents");
        return documentService.getAllDocuments();
    }

    @PostMapping("/document")
    public ResponseEntity<DocumentDTO> createDocument(
            @RequestParam("title") String title,
            @RequestParam("author") String author,
            @RequestParam("file") MultipartFile file) {
        LOGGER.info("Uploading file to MinIO and saving metadata: title={}, author={}", title, author);

        try {
            DocumentDTO documentDTO = documentService.uploadAndSaveDocument(title, author, file);
            LOGGER.info("File uploaded and metadata saved successfully with ID: {}", documentDTO.getId());
            return ResponseEntity.ok(documentDTO);
        } catch (Exception e) {
            LOGGER.error("Error uploading file: {}", e.getMessage(), e);
            return ResponseEntity.status(500).body(null);
        }
    }

    @DeleteMapping("/document/{id}")
    public ResponseEntity<String> deleteDocument(@PathVariable Long id) {
        LOGGER.info("Received request to delete document with ID: {}", id);
        try {
            documentService.deleteDocumentById(id.intValue());
            LOGGER.info("Document deleted successfully with ID: {}", id);
            return ResponseEntity.ok("Document deleted successfully");
        } catch (Exception e) {
            LOGGER.error("Error deleting document: {}", e.getMessage(), e);
            return ResponseEntity.status(500).body("Error deleting document: " + e.getMessage());
        }
    }
}

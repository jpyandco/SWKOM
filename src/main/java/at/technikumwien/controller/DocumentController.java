package at.technikumwien.controller;

import at.technikumwien.dto.DocumentDTO;
import at.technikumwien.entities.Document;
import at.technikumwien.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class DocumentController {

    private final DocumentService documentService;

    @Autowired
    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @GetMapping("/all")
    public List<DocumentDTO> getAllDocuments() {
        return documentService.getAllDocuments();
    }

    @PostMapping("/document")
    public ResponseEntity<DocumentDTO> createDocument(@RequestBody Document document) {
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
        try {
            documentService.deleteDocumentById(Math.toIntExact(id));
            return ResponseEntity.ok("Document deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error deleting document: " + e.getMessage());
        }
    }
}

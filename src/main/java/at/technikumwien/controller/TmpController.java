package at.technikumwien.controller;

import at.technikumwien.dto.DocumentDTO;
import at.technikumwien.service.DocumentService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping
@CrossOrigin
public class TmpController {
    private final DocumentService documentService;
    public TmpController(DocumentService documentService){
        this.documentService = documentService;
    }
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("api/all")
    public List<DocumentDTO> getAllItems() {
        List<DocumentDTO> dto = documentService.getAllDocs();
        return dto;
    }

    @GetMapping("/api/message")
    public String getMessage() {
        return "Hello from the Backend!";
    }

    @PostMapping("/api/document")
    public String createItem(@PathVariable String title, @PathVariable String author, @PathVariable String text) {
        DocumentDTO dto = new DocumentDTO();
        dto.setAuthor(author);
        dto.setText(text);
        dto.setTitle(title);

        documentService.saveDocument(dto);

        return "Document saved";
    }

    @PutMapping
    public String updateItem() {
        return "PUT";
    }

    @DeleteMapping
    public String deleteItem() {
        return "DELETE";
    }
}

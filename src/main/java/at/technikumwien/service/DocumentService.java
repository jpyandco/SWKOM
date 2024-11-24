package at.technikumwien.service;

import at.technikumwien.dto.DocumentDTO;
import at.technikumwien.entities.Document;
import at.technikumwien.repositories.DocumentRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import java.util.Set;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DocumentService {

    private final DocumentRepository repository;
    private final Validator validator;

    public DocumentService(DocumentRepository repository, Validator validator) {
        this.repository = repository;
        this.validator = validator;
    }

    public Document saveDocument(Document document) {
        Set<ConstraintViolation<Document>> violations = validator.validate(document);
        if (!violations.isEmpty()) {
            String errorMessage = violations.stream()
                    .map(ConstraintViolation::getMessage)
                    .findFirst()
                    .orElse("Validation failed");
            throw new IllegalArgumentException("Validation failed: " + errorMessage);
        }
        return repository.save(document);
    }


    public List<DocumentDTO> getAllDocuments() {
        return repository.findAll().stream()
                .map(document -> {
                    DocumentDTO dto = new DocumentDTO();
                    dto.setId(document.getId());
                    dto.setTitle(document.getTitle());
                    dto.setAuthor(document.getAuthor());
                    dto.setText(document.getText());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public void deleteDocumentById(int id) {
        if (!repository.existsById((long) id)) {
            throw new IllegalArgumentException("Document with ID " + id + " does not exist.");
        }
        repository.deleteById((long) id);
    }
}

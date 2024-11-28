package at.technikumwien.service;

import at.technikumwien.dto.DocumentDTO;
import at.technikumwien.entities.Document;
import at.technikumwien.messenging.RabbitMQSender;
import at.technikumwien.messenging.Sender;
import at.technikumwien.repositories.DocumentRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import jakarta.validation.constraints.Null;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DocumentService {
    private static final Logger LOGGER = LogManager.getLogger();
    private final DocumentRepository repository;
    private final Validator validator;

    private final RabbitMQSender sender;

    public DocumentService(DocumentRepository repository, Validator validator, RabbitMQSender sender) {
        this.repository = repository;
        this.validator = validator;
        this.sender = sender;
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
        try {
            sender.sendMessage("New Document uploaded!");
        } catch (Exception e){
            System.out.println(e);
            LOGGER.error(e);
        }
        return repository.save(document);
    }


    public List<DocumentDTO> getAllDocuments() {
        try {
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
        } catch (Exception e) {
            LOGGER.error(e);
            List<DocumentDTO> list = new ArrayList<>();
            return list;
        }
    }

    public void deleteDocumentById(int id) {
        if (!repository.existsById((long) id)) {
            throw new IllegalArgumentException("Document with ID " + id + " does not exist.");
        }
        try {
            repository.deleteById((long) id);
        } catch (Exception e){
            LOGGER.error(e);
        }
    }
}

package at.technikumwien.service;

import at.technikumwien.dto.DocumentDTO;
import at.technikumwien.entities.Document;
import at.technikumwien.messaging.RabbitMQSender;
import at.technikumwien.repositories.DocumentRepository;
import jakarta.validation.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DocumentService {

    private static final Logger LOGGER = LogManager.getLogger();
    private final DocumentRepository repository;
    private final Validator validator;
    private final RabbitMQSender rabbitMQSender;

    public DocumentService(DocumentRepository repository, Validator validator, RabbitMQSender rabbitMQSender) {
        this.repository = repository;
        this.validator = validator;
        this.rabbitMQSender = rabbitMQSender;
    }

    public DocumentDTO processAndSaveDocument(String title, String author, MultipartFile file) {
        LOGGER.info("Processing document: title={}, author={}, fileSize={} bytes", title, author, file.getSize());

        if (file.getSize() > 10 * 1024 * 1024) {
            LOGGER.warn("File size exceeds the maximum limit of 10MB: size={} bytes", file.getSize());
            throw new IllegalArgumentException("File size exceeds the maximum limit of 10MB");
        }

        Document document = new Document();
        document.setTitle(title);
        document.setAuthor(author);

        try {
            byte[] data = file.getBytes();
            document.setData(data);
            LOGGER.info("File content successfully converted to bytes");
        } catch (Exception e) {
            LOGGER.error("Error reading file bytes: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to read file bytes", e);
        }

        Document savedDocument = repository.save(document);
        LOGGER.info("Document saved successfully in database with ID: {}", savedDocument.getId());

        return convertToDTO(savedDocument);
    }

    public List<DocumentDTO> getAllDocuments() {
        LOGGER.info("Fetching all documents from database");
        return repository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public void deleteDocumentById(int id) {
        LOGGER.info("Deleting document with ID: {}", id);
        repository.deleteById((long) id);
        LOGGER.info("Document deleted successfully with ID: {}", id);
    }

    private DocumentDTO convertToDTO(Document document) {
        LOGGER.debug("Converting Document entity to DTO: ID={}", document.getId());
        DocumentDTO dto = new DocumentDTO();
        dto.setId(document.getId());
        dto.setTitle(document.getTitle());
        dto.setAuthor(document.getAuthor());
        dto.setData(document.getData());
        return dto;
    }
}

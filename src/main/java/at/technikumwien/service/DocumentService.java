package at.technikumwien.service;

import at.technikumwien.dto.DocumentDTO;
import at.technikumwien.entities.Document;
import at.technikumwien.messaging.RabbitMQSender;
import at.technikumwien.repositories.DocumentRepository;
import jakarta.validation.Validator;
import java.io.InputStream;
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
    private final MinioService minioService;

    public DocumentService(DocumentRepository repository, Validator validator, RabbitMQSender rabbitMQSender, MinioService minioService) {
        this.repository = repository;
        this.minioService = minioService;
    }

    public DocumentDTO uploadAndSaveDocument(String title, String author, MultipartFile file) {
        LOGGER.info("Processing document for upload and database save: title={}, author={}", title, author);

        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty. Cannot process upload.");
        }

        String fileName = file.getOriginalFilename();
        try {
            LOGGER.info("Uploading file '{}' to MinIO.", fileName);
            minioService.uploadFile(fileName, file.getInputStream(), file.getContentType());
        } catch (Exception e) {
            LOGGER.error("Failed to upload file '{}' to MinIO.", fileName, e);
            throw new RuntimeException("Failed to upload file to MinIO", e);
        }

        Document document = new Document();
        document.setTitle(title);
        document.setAuthor(author);
        document.setMinioKey(fileName);

        Document savedDocument = repository.save(document);
        LOGGER.info("Document metadata saved successfully in the database with ID: {}", savedDocument.getId());

        return convertToDTO(savedDocument);
    }

    public void updateDocumentText(int documentId, String text) {
        LOGGER.info("Updating document with ID: {} with OCR text.", documentId);

        Document document = repository.findById((long) documentId).orElseThrow(() ->
                new IllegalArgumentException("Document with ID " + documentId + " not found")
        );

        document.setText(text);
        repository.save(document);

        LOGGER.info("Updated document text for document ID: {}", documentId);
    }

    public InputStream downloadDocumentFromMinIO(String minioKey) {
        LOGGER.info("Fetching file '{}' from MinIO.", minioKey);
        return minioService.downloadFile(minioKey);
    }

    public List<DocumentDTO> getAllDocuments() {
        LOGGER.info("Fetching all documents from the database.");
        return repository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public DocumentDTO getDocumentById(int id) {
        return repository.findById((long) id)
                .map(this::convertToDTO)
                .orElse(null);
    }

    public void deleteDocumentById(int id) {
        LOGGER.info("Attempting to delete document with ID: {}", id);

        Document document = repository.findById((long) id).orElse(null);
        if (document == null) {
            LOGGER.warn("Document with ID: {} does not exist. Skipping delete operation.", id);
            throw new IllegalArgumentException("Document with ID: " + id + " does not exist.");
        }

        try {
            LOGGER.info("Deleting file '{}' from MinIO.", document.getMinioKey());
            minioService.deleteFile(document.getMinioKey());
        } catch (Exception e) {
            LOGGER.error("Failed to delete file '{}' from MinIO.", document.getMinioKey(), e);
            throw new RuntimeException("Failed to delete file from MinIO", e);
        }

        repository.deleteById((long) id);
        LOGGER.info("Document deleted successfully with ID: {}", id);
    }

    private DocumentDTO convertToDTO(Document document) {
        LOGGER.debug("Converting Document entity to DTO: ID={}", document.getId());
        DocumentDTO dto = new DocumentDTO();
        dto.setId(document.getId());
        dto.setTitle(document.getTitle());
        dto.setAuthor(document.getAuthor());
        dto.setMinioKey(document.getMinioKey());
        return dto;
    }
}

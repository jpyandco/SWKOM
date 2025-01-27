package at.technikumwien.messaging;

import at.technikumwien.dto.DocumentDTO;
import at.technikumwien.service.DocumentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQConsumer {

    private final DocumentService documentService;
    private final ObjectMapper objectMapper;

    public RabbitMQConsumer(DocumentService documentService, ObjectMapper objectMapper) {
        this.documentService = documentService;
        this.objectMapper = objectMapper;
    }

    @RabbitListener(queues = "RESULT_QUEUE")
    public void consumeResultQueue(String message) {
        System.out.println("Received OCR result from RESULT_QUEUE");

        try {
            DocumentDTO documentDTO = objectMapper.readValue(message, DocumentDTO.class);

            if (documentDTO.getText() != null) {
                documentService.updateDocumentText(documentDTO.getId(), documentDTO.getText());
                System.out.println("Document updated with OCR text: " + documentDTO.getTitle());
            } else {
                System.err.println("OCR result received but text is null.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Failed to process OCR result: " + e.getMessage());
        }
    }
}

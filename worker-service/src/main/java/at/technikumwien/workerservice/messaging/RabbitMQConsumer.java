package at.technikumwien.workerservice.messaging;

import at.technikumwien.workerservice.entities.DocumentElasticsearch;
import at.technikumwien.workerservice.service.ElasticsearchService;
import at.technikumwien.workerservice.service.OCRService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQConsumer {

    private final OCRService ocrService;
    private final ElasticsearchService elasticsearchService;
    private final ObjectMapper objectMapper;

    public RabbitMQConsumer(OCRService ocrService, ElasticsearchService elasticsearchService, ObjectMapper objectMapper) {
        this.ocrService = ocrService;
        this.elasticsearchService = elasticsearchService;
        this.objectMapper = objectMapper;
    }

    @RabbitListener(queues = "shared-queue")
    public void consumeMessage(String message) {
        System.out.println("Received message from MainApp");

        try {
            DocumentElasticsearch document = objectMapper.readValue(message, DocumentElasticsearch.class);
            String extractedText = ocrService.performOCR(document.getData());
            document.setText(extractedText);
            elasticsearchService.save(document);
            System.out.println("Processed and saved document: " + document.getTitle());
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Failed to process message: " + e.getMessage());
        }
    }
}

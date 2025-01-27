package at.technikumwien.workerservice.messaging;

import at.technikumwien.workerservice.entities.WorkerDocument;
import at.technikumwien.workerservice.service.MinioService;
import at.technikumwien.workerservice.service.OCRService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQConsumer {

    private final OCRService ocrService;
    private final RabbitMQSender rabbitMQSender;
    private final ObjectMapper objectMapper;
    private final MinioService minioService;

    public RabbitMQConsumer(OCRService ocrService, RabbitMQSender rabbitMQSender, ObjectMapper objectMapper, MinioService minioService) {
        this.ocrService = ocrService;
        this.rabbitMQSender = rabbitMQSender;
        this.objectMapper = objectMapper;
        this.minioService = minioService;
    }

    @RabbitListener(queues = "OCR_QUEUE")
    public void consumeOCRQueue(String message) {
        System.out.println("Received OCR job from OCR_QUEUE");

        try {
            WorkerDocument document = objectMapper.readValue(message, WorkerDocument.class);
            byte[] documentData = minioService.downloadFile(document.getMinioKey());

            String extractedText = ocrService.performOCR(documentData);
            document.setText(extractedText);

            rabbitMQSender.sendToResultQueue(document);
            System.out.println("Processed OCR job and sent result to RESULT_QUEUE: " + document.getTitle());
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Failed to process OCR job: " + e.getMessage());
        }
    }
}

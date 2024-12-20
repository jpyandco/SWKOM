package at.technikumwien.workerservice.messaging;

import at.technikumwien.workerservice.service.OCRService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQConsumer {

    private final OCRService ocrService;

    public RabbitMQConsumer(OCRService ocrService) {
        this.ocrService = ocrService;
    }

    @RabbitListener(queues = "document-queue")
    public void consumeMessage(String filePath) {
        try {
            String text = ocrService.performOCR(filePath);
            System.out.println("Extracted text from file: " + text);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

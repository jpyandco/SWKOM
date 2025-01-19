package at.technikumwien.workerservice.messaging;

import at.technikumwien.workerservice.service.OCRService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RabbitMQConsumer {

    private final OCRService ocrService;

    public RabbitMQConsumer(OCRService ocrService) {
        this.ocrService = ocrService;
    }

    @RabbitListener(queues = "shared-queue")
    public void consumeMessage(byte[] files) {
        System.out.println("Received files from MainApp");
        try {
            List<String> text = ocrService.extractTextFromPdf(files);
            System.out.println("Extracted text from file: " + text);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

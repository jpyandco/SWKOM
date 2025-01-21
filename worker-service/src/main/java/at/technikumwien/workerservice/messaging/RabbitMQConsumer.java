package at.technikumwien.workerservice.messaging;

import at.technikumwien.workerservice.entities.Document;
import at.technikumwien.workerservice.entities.DocumentElasticsearch;
import at.technikumwien.workerservice.service.ElasticsearchService;
import at.technikumwien.workerservice.service.OCRService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RabbitMQConsumer {

    private final OCRService ocrService;
    private final ElasticsearchService elasticsearchService;

    public RabbitMQConsumer(OCRService ocrService, ElasticsearchService elasticsearchService) {
        this.ocrService = ocrService;
        this.elasticsearchService = elasticsearchService;
    }

    @RabbitListener(queues = "shared-queue")
    public void consumeMessage(byte[] document) {
        System.out.println("Received files from MainApp");
        try {
            //DocumentElasticsearch eDocument = new DocumentElasticsearch();
            //eDocument.setAuthor(document.getAuthor());
            // eDocument.setTitle(document.getTitle());

            String text = ocrService.performOCR(document);
            //eDocument.setText(text);
            System.out.println("Extracted text from file: " + text);
            elasticsearchService.save(text);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

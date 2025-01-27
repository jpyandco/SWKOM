package at.technikumwien.workerservice.messaging;

import at.technikumwien.workerservice.entities.WorkerDocument;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQSender {

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    public RabbitMQSender(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    public void sendToResultQueue(WorkerDocument document) {
        try {
            String message = objectMapper.writeValueAsString(document);
            rabbitTemplate.convertAndSend("RESULT_QUEUE", message);
            System.out.println("Sent OCR result to RESULT_QUEUE for document: " + document.getTitle());
        } catch (Exception e) {
            System.err.println("Error sending OCR result to RESULT_QUEUE: " + e.getMessage());
        }
    }
}

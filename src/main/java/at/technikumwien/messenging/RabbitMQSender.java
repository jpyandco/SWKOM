package at.technikumwien.messenging;

import at.technikumwien.entities.Document;
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

    public void sendMessage(Document document) {
        try {
            // Convert the document to JSON
            String message = objectMapper.writeValueAsString(document);
            rabbitTemplate.convertAndSend("shared-queue", message);
            System.out.println("Sent document to RabbitMQ");
        } catch (Exception e) {
            System.err.println("Error while sending message: " + e.getMessage());
        }
    }
}

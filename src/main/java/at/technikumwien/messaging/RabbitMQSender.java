package at.technikumwien.messaging;

import at.technikumwien.dto.DocumentDTO;
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

    public void sendToOCRQueue(DocumentDTO documentDTO) {
        try {
            String message = objectMapper.writeValueAsString(documentDTO);
            rabbitTemplate.convertAndSend("OCR_QUEUE", message);
            System.out.println("Sent OCR job to OCR_QUEUE for document: " + documentDTO.getTitle());
        } catch (Exception e) {
            System.err.println("Error sending OCR job to OCR_QUEUE: " + e.getMessage());
        }
    }
}

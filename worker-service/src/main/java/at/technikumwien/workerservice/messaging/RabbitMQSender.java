package at.technikumwien.workerservice.messaging;

import at.technikumwien.workerservice.service.OCRService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQSender {

    //private final OCRService ocrService;

    private final RabbitTemplate rabbitTemplate;

    public RabbitMQSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(byte[] files) {
        rabbitTemplate.convertAndSend("search-queue", files);
        System.out.println("Sent new document to MainApp!");
    }
}
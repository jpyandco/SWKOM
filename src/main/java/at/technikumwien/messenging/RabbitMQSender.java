package at.technikumwien.messenging;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQSender {

    private final RabbitTemplate rabbitTemplate;

    public RabbitMQSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(byte[] files) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.QUEUE_NAME, files);
        System.out.println("Sent new document!");
    }

    public void sendSearch(String message){
        rabbitTemplate.convertAndSend("search-queue", message);
        System.out.println("Sent new search request!");
    }
}

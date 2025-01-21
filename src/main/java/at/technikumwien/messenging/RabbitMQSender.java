package at.technikumwien.messenging;

import at.technikumwien.entities.Document;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQSender {

    private final RabbitTemplate rabbitTemplate;

    public RabbitMQSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(Document document) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.QUEUE_NAME, document.getData());
        System.out.println("Sent new document!");
    }

    public void sendSearch(String message){
        rabbitTemplate.convertAndSend("search-queue", message);
        System.out.println("Sent new search request!");
    }
}

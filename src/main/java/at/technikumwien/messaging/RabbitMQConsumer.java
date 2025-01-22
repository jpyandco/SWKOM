package at.technikumwien.messaging;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQConsumer {

    @RabbitListener(queues = "search-queue")
    public void receiveSearch(byte[] files) {
        System.out.println("Received message from worker-service");
    }
}

package at.technikumwien.messenging;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class Receiver {

    @RabbitListener(queues = "search-queue")
    public void receiveSearch(byte[] files) {
        System.out.println("Received message from worker-service");
    }
}

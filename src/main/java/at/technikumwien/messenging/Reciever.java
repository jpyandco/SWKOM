package at.technikumwien.messenging;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service
public class  Reciever {

    //private final static String QUEUE_NAME = "test";

    @RabbitListener(queues = "search-queue")
    public void receiveMessage(byte[] files) {
        System.out.println("Received message from worker-service");
    }
}

package at.technikumwien.messenging;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
@Component
@EnableRabbit
public class Sender {
    private final static String QUEUE_NAME = "test";
    private Connection connection;
    private Channel channel;
    public Sender(){
        try {
            ConnectionFactory factory = new ConnectionFactory();
            this.connection = factory.newConnection();
            this.channel = connection.createChannel();
            channel.queueDeclare(QUEUE_NAME, true, false, false, null);
        } catch (Exception e){
            System.out.println(e);
        }

    }
    public void sendMessage(String message) throws Exception {
        channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
        System.out.println("Sent: " + message);
    }

    public void close() throws Exception {
        channel.close();
        connection.close();
    }

}

package at.technikumwien.messenging;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String QUEUE_NAME = "document-queue"; //myQueue

    public static final String SEARCH_QUEUE = "search-queue"; //myQueue
    public static final String EXCHANGE_NAME = "sharedExchange";
    public static final String ROUTING_KEY = "shared.routing.key";

    @Bean
    public Queue myQueue() {
        return new Queue(QUEUE_NAME, true); // durable = true
    }

    @Bean
    public Queue searchQueue() {
        return new Queue(SEARCH_QUEUE, true); // durable = true
    }
}

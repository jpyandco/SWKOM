package at.technikumwien.messenging;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String QUEUE_NAME = "shared-queue";

    @Bean
    public Queue sharedQueue() {
        return new Queue(QUEUE_NAME, true);
    }
    @Bean
    public Queue searchQueue() {
        return new Queue("search-queue", true);
    }
}

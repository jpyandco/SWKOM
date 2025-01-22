package at.technikumwien.messaging;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public Queue sharedQueue() {
        return new Queue("shared-queue", true);
    }

    @Bean
    public Queue searchQueue() {
        return new Queue("search-queue", true);
    }
}

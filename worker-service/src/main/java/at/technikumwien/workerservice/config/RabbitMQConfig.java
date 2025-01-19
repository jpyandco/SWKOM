package at.technikumwien.workerservice.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public Queue queue() {
        return new Queue("document-queue", true);
    }

    @Bean
    public Queue searchQueue() {
        return new Queue("search-queue", true);
    }
}

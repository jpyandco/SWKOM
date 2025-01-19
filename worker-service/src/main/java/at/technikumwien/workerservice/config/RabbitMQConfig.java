package at.technikumwien.workerservice.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public Queue sharedQueue() {
        return new Queue("shared-queue", true);
    }
}

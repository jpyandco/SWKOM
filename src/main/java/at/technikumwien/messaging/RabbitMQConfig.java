package at.technikumwien.messaging;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public Queue ocrQueue() {
        return new Queue("OCR_QUEUE", true);
    }

    @Bean
    public Queue resultQueue() {
        return new Queue("RESULT_QUEUE", true);
    }
}

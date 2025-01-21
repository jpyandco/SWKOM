package at.technikumwien.workerservice.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticsearchConfig {

    @Bean
    public RestClient lowLevelClient() {
        return RestClient.builder(
                new HttpHost("elasticsearch", 9200, "http")
        ).build();
    }
}

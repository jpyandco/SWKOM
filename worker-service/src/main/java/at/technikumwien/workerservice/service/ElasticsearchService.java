package at.technikumwien.workerservice.service;

import at.technikumwien.workerservice.entities.DocumentElasticsearch;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.springframework.stereotype.Service;

@Service
public class ElasticsearchService {

    private final RestClient restClient;
    private final ObjectMapper objectMapper;

    public ElasticsearchService(RestClient restClient, ObjectMapper objectMapper) {
        this.restClient = restClient;
        this.objectMapper = objectMapper;
    }

    public void save(DocumentElasticsearch document) throws Exception {
        String json = objectMapper.writeValueAsString(document);

        Request request = new Request("POST", "/documents/_doc/");
        request.setEntity(new StringEntity(json, ContentType.APPLICATION_JSON));
        Response response = restClient.performRequest(request);

        System.out.println("Saved document to Elasticsearch: " + EntityUtils.toString(response.getEntity()));
    }
}

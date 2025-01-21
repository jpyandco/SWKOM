package at.technikumwien.workerservice.service;

import at.technikumwien.workerservice.entities.DocumentElasticsearch;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ElasticsearchService {

    private final RestClient restClient;

    @Autowired
    public ElasticsearchService(RestClient restClient) {
        this.restClient = restClient;
    }

    public void search() throws IOException {
        Request request = new Request("GET", "/_search");
        Response response = restClient.performRequest(request);

        System.out.println(EntityUtils.toString(response.getEntity()));
    }

    public void save(String text) throws IOException { //DocumentElasticsearch document

        Request request = new Request("POST", "/documents/_doc/");
        String jsonString = "{ \"content\": \"" + text + "\" }";
        request.setEntity(new StringEntity(jsonString, ContentType.APPLICATION_JSON));
        Response response = restClient.performRequest(request);

        System.out.println(EntityUtils.toString(response.getEntity()));
    }
}

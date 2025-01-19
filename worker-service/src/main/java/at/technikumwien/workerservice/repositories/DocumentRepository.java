package at.technikumwien.workerservice.repositories;

import at.technikumwien.workerservice.entities.Document;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface DocumentRepository extends ElasticsearchRepository<Document, String> {

}

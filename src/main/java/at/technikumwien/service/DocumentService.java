package at.technikumwien.service;

import at.technikumwien.dto.DocumentDTO;
import at.technikumwien.entities.Document;
import at.technikumwien.mapper.DocumentMapper;
import at.technikumwien.repositories.DocumentRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DocumentService {
    private final DocumentRepository documentRepository;
    private final DocumentMapper mapper = Mappers.getMapper(DocumentMapper.class);

    public DocumentService(DocumentRepository documentRepository){
        this.documentRepository = documentRepository;
    }

    public void saveDocument(DocumentDTO dto){
        Document doc = mapper.toEntity(dto);
        documentRepository.save(doc);
    }

    public List<DocumentDTO> getAllDocs(){
        List<Document> list = documentRepository.findAll();
        List<DocumentDTO> dtoList = new ArrayList<>() {
        };

        for (Document document : list) {
            DocumentDTO doc = mapper.toDto(document);
            dtoList.add(doc);
        }

        return dtoList;
    }

}

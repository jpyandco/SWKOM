package at.technikumwien.mapper;

import at.technikumwien.dto.DocumentDTO;
import at.technikumwien.entities.Document;
import org.mapstruct.Mapper;

@Mapper
public interface DocumentMapper {
    Document toEntity(DocumentDTO dto);
    DocumentDTO toDto(Document entity);
}

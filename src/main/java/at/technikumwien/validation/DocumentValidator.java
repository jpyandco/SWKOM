package at.technikumwien.validation;

import at.technikumwien.dto.DocumentDTO;

public class DocumentValidator {

    public static boolean isValid(DocumentDTO dto) {
        return dto.getTitle() != null && !dto.getTitle().isBlank()
                && dto.getAuthor() != null && !dto.getAuthor().isBlank()
                && dto.getData() != null;
    }
}

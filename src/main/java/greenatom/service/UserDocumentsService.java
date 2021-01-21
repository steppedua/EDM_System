package greenatom.service;

import greenatom.model.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface UserDocumentsService {

    void uploadDocument (Document document);

    /*Optional<Document> addDocument(
            MultipartFile file,
            User user,
            DocumentType documentType,
            DocumentGroups documentGroups,
            List<Attributes> attributesList
    ) throws IOException;*/

    List<Document> getUserDocumentsList(User user);

    // Optional<Document> getUserDocumentById(Long id);

    boolean deleteUserDocumentById(Long id);
}
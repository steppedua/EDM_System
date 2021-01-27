package greenatom.service;

import greenatom.model.Document;
import greenatom.model.User;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface DocumentsService {

    Optional<Document> uploadDocument(Document document) throws IOException;

    Optional<Document> getUserDocumentById(Long id, User userId);
    /*Optional<Document> addDocument(
            MultipartFile file,
            User user,
            DocumentType documentType,
            DocumentGroups documentGroups,
            List<Attributes> attributesList
    ) throws IOException;*/

    List<Document> getUserDocumentsList(User user);

    // Optional<Document> getUserDocumentById(Long id);

    boolean deleteDocumentByIdAndOwnerId(Long id, User user);
}
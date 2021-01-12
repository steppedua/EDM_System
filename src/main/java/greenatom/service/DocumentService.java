package greenatom.service;

import greenatom.model.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface DocumentService {
    Document createDocument(
            MultipartFile file,
            UserDocuments userDocuments,
            DocumentType documentType,
            DocumentGroups documentGroups,
            List<Attributes> attributesList) throws IOException;

    Optional<Document> getDocumentById(Long id, User user);

    boolean removeDocumentById(Long id, User user);

    List<Document> getDocumentList();

}

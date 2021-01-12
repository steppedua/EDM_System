package greenatom.service;

import greenatom.model.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface UserDocumentsService {
    Optional<UserDocuments> createUserFolder(UserDocuments userDocuments);

    Optional<UserDocuments> addDocument(
            MultipartFile file,
            UserDocuments userDocuments,
            User user,
            DocumentType documentType,
            DocumentGroups documentGroups,
            List<Attributes> attributesList
    ) throws IOException;

    List<UserDocuments> getUserDocumentsList(UserDocuments userDocuments);

    boolean deleteUserFolder(Long id, UserDocuments userDocuments);

    Optional<UserDocuments> getUserDocumentById(Long id, UserDocuments userDocuments);

    boolean deleteUserDocumentById(Long id, UserDocuments userDocuments);
}
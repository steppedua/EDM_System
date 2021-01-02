package greenatom.service;

import greenatom.model.Document;
import greenatom.model.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface DocumentService {
    Optional<Document> createDocument(MultipartFile file, User user) throws IOException;

    Optional<Document> getDocumentById(Long id);

    boolean removeDocumentById(Long id);

    List<Document> getDocumentList();

}

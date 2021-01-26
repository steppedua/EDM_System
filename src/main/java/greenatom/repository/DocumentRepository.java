package greenatom.repository;

import greenatom.model.Document;
import greenatom.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {

    List<Document> findAllDocumentsByOwnerId(Long id);

    Optional<Document> findByName(String name);

    Optional<Document> findDocumentByIdAndOwner(Long id, User user);

    void deleteDocumentByIdAndOwnerId(Long id, User user);
}
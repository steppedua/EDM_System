package greenatom.repository;

import greenatom.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {

    List<Document> findAllByOwnerId(Long id);

    Optional<Document> findByName(String name);

    //Optional<Document> findByDocumentsIdAndOwner(Long id, User user);

    //void deleteByDocumentsIdAndOwner(Long id, User user);
}
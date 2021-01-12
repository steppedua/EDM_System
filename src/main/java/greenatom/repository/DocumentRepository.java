package greenatom.repository;

import greenatom.model.Document;
import greenatom.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {
    Optional<Document> findByName(String name);

    Optional<Document> findByIdAndOwner(Long id, User user);
}

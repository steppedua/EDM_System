package greenatom.repository;

import greenatom.model.User;
import greenatom.model.UserDocuments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserDocumentsRepository extends JpaRepository<UserDocuments, Long> {
    List<UserDocuments> findAllByOwnerId(Long id);

    Optional<UserDocuments> findByDocumentsIdAndOwner(Long id, User user);
}
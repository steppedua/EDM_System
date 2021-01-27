package greenatom.repository;

import greenatom.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {

    @Transactional
    @Modifying
    @Query("SELECT u FROM Document u WHERE u.owner.id = :id")
    List<Document> getDocumentsByOwnerId(@Param("id") Long id);

    Optional<Document> findByName(String name);

    @Transactional
    @Modifying
    @Query("SELECT u FROM Document u WHERE u.id= :id AND u.owner.id = :ownerId")
    Optional<Document> findDocumentByIdAndOwnerId(@Param("id") Long id, @Param("ownerId") Long ownerId);

    @Transactional
    @Modifying
    @Query("DELETE FROM Document u WHERE u.id= :id AND u.owner.id = :ownerId")
    void deleteDocumentByIdAndOwnerId(@Param("id") Long id, @Param("ownerId") Long ownerId);
}
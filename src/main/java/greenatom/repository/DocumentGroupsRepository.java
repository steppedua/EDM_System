package greenatom.repository;

import greenatom.model.DocumentGroups;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentGroupsRepository extends JpaRepository<DocumentGroups, Long> {
}

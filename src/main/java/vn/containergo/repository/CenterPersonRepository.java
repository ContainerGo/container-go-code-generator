package vn.containergo.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import vn.containergo.domain.CenterPerson;

/**
 * Spring Data MongoDB repository for the CenterPerson entity.
 */
@Repository
public interface CenterPersonRepository extends MongoRepository<CenterPerson, Long> {
    @Query("{}")
    Page<CenterPerson> findAllWithEagerRelationships(Pageable pageable);

    @Query("{}")
    List<CenterPerson> findAllWithEagerRelationships();

    @Query("{'id': ?0}")
    Optional<CenterPerson> findOneWithEagerRelationships(Long id);
}

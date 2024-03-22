package vn.containergo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import vn.containergo.domain.Ward;

/**
 * Spring Data MongoDB repository for the Ward entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WardRepository extends MongoRepository<Ward, Long> {}

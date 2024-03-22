package vn.containergo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import vn.containergo.domain.District;

/**
 * Spring Data MongoDB repository for the District entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DistrictRepository extends MongoRepository<District, Long> {}

package vn.containergo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import vn.containergo.domain.TruckType;

/**
 * Spring Data MongoDB repository for the TruckType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TruckTypeRepository extends MongoRepository<TruckType, Long> {}

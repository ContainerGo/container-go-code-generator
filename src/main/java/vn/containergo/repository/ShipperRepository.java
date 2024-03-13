package vn.containergo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import vn.containergo.domain.Shipper;

/**
 * Spring Data MongoDB repository for the Shipper entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ShipperRepository extends MongoRepository<Shipper, Long> {}

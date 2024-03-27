package vn.containergo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import vn.containergo.domain.ShipmentHistory;

/**
 * Spring Data MongoDB repository for the ShipmentHistory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ShipmentHistoryRepository extends MongoRepository<ShipmentHistory, Long> {}

package vn.containergo.repository;

import java.util.UUID;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import vn.containergo.domain.Truck;

/**
 * Spring Data MongoDB repository for the Truck entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TruckRepository extends MongoRepository<Truck, UUID> {}

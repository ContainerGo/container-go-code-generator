package vn.containergo.repository;

import java.util.UUID;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import vn.containergo.domain.Carrier;

/**
 * Spring Data MongoDB repository for the Carrier entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CarrierRepository extends MongoRepository<Carrier, UUID> {}

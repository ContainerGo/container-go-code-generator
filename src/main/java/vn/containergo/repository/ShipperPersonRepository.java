package vn.containergo.repository;

import java.util.UUID;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import vn.containergo.domain.ShipperPerson;

/**
 * Spring Data MongoDB repository for the ShipperPerson entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ShipperPersonRepository extends MongoRepository<ShipperPerson, UUID> {}

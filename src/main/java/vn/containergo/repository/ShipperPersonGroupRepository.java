package vn.containergo.repository;

import java.util.UUID;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import vn.containergo.domain.ShipperPersonGroup;

/**
 * Spring Data MongoDB repository for the ShipperPersonGroup entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ShipperPersonGroupRepository extends MongoRepository<ShipperPersonGroup, UUID> {}

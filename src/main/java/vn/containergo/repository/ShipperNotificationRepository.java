package vn.containergo.repository;

import java.util.UUID;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import vn.containergo.domain.ShipperNotification;

/**
 * Spring Data MongoDB repository for the ShipperNotification entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ShipperNotificationRepository extends MongoRepository<ShipperNotification, UUID> {}

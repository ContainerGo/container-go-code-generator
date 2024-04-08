package vn.containergo.repository;

import java.util.UUID;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import vn.containergo.domain.ShipmentPlan;

/**
 * Spring Data MongoDB repository for the ShipmentPlan entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ShipmentPlanRepository extends MongoRepository<ShipmentPlan, UUID> {}

package vn.containergo.repository;

import java.util.UUID;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import vn.containergo.domain.CarrierPersonGroup;

/**
 * Spring Data MongoDB repository for the CarrierPersonGroup entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CarrierPersonGroupRepository extends MongoRepository<CarrierPersonGroup, UUID> {}

package vn.containergo.repository;

import java.util.UUID;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import vn.containergo.domain.ShipperAccount;

/**
 * Spring Data MongoDB repository for the ShipperAccount entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ShipperAccountRepository extends MongoRepository<ShipperAccount, UUID> {}

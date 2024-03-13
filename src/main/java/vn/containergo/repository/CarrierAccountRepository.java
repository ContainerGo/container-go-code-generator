package vn.containergo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import vn.containergo.domain.CarrierAccount;

/**
 * Spring Data MongoDB repository for the CarrierAccount entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CarrierAccountRepository extends MongoRepository<CarrierAccount, Long> {}

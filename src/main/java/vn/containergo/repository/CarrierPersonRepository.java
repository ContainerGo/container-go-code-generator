package vn.containergo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import vn.containergo.domain.CarrierPerson;

/**
 * Spring Data MongoDB repository for the CarrierPerson entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CarrierPersonRepository extends MongoRepository<CarrierPerson, Long> {}

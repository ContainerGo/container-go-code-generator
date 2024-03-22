package vn.containergo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import vn.containergo.domain.Provice;

/**
 * Spring Data MongoDB repository for the Provice entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProviceRepository extends MongoRepository<Provice, Long> {}

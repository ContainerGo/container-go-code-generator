package vn.containergo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import vn.containergo.domain.Authority;

/**
 * Spring Data MongoDB repository for the {@link Authority} entity.
 */
public interface AuthorityRepository extends MongoRepository<Authority, String> {}

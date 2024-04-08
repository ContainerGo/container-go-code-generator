package vn.containergo.repository;

import java.util.UUID;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import vn.containergo.domain.Container;

/**
 * Spring Data MongoDB repository for the Container entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ContainerRepository extends MongoRepository<Container, UUID> {}

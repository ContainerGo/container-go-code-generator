package vn.containergo.repository;

import java.util.UUID;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import vn.containergo.domain.ContainerOwner;

/**
 * Spring Data MongoDB repository for the ContainerOwner entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ContainerOwnerRepository extends MongoRepository<ContainerOwner, UUID> {}

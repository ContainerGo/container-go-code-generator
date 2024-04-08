package vn.containergo.repository;

import java.util.UUID;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import vn.containergo.domain.ContainerType;

/**
 * Spring Data MongoDB repository for the ContainerType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ContainerTypeRepository extends MongoRepository<ContainerType, UUID> {}

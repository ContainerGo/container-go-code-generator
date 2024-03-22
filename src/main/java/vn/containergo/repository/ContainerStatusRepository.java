package vn.containergo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import vn.containergo.domain.ContainerStatus;

/**
 * Spring Data MongoDB repository for the ContainerStatus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ContainerStatusRepository extends MongoRepository<ContainerStatus, Long> {}

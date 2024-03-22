package vn.containergo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import vn.containergo.domain.ContainerStatusGroup;

/**
 * Spring Data MongoDB repository for the ContainerStatusGroup entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ContainerStatusGroupRepository extends MongoRepository<ContainerStatusGroup, Long> {}

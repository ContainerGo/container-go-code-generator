package vn.containergo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import vn.containergo.domain.CenterPersonGroup;

/**
 * Spring Data MongoDB repository for the CenterPersonGroup entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CenterPersonGroupRepository extends MongoRepository<CenterPersonGroup, Long> {}

package vn.containergo.repository;

import java.util.UUID;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import vn.containergo.domain.Offer;

/**
 * Spring Data MongoDB repository for the Offer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OfferRepository extends MongoRepository<Offer, UUID> {}

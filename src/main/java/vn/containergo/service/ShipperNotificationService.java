package vn.containergo.service;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.containergo.service.dto.ShipperNotificationDTO;

/**
 * Service Interface for managing {@link vn.containergo.domain.ShipperNotification}.
 */
public interface ShipperNotificationService {
    /**
     * Save a shipperNotification.
     *
     * @param shipperNotificationDTO the entity to save.
     * @return the persisted entity.
     */
    ShipperNotificationDTO save(ShipperNotificationDTO shipperNotificationDTO);

    /**
     * Updates a shipperNotification.
     *
     * @param shipperNotificationDTO the entity to update.
     * @return the persisted entity.
     */
    ShipperNotificationDTO update(ShipperNotificationDTO shipperNotificationDTO);

    /**
     * Partially updates a shipperNotification.
     *
     * @param shipperNotificationDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ShipperNotificationDTO> partialUpdate(ShipperNotificationDTO shipperNotificationDTO);

    /**
     * Get all the shipperNotifications.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ShipperNotificationDTO> findAll(Pageable pageable);

    /**
     * Get the "id" shipperNotification.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ShipperNotificationDTO> findOne(UUID id);

    /**
     * Delete the "id" shipperNotification.
     *
     * @param id the id of the entity.
     */
    void delete(UUID id);
}

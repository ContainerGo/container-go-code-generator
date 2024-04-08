package vn.containergo.service;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.containergo.service.dto.ShipmentHistoryDTO;

/**
 * Service Interface for managing {@link vn.containergo.domain.ShipmentHistory}.
 */
public interface ShipmentHistoryService {
    /**
     * Save a shipmentHistory.
     *
     * @param shipmentHistoryDTO the entity to save.
     * @return the persisted entity.
     */
    ShipmentHistoryDTO save(ShipmentHistoryDTO shipmentHistoryDTO);

    /**
     * Updates a shipmentHistory.
     *
     * @param shipmentHistoryDTO the entity to update.
     * @return the persisted entity.
     */
    ShipmentHistoryDTO update(ShipmentHistoryDTO shipmentHistoryDTO);

    /**
     * Partially updates a shipmentHistory.
     *
     * @param shipmentHistoryDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ShipmentHistoryDTO> partialUpdate(ShipmentHistoryDTO shipmentHistoryDTO);

    /**
     * Get all the shipmentHistories.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ShipmentHistoryDTO> findAll(Pageable pageable);

    /**
     * Get the "id" shipmentHistory.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ShipmentHistoryDTO> findOne(UUID id);

    /**
     * Delete the "id" shipmentHistory.
     *
     * @param id the id of the entity.
     */
    void delete(UUID id);
}

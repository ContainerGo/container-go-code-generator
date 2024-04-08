package vn.containergo.service;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.containergo.service.dto.ShipmentPlanDTO;

/**
 * Service Interface for managing {@link vn.containergo.domain.ShipmentPlan}.
 */
public interface ShipmentPlanService {
    /**
     * Save a shipmentPlan.
     *
     * @param shipmentPlanDTO the entity to save.
     * @return the persisted entity.
     */
    ShipmentPlanDTO save(ShipmentPlanDTO shipmentPlanDTO);

    /**
     * Updates a shipmentPlan.
     *
     * @param shipmentPlanDTO the entity to update.
     * @return the persisted entity.
     */
    ShipmentPlanDTO update(ShipmentPlanDTO shipmentPlanDTO);

    /**
     * Partially updates a shipmentPlan.
     *
     * @param shipmentPlanDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ShipmentPlanDTO> partialUpdate(ShipmentPlanDTO shipmentPlanDTO);

    /**
     * Get all the shipmentPlans.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ShipmentPlanDTO> findAll(Pageable pageable);

    /**
     * Get the "id" shipmentPlan.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ShipmentPlanDTO> findOne(UUID id);

    /**
     * Delete the "id" shipmentPlan.
     *
     * @param id the id of the entity.
     */
    void delete(UUID id);
}

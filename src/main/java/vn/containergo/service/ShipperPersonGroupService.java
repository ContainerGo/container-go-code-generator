package vn.containergo.service;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.containergo.service.dto.ShipperPersonGroupDTO;

/**
 * Service Interface for managing {@link vn.containergo.domain.ShipperPersonGroup}.
 */
public interface ShipperPersonGroupService {
    /**
     * Save a shipperPersonGroup.
     *
     * @param shipperPersonGroupDTO the entity to save.
     * @return the persisted entity.
     */
    ShipperPersonGroupDTO save(ShipperPersonGroupDTO shipperPersonGroupDTO);

    /**
     * Updates a shipperPersonGroup.
     *
     * @param shipperPersonGroupDTO the entity to update.
     * @return the persisted entity.
     */
    ShipperPersonGroupDTO update(ShipperPersonGroupDTO shipperPersonGroupDTO);

    /**
     * Partially updates a shipperPersonGroup.
     *
     * @param shipperPersonGroupDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ShipperPersonGroupDTO> partialUpdate(ShipperPersonGroupDTO shipperPersonGroupDTO);

    /**
     * Get all the shipperPersonGroups.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ShipperPersonGroupDTO> findAll(Pageable pageable);

    /**
     * Get the "id" shipperPersonGroup.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ShipperPersonGroupDTO> findOne(UUID id);

    /**
     * Delete the "id" shipperPersonGroup.
     *
     * @param id the id of the entity.
     */
    void delete(UUID id);
}

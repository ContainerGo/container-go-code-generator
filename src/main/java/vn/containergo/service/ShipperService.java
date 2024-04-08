package vn.containergo.service;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.containergo.service.dto.ShipperDTO;

/**
 * Service Interface for managing {@link vn.containergo.domain.Shipper}.
 */
public interface ShipperService {
    /**
     * Save a shipper.
     *
     * @param shipperDTO the entity to save.
     * @return the persisted entity.
     */
    ShipperDTO save(ShipperDTO shipperDTO);

    /**
     * Updates a shipper.
     *
     * @param shipperDTO the entity to update.
     * @return the persisted entity.
     */
    ShipperDTO update(ShipperDTO shipperDTO);

    /**
     * Partially updates a shipper.
     *
     * @param shipperDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ShipperDTO> partialUpdate(ShipperDTO shipperDTO);

    /**
     * Get all the shippers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ShipperDTO> findAll(Pageable pageable);

    /**
     * Get the "id" shipper.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ShipperDTO> findOne(UUID id);

    /**
     * Delete the "id" shipper.
     *
     * @param id the id of the entity.
     */
    void delete(UUID id);
}

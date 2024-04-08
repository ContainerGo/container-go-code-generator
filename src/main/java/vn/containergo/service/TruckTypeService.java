package vn.containergo.service;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.containergo.service.dto.TruckTypeDTO;

/**
 * Service Interface for managing {@link vn.containergo.domain.TruckType}.
 */
public interface TruckTypeService {
    /**
     * Save a truckType.
     *
     * @param truckTypeDTO the entity to save.
     * @return the persisted entity.
     */
    TruckTypeDTO save(TruckTypeDTO truckTypeDTO);

    /**
     * Updates a truckType.
     *
     * @param truckTypeDTO the entity to update.
     * @return the persisted entity.
     */
    TruckTypeDTO update(TruckTypeDTO truckTypeDTO);

    /**
     * Partially updates a truckType.
     *
     * @param truckTypeDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TruckTypeDTO> partialUpdate(TruckTypeDTO truckTypeDTO);

    /**
     * Get all the truckTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TruckTypeDTO> findAll(Pageable pageable);

    /**
     * Get the "id" truckType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TruckTypeDTO> findOne(UUID id);

    /**
     * Delete the "id" truckType.
     *
     * @param id the id of the entity.
     */
    void delete(UUID id);
}

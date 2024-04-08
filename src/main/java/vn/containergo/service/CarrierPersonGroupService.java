package vn.containergo.service;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.containergo.service.dto.CarrierPersonGroupDTO;

/**
 * Service Interface for managing {@link vn.containergo.domain.CarrierPersonGroup}.
 */
public interface CarrierPersonGroupService {
    /**
     * Save a carrierPersonGroup.
     *
     * @param carrierPersonGroupDTO the entity to save.
     * @return the persisted entity.
     */
    CarrierPersonGroupDTO save(CarrierPersonGroupDTO carrierPersonGroupDTO);

    /**
     * Updates a carrierPersonGroup.
     *
     * @param carrierPersonGroupDTO the entity to update.
     * @return the persisted entity.
     */
    CarrierPersonGroupDTO update(CarrierPersonGroupDTO carrierPersonGroupDTO);

    /**
     * Partially updates a carrierPersonGroup.
     *
     * @param carrierPersonGroupDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CarrierPersonGroupDTO> partialUpdate(CarrierPersonGroupDTO carrierPersonGroupDTO);

    /**
     * Get all the carrierPersonGroups.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CarrierPersonGroupDTO> findAll(Pageable pageable);

    /**
     * Get the "id" carrierPersonGroup.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CarrierPersonGroupDTO> findOne(UUID id);

    /**
     * Delete the "id" carrierPersonGroup.
     *
     * @param id the id of the entity.
     */
    void delete(UUID id);
}

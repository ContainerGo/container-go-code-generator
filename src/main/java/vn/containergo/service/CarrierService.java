package vn.containergo.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.containergo.service.dto.CarrierDTO;

/**
 * Service Interface for managing {@link vn.containergo.domain.Carrier}.
 */
public interface CarrierService {
    /**
     * Save a carrier.
     *
     * @param carrierDTO the entity to save.
     * @return the persisted entity.
     */
    CarrierDTO save(CarrierDTO carrierDTO);

    /**
     * Updates a carrier.
     *
     * @param carrierDTO the entity to update.
     * @return the persisted entity.
     */
    CarrierDTO update(CarrierDTO carrierDTO);

    /**
     * Partially updates a carrier.
     *
     * @param carrierDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CarrierDTO> partialUpdate(CarrierDTO carrierDTO);

    /**
     * Get all the carriers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CarrierDTO> findAll(Pageable pageable);

    /**
     * Get the "id" carrier.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CarrierDTO> findOne(Long id);

    /**
     * Delete the "id" carrier.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}

package vn.containergo.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.containergo.service.dto.CarrierPersonDTO;

/**
 * Service Interface for managing {@link vn.containergo.domain.CarrierPerson}.
 */
public interface CarrierPersonService {
    /**
     * Save a carrierPerson.
     *
     * @param carrierPersonDTO the entity to save.
     * @return the persisted entity.
     */
    CarrierPersonDTO save(CarrierPersonDTO carrierPersonDTO);

    /**
     * Updates a carrierPerson.
     *
     * @param carrierPersonDTO the entity to update.
     * @return the persisted entity.
     */
    CarrierPersonDTO update(CarrierPersonDTO carrierPersonDTO);

    /**
     * Partially updates a carrierPerson.
     *
     * @param carrierPersonDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CarrierPersonDTO> partialUpdate(CarrierPersonDTO carrierPersonDTO);

    /**
     * Get all the carrierPeople.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CarrierPersonDTO> findAll(Pageable pageable);

    /**
     * Get the "id" carrierPerson.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CarrierPersonDTO> findOne(Long id);

    /**
     * Delete the "id" carrierPerson.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}

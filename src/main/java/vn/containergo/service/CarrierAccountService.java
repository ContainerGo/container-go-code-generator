package vn.containergo.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.containergo.service.dto.CarrierAccountDTO;

/**
 * Service Interface for managing {@link vn.containergo.domain.CarrierAccount}.
 */
public interface CarrierAccountService {
    /**
     * Save a carrierAccount.
     *
     * @param carrierAccountDTO the entity to save.
     * @return the persisted entity.
     */
    CarrierAccountDTO save(CarrierAccountDTO carrierAccountDTO);

    /**
     * Updates a carrierAccount.
     *
     * @param carrierAccountDTO the entity to update.
     * @return the persisted entity.
     */
    CarrierAccountDTO update(CarrierAccountDTO carrierAccountDTO);

    /**
     * Partially updates a carrierAccount.
     *
     * @param carrierAccountDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CarrierAccountDTO> partialUpdate(CarrierAccountDTO carrierAccountDTO);

    /**
     * Get all the carrierAccounts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CarrierAccountDTO> findAll(Pageable pageable);

    /**
     * Get the "id" carrierAccount.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CarrierAccountDTO> findOne(Long id);

    /**
     * Delete the "id" carrierAccount.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}

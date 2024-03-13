package vn.containergo.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.containergo.service.dto.ShipperAccountDTO;

/**
 * Service Interface for managing {@link vn.containergo.domain.ShipperAccount}.
 */
public interface ShipperAccountService {
    /**
     * Save a shipperAccount.
     *
     * @param shipperAccountDTO the entity to save.
     * @return the persisted entity.
     */
    ShipperAccountDTO save(ShipperAccountDTO shipperAccountDTO);

    /**
     * Updates a shipperAccount.
     *
     * @param shipperAccountDTO the entity to update.
     * @return the persisted entity.
     */
    ShipperAccountDTO update(ShipperAccountDTO shipperAccountDTO);

    /**
     * Partially updates a shipperAccount.
     *
     * @param shipperAccountDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ShipperAccountDTO> partialUpdate(ShipperAccountDTO shipperAccountDTO);

    /**
     * Get all the shipperAccounts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ShipperAccountDTO> findAll(Pageable pageable);

    /**
     * Get the "id" shipperAccount.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ShipperAccountDTO> findOne(Long id);

    /**
     * Delete the "id" shipperAccount.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}

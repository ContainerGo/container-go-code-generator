package vn.containergo.service;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.containergo.service.dto.ShipperPersonDTO;

/**
 * Service Interface for managing {@link vn.containergo.domain.ShipperPerson}.
 */
public interface ShipperPersonService {
    /**
     * Save a shipperPerson.
     *
     * @param shipperPersonDTO the entity to save.
     * @return the persisted entity.
     */
    ShipperPersonDTO save(ShipperPersonDTO shipperPersonDTO);

    /**
     * Updates a shipperPerson.
     *
     * @param shipperPersonDTO the entity to update.
     * @return the persisted entity.
     */
    ShipperPersonDTO update(ShipperPersonDTO shipperPersonDTO);

    /**
     * Partially updates a shipperPerson.
     *
     * @param shipperPersonDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ShipperPersonDTO> partialUpdate(ShipperPersonDTO shipperPersonDTO);

    /**
     * Get all the shipperPeople.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ShipperPersonDTO> findAll(Pageable pageable);

    /**
     * Get the "id" shipperPerson.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ShipperPersonDTO> findOne(UUID id);

    /**
     * Delete the "id" shipperPerson.
     *
     * @param id the id of the entity.
     */
    void delete(UUID id);
}

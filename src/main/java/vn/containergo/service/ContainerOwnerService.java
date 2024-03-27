package vn.containergo.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.containergo.service.dto.ContainerOwnerDTO;

/**
 * Service Interface for managing {@link vn.containergo.domain.ContainerOwner}.
 */
public interface ContainerOwnerService {
    /**
     * Save a containerOwner.
     *
     * @param containerOwnerDTO the entity to save.
     * @return the persisted entity.
     */
    ContainerOwnerDTO save(ContainerOwnerDTO containerOwnerDTO);

    /**
     * Updates a containerOwner.
     *
     * @param containerOwnerDTO the entity to update.
     * @return the persisted entity.
     */
    ContainerOwnerDTO update(ContainerOwnerDTO containerOwnerDTO);

    /**
     * Partially updates a containerOwner.
     *
     * @param containerOwnerDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ContainerOwnerDTO> partialUpdate(ContainerOwnerDTO containerOwnerDTO);

    /**
     * Get all the containerOwners.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ContainerOwnerDTO> findAll(Pageable pageable);

    /**
     * Get the "id" containerOwner.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ContainerOwnerDTO> findOne(Long id);

    /**
     * Delete the "id" containerOwner.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}

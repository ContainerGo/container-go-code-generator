package vn.containergo.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.containergo.service.dto.ContainerTypeDTO;

/**
 * Service Interface for managing {@link vn.containergo.domain.ContainerType}.
 */
public interface ContainerTypeService {
    /**
     * Save a containerType.
     *
     * @param containerTypeDTO the entity to save.
     * @return the persisted entity.
     */
    ContainerTypeDTO save(ContainerTypeDTO containerTypeDTO);

    /**
     * Updates a containerType.
     *
     * @param containerTypeDTO the entity to update.
     * @return the persisted entity.
     */
    ContainerTypeDTO update(ContainerTypeDTO containerTypeDTO);

    /**
     * Partially updates a containerType.
     *
     * @param containerTypeDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ContainerTypeDTO> partialUpdate(ContainerTypeDTO containerTypeDTO);

    /**
     * Get all the containerTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ContainerTypeDTO> findAll(Pageable pageable);

    /**
     * Get the "id" containerType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ContainerTypeDTO> findOne(Long id);

    /**
     * Delete the "id" containerType.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}

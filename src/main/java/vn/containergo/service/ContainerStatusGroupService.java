package vn.containergo.service;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.containergo.service.dto.ContainerStatusGroupDTO;

/**
 * Service Interface for managing {@link vn.containergo.domain.ContainerStatusGroup}.
 */
public interface ContainerStatusGroupService {
    /**
     * Save a containerStatusGroup.
     *
     * @param containerStatusGroupDTO the entity to save.
     * @return the persisted entity.
     */
    ContainerStatusGroupDTO save(ContainerStatusGroupDTO containerStatusGroupDTO);

    /**
     * Updates a containerStatusGroup.
     *
     * @param containerStatusGroupDTO the entity to update.
     * @return the persisted entity.
     */
    ContainerStatusGroupDTO update(ContainerStatusGroupDTO containerStatusGroupDTO);

    /**
     * Partially updates a containerStatusGroup.
     *
     * @param containerStatusGroupDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ContainerStatusGroupDTO> partialUpdate(ContainerStatusGroupDTO containerStatusGroupDTO);

    /**
     * Get all the containerStatusGroups.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ContainerStatusGroupDTO> findAll(Pageable pageable);

    /**
     * Get the "id" containerStatusGroup.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ContainerStatusGroupDTO> findOne(UUID id);

    /**
     * Delete the "id" containerStatusGroup.
     *
     * @param id the id of the entity.
     */
    void delete(UUID id);
}

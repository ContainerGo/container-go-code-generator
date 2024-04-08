package vn.containergo.service;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.containergo.service.dto.ContainerStatusDTO;

/**
 * Service Interface for managing {@link vn.containergo.domain.ContainerStatus}.
 */
public interface ContainerStatusService {
    /**
     * Save a containerStatus.
     *
     * @param containerStatusDTO the entity to save.
     * @return the persisted entity.
     */
    ContainerStatusDTO save(ContainerStatusDTO containerStatusDTO);

    /**
     * Updates a containerStatus.
     *
     * @param containerStatusDTO the entity to update.
     * @return the persisted entity.
     */
    ContainerStatusDTO update(ContainerStatusDTO containerStatusDTO);

    /**
     * Partially updates a containerStatus.
     *
     * @param containerStatusDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ContainerStatusDTO> partialUpdate(ContainerStatusDTO containerStatusDTO);

    /**
     * Get all the containerStatuses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ContainerStatusDTO> findAll(Pageable pageable);

    /**
     * Get the "id" containerStatus.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ContainerStatusDTO> findOne(UUID id);

    /**
     * Delete the "id" containerStatus.
     *
     * @param id the id of the entity.
     */
    void delete(UUID id);
}

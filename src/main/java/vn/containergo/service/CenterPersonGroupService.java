package vn.containergo.service;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.containergo.service.dto.CenterPersonGroupDTO;

/**
 * Service Interface for managing {@link vn.containergo.domain.CenterPersonGroup}.
 */
public interface CenterPersonGroupService {
    /**
     * Save a centerPersonGroup.
     *
     * @param centerPersonGroupDTO the entity to save.
     * @return the persisted entity.
     */
    CenterPersonGroupDTO save(CenterPersonGroupDTO centerPersonGroupDTO);

    /**
     * Updates a centerPersonGroup.
     *
     * @param centerPersonGroupDTO the entity to update.
     * @return the persisted entity.
     */
    CenterPersonGroupDTO update(CenterPersonGroupDTO centerPersonGroupDTO);

    /**
     * Partially updates a centerPersonGroup.
     *
     * @param centerPersonGroupDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CenterPersonGroupDTO> partialUpdate(CenterPersonGroupDTO centerPersonGroupDTO);

    /**
     * Get all the centerPersonGroups.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CenterPersonGroupDTO> findAll(Pageable pageable);

    /**
     * Get the "id" centerPersonGroup.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CenterPersonGroupDTO> findOne(UUID id);

    /**
     * Delete the "id" centerPersonGroup.
     *
     * @param id the id of the entity.
     */
    void delete(UUID id);
}

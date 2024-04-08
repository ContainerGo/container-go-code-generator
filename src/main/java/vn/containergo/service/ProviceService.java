package vn.containergo.service;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.containergo.service.dto.ProviceDTO;

/**
 * Service Interface for managing {@link vn.containergo.domain.Provice}.
 */
public interface ProviceService {
    /**
     * Save a provice.
     *
     * @param proviceDTO the entity to save.
     * @return the persisted entity.
     */
    ProviceDTO save(ProviceDTO proviceDTO);

    /**
     * Updates a provice.
     *
     * @param proviceDTO the entity to update.
     * @return the persisted entity.
     */
    ProviceDTO update(ProviceDTO proviceDTO);

    /**
     * Partially updates a provice.
     *
     * @param proviceDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ProviceDTO> partialUpdate(ProviceDTO proviceDTO);

    /**
     * Get all the provices.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ProviceDTO> findAll(Pageable pageable);

    /**
     * Get the "id" provice.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProviceDTO> findOne(UUID id);

    /**
     * Delete the "id" provice.
     *
     * @param id the id of the entity.
     */
    void delete(UUID id);
}

package vn.containergo.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.containergo.service.dto.CenterPersonDTO;

/**
 * Service Interface for managing {@link vn.containergo.domain.CenterPerson}.
 */
public interface CenterPersonService {
    /**
     * Save a centerPerson.
     *
     * @param centerPersonDTO the entity to save.
     * @return the persisted entity.
     */
    CenterPersonDTO save(CenterPersonDTO centerPersonDTO);

    /**
     * Updates a centerPerson.
     *
     * @param centerPersonDTO the entity to update.
     * @return the persisted entity.
     */
    CenterPersonDTO update(CenterPersonDTO centerPersonDTO);

    /**
     * Partially updates a centerPerson.
     *
     * @param centerPersonDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CenterPersonDTO> partialUpdate(CenterPersonDTO centerPersonDTO);

    /**
     * Get all the centerPeople.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CenterPersonDTO> findAll(Pageable pageable);

    /**
     * Get all the centerPeople with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CenterPersonDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" centerPerson.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CenterPersonDTO> findOne(Long id);

    /**
     * Delete the "id" centerPerson.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}

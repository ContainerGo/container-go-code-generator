package vn.containergo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;
import vn.containergo.repository.CenterPersonRepository;
import vn.containergo.service.CenterPersonService;
import vn.containergo.service.dto.CenterPersonDTO;
import vn.containergo.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link vn.containergo.domain.CenterPerson}.
 */
@RestController
@RequestMapping("/api/center-people")
public class CenterPersonResource {

    private final Logger log = LoggerFactory.getLogger(CenterPersonResource.class);

    private static final String ENTITY_NAME = "centerPerson";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CenterPersonService centerPersonService;

    private final CenterPersonRepository centerPersonRepository;

    public CenterPersonResource(CenterPersonService centerPersonService, CenterPersonRepository centerPersonRepository) {
        this.centerPersonService = centerPersonService;
        this.centerPersonRepository = centerPersonRepository;
    }

    /**
     * {@code POST  /center-people} : Create a new centerPerson.
     *
     * @param centerPersonDTO the centerPersonDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new centerPersonDTO, or with status {@code 400 (Bad Request)} if the centerPerson has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<CenterPersonDTO> createCenterPerson(@Valid @RequestBody CenterPersonDTO centerPersonDTO)
        throws URISyntaxException {
        log.debug("REST request to save CenterPerson : {}", centerPersonDTO);
        if (centerPersonDTO.getId() != null) {
            throw new BadRequestAlertException("A new centerPerson cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CenterPersonDTO result = centerPersonService.save(centerPersonDTO);
        return ResponseEntity
            .created(new URI("/api/center-people/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /center-people/:id} : Updates an existing centerPerson.
     *
     * @param id the id of the centerPersonDTO to save.
     * @param centerPersonDTO the centerPersonDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated centerPersonDTO,
     * or with status {@code 400 (Bad Request)} if the centerPersonDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the centerPersonDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CenterPersonDTO> updateCenterPerson(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CenterPersonDTO centerPersonDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CenterPerson : {}, {}", id, centerPersonDTO);
        if (centerPersonDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, centerPersonDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!centerPersonRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CenterPersonDTO result = centerPersonService.update(centerPersonDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, centerPersonDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /center-people/:id} : Partial updates given fields of an existing centerPerson, field will ignore if it is null
     *
     * @param id the id of the centerPersonDTO to save.
     * @param centerPersonDTO the centerPersonDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated centerPersonDTO,
     * or with status {@code 400 (Bad Request)} if the centerPersonDTO is not valid,
     * or with status {@code 404 (Not Found)} if the centerPersonDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the centerPersonDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CenterPersonDTO> partialUpdateCenterPerson(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CenterPersonDTO centerPersonDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CenterPerson partially : {}, {}", id, centerPersonDTO);
        if (centerPersonDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, centerPersonDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!centerPersonRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CenterPersonDTO> result = centerPersonService.partialUpdate(centerPersonDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, centerPersonDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /center-people} : get all the centerPeople.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of centerPeople in body.
     */
    @GetMapping("")
    public ResponseEntity<List<CenterPersonDTO>> getAllCenterPeople(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get a page of CenterPeople");
        Page<CenterPersonDTO> page;
        if (eagerload) {
            page = centerPersonService.findAllWithEagerRelationships(pageable);
        } else {
            page = centerPersonService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /center-people/:id} : get the "id" centerPerson.
     *
     * @param id the id of the centerPersonDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the centerPersonDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CenterPersonDTO> getCenterPerson(@PathVariable("id") Long id) {
        log.debug("REST request to get CenterPerson : {}", id);
        Optional<CenterPersonDTO> centerPersonDTO = centerPersonService.findOne(id);
        return ResponseUtil.wrapOrNotFound(centerPersonDTO);
    }

    /**
     * {@code DELETE  /center-people/:id} : delete the "id" centerPerson.
     *
     * @param id the id of the centerPersonDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCenterPerson(@PathVariable("id") Long id) {
        log.debug("REST request to delete CenterPerson : {}", id);
        centerPersonService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

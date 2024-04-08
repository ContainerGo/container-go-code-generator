package vn.containergo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
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
import vn.containergo.repository.TruckTypeRepository;
import vn.containergo.service.TruckTypeService;
import vn.containergo.service.dto.TruckTypeDTO;
import vn.containergo.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link vn.containergo.domain.TruckType}.
 */
@RestController
@RequestMapping("/api/truck-types")
public class TruckTypeResource {

    private final Logger log = LoggerFactory.getLogger(TruckTypeResource.class);

    private static final String ENTITY_NAME = "truckType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TruckTypeService truckTypeService;

    private final TruckTypeRepository truckTypeRepository;

    public TruckTypeResource(TruckTypeService truckTypeService, TruckTypeRepository truckTypeRepository) {
        this.truckTypeService = truckTypeService;
        this.truckTypeRepository = truckTypeRepository;
    }

    /**
     * {@code POST  /truck-types} : Create a new truckType.
     *
     * @param truckTypeDTO the truckTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new truckTypeDTO, or with status {@code 400 (Bad Request)} if the truckType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<TruckTypeDTO> createTruckType(@Valid @RequestBody TruckTypeDTO truckTypeDTO) throws URISyntaxException {
        log.debug("REST request to save TruckType : {}", truckTypeDTO);
        if (truckTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new truckType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        truckTypeDTO.setId(UUID.randomUUID());
        truckTypeDTO = truckTypeService.save(truckTypeDTO);
        return ResponseEntity.created(new URI("/api/truck-types/" + truckTypeDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, truckTypeDTO.getId().toString()))
            .body(truckTypeDTO);
    }

    /**
     * {@code PUT  /truck-types/:id} : Updates an existing truckType.
     *
     * @param id the id of the truckTypeDTO to save.
     * @param truckTypeDTO the truckTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated truckTypeDTO,
     * or with status {@code 400 (Bad Request)} if the truckTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the truckTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<TruckTypeDTO> updateTruckType(
        @PathVariable(value = "id", required = false) final UUID id,
        @Valid @RequestBody TruckTypeDTO truckTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update TruckType : {}, {}", id, truckTypeDTO);
        if (truckTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, truckTypeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!truckTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        truckTypeDTO = truckTypeService.update(truckTypeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, truckTypeDTO.getId().toString()))
            .body(truckTypeDTO);
    }

    /**
     * {@code PATCH  /truck-types/:id} : Partial updates given fields of an existing truckType, field will ignore if it is null
     *
     * @param id the id of the truckTypeDTO to save.
     * @param truckTypeDTO the truckTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated truckTypeDTO,
     * or with status {@code 400 (Bad Request)} if the truckTypeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the truckTypeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the truckTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TruckTypeDTO> partialUpdateTruckType(
        @PathVariable(value = "id", required = false) final UUID id,
        @NotNull @RequestBody TruckTypeDTO truckTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update TruckType partially : {}, {}", id, truckTypeDTO);
        if (truckTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, truckTypeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!truckTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TruckTypeDTO> result = truckTypeService.partialUpdate(truckTypeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, truckTypeDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /truck-types} : get all the truckTypes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of truckTypes in body.
     */
    @GetMapping("")
    public ResponseEntity<List<TruckTypeDTO>> getAllTruckTypes(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of TruckTypes");
        Page<TruckTypeDTO> page = truckTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /truck-types/:id} : get the "id" truckType.
     *
     * @param id the id of the truckTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the truckTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TruckTypeDTO> getTruckType(@PathVariable("id") UUID id) {
        log.debug("REST request to get TruckType : {}", id);
        Optional<TruckTypeDTO> truckTypeDTO = truckTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(truckTypeDTO);
    }

    /**
     * {@code DELETE  /truck-types/:id} : delete the "id" truckType.
     *
     * @param id the id of the truckTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTruckType(@PathVariable("id") UUID id) {
        log.debug("REST request to delete TruckType : {}", id);
        truckTypeService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

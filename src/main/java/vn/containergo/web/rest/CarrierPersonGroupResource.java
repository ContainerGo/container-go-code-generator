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
import vn.containergo.repository.CarrierPersonGroupRepository;
import vn.containergo.service.CarrierPersonGroupService;
import vn.containergo.service.dto.CarrierPersonGroupDTO;
import vn.containergo.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link vn.containergo.domain.CarrierPersonGroup}.
 */
@RestController
@RequestMapping("/api/carrier-person-groups")
public class CarrierPersonGroupResource {

    private final Logger log = LoggerFactory.getLogger(CarrierPersonGroupResource.class);

    private static final String ENTITY_NAME = "carrierPersonGroup";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CarrierPersonGroupService carrierPersonGroupService;

    private final CarrierPersonGroupRepository carrierPersonGroupRepository;

    public CarrierPersonGroupResource(
        CarrierPersonGroupService carrierPersonGroupService,
        CarrierPersonGroupRepository carrierPersonGroupRepository
    ) {
        this.carrierPersonGroupService = carrierPersonGroupService;
        this.carrierPersonGroupRepository = carrierPersonGroupRepository;
    }

    /**
     * {@code POST  /carrier-person-groups} : Create a new carrierPersonGroup.
     *
     * @param carrierPersonGroupDTO the carrierPersonGroupDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new carrierPersonGroupDTO, or with status {@code 400 (Bad Request)} if the carrierPersonGroup has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<CarrierPersonGroupDTO> createCarrierPersonGroup(@Valid @RequestBody CarrierPersonGroupDTO carrierPersonGroupDTO)
        throws URISyntaxException {
        log.debug("REST request to save CarrierPersonGroup : {}", carrierPersonGroupDTO);
        if (carrierPersonGroupDTO.getId() != null) {
            throw new BadRequestAlertException("A new carrierPersonGroup cannot already have an ID", ENTITY_NAME, "idexists");
        }
        carrierPersonGroupDTO.setId(UUID.randomUUID());
        carrierPersonGroupDTO = carrierPersonGroupService.save(carrierPersonGroupDTO);
        return ResponseEntity.created(new URI("/api/carrier-person-groups/" + carrierPersonGroupDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, carrierPersonGroupDTO.getId().toString()))
            .body(carrierPersonGroupDTO);
    }

    /**
     * {@code PUT  /carrier-person-groups/:id} : Updates an existing carrierPersonGroup.
     *
     * @param id the id of the carrierPersonGroupDTO to save.
     * @param carrierPersonGroupDTO the carrierPersonGroupDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated carrierPersonGroupDTO,
     * or with status {@code 400 (Bad Request)} if the carrierPersonGroupDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the carrierPersonGroupDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CarrierPersonGroupDTO> updateCarrierPersonGroup(
        @PathVariable(value = "id", required = false) final UUID id,
        @Valid @RequestBody CarrierPersonGroupDTO carrierPersonGroupDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CarrierPersonGroup : {}, {}", id, carrierPersonGroupDTO);
        if (carrierPersonGroupDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, carrierPersonGroupDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!carrierPersonGroupRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        carrierPersonGroupDTO = carrierPersonGroupService.update(carrierPersonGroupDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, carrierPersonGroupDTO.getId().toString()))
            .body(carrierPersonGroupDTO);
    }

    /**
     * {@code PATCH  /carrier-person-groups/:id} : Partial updates given fields of an existing carrierPersonGroup, field will ignore if it is null
     *
     * @param id the id of the carrierPersonGroupDTO to save.
     * @param carrierPersonGroupDTO the carrierPersonGroupDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated carrierPersonGroupDTO,
     * or with status {@code 400 (Bad Request)} if the carrierPersonGroupDTO is not valid,
     * or with status {@code 404 (Not Found)} if the carrierPersonGroupDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the carrierPersonGroupDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CarrierPersonGroupDTO> partialUpdateCarrierPersonGroup(
        @PathVariable(value = "id", required = false) final UUID id,
        @NotNull @RequestBody CarrierPersonGroupDTO carrierPersonGroupDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CarrierPersonGroup partially : {}, {}", id, carrierPersonGroupDTO);
        if (carrierPersonGroupDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, carrierPersonGroupDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!carrierPersonGroupRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CarrierPersonGroupDTO> result = carrierPersonGroupService.partialUpdate(carrierPersonGroupDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, carrierPersonGroupDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /carrier-person-groups} : get all the carrierPersonGroups.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of carrierPersonGroups in body.
     */
    @GetMapping("")
    public ResponseEntity<List<CarrierPersonGroupDTO>> getAllCarrierPersonGroups(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of CarrierPersonGroups");
        Page<CarrierPersonGroupDTO> page = carrierPersonGroupService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /carrier-person-groups/:id} : get the "id" carrierPersonGroup.
     *
     * @param id the id of the carrierPersonGroupDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the carrierPersonGroupDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CarrierPersonGroupDTO> getCarrierPersonGroup(@PathVariable("id") UUID id) {
        log.debug("REST request to get CarrierPersonGroup : {}", id);
        Optional<CarrierPersonGroupDTO> carrierPersonGroupDTO = carrierPersonGroupService.findOne(id);
        return ResponseUtil.wrapOrNotFound(carrierPersonGroupDTO);
    }

    /**
     * {@code DELETE  /carrier-person-groups/:id} : delete the "id" carrierPersonGroup.
     *
     * @param id the id of the carrierPersonGroupDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCarrierPersonGroup(@PathVariable("id") UUID id) {
        log.debug("REST request to delete CarrierPersonGroup : {}", id);
        carrierPersonGroupService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

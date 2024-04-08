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
import vn.containergo.repository.ShipperPersonGroupRepository;
import vn.containergo.service.ShipperPersonGroupService;
import vn.containergo.service.dto.ShipperPersonGroupDTO;
import vn.containergo.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link vn.containergo.domain.ShipperPersonGroup}.
 */
@RestController
@RequestMapping("/api/shipper-person-groups")
public class ShipperPersonGroupResource {

    private final Logger log = LoggerFactory.getLogger(ShipperPersonGroupResource.class);

    private static final String ENTITY_NAME = "shipperPersonGroup";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ShipperPersonGroupService shipperPersonGroupService;

    private final ShipperPersonGroupRepository shipperPersonGroupRepository;

    public ShipperPersonGroupResource(
        ShipperPersonGroupService shipperPersonGroupService,
        ShipperPersonGroupRepository shipperPersonGroupRepository
    ) {
        this.shipperPersonGroupService = shipperPersonGroupService;
        this.shipperPersonGroupRepository = shipperPersonGroupRepository;
    }

    /**
     * {@code POST  /shipper-person-groups} : Create a new shipperPersonGroup.
     *
     * @param shipperPersonGroupDTO the shipperPersonGroupDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new shipperPersonGroupDTO, or with status {@code 400 (Bad Request)} if the shipperPersonGroup has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ShipperPersonGroupDTO> createShipperPersonGroup(@Valid @RequestBody ShipperPersonGroupDTO shipperPersonGroupDTO)
        throws URISyntaxException {
        log.debug("REST request to save ShipperPersonGroup : {}", shipperPersonGroupDTO);
        if (shipperPersonGroupDTO.getId() != null) {
            throw new BadRequestAlertException("A new shipperPersonGroup cannot already have an ID", ENTITY_NAME, "idexists");
        }
        shipperPersonGroupDTO.setId(UUID.randomUUID());
        shipperPersonGroupDTO = shipperPersonGroupService.save(shipperPersonGroupDTO);
        return ResponseEntity.created(new URI("/api/shipper-person-groups/" + shipperPersonGroupDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, shipperPersonGroupDTO.getId().toString()))
            .body(shipperPersonGroupDTO);
    }

    /**
     * {@code PUT  /shipper-person-groups/:id} : Updates an existing shipperPersonGroup.
     *
     * @param id the id of the shipperPersonGroupDTO to save.
     * @param shipperPersonGroupDTO the shipperPersonGroupDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated shipperPersonGroupDTO,
     * or with status {@code 400 (Bad Request)} if the shipperPersonGroupDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the shipperPersonGroupDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ShipperPersonGroupDTO> updateShipperPersonGroup(
        @PathVariable(value = "id", required = false) final UUID id,
        @Valid @RequestBody ShipperPersonGroupDTO shipperPersonGroupDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ShipperPersonGroup : {}, {}", id, shipperPersonGroupDTO);
        if (shipperPersonGroupDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, shipperPersonGroupDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!shipperPersonGroupRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        shipperPersonGroupDTO = shipperPersonGroupService.update(shipperPersonGroupDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, shipperPersonGroupDTO.getId().toString()))
            .body(shipperPersonGroupDTO);
    }

    /**
     * {@code PATCH  /shipper-person-groups/:id} : Partial updates given fields of an existing shipperPersonGroup, field will ignore if it is null
     *
     * @param id the id of the shipperPersonGroupDTO to save.
     * @param shipperPersonGroupDTO the shipperPersonGroupDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated shipperPersonGroupDTO,
     * or with status {@code 400 (Bad Request)} if the shipperPersonGroupDTO is not valid,
     * or with status {@code 404 (Not Found)} if the shipperPersonGroupDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the shipperPersonGroupDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ShipperPersonGroupDTO> partialUpdateShipperPersonGroup(
        @PathVariable(value = "id", required = false) final UUID id,
        @NotNull @RequestBody ShipperPersonGroupDTO shipperPersonGroupDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ShipperPersonGroup partially : {}, {}", id, shipperPersonGroupDTO);
        if (shipperPersonGroupDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, shipperPersonGroupDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!shipperPersonGroupRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ShipperPersonGroupDTO> result = shipperPersonGroupService.partialUpdate(shipperPersonGroupDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, shipperPersonGroupDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /shipper-person-groups} : get all the shipperPersonGroups.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of shipperPersonGroups in body.
     */
    @GetMapping("")
    public ResponseEntity<List<ShipperPersonGroupDTO>> getAllShipperPersonGroups(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of ShipperPersonGroups");
        Page<ShipperPersonGroupDTO> page = shipperPersonGroupService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /shipper-person-groups/:id} : get the "id" shipperPersonGroup.
     *
     * @param id the id of the shipperPersonGroupDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the shipperPersonGroupDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ShipperPersonGroupDTO> getShipperPersonGroup(@PathVariable("id") UUID id) {
        log.debug("REST request to get ShipperPersonGroup : {}", id);
        Optional<ShipperPersonGroupDTO> shipperPersonGroupDTO = shipperPersonGroupService.findOne(id);
        return ResponseUtil.wrapOrNotFound(shipperPersonGroupDTO);
    }

    /**
     * {@code DELETE  /shipper-person-groups/:id} : delete the "id" shipperPersonGroup.
     *
     * @param id the id of the shipperPersonGroupDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShipperPersonGroup(@PathVariable("id") UUID id) {
        log.debug("REST request to delete ShipperPersonGroup : {}", id);
        shipperPersonGroupService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

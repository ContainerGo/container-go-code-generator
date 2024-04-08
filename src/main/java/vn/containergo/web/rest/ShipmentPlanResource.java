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
import vn.containergo.repository.ShipmentPlanRepository;
import vn.containergo.service.ShipmentPlanService;
import vn.containergo.service.dto.ShipmentPlanDTO;
import vn.containergo.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link vn.containergo.domain.ShipmentPlan}.
 */
@RestController
@RequestMapping("/api/shipment-plans")
public class ShipmentPlanResource {

    private final Logger log = LoggerFactory.getLogger(ShipmentPlanResource.class);

    private static final String ENTITY_NAME = "shipmentPlan";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ShipmentPlanService shipmentPlanService;

    private final ShipmentPlanRepository shipmentPlanRepository;

    public ShipmentPlanResource(ShipmentPlanService shipmentPlanService, ShipmentPlanRepository shipmentPlanRepository) {
        this.shipmentPlanService = shipmentPlanService;
        this.shipmentPlanRepository = shipmentPlanRepository;
    }

    /**
     * {@code POST  /shipment-plans} : Create a new shipmentPlan.
     *
     * @param shipmentPlanDTO the shipmentPlanDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new shipmentPlanDTO, or with status {@code 400 (Bad Request)} if the shipmentPlan has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ShipmentPlanDTO> createShipmentPlan(@Valid @RequestBody ShipmentPlanDTO shipmentPlanDTO)
        throws URISyntaxException {
        log.debug("REST request to save ShipmentPlan : {}", shipmentPlanDTO);
        if (shipmentPlanDTO.getId() != null) {
            throw new BadRequestAlertException("A new shipmentPlan cannot already have an ID", ENTITY_NAME, "idexists");
        }
        shipmentPlanDTO.setId(UUID.randomUUID());
        shipmentPlanDTO = shipmentPlanService.save(shipmentPlanDTO);
        return ResponseEntity.created(new URI("/api/shipment-plans/" + shipmentPlanDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, shipmentPlanDTO.getId().toString()))
            .body(shipmentPlanDTO);
    }

    /**
     * {@code PUT  /shipment-plans/:id} : Updates an existing shipmentPlan.
     *
     * @param id the id of the shipmentPlanDTO to save.
     * @param shipmentPlanDTO the shipmentPlanDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated shipmentPlanDTO,
     * or with status {@code 400 (Bad Request)} if the shipmentPlanDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the shipmentPlanDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ShipmentPlanDTO> updateShipmentPlan(
        @PathVariable(value = "id", required = false) final UUID id,
        @Valid @RequestBody ShipmentPlanDTO shipmentPlanDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ShipmentPlan : {}, {}", id, shipmentPlanDTO);
        if (shipmentPlanDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, shipmentPlanDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!shipmentPlanRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        shipmentPlanDTO = shipmentPlanService.update(shipmentPlanDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, shipmentPlanDTO.getId().toString()))
            .body(shipmentPlanDTO);
    }

    /**
     * {@code PATCH  /shipment-plans/:id} : Partial updates given fields of an existing shipmentPlan, field will ignore if it is null
     *
     * @param id the id of the shipmentPlanDTO to save.
     * @param shipmentPlanDTO the shipmentPlanDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated shipmentPlanDTO,
     * or with status {@code 400 (Bad Request)} if the shipmentPlanDTO is not valid,
     * or with status {@code 404 (Not Found)} if the shipmentPlanDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the shipmentPlanDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ShipmentPlanDTO> partialUpdateShipmentPlan(
        @PathVariable(value = "id", required = false) final UUID id,
        @NotNull @RequestBody ShipmentPlanDTO shipmentPlanDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ShipmentPlan partially : {}, {}", id, shipmentPlanDTO);
        if (shipmentPlanDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, shipmentPlanDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!shipmentPlanRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ShipmentPlanDTO> result = shipmentPlanService.partialUpdate(shipmentPlanDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, shipmentPlanDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /shipment-plans} : get all the shipmentPlans.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of shipmentPlans in body.
     */
    @GetMapping("")
    public ResponseEntity<List<ShipmentPlanDTO>> getAllShipmentPlans(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of ShipmentPlans");
        Page<ShipmentPlanDTO> page = shipmentPlanService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /shipment-plans/:id} : get the "id" shipmentPlan.
     *
     * @param id the id of the shipmentPlanDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the shipmentPlanDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ShipmentPlanDTO> getShipmentPlan(@PathVariable("id") UUID id) {
        log.debug("REST request to get ShipmentPlan : {}", id);
        Optional<ShipmentPlanDTO> shipmentPlanDTO = shipmentPlanService.findOne(id);
        return ResponseUtil.wrapOrNotFound(shipmentPlanDTO);
    }

    /**
     * {@code DELETE  /shipment-plans/:id} : delete the "id" shipmentPlan.
     *
     * @param id the id of the shipmentPlanDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShipmentPlan(@PathVariable("id") UUID id) {
        log.debug("REST request to delete ShipmentPlan : {}", id);
        shipmentPlanService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

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
import vn.containergo.repository.ShipmentHistoryRepository;
import vn.containergo.service.ShipmentHistoryService;
import vn.containergo.service.dto.ShipmentHistoryDTO;
import vn.containergo.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link vn.containergo.domain.ShipmentHistory}.
 */
@RestController
@RequestMapping("/api/shipment-histories")
public class ShipmentHistoryResource {

    private final Logger log = LoggerFactory.getLogger(ShipmentHistoryResource.class);

    private static final String ENTITY_NAME = "shipmentHistory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ShipmentHistoryService shipmentHistoryService;

    private final ShipmentHistoryRepository shipmentHistoryRepository;

    public ShipmentHistoryResource(ShipmentHistoryService shipmentHistoryService, ShipmentHistoryRepository shipmentHistoryRepository) {
        this.shipmentHistoryService = shipmentHistoryService;
        this.shipmentHistoryRepository = shipmentHistoryRepository;
    }

    /**
     * {@code POST  /shipment-histories} : Create a new shipmentHistory.
     *
     * @param shipmentHistoryDTO the shipmentHistoryDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new shipmentHistoryDTO, or with status {@code 400 (Bad Request)} if the shipmentHistory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ShipmentHistoryDTO> createShipmentHistory(@Valid @RequestBody ShipmentHistoryDTO shipmentHistoryDTO)
        throws URISyntaxException {
        log.debug("REST request to save ShipmentHistory : {}", shipmentHistoryDTO);
        if (shipmentHistoryDTO.getId() != null) {
            throw new BadRequestAlertException("A new shipmentHistory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        shipmentHistoryDTO = shipmentHistoryService.save(shipmentHistoryDTO);
        return ResponseEntity.created(new URI("/api/shipment-histories/" + shipmentHistoryDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, shipmentHistoryDTO.getId().toString()))
            .body(shipmentHistoryDTO);
    }

    /**
     * {@code PUT  /shipment-histories/:id} : Updates an existing shipmentHistory.
     *
     * @param id the id of the shipmentHistoryDTO to save.
     * @param shipmentHistoryDTO the shipmentHistoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated shipmentHistoryDTO,
     * or with status {@code 400 (Bad Request)} if the shipmentHistoryDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the shipmentHistoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ShipmentHistoryDTO> updateShipmentHistory(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ShipmentHistoryDTO shipmentHistoryDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ShipmentHistory : {}, {}", id, shipmentHistoryDTO);
        if (shipmentHistoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, shipmentHistoryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!shipmentHistoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        shipmentHistoryDTO = shipmentHistoryService.update(shipmentHistoryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, shipmentHistoryDTO.getId().toString()))
            .body(shipmentHistoryDTO);
    }

    /**
     * {@code PATCH  /shipment-histories/:id} : Partial updates given fields of an existing shipmentHistory, field will ignore if it is null
     *
     * @param id the id of the shipmentHistoryDTO to save.
     * @param shipmentHistoryDTO the shipmentHistoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated shipmentHistoryDTO,
     * or with status {@code 400 (Bad Request)} if the shipmentHistoryDTO is not valid,
     * or with status {@code 404 (Not Found)} if the shipmentHistoryDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the shipmentHistoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ShipmentHistoryDTO> partialUpdateShipmentHistory(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ShipmentHistoryDTO shipmentHistoryDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ShipmentHistory partially : {}, {}", id, shipmentHistoryDTO);
        if (shipmentHistoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, shipmentHistoryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!shipmentHistoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ShipmentHistoryDTO> result = shipmentHistoryService.partialUpdate(shipmentHistoryDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, shipmentHistoryDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /shipment-histories} : get all the shipmentHistories.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of shipmentHistories in body.
     */
    @GetMapping("")
    public ResponseEntity<List<ShipmentHistoryDTO>> getAllShipmentHistories(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of ShipmentHistories");
        Page<ShipmentHistoryDTO> page = shipmentHistoryService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /shipment-histories/:id} : get the "id" shipmentHistory.
     *
     * @param id the id of the shipmentHistoryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the shipmentHistoryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ShipmentHistoryDTO> getShipmentHistory(@PathVariable("id") Long id) {
        log.debug("REST request to get ShipmentHistory : {}", id);
        Optional<ShipmentHistoryDTO> shipmentHistoryDTO = shipmentHistoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(shipmentHistoryDTO);
    }

    /**
     * {@code DELETE  /shipment-histories/:id} : delete the "id" shipmentHistory.
     *
     * @param id the id of the shipmentHistoryDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShipmentHistory(@PathVariable("id") Long id) {
        log.debug("REST request to delete ShipmentHistory : {}", id);
        shipmentHistoryService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

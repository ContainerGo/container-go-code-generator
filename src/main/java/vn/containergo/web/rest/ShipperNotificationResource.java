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
import vn.containergo.repository.ShipperNotificationRepository;
import vn.containergo.service.ShipperNotificationService;
import vn.containergo.service.dto.ShipperNotificationDTO;
import vn.containergo.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link vn.containergo.domain.ShipperNotification}.
 */
@RestController
@RequestMapping("/api/shipper-notifications")
public class ShipperNotificationResource {

    private final Logger log = LoggerFactory.getLogger(ShipperNotificationResource.class);

    private static final String ENTITY_NAME = "shipperNotification";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ShipperNotificationService shipperNotificationService;

    private final ShipperNotificationRepository shipperNotificationRepository;

    public ShipperNotificationResource(
        ShipperNotificationService shipperNotificationService,
        ShipperNotificationRepository shipperNotificationRepository
    ) {
        this.shipperNotificationService = shipperNotificationService;
        this.shipperNotificationRepository = shipperNotificationRepository;
    }

    /**
     * {@code POST  /shipper-notifications} : Create a new shipperNotification.
     *
     * @param shipperNotificationDTO the shipperNotificationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new shipperNotificationDTO, or with status {@code 400 (Bad Request)} if the shipperNotification has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ShipperNotificationDTO> createShipperNotification(
        @Valid @RequestBody ShipperNotificationDTO shipperNotificationDTO
    ) throws URISyntaxException {
        log.debug("REST request to save ShipperNotification : {}", shipperNotificationDTO);
        if (shipperNotificationDTO.getId() != null) {
            throw new BadRequestAlertException("A new shipperNotification cannot already have an ID", ENTITY_NAME, "idexists");
        }
        shipperNotificationDTO.setId(UUID.randomUUID());
        shipperNotificationDTO = shipperNotificationService.save(shipperNotificationDTO);
        return ResponseEntity.created(new URI("/api/shipper-notifications/" + shipperNotificationDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, shipperNotificationDTO.getId().toString()))
            .body(shipperNotificationDTO);
    }

    /**
     * {@code PUT  /shipper-notifications/:id} : Updates an existing shipperNotification.
     *
     * @param id the id of the shipperNotificationDTO to save.
     * @param shipperNotificationDTO the shipperNotificationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated shipperNotificationDTO,
     * or with status {@code 400 (Bad Request)} if the shipperNotificationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the shipperNotificationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ShipperNotificationDTO> updateShipperNotification(
        @PathVariable(value = "id", required = false) final UUID id,
        @Valid @RequestBody ShipperNotificationDTO shipperNotificationDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ShipperNotification : {}, {}", id, shipperNotificationDTO);
        if (shipperNotificationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, shipperNotificationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!shipperNotificationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        shipperNotificationDTO = shipperNotificationService.update(shipperNotificationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, shipperNotificationDTO.getId().toString()))
            .body(shipperNotificationDTO);
    }

    /**
     * {@code PATCH  /shipper-notifications/:id} : Partial updates given fields of an existing shipperNotification, field will ignore if it is null
     *
     * @param id the id of the shipperNotificationDTO to save.
     * @param shipperNotificationDTO the shipperNotificationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated shipperNotificationDTO,
     * or with status {@code 400 (Bad Request)} if the shipperNotificationDTO is not valid,
     * or with status {@code 404 (Not Found)} if the shipperNotificationDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the shipperNotificationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ShipperNotificationDTO> partialUpdateShipperNotification(
        @PathVariable(value = "id", required = false) final UUID id,
        @NotNull @RequestBody ShipperNotificationDTO shipperNotificationDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ShipperNotification partially : {}, {}", id, shipperNotificationDTO);
        if (shipperNotificationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, shipperNotificationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!shipperNotificationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ShipperNotificationDTO> result = shipperNotificationService.partialUpdate(shipperNotificationDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, shipperNotificationDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /shipper-notifications} : get all the shipperNotifications.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of shipperNotifications in body.
     */
    @GetMapping("")
    public ResponseEntity<List<ShipperNotificationDTO>> getAllShipperNotifications(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of ShipperNotifications");
        Page<ShipperNotificationDTO> page = shipperNotificationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /shipper-notifications/:id} : get the "id" shipperNotification.
     *
     * @param id the id of the shipperNotificationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the shipperNotificationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ShipperNotificationDTO> getShipperNotification(@PathVariable("id") UUID id) {
        log.debug("REST request to get ShipperNotification : {}", id);
        Optional<ShipperNotificationDTO> shipperNotificationDTO = shipperNotificationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(shipperNotificationDTO);
    }

    /**
     * {@code DELETE  /shipper-notifications/:id} : delete the "id" shipperNotification.
     *
     * @param id the id of the shipperNotificationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShipperNotification(@PathVariable("id") UUID id) {
        log.debug("REST request to delete ShipperNotification : {}", id);
        shipperNotificationService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

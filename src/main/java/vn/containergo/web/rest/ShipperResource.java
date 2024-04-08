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
import vn.containergo.repository.ShipperRepository;
import vn.containergo.service.ShipperService;
import vn.containergo.service.dto.ShipperDTO;
import vn.containergo.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link vn.containergo.domain.Shipper}.
 */
@RestController
@RequestMapping("/api/shippers")
public class ShipperResource {

    private final Logger log = LoggerFactory.getLogger(ShipperResource.class);

    private static final String ENTITY_NAME = "shipper";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ShipperService shipperService;

    private final ShipperRepository shipperRepository;

    public ShipperResource(ShipperService shipperService, ShipperRepository shipperRepository) {
        this.shipperService = shipperService;
        this.shipperRepository = shipperRepository;
    }

    /**
     * {@code POST  /shippers} : Create a new shipper.
     *
     * @param shipperDTO the shipperDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new shipperDTO, or with status {@code 400 (Bad Request)} if the shipper has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ShipperDTO> createShipper(@Valid @RequestBody ShipperDTO shipperDTO) throws URISyntaxException {
        log.debug("REST request to save Shipper : {}", shipperDTO);
        if (shipperDTO.getId() != null) {
            throw new BadRequestAlertException("A new shipper cannot already have an ID", ENTITY_NAME, "idexists");
        }
        shipperDTO.setId(UUID.randomUUID());
        shipperDTO = shipperService.save(shipperDTO);
        return ResponseEntity.created(new URI("/api/shippers/" + shipperDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, shipperDTO.getId().toString()))
            .body(shipperDTO);
    }

    /**
     * {@code PUT  /shippers/:id} : Updates an existing shipper.
     *
     * @param id the id of the shipperDTO to save.
     * @param shipperDTO the shipperDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated shipperDTO,
     * or with status {@code 400 (Bad Request)} if the shipperDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the shipperDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ShipperDTO> updateShipper(
        @PathVariable(value = "id", required = false) final UUID id,
        @Valid @RequestBody ShipperDTO shipperDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Shipper : {}, {}", id, shipperDTO);
        if (shipperDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, shipperDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!shipperRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        shipperDTO = shipperService.update(shipperDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, shipperDTO.getId().toString()))
            .body(shipperDTO);
    }

    /**
     * {@code PATCH  /shippers/:id} : Partial updates given fields of an existing shipper, field will ignore if it is null
     *
     * @param id the id of the shipperDTO to save.
     * @param shipperDTO the shipperDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated shipperDTO,
     * or with status {@code 400 (Bad Request)} if the shipperDTO is not valid,
     * or with status {@code 404 (Not Found)} if the shipperDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the shipperDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ShipperDTO> partialUpdateShipper(
        @PathVariable(value = "id", required = false) final UUID id,
        @NotNull @RequestBody ShipperDTO shipperDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Shipper partially : {}, {}", id, shipperDTO);
        if (shipperDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, shipperDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!shipperRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ShipperDTO> result = shipperService.partialUpdate(shipperDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, shipperDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /shippers} : get all the shippers.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of shippers in body.
     */
    @GetMapping("")
    public ResponseEntity<List<ShipperDTO>> getAllShippers(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Shippers");
        Page<ShipperDTO> page = shipperService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /shippers/:id} : get the "id" shipper.
     *
     * @param id the id of the shipperDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the shipperDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ShipperDTO> getShipper(@PathVariable("id") UUID id) {
        log.debug("REST request to get Shipper : {}", id);
        Optional<ShipperDTO> shipperDTO = shipperService.findOne(id);
        return ResponseUtil.wrapOrNotFound(shipperDTO);
    }

    /**
     * {@code DELETE  /shippers/:id} : delete the "id" shipper.
     *
     * @param id the id of the shipperDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShipper(@PathVariable("id") UUID id) {
        log.debug("REST request to delete Shipper : {}", id);
        shipperService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

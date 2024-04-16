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
import vn.containergo.repository.ShipperAccountRepository;
import vn.containergo.service.ShipperAccountService;
import vn.containergo.service.dto.ShipperAccountDTO;
import vn.containergo.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link vn.containergo.domain.ShipperAccount}.
 */
@RestController
@RequestMapping("/api/shipper-accounts")
public class ShipperAccountResource {

    private final Logger log = LoggerFactory.getLogger(ShipperAccountResource.class);

    private static final String ENTITY_NAME = "shipperAccount";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ShipperAccountService shipperAccountService;

    private final ShipperAccountRepository shipperAccountRepository;

    public ShipperAccountResource(ShipperAccountService shipperAccountService, ShipperAccountRepository shipperAccountRepository) {
        this.shipperAccountService = shipperAccountService;
        this.shipperAccountRepository = shipperAccountRepository;
    }

    /**
     * {@code POST  /shipper-accounts} : Create a new shipperAccount.
     *
     * @param shipperAccountDTO the shipperAccountDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new shipperAccountDTO, or with status {@code 400 (Bad Request)} if the shipperAccount has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ShipperAccountDTO> createShipperAccount(@Valid @RequestBody ShipperAccountDTO shipperAccountDTO)
        throws URISyntaxException {
        log.debug("REST request to save ShipperAccount : {}", shipperAccountDTO);
        if (shipperAccountDTO.getId() != null) {
            throw new BadRequestAlertException("A new shipperAccount cannot already have an ID", ENTITY_NAME, "idexists");
        }
        shipperAccountDTO.setId(UUID.randomUUID());
        shipperAccountDTO = shipperAccountService.save(shipperAccountDTO);
        return ResponseEntity.created(new URI("/api/shipper-accounts/" + shipperAccountDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, shipperAccountDTO.getId().toString()))
            .body(shipperAccountDTO);
    }

    /**
     * {@code PUT  /shipper-accounts/:id} : Updates an existing shipperAccount.
     *
     * @param id the id of the shipperAccountDTO to save.
     * @param shipperAccountDTO the shipperAccountDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated shipperAccountDTO,
     * or with status {@code 400 (Bad Request)} if the shipperAccountDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the shipperAccountDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ShipperAccountDTO> updateShipperAccount(
        @PathVariable(value = "id", required = false) final UUID id,
        @Valid @RequestBody ShipperAccountDTO shipperAccountDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ShipperAccount : {}, {}", id, shipperAccountDTO);
        if (shipperAccountDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, shipperAccountDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!shipperAccountRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        shipperAccountDTO = shipperAccountService.update(shipperAccountDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, shipperAccountDTO.getId().toString()))
            .body(shipperAccountDTO);
    }

    /**
     * {@code PATCH  /shipper-accounts/:id} : Partial updates given fields of an existing shipperAccount, field will ignore if it is null
     *
     * @param id the id of the shipperAccountDTO to save.
     * @param shipperAccountDTO the shipperAccountDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated shipperAccountDTO,
     * or with status {@code 400 (Bad Request)} if the shipperAccountDTO is not valid,
     * or with status {@code 404 (Not Found)} if the shipperAccountDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the shipperAccountDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ShipperAccountDTO> partialUpdateShipperAccount(
        @PathVariable(value = "id", required = false) final UUID id,
        @NotNull @RequestBody ShipperAccountDTO shipperAccountDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ShipperAccount partially : {}, {}", id, shipperAccountDTO);
        if (shipperAccountDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, shipperAccountDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!shipperAccountRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ShipperAccountDTO> result = shipperAccountService.partialUpdate(shipperAccountDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, shipperAccountDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /shipper-accounts} : get all the shipperAccounts.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of shipperAccounts in body.
     */
    @GetMapping("")
    public ResponseEntity<List<ShipperAccountDTO>> getAllShipperAccounts(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of ShipperAccounts");
        Page<ShipperAccountDTO> page = shipperAccountService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /shipper-accounts/:id} : get the "id" shipperAccount.
     *
     * @param id the id of the shipperAccountDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the shipperAccountDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ShipperAccountDTO> getShipperAccount(@PathVariable("id") UUID id) {
        log.debug("REST request to get ShipperAccount : {}", id);
        Optional<ShipperAccountDTO> shipperAccountDTO = shipperAccountService.findOne(id);
        return ResponseUtil.wrapOrNotFound(shipperAccountDTO);
    }

    /**
     * {@code DELETE  /shipper-accounts/:id} : delete the "id" shipperAccount.
     *
     * @param id the id of the shipperAccountDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShipperAccount(@PathVariable("id") UUID id) {
        log.debug("REST request to delete ShipperAccount : {}", id);
        shipperAccountService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

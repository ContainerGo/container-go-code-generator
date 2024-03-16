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
import vn.containergo.repository.ShipperPersonRepository;
import vn.containergo.service.ShipperPersonService;
import vn.containergo.service.dto.ShipperPersonDTO;
import vn.containergo.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link vn.containergo.domain.ShipperPerson}.
 */
@RestController
@RequestMapping("/api/shipper-people")
public class ShipperPersonResource {

    private final Logger log = LoggerFactory.getLogger(ShipperPersonResource.class);

    private static final String ENTITY_NAME = "shipperPerson";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ShipperPersonService shipperPersonService;

    private final ShipperPersonRepository shipperPersonRepository;

    public ShipperPersonResource(ShipperPersonService shipperPersonService, ShipperPersonRepository shipperPersonRepository) {
        this.shipperPersonService = shipperPersonService;
        this.shipperPersonRepository = shipperPersonRepository;
    }

    /**
     * {@code POST  /shipper-people} : Create a new shipperPerson.
     *
     * @param shipperPersonDTO the shipperPersonDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new shipperPersonDTO, or with status {@code 400 (Bad Request)} if the shipperPerson has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ShipperPersonDTO> createShipperPerson(@Valid @RequestBody ShipperPersonDTO shipperPersonDTO)
        throws URISyntaxException {
        log.debug("REST request to save ShipperPerson : {}", shipperPersonDTO);
        if (shipperPersonDTO.getId() != null) {
            throw new BadRequestAlertException("A new shipperPerson cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ShipperPersonDTO result = shipperPersonService.save(shipperPersonDTO);
        return ResponseEntity
            .created(new URI("/api/shipper-people/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /shipper-people/:id} : Updates an existing shipperPerson.
     *
     * @param id the id of the shipperPersonDTO to save.
     * @param shipperPersonDTO the shipperPersonDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated shipperPersonDTO,
     * or with status {@code 400 (Bad Request)} if the shipperPersonDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the shipperPersonDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ShipperPersonDTO> updateShipperPerson(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ShipperPersonDTO shipperPersonDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ShipperPerson : {}, {}", id, shipperPersonDTO);
        if (shipperPersonDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, shipperPersonDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!shipperPersonRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ShipperPersonDTO result = shipperPersonService.update(shipperPersonDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, shipperPersonDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /shipper-people/:id} : Partial updates given fields of an existing shipperPerson, field will ignore if it is null
     *
     * @param id the id of the shipperPersonDTO to save.
     * @param shipperPersonDTO the shipperPersonDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated shipperPersonDTO,
     * or with status {@code 400 (Bad Request)} if the shipperPersonDTO is not valid,
     * or with status {@code 404 (Not Found)} if the shipperPersonDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the shipperPersonDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ShipperPersonDTO> partialUpdateShipperPerson(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ShipperPersonDTO shipperPersonDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ShipperPerson partially : {}, {}", id, shipperPersonDTO);
        if (shipperPersonDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, shipperPersonDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!shipperPersonRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ShipperPersonDTO> result = shipperPersonService.partialUpdate(shipperPersonDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, shipperPersonDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /shipper-people} : get all the shipperPeople.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of shipperPeople in body.
     */
    @GetMapping("")
    public ResponseEntity<List<ShipperPersonDTO>> getAllShipperPeople(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of ShipperPeople");
        Page<ShipperPersonDTO> page = shipperPersonService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /shipper-people/:id} : get the "id" shipperPerson.
     *
     * @param id the id of the shipperPersonDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the shipperPersonDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ShipperPersonDTO> getShipperPerson(@PathVariable("id") Long id) {
        log.debug("REST request to get ShipperPerson : {}", id);
        Optional<ShipperPersonDTO> shipperPersonDTO = shipperPersonService.findOne(id);
        return ResponseUtil.wrapOrNotFound(shipperPersonDTO);
    }

    /**
     * {@code DELETE  /shipper-people/:id} : delete the "id" shipperPerson.
     *
     * @param id the id of the shipperPersonDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShipperPerson(@PathVariable("id") Long id) {
        log.debug("REST request to delete ShipperPerson : {}", id);
        shipperPersonService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

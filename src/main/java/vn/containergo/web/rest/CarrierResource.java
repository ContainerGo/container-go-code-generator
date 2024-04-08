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
import vn.containergo.repository.CarrierRepository;
import vn.containergo.service.CarrierService;
import vn.containergo.service.dto.CarrierDTO;
import vn.containergo.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link vn.containergo.domain.Carrier}.
 */
@RestController
@RequestMapping("/api/carriers")
public class CarrierResource {

    private final Logger log = LoggerFactory.getLogger(CarrierResource.class);

    private static final String ENTITY_NAME = "carrier";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CarrierService carrierService;

    private final CarrierRepository carrierRepository;

    public CarrierResource(CarrierService carrierService, CarrierRepository carrierRepository) {
        this.carrierService = carrierService;
        this.carrierRepository = carrierRepository;
    }

    /**
     * {@code POST  /carriers} : Create a new carrier.
     *
     * @param carrierDTO the carrierDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new carrierDTO, or with status {@code 400 (Bad Request)} if the carrier has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<CarrierDTO> createCarrier(@Valid @RequestBody CarrierDTO carrierDTO) throws URISyntaxException {
        log.debug("REST request to save Carrier : {}", carrierDTO);
        if (carrierDTO.getId() != null) {
            throw new BadRequestAlertException("A new carrier cannot already have an ID", ENTITY_NAME, "idexists");
        }
        carrierDTO.setId(UUID.randomUUID());
        carrierDTO = carrierService.save(carrierDTO);
        return ResponseEntity.created(new URI("/api/carriers/" + carrierDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, carrierDTO.getId().toString()))
            .body(carrierDTO);
    }

    /**
     * {@code PUT  /carriers/:id} : Updates an existing carrier.
     *
     * @param id the id of the carrierDTO to save.
     * @param carrierDTO the carrierDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated carrierDTO,
     * or with status {@code 400 (Bad Request)} if the carrierDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the carrierDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CarrierDTO> updateCarrier(
        @PathVariable(value = "id", required = false) final UUID id,
        @Valid @RequestBody CarrierDTO carrierDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Carrier : {}, {}", id, carrierDTO);
        if (carrierDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, carrierDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!carrierRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        carrierDTO = carrierService.update(carrierDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, carrierDTO.getId().toString()))
            .body(carrierDTO);
    }

    /**
     * {@code PATCH  /carriers/:id} : Partial updates given fields of an existing carrier, field will ignore if it is null
     *
     * @param id the id of the carrierDTO to save.
     * @param carrierDTO the carrierDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated carrierDTO,
     * or with status {@code 400 (Bad Request)} if the carrierDTO is not valid,
     * or with status {@code 404 (Not Found)} if the carrierDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the carrierDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CarrierDTO> partialUpdateCarrier(
        @PathVariable(value = "id", required = false) final UUID id,
        @NotNull @RequestBody CarrierDTO carrierDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Carrier partially : {}, {}", id, carrierDTO);
        if (carrierDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, carrierDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!carrierRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CarrierDTO> result = carrierService.partialUpdate(carrierDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, carrierDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /carriers} : get all the carriers.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of carriers in body.
     */
    @GetMapping("")
    public ResponseEntity<List<CarrierDTO>> getAllCarriers(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Carriers");
        Page<CarrierDTO> page = carrierService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /carriers/:id} : get the "id" carrier.
     *
     * @param id the id of the carrierDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the carrierDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CarrierDTO> getCarrier(@PathVariable("id") UUID id) {
        log.debug("REST request to get Carrier : {}", id);
        Optional<CarrierDTO> carrierDTO = carrierService.findOne(id);
        return ResponseUtil.wrapOrNotFound(carrierDTO);
    }

    /**
     * {@code DELETE  /carriers/:id} : delete the "id" carrier.
     *
     * @param id the id of the carrierDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCarrier(@PathVariable("id") UUID id) {
        log.debug("REST request to delete Carrier : {}", id);
        carrierService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

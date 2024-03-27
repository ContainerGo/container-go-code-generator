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
import vn.containergo.repository.CarrierPersonRepository;
import vn.containergo.service.CarrierPersonService;
import vn.containergo.service.dto.CarrierPersonDTO;
import vn.containergo.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link vn.containergo.domain.CarrierPerson}.
 */
@RestController
@RequestMapping("/api/carrier-people")
public class CarrierPersonResource {

    private final Logger log = LoggerFactory.getLogger(CarrierPersonResource.class);

    private static final String ENTITY_NAME = "carrierPerson";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CarrierPersonService carrierPersonService;

    private final CarrierPersonRepository carrierPersonRepository;

    public CarrierPersonResource(CarrierPersonService carrierPersonService, CarrierPersonRepository carrierPersonRepository) {
        this.carrierPersonService = carrierPersonService;
        this.carrierPersonRepository = carrierPersonRepository;
    }

    /**
     * {@code POST  /carrier-people} : Create a new carrierPerson.
     *
     * @param carrierPersonDTO the carrierPersonDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new carrierPersonDTO, or with status {@code 400 (Bad Request)} if the carrierPerson has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<CarrierPersonDTO> createCarrierPerson(@Valid @RequestBody CarrierPersonDTO carrierPersonDTO)
        throws URISyntaxException {
        log.debug("REST request to save CarrierPerson : {}", carrierPersonDTO);
        if (carrierPersonDTO.getId() != null) {
            throw new BadRequestAlertException("A new carrierPerson cannot already have an ID", ENTITY_NAME, "idexists");
        }
        carrierPersonDTO = carrierPersonService.save(carrierPersonDTO);
        return ResponseEntity.created(new URI("/api/carrier-people/" + carrierPersonDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, carrierPersonDTO.getId().toString()))
            .body(carrierPersonDTO);
    }

    /**
     * {@code PUT  /carrier-people/:id} : Updates an existing carrierPerson.
     *
     * @param id the id of the carrierPersonDTO to save.
     * @param carrierPersonDTO the carrierPersonDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated carrierPersonDTO,
     * or with status {@code 400 (Bad Request)} if the carrierPersonDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the carrierPersonDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CarrierPersonDTO> updateCarrierPerson(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CarrierPersonDTO carrierPersonDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CarrierPerson : {}, {}", id, carrierPersonDTO);
        if (carrierPersonDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, carrierPersonDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!carrierPersonRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        carrierPersonDTO = carrierPersonService.update(carrierPersonDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, carrierPersonDTO.getId().toString()))
            .body(carrierPersonDTO);
    }

    /**
     * {@code PATCH  /carrier-people/:id} : Partial updates given fields of an existing carrierPerson, field will ignore if it is null
     *
     * @param id the id of the carrierPersonDTO to save.
     * @param carrierPersonDTO the carrierPersonDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated carrierPersonDTO,
     * or with status {@code 400 (Bad Request)} if the carrierPersonDTO is not valid,
     * or with status {@code 404 (Not Found)} if the carrierPersonDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the carrierPersonDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CarrierPersonDTO> partialUpdateCarrierPerson(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CarrierPersonDTO carrierPersonDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CarrierPerson partially : {}, {}", id, carrierPersonDTO);
        if (carrierPersonDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, carrierPersonDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!carrierPersonRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CarrierPersonDTO> result = carrierPersonService.partialUpdate(carrierPersonDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, carrierPersonDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /carrier-people} : get all the carrierPeople.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of carrierPeople in body.
     */
    @GetMapping("")
    public ResponseEntity<List<CarrierPersonDTO>> getAllCarrierPeople(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of CarrierPeople");
        Page<CarrierPersonDTO> page = carrierPersonService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /carrier-people/:id} : get the "id" carrierPerson.
     *
     * @param id the id of the carrierPersonDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the carrierPersonDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CarrierPersonDTO> getCarrierPerson(@PathVariable("id") Long id) {
        log.debug("REST request to get CarrierPerson : {}", id);
        Optional<CarrierPersonDTO> carrierPersonDTO = carrierPersonService.findOne(id);
        return ResponseUtil.wrapOrNotFound(carrierPersonDTO);
    }

    /**
     * {@code DELETE  /carrier-people/:id} : delete the "id" carrierPerson.
     *
     * @param id the id of the carrierPersonDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCarrierPerson(@PathVariable("id") Long id) {
        log.debug("REST request to delete CarrierPerson : {}", id);
        carrierPersonService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

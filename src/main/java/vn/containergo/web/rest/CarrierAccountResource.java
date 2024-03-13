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
import vn.containergo.repository.CarrierAccountRepository;
import vn.containergo.service.CarrierAccountService;
import vn.containergo.service.dto.CarrierAccountDTO;
import vn.containergo.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link vn.containergo.domain.CarrierAccount}.
 */
@RestController
@RequestMapping("/api/carrier-accounts")
public class CarrierAccountResource {

    private final Logger log = LoggerFactory.getLogger(CarrierAccountResource.class);

    private static final String ENTITY_NAME = "carrierAccount";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CarrierAccountService carrierAccountService;

    private final CarrierAccountRepository carrierAccountRepository;

    public CarrierAccountResource(CarrierAccountService carrierAccountService, CarrierAccountRepository carrierAccountRepository) {
        this.carrierAccountService = carrierAccountService;
        this.carrierAccountRepository = carrierAccountRepository;
    }

    /**
     * {@code POST  /carrier-accounts} : Create a new carrierAccount.
     *
     * @param carrierAccountDTO the carrierAccountDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new carrierAccountDTO, or with status {@code 400 (Bad Request)} if the carrierAccount has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<CarrierAccountDTO> createCarrierAccount(@Valid @RequestBody CarrierAccountDTO carrierAccountDTO)
        throws URISyntaxException {
        log.debug("REST request to save CarrierAccount : {}", carrierAccountDTO);
        if (carrierAccountDTO.getId() != null) {
            throw new BadRequestAlertException("A new carrierAccount cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CarrierAccountDTO result = carrierAccountService.save(carrierAccountDTO);
        return ResponseEntity
            .created(new URI("/api/carrier-accounts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /carrier-accounts/:id} : Updates an existing carrierAccount.
     *
     * @param id the id of the carrierAccountDTO to save.
     * @param carrierAccountDTO the carrierAccountDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated carrierAccountDTO,
     * or with status {@code 400 (Bad Request)} if the carrierAccountDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the carrierAccountDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CarrierAccountDTO> updateCarrierAccount(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CarrierAccountDTO carrierAccountDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CarrierAccount : {}, {}", id, carrierAccountDTO);
        if (carrierAccountDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, carrierAccountDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!carrierAccountRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CarrierAccountDTO result = carrierAccountService.update(carrierAccountDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, carrierAccountDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /carrier-accounts/:id} : Partial updates given fields of an existing carrierAccount, field will ignore if it is null
     *
     * @param id the id of the carrierAccountDTO to save.
     * @param carrierAccountDTO the carrierAccountDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated carrierAccountDTO,
     * or with status {@code 400 (Bad Request)} if the carrierAccountDTO is not valid,
     * or with status {@code 404 (Not Found)} if the carrierAccountDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the carrierAccountDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CarrierAccountDTO> partialUpdateCarrierAccount(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CarrierAccountDTO carrierAccountDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CarrierAccount partially : {}, {}", id, carrierAccountDTO);
        if (carrierAccountDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, carrierAccountDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!carrierAccountRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CarrierAccountDTO> result = carrierAccountService.partialUpdate(carrierAccountDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, carrierAccountDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /carrier-accounts} : get all the carrierAccounts.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of carrierAccounts in body.
     */
    @GetMapping("")
    public ResponseEntity<List<CarrierAccountDTO>> getAllCarrierAccounts(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of CarrierAccounts");
        Page<CarrierAccountDTO> page = carrierAccountService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /carrier-accounts/:id} : get the "id" carrierAccount.
     *
     * @param id the id of the carrierAccountDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the carrierAccountDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CarrierAccountDTO> getCarrierAccount(@PathVariable("id") Long id) {
        log.debug("REST request to get CarrierAccount : {}", id);
        Optional<CarrierAccountDTO> carrierAccountDTO = carrierAccountService.findOne(id);
        return ResponseUtil.wrapOrNotFound(carrierAccountDTO);
    }

    /**
     * {@code DELETE  /carrier-accounts/:id} : delete the "id" carrierAccount.
     *
     * @param id the id of the carrierAccountDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCarrierAccount(@PathVariable("id") Long id) {
        log.debug("REST request to delete CarrierAccount : {}", id);
        carrierAccountService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

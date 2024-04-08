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
import vn.containergo.repository.ProviceRepository;
import vn.containergo.service.ProviceService;
import vn.containergo.service.dto.ProviceDTO;
import vn.containergo.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link vn.containergo.domain.Provice}.
 */
@RestController
@RequestMapping("/api/provices")
public class ProviceResource {

    private final Logger log = LoggerFactory.getLogger(ProviceResource.class);

    private static final String ENTITY_NAME = "provice";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProviceService proviceService;

    private final ProviceRepository proviceRepository;

    public ProviceResource(ProviceService proviceService, ProviceRepository proviceRepository) {
        this.proviceService = proviceService;
        this.proviceRepository = proviceRepository;
    }

    /**
     * {@code POST  /provices} : Create a new provice.
     *
     * @param proviceDTO the proviceDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new proviceDTO, or with status {@code 400 (Bad Request)} if the provice has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ProviceDTO> createProvice(@Valid @RequestBody ProviceDTO proviceDTO) throws URISyntaxException {
        log.debug("REST request to save Provice : {}", proviceDTO);
        if (proviceDTO.getId() != null) {
            throw new BadRequestAlertException("A new provice cannot already have an ID", ENTITY_NAME, "idexists");
        }
        proviceDTO.setId(UUID.randomUUID());
        proviceDTO = proviceService.save(proviceDTO);
        return ResponseEntity.created(new URI("/api/provices/" + proviceDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, proviceDTO.getId().toString()))
            .body(proviceDTO);
    }

    /**
     * {@code PUT  /provices/:id} : Updates an existing provice.
     *
     * @param id the id of the proviceDTO to save.
     * @param proviceDTO the proviceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated proviceDTO,
     * or with status {@code 400 (Bad Request)} if the proviceDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the proviceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ProviceDTO> updateProvice(
        @PathVariable(value = "id", required = false) final UUID id,
        @Valid @RequestBody ProviceDTO proviceDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Provice : {}, {}", id, proviceDTO);
        if (proviceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, proviceDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!proviceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        proviceDTO = proviceService.update(proviceDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, proviceDTO.getId().toString()))
            .body(proviceDTO);
    }

    /**
     * {@code PATCH  /provices/:id} : Partial updates given fields of an existing provice, field will ignore if it is null
     *
     * @param id the id of the proviceDTO to save.
     * @param proviceDTO the proviceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated proviceDTO,
     * or with status {@code 400 (Bad Request)} if the proviceDTO is not valid,
     * or with status {@code 404 (Not Found)} if the proviceDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the proviceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ProviceDTO> partialUpdateProvice(
        @PathVariable(value = "id", required = false) final UUID id,
        @NotNull @RequestBody ProviceDTO proviceDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Provice partially : {}, {}", id, proviceDTO);
        if (proviceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, proviceDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!proviceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProviceDTO> result = proviceService.partialUpdate(proviceDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, proviceDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /provices} : get all the provices.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of provices in body.
     */
    @GetMapping("")
    public ResponseEntity<List<ProviceDTO>> getAllProvices(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Provices");
        Page<ProviceDTO> page = proviceService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /provices/:id} : get the "id" provice.
     *
     * @param id the id of the proviceDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the proviceDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProviceDTO> getProvice(@PathVariable("id") UUID id) {
        log.debug("REST request to get Provice : {}", id);
        Optional<ProviceDTO> proviceDTO = proviceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(proviceDTO);
    }

    /**
     * {@code DELETE  /provices/:id} : delete the "id" provice.
     *
     * @param id the id of the proviceDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProvice(@PathVariable("id") UUID id) {
        log.debug("REST request to delete Provice : {}", id);
        proviceService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

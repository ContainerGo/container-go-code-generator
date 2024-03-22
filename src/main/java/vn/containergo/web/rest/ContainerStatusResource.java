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
import vn.containergo.repository.ContainerStatusRepository;
import vn.containergo.service.ContainerStatusService;
import vn.containergo.service.dto.ContainerStatusDTO;
import vn.containergo.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link vn.containergo.domain.ContainerStatus}.
 */
@RestController
@RequestMapping("/api/container-statuses")
public class ContainerStatusResource {

    private final Logger log = LoggerFactory.getLogger(ContainerStatusResource.class);

    private static final String ENTITY_NAME = "containerStatus";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ContainerStatusService containerStatusService;

    private final ContainerStatusRepository containerStatusRepository;

    public ContainerStatusResource(ContainerStatusService containerStatusService, ContainerStatusRepository containerStatusRepository) {
        this.containerStatusService = containerStatusService;
        this.containerStatusRepository = containerStatusRepository;
    }

    /**
     * {@code POST  /container-statuses} : Create a new containerStatus.
     *
     * @param containerStatusDTO the containerStatusDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new containerStatusDTO, or with status {@code 400 (Bad Request)} if the containerStatus has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ContainerStatusDTO> createContainerStatus(@Valid @RequestBody ContainerStatusDTO containerStatusDTO)
        throws URISyntaxException {
        log.debug("REST request to save ContainerStatus : {}", containerStatusDTO);
        if (containerStatusDTO.getId() != null) {
            throw new BadRequestAlertException("A new containerStatus cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ContainerStatusDTO result = containerStatusService.save(containerStatusDTO);
        return ResponseEntity
            .created(new URI("/api/container-statuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /container-statuses/:id} : Updates an existing containerStatus.
     *
     * @param id the id of the containerStatusDTO to save.
     * @param containerStatusDTO the containerStatusDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated containerStatusDTO,
     * or with status {@code 400 (Bad Request)} if the containerStatusDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the containerStatusDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ContainerStatusDTO> updateContainerStatus(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ContainerStatusDTO containerStatusDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ContainerStatus : {}, {}", id, containerStatusDTO);
        if (containerStatusDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, containerStatusDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!containerStatusRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ContainerStatusDTO result = containerStatusService.update(containerStatusDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, containerStatusDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /container-statuses/:id} : Partial updates given fields of an existing containerStatus, field will ignore if it is null
     *
     * @param id the id of the containerStatusDTO to save.
     * @param containerStatusDTO the containerStatusDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated containerStatusDTO,
     * or with status {@code 400 (Bad Request)} if the containerStatusDTO is not valid,
     * or with status {@code 404 (Not Found)} if the containerStatusDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the containerStatusDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ContainerStatusDTO> partialUpdateContainerStatus(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ContainerStatusDTO containerStatusDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ContainerStatus partially : {}, {}", id, containerStatusDTO);
        if (containerStatusDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, containerStatusDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!containerStatusRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ContainerStatusDTO> result = containerStatusService.partialUpdate(containerStatusDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, containerStatusDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /container-statuses} : get all the containerStatuses.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of containerStatuses in body.
     */
    @GetMapping("")
    public ResponseEntity<List<ContainerStatusDTO>> getAllContainerStatuses(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of ContainerStatuses");
        Page<ContainerStatusDTO> page = containerStatusService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /container-statuses/:id} : get the "id" containerStatus.
     *
     * @param id the id of the containerStatusDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the containerStatusDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ContainerStatusDTO> getContainerStatus(@PathVariable("id") Long id) {
        log.debug("REST request to get ContainerStatus : {}", id);
        Optional<ContainerStatusDTO> containerStatusDTO = containerStatusService.findOne(id);
        return ResponseUtil.wrapOrNotFound(containerStatusDTO);
    }

    /**
     * {@code DELETE  /container-statuses/:id} : delete the "id" containerStatus.
     *
     * @param id the id of the containerStatusDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContainerStatus(@PathVariable("id") Long id) {
        log.debug("REST request to delete ContainerStatus : {}", id);
        containerStatusService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

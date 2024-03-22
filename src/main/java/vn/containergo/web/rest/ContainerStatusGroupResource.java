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
import vn.containergo.repository.ContainerStatusGroupRepository;
import vn.containergo.service.ContainerStatusGroupService;
import vn.containergo.service.dto.ContainerStatusGroupDTO;
import vn.containergo.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link vn.containergo.domain.ContainerStatusGroup}.
 */
@RestController
@RequestMapping("/api/container-status-groups")
public class ContainerStatusGroupResource {

    private final Logger log = LoggerFactory.getLogger(ContainerStatusGroupResource.class);

    private static final String ENTITY_NAME = "containerStatusGroup";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ContainerStatusGroupService containerStatusGroupService;

    private final ContainerStatusGroupRepository containerStatusGroupRepository;

    public ContainerStatusGroupResource(
        ContainerStatusGroupService containerStatusGroupService,
        ContainerStatusGroupRepository containerStatusGroupRepository
    ) {
        this.containerStatusGroupService = containerStatusGroupService;
        this.containerStatusGroupRepository = containerStatusGroupRepository;
    }

    /**
     * {@code POST  /container-status-groups} : Create a new containerStatusGroup.
     *
     * @param containerStatusGroupDTO the containerStatusGroupDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new containerStatusGroupDTO, or with status {@code 400 (Bad Request)} if the containerStatusGroup has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ContainerStatusGroupDTO> createContainerStatusGroup(
        @Valid @RequestBody ContainerStatusGroupDTO containerStatusGroupDTO
    ) throws URISyntaxException {
        log.debug("REST request to save ContainerStatusGroup : {}", containerStatusGroupDTO);
        if (containerStatusGroupDTO.getId() != null) {
            throw new BadRequestAlertException("A new containerStatusGroup cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ContainerStatusGroupDTO result = containerStatusGroupService.save(containerStatusGroupDTO);
        return ResponseEntity
            .created(new URI("/api/container-status-groups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /container-status-groups/:id} : Updates an existing containerStatusGroup.
     *
     * @param id the id of the containerStatusGroupDTO to save.
     * @param containerStatusGroupDTO the containerStatusGroupDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated containerStatusGroupDTO,
     * or with status {@code 400 (Bad Request)} if the containerStatusGroupDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the containerStatusGroupDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ContainerStatusGroupDTO> updateContainerStatusGroup(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ContainerStatusGroupDTO containerStatusGroupDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ContainerStatusGroup : {}, {}", id, containerStatusGroupDTO);
        if (containerStatusGroupDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, containerStatusGroupDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!containerStatusGroupRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ContainerStatusGroupDTO result = containerStatusGroupService.update(containerStatusGroupDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, containerStatusGroupDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /container-status-groups/:id} : Partial updates given fields of an existing containerStatusGroup, field will ignore if it is null
     *
     * @param id the id of the containerStatusGroupDTO to save.
     * @param containerStatusGroupDTO the containerStatusGroupDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated containerStatusGroupDTO,
     * or with status {@code 400 (Bad Request)} if the containerStatusGroupDTO is not valid,
     * or with status {@code 404 (Not Found)} if the containerStatusGroupDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the containerStatusGroupDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ContainerStatusGroupDTO> partialUpdateContainerStatusGroup(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ContainerStatusGroupDTO containerStatusGroupDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ContainerStatusGroup partially : {}, {}", id, containerStatusGroupDTO);
        if (containerStatusGroupDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, containerStatusGroupDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!containerStatusGroupRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ContainerStatusGroupDTO> result = containerStatusGroupService.partialUpdate(containerStatusGroupDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, containerStatusGroupDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /container-status-groups} : get all the containerStatusGroups.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of containerStatusGroups in body.
     */
    @GetMapping("")
    public ResponseEntity<List<ContainerStatusGroupDTO>> getAllContainerStatusGroups(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of ContainerStatusGroups");
        Page<ContainerStatusGroupDTO> page = containerStatusGroupService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /container-status-groups/:id} : get the "id" containerStatusGroup.
     *
     * @param id the id of the containerStatusGroupDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the containerStatusGroupDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ContainerStatusGroupDTO> getContainerStatusGroup(@PathVariable("id") Long id) {
        log.debug("REST request to get ContainerStatusGroup : {}", id);
        Optional<ContainerStatusGroupDTO> containerStatusGroupDTO = containerStatusGroupService.findOne(id);
        return ResponseUtil.wrapOrNotFound(containerStatusGroupDTO);
    }

    /**
     * {@code DELETE  /container-status-groups/:id} : delete the "id" containerStatusGroup.
     *
     * @param id the id of the containerStatusGroupDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContainerStatusGroup(@PathVariable("id") Long id) {
        log.debug("REST request to delete ContainerStatusGroup : {}", id);
        containerStatusGroupService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

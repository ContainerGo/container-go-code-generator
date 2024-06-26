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
import vn.containergo.repository.ContainerRepository;
import vn.containergo.service.ContainerService;
import vn.containergo.service.dto.ContainerDTO;
import vn.containergo.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link vn.containergo.domain.Container}.
 */
@RestController
@RequestMapping("/api/containers")
public class ContainerResource {

    private final Logger log = LoggerFactory.getLogger(ContainerResource.class);

    private static final String ENTITY_NAME = "container";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ContainerService containerService;

    private final ContainerRepository containerRepository;

    public ContainerResource(ContainerService containerService, ContainerRepository containerRepository) {
        this.containerService = containerService;
        this.containerRepository = containerRepository;
    }

    /**
     * {@code POST  /containers} : Create a new container.
     *
     * @param containerDTO the containerDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new containerDTO, or with status {@code 400 (Bad Request)} if the container has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ContainerDTO> createContainer(@Valid @RequestBody ContainerDTO containerDTO) throws URISyntaxException {
        log.debug("REST request to save Container : {}", containerDTO);
        if (containerDTO.getId() != null) {
            throw new BadRequestAlertException("A new container cannot already have an ID", ENTITY_NAME, "idexists");
        }
        containerDTO.setId(UUID.randomUUID());
        containerDTO = containerService.save(containerDTO);
        return ResponseEntity.created(new URI("/api/containers/" + containerDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, containerDTO.getId().toString()))
            .body(containerDTO);
    }

    /**
     * {@code PUT  /containers/:id} : Updates an existing container.
     *
     * @param id the id of the containerDTO to save.
     * @param containerDTO the containerDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated containerDTO,
     * or with status {@code 400 (Bad Request)} if the containerDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the containerDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ContainerDTO> updateContainer(
        @PathVariable(value = "id", required = false) final UUID id,
        @Valid @RequestBody ContainerDTO containerDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Container : {}, {}", id, containerDTO);
        if (containerDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, containerDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!containerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        containerDTO = containerService.update(containerDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, containerDTO.getId().toString()))
            .body(containerDTO);
    }

    /**
     * {@code PATCH  /containers/:id} : Partial updates given fields of an existing container, field will ignore if it is null
     *
     * @param id the id of the containerDTO to save.
     * @param containerDTO the containerDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated containerDTO,
     * or with status {@code 400 (Bad Request)} if the containerDTO is not valid,
     * or with status {@code 404 (Not Found)} if the containerDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the containerDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ContainerDTO> partialUpdateContainer(
        @PathVariable(value = "id", required = false) final UUID id,
        @NotNull @RequestBody ContainerDTO containerDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Container partially : {}, {}", id, containerDTO);
        if (containerDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, containerDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!containerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ContainerDTO> result = containerService.partialUpdate(containerDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, containerDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /containers} : get all the containers.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of containers in body.
     */
    @GetMapping("")
    public ResponseEntity<List<ContainerDTO>> getAllContainers(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Containers");
        Page<ContainerDTO> page = containerService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /containers/:id} : get the "id" container.
     *
     * @param id the id of the containerDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the containerDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ContainerDTO> getContainer(@PathVariable("id") UUID id) {
        log.debug("REST request to get Container : {}", id);
        Optional<ContainerDTO> containerDTO = containerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(containerDTO);
    }

    /**
     * {@code DELETE  /containers/:id} : delete the "id" container.
     *
     * @param id the id of the containerDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContainer(@PathVariable("id") UUID id) {
        log.debug("REST request to delete Container : {}", id);
        containerService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

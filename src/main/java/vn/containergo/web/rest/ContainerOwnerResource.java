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
import vn.containergo.repository.ContainerOwnerRepository;
import vn.containergo.service.ContainerOwnerService;
import vn.containergo.service.dto.ContainerOwnerDTO;
import vn.containergo.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link vn.containergo.domain.ContainerOwner}.
 */
@RestController
@RequestMapping("/api/container-owners")
public class ContainerOwnerResource {

    private final Logger log = LoggerFactory.getLogger(ContainerOwnerResource.class);

    private static final String ENTITY_NAME = "containerOwner";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ContainerOwnerService containerOwnerService;

    private final ContainerOwnerRepository containerOwnerRepository;

    public ContainerOwnerResource(ContainerOwnerService containerOwnerService, ContainerOwnerRepository containerOwnerRepository) {
        this.containerOwnerService = containerOwnerService;
        this.containerOwnerRepository = containerOwnerRepository;
    }

    /**
     * {@code POST  /container-owners} : Create a new containerOwner.
     *
     * @param containerOwnerDTO the containerOwnerDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new containerOwnerDTO, or with status {@code 400 (Bad Request)} if the containerOwner has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ContainerOwnerDTO> createContainerOwner(@Valid @RequestBody ContainerOwnerDTO containerOwnerDTO)
        throws URISyntaxException {
        log.debug("REST request to save ContainerOwner : {}", containerOwnerDTO);
        if (containerOwnerDTO.getId() != null) {
            throw new BadRequestAlertException("A new containerOwner cannot already have an ID", ENTITY_NAME, "idexists");
        }
        containerOwnerDTO.setId(UUID.randomUUID());
        containerOwnerDTO = containerOwnerService.save(containerOwnerDTO);
        return ResponseEntity.created(new URI("/api/container-owners/" + containerOwnerDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, containerOwnerDTO.getId().toString()))
            .body(containerOwnerDTO);
    }

    /**
     * {@code PUT  /container-owners/:id} : Updates an existing containerOwner.
     *
     * @param id the id of the containerOwnerDTO to save.
     * @param containerOwnerDTO the containerOwnerDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated containerOwnerDTO,
     * or with status {@code 400 (Bad Request)} if the containerOwnerDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the containerOwnerDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ContainerOwnerDTO> updateContainerOwner(
        @PathVariable(value = "id", required = false) final UUID id,
        @Valid @RequestBody ContainerOwnerDTO containerOwnerDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ContainerOwner : {}, {}", id, containerOwnerDTO);
        if (containerOwnerDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, containerOwnerDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!containerOwnerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        containerOwnerDTO = containerOwnerService.update(containerOwnerDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, containerOwnerDTO.getId().toString()))
            .body(containerOwnerDTO);
    }

    /**
     * {@code PATCH  /container-owners/:id} : Partial updates given fields of an existing containerOwner, field will ignore if it is null
     *
     * @param id the id of the containerOwnerDTO to save.
     * @param containerOwnerDTO the containerOwnerDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated containerOwnerDTO,
     * or with status {@code 400 (Bad Request)} if the containerOwnerDTO is not valid,
     * or with status {@code 404 (Not Found)} if the containerOwnerDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the containerOwnerDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ContainerOwnerDTO> partialUpdateContainerOwner(
        @PathVariable(value = "id", required = false) final UUID id,
        @NotNull @RequestBody ContainerOwnerDTO containerOwnerDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ContainerOwner partially : {}, {}", id, containerOwnerDTO);
        if (containerOwnerDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, containerOwnerDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!containerOwnerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ContainerOwnerDTO> result = containerOwnerService.partialUpdate(containerOwnerDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, containerOwnerDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /container-owners} : get all the containerOwners.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of containerOwners in body.
     */
    @GetMapping("")
    public ResponseEntity<List<ContainerOwnerDTO>> getAllContainerOwners(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of ContainerOwners");
        Page<ContainerOwnerDTO> page = containerOwnerService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /container-owners/:id} : get the "id" containerOwner.
     *
     * @param id the id of the containerOwnerDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the containerOwnerDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ContainerOwnerDTO> getContainerOwner(@PathVariable("id") UUID id) {
        log.debug("REST request to get ContainerOwner : {}", id);
        Optional<ContainerOwnerDTO> containerOwnerDTO = containerOwnerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(containerOwnerDTO);
    }

    /**
     * {@code DELETE  /container-owners/:id} : delete the "id" containerOwner.
     *
     * @param id the id of the containerOwnerDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContainerOwner(@PathVariable("id") UUID id) {
        log.debug("REST request to delete ContainerOwner : {}", id);
        containerOwnerService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

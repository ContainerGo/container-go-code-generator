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
import vn.containergo.repository.ContainerTypeRepository;
import vn.containergo.service.ContainerTypeService;
import vn.containergo.service.dto.ContainerTypeDTO;
import vn.containergo.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link vn.containergo.domain.ContainerType}.
 */
@RestController
@RequestMapping("/api/container-types")
public class ContainerTypeResource {

    private final Logger log = LoggerFactory.getLogger(ContainerTypeResource.class);

    private static final String ENTITY_NAME = "containerType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ContainerTypeService containerTypeService;

    private final ContainerTypeRepository containerTypeRepository;

    public ContainerTypeResource(ContainerTypeService containerTypeService, ContainerTypeRepository containerTypeRepository) {
        this.containerTypeService = containerTypeService;
        this.containerTypeRepository = containerTypeRepository;
    }

    /**
     * {@code POST  /container-types} : Create a new containerType.
     *
     * @param containerTypeDTO the containerTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new containerTypeDTO, or with status {@code 400 (Bad Request)} if the containerType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ContainerTypeDTO> createContainerType(@Valid @RequestBody ContainerTypeDTO containerTypeDTO)
        throws URISyntaxException {
        log.debug("REST request to save ContainerType : {}", containerTypeDTO);
        if (containerTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new containerType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        containerTypeDTO.setId(UUID.randomUUID());
        containerTypeDTO = containerTypeService.save(containerTypeDTO);
        return ResponseEntity.created(new URI("/api/container-types/" + containerTypeDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, containerTypeDTO.getId().toString()))
            .body(containerTypeDTO);
    }

    /**
     * {@code PUT  /container-types/:id} : Updates an existing containerType.
     *
     * @param id the id of the containerTypeDTO to save.
     * @param containerTypeDTO the containerTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated containerTypeDTO,
     * or with status {@code 400 (Bad Request)} if the containerTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the containerTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ContainerTypeDTO> updateContainerType(
        @PathVariable(value = "id", required = false) final UUID id,
        @Valid @RequestBody ContainerTypeDTO containerTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ContainerType : {}, {}", id, containerTypeDTO);
        if (containerTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, containerTypeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!containerTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        containerTypeDTO = containerTypeService.update(containerTypeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, containerTypeDTO.getId().toString()))
            .body(containerTypeDTO);
    }

    /**
     * {@code PATCH  /container-types/:id} : Partial updates given fields of an existing containerType, field will ignore if it is null
     *
     * @param id the id of the containerTypeDTO to save.
     * @param containerTypeDTO the containerTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated containerTypeDTO,
     * or with status {@code 400 (Bad Request)} if the containerTypeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the containerTypeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the containerTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ContainerTypeDTO> partialUpdateContainerType(
        @PathVariable(value = "id", required = false) final UUID id,
        @NotNull @RequestBody ContainerTypeDTO containerTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ContainerType partially : {}, {}", id, containerTypeDTO);
        if (containerTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, containerTypeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!containerTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ContainerTypeDTO> result = containerTypeService.partialUpdate(containerTypeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, containerTypeDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /container-types} : get all the containerTypes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of containerTypes in body.
     */
    @GetMapping("")
    public ResponseEntity<List<ContainerTypeDTO>> getAllContainerTypes(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of ContainerTypes");
        Page<ContainerTypeDTO> page = containerTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /container-types/:id} : get the "id" containerType.
     *
     * @param id the id of the containerTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the containerTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ContainerTypeDTO> getContainerType(@PathVariable("id") UUID id) {
        log.debug("REST request to get ContainerType : {}", id);
        Optional<ContainerTypeDTO> containerTypeDTO = containerTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(containerTypeDTO);
    }

    /**
     * {@code DELETE  /container-types/:id} : delete the "id" containerType.
     *
     * @param id the id of the containerTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContainerType(@PathVariable("id") UUID id) {
        log.debug("REST request to delete ContainerType : {}", id);
        containerTypeService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

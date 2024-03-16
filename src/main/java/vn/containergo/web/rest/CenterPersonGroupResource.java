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
import vn.containergo.repository.CenterPersonGroupRepository;
import vn.containergo.service.CenterPersonGroupService;
import vn.containergo.service.dto.CenterPersonGroupDTO;
import vn.containergo.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link vn.containergo.domain.CenterPersonGroup}.
 */
@RestController
@RequestMapping("/api/center-person-groups")
public class CenterPersonGroupResource {

    private final Logger log = LoggerFactory.getLogger(CenterPersonGroupResource.class);

    private static final String ENTITY_NAME = "centerPersonGroup";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CenterPersonGroupService centerPersonGroupService;

    private final CenterPersonGroupRepository centerPersonGroupRepository;

    public CenterPersonGroupResource(
        CenterPersonGroupService centerPersonGroupService,
        CenterPersonGroupRepository centerPersonGroupRepository
    ) {
        this.centerPersonGroupService = centerPersonGroupService;
        this.centerPersonGroupRepository = centerPersonGroupRepository;
    }

    /**
     * {@code POST  /center-person-groups} : Create a new centerPersonGroup.
     *
     * @param centerPersonGroupDTO the centerPersonGroupDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new centerPersonGroupDTO, or with status {@code 400 (Bad Request)} if the centerPersonGroup has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<CenterPersonGroupDTO> createCenterPersonGroup(@Valid @RequestBody CenterPersonGroupDTO centerPersonGroupDTO)
        throws URISyntaxException {
        log.debug("REST request to save CenterPersonGroup : {}", centerPersonGroupDTO);
        if (centerPersonGroupDTO.getId() != null) {
            throw new BadRequestAlertException("A new centerPersonGroup cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CenterPersonGroupDTO result = centerPersonGroupService.save(centerPersonGroupDTO);
        return ResponseEntity
            .created(new URI("/api/center-person-groups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /center-person-groups/:id} : Updates an existing centerPersonGroup.
     *
     * @param id the id of the centerPersonGroupDTO to save.
     * @param centerPersonGroupDTO the centerPersonGroupDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated centerPersonGroupDTO,
     * or with status {@code 400 (Bad Request)} if the centerPersonGroupDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the centerPersonGroupDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CenterPersonGroupDTO> updateCenterPersonGroup(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CenterPersonGroupDTO centerPersonGroupDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CenterPersonGroup : {}, {}", id, centerPersonGroupDTO);
        if (centerPersonGroupDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, centerPersonGroupDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!centerPersonGroupRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CenterPersonGroupDTO result = centerPersonGroupService.update(centerPersonGroupDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, centerPersonGroupDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /center-person-groups/:id} : Partial updates given fields of an existing centerPersonGroup, field will ignore if it is null
     *
     * @param id the id of the centerPersonGroupDTO to save.
     * @param centerPersonGroupDTO the centerPersonGroupDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated centerPersonGroupDTO,
     * or with status {@code 400 (Bad Request)} if the centerPersonGroupDTO is not valid,
     * or with status {@code 404 (Not Found)} if the centerPersonGroupDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the centerPersonGroupDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CenterPersonGroupDTO> partialUpdateCenterPersonGroup(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CenterPersonGroupDTO centerPersonGroupDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CenterPersonGroup partially : {}, {}", id, centerPersonGroupDTO);
        if (centerPersonGroupDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, centerPersonGroupDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!centerPersonGroupRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CenterPersonGroupDTO> result = centerPersonGroupService.partialUpdate(centerPersonGroupDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, centerPersonGroupDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /center-person-groups} : get all the centerPersonGroups.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of centerPersonGroups in body.
     */
    @GetMapping("")
    public ResponseEntity<List<CenterPersonGroupDTO>> getAllCenterPersonGroups(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of CenterPersonGroups");
        Page<CenterPersonGroupDTO> page = centerPersonGroupService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /center-person-groups/:id} : get the "id" centerPersonGroup.
     *
     * @param id the id of the centerPersonGroupDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the centerPersonGroupDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CenterPersonGroupDTO> getCenterPersonGroup(@PathVariable("id") Long id) {
        log.debug("REST request to get CenterPersonGroup : {}", id);
        Optional<CenterPersonGroupDTO> centerPersonGroupDTO = centerPersonGroupService.findOne(id);
        return ResponseUtil.wrapOrNotFound(centerPersonGroupDTO);
    }

    /**
     * {@code DELETE  /center-person-groups/:id} : delete the "id" centerPersonGroup.
     *
     * @param id the id of the centerPersonGroupDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCenterPersonGroup(@PathVariable("id") Long id) {
        log.debug("REST request to delete CenterPersonGroup : {}", id);
        centerPersonGroupService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

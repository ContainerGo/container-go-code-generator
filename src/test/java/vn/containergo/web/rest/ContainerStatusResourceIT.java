package vn.containergo.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import vn.containergo.IntegrationTest;
import vn.containergo.domain.ContainerStatus;
import vn.containergo.repository.ContainerStatusRepository;
import vn.containergo.service.dto.ContainerStatusDTO;
import vn.containergo.service.mapper.ContainerStatusMapper;

/**
 * Integration tests for the {@link ContainerStatusResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ContainerStatusResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/container-statuses";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ContainerStatusRepository containerStatusRepository;

    @Autowired
    private ContainerStatusMapper containerStatusMapper;

    @Autowired
    private MockMvc restContainerStatusMockMvc;

    private ContainerStatus containerStatus;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ContainerStatus createEntity() {
        ContainerStatus containerStatus = new ContainerStatus().code(DEFAULT_CODE).name(DEFAULT_NAME).description(DEFAULT_DESCRIPTION);
        return containerStatus;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ContainerStatus createUpdatedEntity() {
        ContainerStatus containerStatus = new ContainerStatus().code(UPDATED_CODE).name(UPDATED_NAME).description(UPDATED_DESCRIPTION);
        return containerStatus;
    }

    @BeforeEach
    public void initTest() {
        containerStatusRepository.deleteAll();
        containerStatus = createEntity();
    }

    @Test
    void createContainerStatus() throws Exception {
        int databaseSizeBeforeCreate = containerStatusRepository.findAll().size();
        // Create the ContainerStatus
        ContainerStatusDTO containerStatusDTO = containerStatusMapper.toDto(containerStatus);
        restContainerStatusMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(containerStatusDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ContainerStatus in the database
        List<ContainerStatus> containerStatusList = containerStatusRepository.findAll();
        assertThat(containerStatusList).hasSize(databaseSizeBeforeCreate + 1);
        ContainerStatus testContainerStatus = containerStatusList.get(containerStatusList.size() - 1);
        assertThat(testContainerStatus.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testContainerStatus.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testContainerStatus.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    void createContainerStatusWithExistingId() throws Exception {
        // Create the ContainerStatus with an existing ID
        containerStatus.setId(1L);
        ContainerStatusDTO containerStatusDTO = containerStatusMapper.toDto(containerStatus);

        int databaseSizeBeforeCreate = containerStatusRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restContainerStatusMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(containerStatusDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ContainerStatus in the database
        List<ContainerStatus> containerStatusList = containerStatusRepository.findAll();
        assertThat(containerStatusList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = containerStatusRepository.findAll().size();
        // set the field null
        containerStatus.setCode(null);

        // Create the ContainerStatus, which fails.
        ContainerStatusDTO containerStatusDTO = containerStatusMapper.toDto(containerStatus);

        restContainerStatusMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(containerStatusDTO))
            )
            .andExpect(status().isBadRequest());

        List<ContainerStatus> containerStatusList = containerStatusRepository.findAll();
        assertThat(containerStatusList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = containerStatusRepository.findAll().size();
        // set the field null
        containerStatus.setName(null);

        // Create the ContainerStatus, which fails.
        ContainerStatusDTO containerStatusDTO = containerStatusMapper.toDto(containerStatus);

        restContainerStatusMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(containerStatusDTO))
            )
            .andExpect(status().isBadRequest());

        List<ContainerStatus> containerStatusList = containerStatusRepository.findAll();
        assertThat(containerStatusList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void getAllContainerStatuses() throws Exception {
        // Initialize the database
        containerStatusRepository.save(containerStatus);

        // Get all the containerStatusList
        restContainerStatusMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(containerStatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    void getContainerStatus() throws Exception {
        // Initialize the database
        containerStatusRepository.save(containerStatus);

        // Get the containerStatus
        restContainerStatusMockMvc
            .perform(get(ENTITY_API_URL_ID, containerStatus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(containerStatus.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    void getNonExistingContainerStatus() throws Exception {
        // Get the containerStatus
        restContainerStatusMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingContainerStatus() throws Exception {
        // Initialize the database
        containerStatusRepository.save(containerStatus);

        int databaseSizeBeforeUpdate = containerStatusRepository.findAll().size();

        // Update the containerStatus
        ContainerStatus updatedContainerStatus = containerStatusRepository.findById(containerStatus.getId()).orElseThrow();
        updatedContainerStatus.code(UPDATED_CODE).name(UPDATED_NAME).description(UPDATED_DESCRIPTION);
        ContainerStatusDTO containerStatusDTO = containerStatusMapper.toDto(updatedContainerStatus);

        restContainerStatusMockMvc
            .perform(
                put(ENTITY_API_URL_ID, containerStatusDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(containerStatusDTO))
            )
            .andExpect(status().isOk());

        // Validate the ContainerStatus in the database
        List<ContainerStatus> containerStatusList = containerStatusRepository.findAll();
        assertThat(containerStatusList).hasSize(databaseSizeBeforeUpdate);
        ContainerStatus testContainerStatus = containerStatusList.get(containerStatusList.size() - 1);
        assertThat(testContainerStatus.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testContainerStatus.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testContainerStatus.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    void putNonExistingContainerStatus() throws Exception {
        int databaseSizeBeforeUpdate = containerStatusRepository.findAll().size();
        containerStatus.setId(longCount.incrementAndGet());

        // Create the ContainerStatus
        ContainerStatusDTO containerStatusDTO = containerStatusMapper.toDto(containerStatus);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContainerStatusMockMvc
            .perform(
                put(ENTITY_API_URL_ID, containerStatusDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(containerStatusDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ContainerStatus in the database
        List<ContainerStatus> containerStatusList = containerStatusRepository.findAll();
        assertThat(containerStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchContainerStatus() throws Exception {
        int databaseSizeBeforeUpdate = containerStatusRepository.findAll().size();
        containerStatus.setId(longCount.incrementAndGet());

        // Create the ContainerStatus
        ContainerStatusDTO containerStatusDTO = containerStatusMapper.toDto(containerStatus);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContainerStatusMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(containerStatusDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ContainerStatus in the database
        List<ContainerStatus> containerStatusList = containerStatusRepository.findAll();
        assertThat(containerStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamContainerStatus() throws Exception {
        int databaseSizeBeforeUpdate = containerStatusRepository.findAll().size();
        containerStatus.setId(longCount.incrementAndGet());

        // Create the ContainerStatus
        ContainerStatusDTO containerStatusDTO = containerStatusMapper.toDto(containerStatus);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContainerStatusMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(containerStatusDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ContainerStatus in the database
        List<ContainerStatus> containerStatusList = containerStatusRepository.findAll();
        assertThat(containerStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateContainerStatusWithPatch() throws Exception {
        // Initialize the database
        containerStatusRepository.save(containerStatus);

        int databaseSizeBeforeUpdate = containerStatusRepository.findAll().size();

        // Update the containerStatus using partial update
        ContainerStatus partialUpdatedContainerStatus = new ContainerStatus();
        partialUpdatedContainerStatus.setId(containerStatus.getId());

        partialUpdatedContainerStatus.code(UPDATED_CODE);

        restContainerStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedContainerStatus.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedContainerStatus))
            )
            .andExpect(status().isOk());

        // Validate the ContainerStatus in the database
        List<ContainerStatus> containerStatusList = containerStatusRepository.findAll();
        assertThat(containerStatusList).hasSize(databaseSizeBeforeUpdate);
        ContainerStatus testContainerStatus = containerStatusList.get(containerStatusList.size() - 1);
        assertThat(testContainerStatus.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testContainerStatus.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testContainerStatus.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    void fullUpdateContainerStatusWithPatch() throws Exception {
        // Initialize the database
        containerStatusRepository.save(containerStatus);

        int databaseSizeBeforeUpdate = containerStatusRepository.findAll().size();

        // Update the containerStatus using partial update
        ContainerStatus partialUpdatedContainerStatus = new ContainerStatus();
        partialUpdatedContainerStatus.setId(containerStatus.getId());

        partialUpdatedContainerStatus.code(UPDATED_CODE).name(UPDATED_NAME).description(UPDATED_DESCRIPTION);

        restContainerStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedContainerStatus.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedContainerStatus))
            )
            .andExpect(status().isOk());

        // Validate the ContainerStatus in the database
        List<ContainerStatus> containerStatusList = containerStatusRepository.findAll();
        assertThat(containerStatusList).hasSize(databaseSizeBeforeUpdate);
        ContainerStatus testContainerStatus = containerStatusList.get(containerStatusList.size() - 1);
        assertThat(testContainerStatus.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testContainerStatus.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testContainerStatus.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    void patchNonExistingContainerStatus() throws Exception {
        int databaseSizeBeforeUpdate = containerStatusRepository.findAll().size();
        containerStatus.setId(longCount.incrementAndGet());

        // Create the ContainerStatus
        ContainerStatusDTO containerStatusDTO = containerStatusMapper.toDto(containerStatus);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContainerStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, containerStatusDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(containerStatusDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ContainerStatus in the database
        List<ContainerStatus> containerStatusList = containerStatusRepository.findAll();
        assertThat(containerStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchContainerStatus() throws Exception {
        int databaseSizeBeforeUpdate = containerStatusRepository.findAll().size();
        containerStatus.setId(longCount.incrementAndGet());

        // Create the ContainerStatus
        ContainerStatusDTO containerStatusDTO = containerStatusMapper.toDto(containerStatus);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContainerStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(containerStatusDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ContainerStatus in the database
        List<ContainerStatus> containerStatusList = containerStatusRepository.findAll();
        assertThat(containerStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamContainerStatus() throws Exception {
        int databaseSizeBeforeUpdate = containerStatusRepository.findAll().size();
        containerStatus.setId(longCount.incrementAndGet());

        // Create the ContainerStatus
        ContainerStatusDTO containerStatusDTO = containerStatusMapper.toDto(containerStatus);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContainerStatusMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(containerStatusDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ContainerStatus in the database
        List<ContainerStatus> containerStatusList = containerStatusRepository.findAll();
        assertThat(containerStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteContainerStatus() throws Exception {
        // Initialize the database
        containerStatusRepository.save(containerStatus);

        int databaseSizeBeforeDelete = containerStatusRepository.findAll().size();

        // Delete the containerStatus
        restContainerStatusMockMvc
            .perform(delete(ENTITY_API_URL_ID, containerStatus.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ContainerStatus> containerStatusList = containerStatusRepository.findAll();
        assertThat(containerStatusList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

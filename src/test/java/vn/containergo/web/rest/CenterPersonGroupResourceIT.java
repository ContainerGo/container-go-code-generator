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
import vn.containergo.domain.CenterPersonGroup;
import vn.containergo.repository.CenterPersonGroupRepository;
import vn.containergo.service.dto.CenterPersonGroupDTO;
import vn.containergo.service.mapper.CenterPersonGroupMapper;

/**
 * Integration tests for the {@link CenterPersonGroupResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CenterPersonGroupResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/center-person-groups";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CenterPersonGroupRepository centerPersonGroupRepository;

    @Autowired
    private CenterPersonGroupMapper centerPersonGroupMapper;

    @Autowired
    private MockMvc restCenterPersonGroupMockMvc;

    private CenterPersonGroup centerPersonGroup;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CenterPersonGroup createEntity() {
        CenterPersonGroup centerPersonGroup = new CenterPersonGroup().name(DEFAULT_NAME).description(DEFAULT_DESCRIPTION);
        return centerPersonGroup;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CenterPersonGroup createUpdatedEntity() {
        CenterPersonGroup centerPersonGroup = new CenterPersonGroup().name(UPDATED_NAME).description(UPDATED_DESCRIPTION);
        return centerPersonGroup;
    }

    @BeforeEach
    public void initTest() {
        centerPersonGroupRepository.deleteAll();
        centerPersonGroup = createEntity();
    }

    @Test
    void createCenterPersonGroup() throws Exception {
        int databaseSizeBeforeCreate = centerPersonGroupRepository.findAll().size();
        // Create the CenterPersonGroup
        CenterPersonGroupDTO centerPersonGroupDTO = centerPersonGroupMapper.toDto(centerPersonGroup);
        restCenterPersonGroupMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(centerPersonGroupDTO))
            )
            .andExpect(status().isCreated());

        // Validate the CenterPersonGroup in the database
        List<CenterPersonGroup> centerPersonGroupList = centerPersonGroupRepository.findAll();
        assertThat(centerPersonGroupList).hasSize(databaseSizeBeforeCreate + 1);
        CenterPersonGroup testCenterPersonGroup = centerPersonGroupList.get(centerPersonGroupList.size() - 1);
        assertThat(testCenterPersonGroup.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCenterPersonGroup.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    void createCenterPersonGroupWithExistingId() throws Exception {
        // Create the CenterPersonGroup with an existing ID
        centerPersonGroup.setId(1L);
        CenterPersonGroupDTO centerPersonGroupDTO = centerPersonGroupMapper.toDto(centerPersonGroup);

        int databaseSizeBeforeCreate = centerPersonGroupRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCenterPersonGroupMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(centerPersonGroupDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CenterPersonGroup in the database
        List<CenterPersonGroup> centerPersonGroupList = centerPersonGroupRepository.findAll();
        assertThat(centerPersonGroupList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = centerPersonGroupRepository.findAll().size();
        // set the field null
        centerPersonGroup.setName(null);

        // Create the CenterPersonGroup, which fails.
        CenterPersonGroupDTO centerPersonGroupDTO = centerPersonGroupMapper.toDto(centerPersonGroup);

        restCenterPersonGroupMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(centerPersonGroupDTO))
            )
            .andExpect(status().isBadRequest());

        List<CenterPersonGroup> centerPersonGroupList = centerPersonGroupRepository.findAll();
        assertThat(centerPersonGroupList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void getAllCenterPersonGroups() throws Exception {
        // Initialize the database
        centerPersonGroupRepository.save(centerPersonGroup);

        // Get all the centerPersonGroupList
        restCenterPersonGroupMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(centerPersonGroup.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    void getCenterPersonGroup() throws Exception {
        // Initialize the database
        centerPersonGroupRepository.save(centerPersonGroup);

        // Get the centerPersonGroup
        restCenterPersonGroupMockMvc
            .perform(get(ENTITY_API_URL_ID, centerPersonGroup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(centerPersonGroup.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    void getNonExistingCenterPersonGroup() throws Exception {
        // Get the centerPersonGroup
        restCenterPersonGroupMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingCenterPersonGroup() throws Exception {
        // Initialize the database
        centerPersonGroupRepository.save(centerPersonGroup);

        int databaseSizeBeforeUpdate = centerPersonGroupRepository.findAll().size();

        // Update the centerPersonGroup
        CenterPersonGroup updatedCenterPersonGroup = centerPersonGroupRepository.findById(centerPersonGroup.getId()).orElseThrow();
        updatedCenterPersonGroup.name(UPDATED_NAME).description(UPDATED_DESCRIPTION);
        CenterPersonGroupDTO centerPersonGroupDTO = centerPersonGroupMapper.toDto(updatedCenterPersonGroup);

        restCenterPersonGroupMockMvc
            .perform(
                put(ENTITY_API_URL_ID, centerPersonGroupDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(centerPersonGroupDTO))
            )
            .andExpect(status().isOk());

        // Validate the CenterPersonGroup in the database
        List<CenterPersonGroup> centerPersonGroupList = centerPersonGroupRepository.findAll();
        assertThat(centerPersonGroupList).hasSize(databaseSizeBeforeUpdate);
        CenterPersonGroup testCenterPersonGroup = centerPersonGroupList.get(centerPersonGroupList.size() - 1);
        assertThat(testCenterPersonGroup.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCenterPersonGroup.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    void putNonExistingCenterPersonGroup() throws Exception {
        int databaseSizeBeforeUpdate = centerPersonGroupRepository.findAll().size();
        centerPersonGroup.setId(longCount.incrementAndGet());

        // Create the CenterPersonGroup
        CenterPersonGroupDTO centerPersonGroupDTO = centerPersonGroupMapper.toDto(centerPersonGroup);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCenterPersonGroupMockMvc
            .perform(
                put(ENTITY_API_URL_ID, centerPersonGroupDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(centerPersonGroupDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CenterPersonGroup in the database
        List<CenterPersonGroup> centerPersonGroupList = centerPersonGroupRepository.findAll();
        assertThat(centerPersonGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchCenterPersonGroup() throws Exception {
        int databaseSizeBeforeUpdate = centerPersonGroupRepository.findAll().size();
        centerPersonGroup.setId(longCount.incrementAndGet());

        // Create the CenterPersonGroup
        CenterPersonGroupDTO centerPersonGroupDTO = centerPersonGroupMapper.toDto(centerPersonGroup);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCenterPersonGroupMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(centerPersonGroupDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CenterPersonGroup in the database
        List<CenterPersonGroup> centerPersonGroupList = centerPersonGroupRepository.findAll();
        assertThat(centerPersonGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamCenterPersonGroup() throws Exception {
        int databaseSizeBeforeUpdate = centerPersonGroupRepository.findAll().size();
        centerPersonGroup.setId(longCount.incrementAndGet());

        // Create the CenterPersonGroup
        CenterPersonGroupDTO centerPersonGroupDTO = centerPersonGroupMapper.toDto(centerPersonGroup);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCenterPersonGroupMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(centerPersonGroupDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CenterPersonGroup in the database
        List<CenterPersonGroup> centerPersonGroupList = centerPersonGroupRepository.findAll();
        assertThat(centerPersonGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateCenterPersonGroupWithPatch() throws Exception {
        // Initialize the database
        centerPersonGroupRepository.save(centerPersonGroup);

        int databaseSizeBeforeUpdate = centerPersonGroupRepository.findAll().size();

        // Update the centerPersonGroup using partial update
        CenterPersonGroup partialUpdatedCenterPersonGroup = new CenterPersonGroup();
        partialUpdatedCenterPersonGroup.setId(centerPersonGroup.getId());

        restCenterPersonGroupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCenterPersonGroup.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCenterPersonGroup))
            )
            .andExpect(status().isOk());

        // Validate the CenterPersonGroup in the database
        List<CenterPersonGroup> centerPersonGroupList = centerPersonGroupRepository.findAll();
        assertThat(centerPersonGroupList).hasSize(databaseSizeBeforeUpdate);
        CenterPersonGroup testCenterPersonGroup = centerPersonGroupList.get(centerPersonGroupList.size() - 1);
        assertThat(testCenterPersonGroup.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCenterPersonGroup.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    void fullUpdateCenterPersonGroupWithPatch() throws Exception {
        // Initialize the database
        centerPersonGroupRepository.save(centerPersonGroup);

        int databaseSizeBeforeUpdate = centerPersonGroupRepository.findAll().size();

        // Update the centerPersonGroup using partial update
        CenterPersonGroup partialUpdatedCenterPersonGroup = new CenterPersonGroup();
        partialUpdatedCenterPersonGroup.setId(centerPersonGroup.getId());

        partialUpdatedCenterPersonGroup.name(UPDATED_NAME).description(UPDATED_DESCRIPTION);

        restCenterPersonGroupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCenterPersonGroup.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCenterPersonGroup))
            )
            .andExpect(status().isOk());

        // Validate the CenterPersonGroup in the database
        List<CenterPersonGroup> centerPersonGroupList = centerPersonGroupRepository.findAll();
        assertThat(centerPersonGroupList).hasSize(databaseSizeBeforeUpdate);
        CenterPersonGroup testCenterPersonGroup = centerPersonGroupList.get(centerPersonGroupList.size() - 1);
        assertThat(testCenterPersonGroup.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCenterPersonGroup.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    void patchNonExistingCenterPersonGroup() throws Exception {
        int databaseSizeBeforeUpdate = centerPersonGroupRepository.findAll().size();
        centerPersonGroup.setId(longCount.incrementAndGet());

        // Create the CenterPersonGroup
        CenterPersonGroupDTO centerPersonGroupDTO = centerPersonGroupMapper.toDto(centerPersonGroup);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCenterPersonGroupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, centerPersonGroupDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(centerPersonGroupDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CenterPersonGroup in the database
        List<CenterPersonGroup> centerPersonGroupList = centerPersonGroupRepository.findAll();
        assertThat(centerPersonGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchCenterPersonGroup() throws Exception {
        int databaseSizeBeforeUpdate = centerPersonGroupRepository.findAll().size();
        centerPersonGroup.setId(longCount.incrementAndGet());

        // Create the CenterPersonGroup
        CenterPersonGroupDTO centerPersonGroupDTO = centerPersonGroupMapper.toDto(centerPersonGroup);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCenterPersonGroupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(centerPersonGroupDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CenterPersonGroup in the database
        List<CenterPersonGroup> centerPersonGroupList = centerPersonGroupRepository.findAll();
        assertThat(centerPersonGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamCenterPersonGroup() throws Exception {
        int databaseSizeBeforeUpdate = centerPersonGroupRepository.findAll().size();
        centerPersonGroup.setId(longCount.incrementAndGet());

        // Create the CenterPersonGroup
        CenterPersonGroupDTO centerPersonGroupDTO = centerPersonGroupMapper.toDto(centerPersonGroup);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCenterPersonGroupMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(centerPersonGroupDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CenterPersonGroup in the database
        List<CenterPersonGroup> centerPersonGroupList = centerPersonGroupRepository.findAll();
        assertThat(centerPersonGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteCenterPersonGroup() throws Exception {
        // Initialize the database
        centerPersonGroupRepository.save(centerPersonGroup);

        int databaseSizeBeforeDelete = centerPersonGroupRepository.findAll().size();

        // Delete the centerPersonGroup
        restCenterPersonGroupMockMvc
            .perform(delete(ENTITY_API_URL_ID, centerPersonGroup.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CenterPersonGroup> centerPersonGroupList = centerPersonGroupRepository.findAll();
        assertThat(centerPersonGroupList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

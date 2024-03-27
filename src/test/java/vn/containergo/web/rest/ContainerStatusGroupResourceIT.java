package vn.containergo.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static vn.containergo.domain.ContainerStatusGroupAsserts.*;
import static vn.containergo.web.rest.TestUtil.createUpdateProxyForBean;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import vn.containergo.domain.ContainerStatusGroup;
import vn.containergo.repository.ContainerStatusGroupRepository;
import vn.containergo.service.dto.ContainerStatusGroupDTO;
import vn.containergo.service.mapper.ContainerStatusGroupMapper;

/**
 * Integration tests for the {@link ContainerStatusGroupResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ContainerStatusGroupResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/container-status-groups";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ContainerStatusGroupRepository containerStatusGroupRepository;

    @Autowired
    private ContainerStatusGroupMapper containerStatusGroupMapper;

    @Autowired
    private MockMvc restContainerStatusGroupMockMvc;

    private ContainerStatusGroup containerStatusGroup;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ContainerStatusGroup createEntity() {
        ContainerStatusGroup containerStatusGroup = new ContainerStatusGroup()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return containerStatusGroup;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ContainerStatusGroup createUpdatedEntity() {
        ContainerStatusGroup containerStatusGroup = new ContainerStatusGroup()
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        return containerStatusGroup;
    }

    @BeforeEach
    public void initTest() {
        containerStatusGroupRepository.deleteAll();
        containerStatusGroup = createEntity();
    }

    @Test
    void createContainerStatusGroup() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the ContainerStatusGroup
        ContainerStatusGroupDTO containerStatusGroupDTO = containerStatusGroupMapper.toDto(containerStatusGroup);
        var returnedContainerStatusGroupDTO = om.readValue(
            restContainerStatusGroupMockMvc
                .perform(
                    post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(containerStatusGroupDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ContainerStatusGroupDTO.class
        );

        // Validate the ContainerStatusGroup in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedContainerStatusGroup = containerStatusGroupMapper.toEntity(returnedContainerStatusGroupDTO);
        assertContainerStatusGroupUpdatableFieldsEquals(
            returnedContainerStatusGroup,
            getPersistedContainerStatusGroup(returnedContainerStatusGroup)
        );
    }

    @Test
    void createContainerStatusGroupWithExistingId() throws Exception {
        // Create the ContainerStatusGroup with an existing ID
        containerStatusGroup.setId(1L);
        ContainerStatusGroupDTO containerStatusGroupDTO = containerStatusGroupMapper.toDto(containerStatusGroup);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restContainerStatusGroupMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(containerStatusGroupDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ContainerStatusGroup in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    void checkCodeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        containerStatusGroup.setCode(null);

        // Create the ContainerStatusGroup, which fails.
        ContainerStatusGroupDTO containerStatusGroupDTO = containerStatusGroupMapper.toDto(containerStatusGroup);

        restContainerStatusGroupMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(containerStatusGroupDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        containerStatusGroup.setName(null);

        // Create the ContainerStatusGroup, which fails.
        ContainerStatusGroupDTO containerStatusGroupDTO = containerStatusGroupMapper.toDto(containerStatusGroup);

        restContainerStatusGroupMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(containerStatusGroupDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void getAllContainerStatusGroups() throws Exception {
        // Initialize the database
        containerStatusGroupRepository.save(containerStatusGroup);

        // Get all the containerStatusGroupList
        restContainerStatusGroupMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(containerStatusGroup.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    void getContainerStatusGroup() throws Exception {
        // Initialize the database
        containerStatusGroupRepository.save(containerStatusGroup);

        // Get the containerStatusGroup
        restContainerStatusGroupMockMvc
            .perform(get(ENTITY_API_URL_ID, containerStatusGroup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(containerStatusGroup.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    void getNonExistingContainerStatusGroup() throws Exception {
        // Get the containerStatusGroup
        restContainerStatusGroupMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingContainerStatusGroup() throws Exception {
        // Initialize the database
        containerStatusGroupRepository.save(containerStatusGroup);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the containerStatusGroup
        ContainerStatusGroup updatedContainerStatusGroup = containerStatusGroupRepository
            .findById(containerStatusGroup.getId())
            .orElseThrow();
        updatedContainerStatusGroup.code(UPDATED_CODE).name(UPDATED_NAME).description(UPDATED_DESCRIPTION);
        ContainerStatusGroupDTO containerStatusGroupDTO = containerStatusGroupMapper.toDto(updatedContainerStatusGroup);

        restContainerStatusGroupMockMvc
            .perform(
                put(ENTITY_API_URL_ID, containerStatusGroupDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(containerStatusGroupDTO))
            )
            .andExpect(status().isOk());

        // Validate the ContainerStatusGroup in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedContainerStatusGroupToMatchAllProperties(updatedContainerStatusGroup);
    }

    @Test
    void putNonExistingContainerStatusGroup() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        containerStatusGroup.setId(longCount.incrementAndGet());

        // Create the ContainerStatusGroup
        ContainerStatusGroupDTO containerStatusGroupDTO = containerStatusGroupMapper.toDto(containerStatusGroup);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContainerStatusGroupMockMvc
            .perform(
                put(ENTITY_API_URL_ID, containerStatusGroupDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(containerStatusGroupDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ContainerStatusGroup in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchContainerStatusGroup() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        containerStatusGroup.setId(longCount.incrementAndGet());

        // Create the ContainerStatusGroup
        ContainerStatusGroupDTO containerStatusGroupDTO = containerStatusGroupMapper.toDto(containerStatusGroup);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContainerStatusGroupMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(containerStatusGroupDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ContainerStatusGroup in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamContainerStatusGroup() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        containerStatusGroup.setId(longCount.incrementAndGet());

        // Create the ContainerStatusGroup
        ContainerStatusGroupDTO containerStatusGroupDTO = containerStatusGroupMapper.toDto(containerStatusGroup);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContainerStatusGroupMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(containerStatusGroupDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ContainerStatusGroup in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateContainerStatusGroupWithPatch() throws Exception {
        // Initialize the database
        containerStatusGroupRepository.save(containerStatusGroup);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the containerStatusGroup using partial update
        ContainerStatusGroup partialUpdatedContainerStatusGroup = new ContainerStatusGroup();
        partialUpdatedContainerStatusGroup.setId(containerStatusGroup.getId());

        restContainerStatusGroupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedContainerStatusGroup.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedContainerStatusGroup))
            )
            .andExpect(status().isOk());

        // Validate the ContainerStatusGroup in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertContainerStatusGroupUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedContainerStatusGroup, containerStatusGroup),
            getPersistedContainerStatusGroup(containerStatusGroup)
        );
    }

    @Test
    void fullUpdateContainerStatusGroupWithPatch() throws Exception {
        // Initialize the database
        containerStatusGroupRepository.save(containerStatusGroup);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the containerStatusGroup using partial update
        ContainerStatusGroup partialUpdatedContainerStatusGroup = new ContainerStatusGroup();
        partialUpdatedContainerStatusGroup.setId(containerStatusGroup.getId());

        partialUpdatedContainerStatusGroup.code(UPDATED_CODE).name(UPDATED_NAME).description(UPDATED_DESCRIPTION);

        restContainerStatusGroupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedContainerStatusGroup.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedContainerStatusGroup))
            )
            .andExpect(status().isOk());

        // Validate the ContainerStatusGroup in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertContainerStatusGroupUpdatableFieldsEquals(
            partialUpdatedContainerStatusGroup,
            getPersistedContainerStatusGroup(partialUpdatedContainerStatusGroup)
        );
    }

    @Test
    void patchNonExistingContainerStatusGroup() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        containerStatusGroup.setId(longCount.incrementAndGet());

        // Create the ContainerStatusGroup
        ContainerStatusGroupDTO containerStatusGroupDTO = containerStatusGroupMapper.toDto(containerStatusGroup);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContainerStatusGroupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, containerStatusGroupDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(containerStatusGroupDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ContainerStatusGroup in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchContainerStatusGroup() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        containerStatusGroup.setId(longCount.incrementAndGet());

        // Create the ContainerStatusGroup
        ContainerStatusGroupDTO containerStatusGroupDTO = containerStatusGroupMapper.toDto(containerStatusGroup);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContainerStatusGroupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(containerStatusGroupDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ContainerStatusGroup in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamContainerStatusGroup() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        containerStatusGroup.setId(longCount.incrementAndGet());

        // Create the ContainerStatusGroup
        ContainerStatusGroupDTO containerStatusGroupDTO = containerStatusGroupMapper.toDto(containerStatusGroup);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContainerStatusGroupMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(containerStatusGroupDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ContainerStatusGroup in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteContainerStatusGroup() throws Exception {
        // Initialize the database
        containerStatusGroupRepository.save(containerStatusGroup);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the containerStatusGroup
        restContainerStatusGroupMockMvc
            .perform(delete(ENTITY_API_URL_ID, containerStatusGroup.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return containerStatusGroupRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected ContainerStatusGroup getPersistedContainerStatusGroup(ContainerStatusGroup containerStatusGroup) {
        return containerStatusGroupRepository.findById(containerStatusGroup.getId()).orElseThrow();
    }

    protected void assertPersistedContainerStatusGroupToMatchAllProperties(ContainerStatusGroup expectedContainerStatusGroup) {
        assertContainerStatusGroupAllPropertiesEquals(
            expectedContainerStatusGroup,
            getPersistedContainerStatusGroup(expectedContainerStatusGroup)
        );
    }

    protected void assertPersistedContainerStatusGroupToMatchUpdatableProperties(ContainerStatusGroup expectedContainerStatusGroup) {
        assertContainerStatusGroupAllUpdatablePropertiesEquals(
            expectedContainerStatusGroup,
            getPersistedContainerStatusGroup(expectedContainerStatusGroup)
        );
    }
}

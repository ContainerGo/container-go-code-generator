package vn.containergo.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static vn.containergo.domain.ContainerTypeAsserts.*;
import static vn.containergo.web.rest.TestUtil.createUpdateProxyForBean;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import vn.containergo.IntegrationTest;
import vn.containergo.domain.ContainerType;
import vn.containergo.repository.ContainerTypeRepository;
import vn.containergo.service.dto.ContainerTypeDTO;
import vn.containergo.service.mapper.ContainerTypeMapper;

/**
 * Integration tests for the {@link ContainerTypeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ContainerTypeResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/container-types";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ContainerTypeRepository containerTypeRepository;

    @Autowired
    private ContainerTypeMapper containerTypeMapper;

    @Autowired
    private MockMvc restContainerTypeMockMvc;

    private ContainerType containerType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ContainerType createEntity() {
        ContainerType containerType = new ContainerType().code(DEFAULT_CODE).name(DEFAULT_NAME).description(DEFAULT_DESCRIPTION);
        return containerType;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ContainerType createUpdatedEntity() {
        ContainerType containerType = new ContainerType().code(UPDATED_CODE).name(UPDATED_NAME).description(UPDATED_DESCRIPTION);
        return containerType;
    }

    @BeforeEach
    public void initTest() {
        containerTypeRepository.deleteAll();
        containerType = createEntity();
    }

    @Test
    void createContainerType() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the ContainerType
        ContainerTypeDTO containerTypeDTO = containerTypeMapper.toDto(containerType);
        var returnedContainerTypeDTO = om.readValue(
            restContainerTypeMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(containerTypeDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ContainerTypeDTO.class
        );

        // Validate the ContainerType in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedContainerType = containerTypeMapper.toEntity(returnedContainerTypeDTO);
        assertContainerTypeUpdatableFieldsEquals(returnedContainerType, getPersistedContainerType(returnedContainerType));
    }

    @Test
    void createContainerTypeWithExistingId() throws Exception {
        // Create the ContainerType with an existing ID
        containerType.setId(UUID.randomUUID());
        ContainerTypeDTO containerTypeDTO = containerTypeMapper.toDto(containerType);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restContainerTypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(containerTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ContainerType in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    void checkCodeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        containerType.setCode(null);

        // Create the ContainerType, which fails.
        ContainerTypeDTO containerTypeDTO = containerTypeMapper.toDto(containerType);

        restContainerTypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(containerTypeDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        containerType.setName(null);

        // Create the ContainerType, which fails.
        ContainerTypeDTO containerTypeDTO = containerTypeMapper.toDto(containerType);

        restContainerTypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(containerTypeDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void getAllContainerTypes() throws Exception {
        // Initialize the database
        containerType.setId(UUID.randomUUID());
        containerTypeRepository.save(containerType);

        // Get all the containerTypeList
        restContainerTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(containerType.getId().toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    void getContainerType() throws Exception {
        // Initialize the database
        containerType.setId(UUID.randomUUID());
        containerTypeRepository.save(containerType);

        // Get the containerType
        restContainerTypeMockMvc
            .perform(get(ENTITY_API_URL_ID, containerType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(containerType.getId().toString()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    void getNonExistingContainerType() throws Exception {
        // Get the containerType
        restContainerTypeMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    void putExistingContainerType() throws Exception {
        // Initialize the database
        containerType.setId(UUID.randomUUID());
        containerTypeRepository.save(containerType);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the containerType
        ContainerType updatedContainerType = containerTypeRepository.findById(containerType.getId()).orElseThrow();
        updatedContainerType.code(UPDATED_CODE).name(UPDATED_NAME).description(UPDATED_DESCRIPTION);
        ContainerTypeDTO containerTypeDTO = containerTypeMapper.toDto(updatedContainerType);

        restContainerTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, containerTypeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(containerTypeDTO))
            )
            .andExpect(status().isOk());

        // Validate the ContainerType in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedContainerTypeToMatchAllProperties(updatedContainerType);
    }

    @Test
    void putNonExistingContainerType() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        containerType.setId(UUID.randomUUID());

        // Create the ContainerType
        ContainerTypeDTO containerTypeDTO = containerTypeMapper.toDto(containerType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContainerTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, containerTypeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(containerTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ContainerType in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchContainerType() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        containerType.setId(UUID.randomUUID());

        // Create the ContainerType
        ContainerTypeDTO containerTypeDTO = containerTypeMapper.toDto(containerType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContainerTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(containerTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ContainerType in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamContainerType() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        containerType.setId(UUID.randomUUID());

        // Create the ContainerType
        ContainerTypeDTO containerTypeDTO = containerTypeMapper.toDto(containerType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContainerTypeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(containerTypeDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ContainerType in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateContainerTypeWithPatch() throws Exception {
        // Initialize the database
        containerType.setId(UUID.randomUUID());
        containerTypeRepository.save(containerType);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the containerType using partial update
        ContainerType partialUpdatedContainerType = new ContainerType();
        partialUpdatedContainerType.setId(containerType.getId());

        restContainerTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedContainerType.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedContainerType))
            )
            .andExpect(status().isOk());

        // Validate the ContainerType in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertContainerTypeUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedContainerType, containerType),
            getPersistedContainerType(containerType)
        );
    }

    @Test
    void fullUpdateContainerTypeWithPatch() throws Exception {
        // Initialize the database
        containerType.setId(UUID.randomUUID());
        containerTypeRepository.save(containerType);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the containerType using partial update
        ContainerType partialUpdatedContainerType = new ContainerType();
        partialUpdatedContainerType.setId(containerType.getId());

        partialUpdatedContainerType.code(UPDATED_CODE).name(UPDATED_NAME).description(UPDATED_DESCRIPTION);

        restContainerTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedContainerType.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedContainerType))
            )
            .andExpect(status().isOk());

        // Validate the ContainerType in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertContainerTypeUpdatableFieldsEquals(partialUpdatedContainerType, getPersistedContainerType(partialUpdatedContainerType));
    }

    @Test
    void patchNonExistingContainerType() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        containerType.setId(UUID.randomUUID());

        // Create the ContainerType
        ContainerTypeDTO containerTypeDTO = containerTypeMapper.toDto(containerType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContainerTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, containerTypeDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(containerTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ContainerType in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchContainerType() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        containerType.setId(UUID.randomUUID());

        // Create the ContainerType
        ContainerTypeDTO containerTypeDTO = containerTypeMapper.toDto(containerType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContainerTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(containerTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ContainerType in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamContainerType() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        containerType.setId(UUID.randomUUID());

        // Create the ContainerType
        ContainerTypeDTO containerTypeDTO = containerTypeMapper.toDto(containerType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContainerTypeMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(containerTypeDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ContainerType in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteContainerType() throws Exception {
        // Initialize the database
        containerType.setId(UUID.randomUUID());
        containerTypeRepository.save(containerType);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the containerType
        restContainerTypeMockMvc
            .perform(delete(ENTITY_API_URL_ID, containerType.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return containerTypeRepository.count();
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

    protected ContainerType getPersistedContainerType(ContainerType containerType) {
        return containerTypeRepository.findById(containerType.getId()).orElseThrow();
    }

    protected void assertPersistedContainerTypeToMatchAllProperties(ContainerType expectedContainerType) {
        assertContainerTypeAllPropertiesEquals(expectedContainerType, getPersistedContainerType(expectedContainerType));
    }

    protected void assertPersistedContainerTypeToMatchUpdatableProperties(ContainerType expectedContainerType) {
        assertContainerTypeAllUpdatablePropertiesEquals(expectedContainerType, getPersistedContainerType(expectedContainerType));
    }
}

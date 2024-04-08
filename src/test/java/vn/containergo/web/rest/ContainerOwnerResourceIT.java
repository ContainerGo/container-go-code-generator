package vn.containergo.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static vn.containergo.domain.ContainerOwnerAsserts.*;
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
import vn.containergo.domain.ContainerOwner;
import vn.containergo.repository.ContainerOwnerRepository;
import vn.containergo.service.dto.ContainerOwnerDTO;
import vn.containergo.service.mapper.ContainerOwnerMapper;

/**
 * Integration tests for the {@link ContainerOwnerResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ContainerOwnerResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/container-owners";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ContainerOwnerRepository containerOwnerRepository;

    @Autowired
    private ContainerOwnerMapper containerOwnerMapper;

    @Autowired
    private MockMvc restContainerOwnerMockMvc;

    private ContainerOwner containerOwner;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ContainerOwner createEntity() {
        ContainerOwner containerOwner = new ContainerOwner()
            .name(DEFAULT_NAME)
            .phone(DEFAULT_PHONE)
            .email(DEFAULT_EMAIL)
            .address(DEFAULT_ADDRESS);
        return containerOwner;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ContainerOwner createUpdatedEntity() {
        ContainerOwner containerOwner = new ContainerOwner()
            .name(UPDATED_NAME)
            .phone(UPDATED_PHONE)
            .email(UPDATED_EMAIL)
            .address(UPDATED_ADDRESS);
        return containerOwner;
    }

    @BeforeEach
    public void initTest() {
        containerOwnerRepository.deleteAll();
        containerOwner = createEntity();
    }

    @Test
    void createContainerOwner() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the ContainerOwner
        ContainerOwnerDTO containerOwnerDTO = containerOwnerMapper.toDto(containerOwner);
        var returnedContainerOwnerDTO = om.readValue(
            restContainerOwnerMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(containerOwnerDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ContainerOwnerDTO.class
        );

        // Validate the ContainerOwner in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedContainerOwner = containerOwnerMapper.toEntity(returnedContainerOwnerDTO);
        assertContainerOwnerUpdatableFieldsEquals(returnedContainerOwner, getPersistedContainerOwner(returnedContainerOwner));
    }

    @Test
    void createContainerOwnerWithExistingId() throws Exception {
        // Create the ContainerOwner with an existing ID
        containerOwner.setId(UUID.randomUUID());
        ContainerOwnerDTO containerOwnerDTO = containerOwnerMapper.toDto(containerOwner);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restContainerOwnerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(containerOwnerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ContainerOwner in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    void checkNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        containerOwner.setName(null);

        // Create the ContainerOwner, which fails.
        ContainerOwnerDTO containerOwnerDTO = containerOwnerMapper.toDto(containerOwner);

        restContainerOwnerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(containerOwnerDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void getAllContainerOwners() throws Exception {
        // Initialize the database
        containerOwner.setId(UUID.randomUUID());
        containerOwnerRepository.save(containerOwner);

        // Get all the containerOwnerList
        restContainerOwnerMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(containerOwner.getId().toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)));
    }

    @Test
    void getContainerOwner() throws Exception {
        // Initialize the database
        containerOwner.setId(UUID.randomUUID());
        containerOwnerRepository.save(containerOwner);

        // Get the containerOwner
        restContainerOwnerMockMvc
            .perform(get(ENTITY_API_URL_ID, containerOwner.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(containerOwner.getId().toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS));
    }

    @Test
    void getNonExistingContainerOwner() throws Exception {
        // Get the containerOwner
        restContainerOwnerMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    void putExistingContainerOwner() throws Exception {
        // Initialize the database
        containerOwner.setId(UUID.randomUUID());
        containerOwnerRepository.save(containerOwner);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the containerOwner
        ContainerOwner updatedContainerOwner = containerOwnerRepository.findById(containerOwner.getId()).orElseThrow();
        updatedContainerOwner.name(UPDATED_NAME).phone(UPDATED_PHONE).email(UPDATED_EMAIL).address(UPDATED_ADDRESS);
        ContainerOwnerDTO containerOwnerDTO = containerOwnerMapper.toDto(updatedContainerOwner);

        restContainerOwnerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, containerOwnerDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(containerOwnerDTO))
            )
            .andExpect(status().isOk());

        // Validate the ContainerOwner in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedContainerOwnerToMatchAllProperties(updatedContainerOwner);
    }

    @Test
    void putNonExistingContainerOwner() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        containerOwner.setId(UUID.randomUUID());

        // Create the ContainerOwner
        ContainerOwnerDTO containerOwnerDTO = containerOwnerMapper.toDto(containerOwner);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContainerOwnerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, containerOwnerDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(containerOwnerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ContainerOwner in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchContainerOwner() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        containerOwner.setId(UUID.randomUUID());

        // Create the ContainerOwner
        ContainerOwnerDTO containerOwnerDTO = containerOwnerMapper.toDto(containerOwner);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContainerOwnerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(containerOwnerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ContainerOwner in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamContainerOwner() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        containerOwner.setId(UUID.randomUUID());

        // Create the ContainerOwner
        ContainerOwnerDTO containerOwnerDTO = containerOwnerMapper.toDto(containerOwner);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContainerOwnerMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(containerOwnerDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ContainerOwner in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateContainerOwnerWithPatch() throws Exception {
        // Initialize the database
        containerOwner.setId(UUID.randomUUID());
        containerOwnerRepository.save(containerOwner);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the containerOwner using partial update
        ContainerOwner partialUpdatedContainerOwner = new ContainerOwner();
        partialUpdatedContainerOwner.setId(containerOwner.getId());

        partialUpdatedContainerOwner.name(UPDATED_NAME).phone(UPDATED_PHONE).email(UPDATED_EMAIL);

        restContainerOwnerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedContainerOwner.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedContainerOwner))
            )
            .andExpect(status().isOk());

        // Validate the ContainerOwner in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertContainerOwnerUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedContainerOwner, containerOwner),
            getPersistedContainerOwner(containerOwner)
        );
    }

    @Test
    void fullUpdateContainerOwnerWithPatch() throws Exception {
        // Initialize the database
        containerOwner.setId(UUID.randomUUID());
        containerOwnerRepository.save(containerOwner);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the containerOwner using partial update
        ContainerOwner partialUpdatedContainerOwner = new ContainerOwner();
        partialUpdatedContainerOwner.setId(containerOwner.getId());

        partialUpdatedContainerOwner.name(UPDATED_NAME).phone(UPDATED_PHONE).email(UPDATED_EMAIL).address(UPDATED_ADDRESS);

        restContainerOwnerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedContainerOwner.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedContainerOwner))
            )
            .andExpect(status().isOk());

        // Validate the ContainerOwner in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertContainerOwnerUpdatableFieldsEquals(partialUpdatedContainerOwner, getPersistedContainerOwner(partialUpdatedContainerOwner));
    }

    @Test
    void patchNonExistingContainerOwner() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        containerOwner.setId(UUID.randomUUID());

        // Create the ContainerOwner
        ContainerOwnerDTO containerOwnerDTO = containerOwnerMapper.toDto(containerOwner);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContainerOwnerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, containerOwnerDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(containerOwnerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ContainerOwner in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchContainerOwner() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        containerOwner.setId(UUID.randomUUID());

        // Create the ContainerOwner
        ContainerOwnerDTO containerOwnerDTO = containerOwnerMapper.toDto(containerOwner);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContainerOwnerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(containerOwnerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ContainerOwner in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamContainerOwner() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        containerOwner.setId(UUID.randomUUID());

        // Create the ContainerOwner
        ContainerOwnerDTO containerOwnerDTO = containerOwnerMapper.toDto(containerOwner);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContainerOwnerMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(containerOwnerDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ContainerOwner in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteContainerOwner() throws Exception {
        // Initialize the database
        containerOwner.setId(UUID.randomUUID());
        containerOwnerRepository.save(containerOwner);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the containerOwner
        restContainerOwnerMockMvc
            .perform(delete(ENTITY_API_URL_ID, containerOwner.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return containerOwnerRepository.count();
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

    protected ContainerOwner getPersistedContainerOwner(ContainerOwner containerOwner) {
        return containerOwnerRepository.findById(containerOwner.getId()).orElseThrow();
    }

    protected void assertPersistedContainerOwnerToMatchAllProperties(ContainerOwner expectedContainerOwner) {
        assertContainerOwnerAllPropertiesEquals(expectedContainerOwner, getPersistedContainerOwner(expectedContainerOwner));
    }

    protected void assertPersistedContainerOwnerToMatchUpdatableProperties(ContainerOwner expectedContainerOwner) {
        assertContainerOwnerAllUpdatablePropertiesEquals(expectedContainerOwner, getPersistedContainerOwner(expectedContainerOwner));
    }
}

package vn.containergo.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static vn.containergo.domain.CarrierPersonGroupAsserts.*;
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
import vn.containergo.domain.CarrierPersonGroup;
import vn.containergo.repository.CarrierPersonGroupRepository;
import vn.containergo.service.dto.CarrierPersonGroupDTO;
import vn.containergo.service.mapper.CarrierPersonGroupMapper;

/**
 * Integration tests for the {@link CarrierPersonGroupResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CarrierPersonGroupResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/carrier-person-groups";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private CarrierPersonGroupRepository carrierPersonGroupRepository;

    @Autowired
    private CarrierPersonGroupMapper carrierPersonGroupMapper;

    @Autowired
    private MockMvc restCarrierPersonGroupMockMvc;

    private CarrierPersonGroup carrierPersonGroup;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CarrierPersonGroup createEntity() {
        CarrierPersonGroup carrierPersonGroup = new CarrierPersonGroup().name(DEFAULT_NAME);
        return carrierPersonGroup;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CarrierPersonGroup createUpdatedEntity() {
        CarrierPersonGroup carrierPersonGroup = new CarrierPersonGroup().name(UPDATED_NAME);
        return carrierPersonGroup;
    }

    @BeforeEach
    public void initTest() {
        carrierPersonGroupRepository.deleteAll();
        carrierPersonGroup = createEntity();
    }

    @Test
    void createCarrierPersonGroup() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the CarrierPersonGroup
        CarrierPersonGroupDTO carrierPersonGroupDTO = carrierPersonGroupMapper.toDto(carrierPersonGroup);
        var returnedCarrierPersonGroupDTO = om.readValue(
            restCarrierPersonGroupMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(carrierPersonGroupDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            CarrierPersonGroupDTO.class
        );

        // Validate the CarrierPersonGroup in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedCarrierPersonGroup = carrierPersonGroupMapper.toEntity(returnedCarrierPersonGroupDTO);
        assertCarrierPersonGroupUpdatableFieldsEquals(
            returnedCarrierPersonGroup,
            getPersistedCarrierPersonGroup(returnedCarrierPersonGroup)
        );
    }

    @Test
    void createCarrierPersonGroupWithExistingId() throws Exception {
        // Create the CarrierPersonGroup with an existing ID
        carrierPersonGroup.setId(UUID.randomUUID());
        CarrierPersonGroupDTO carrierPersonGroupDTO = carrierPersonGroupMapper.toDto(carrierPersonGroup);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCarrierPersonGroupMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(carrierPersonGroupDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CarrierPersonGroup in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    void checkNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        carrierPersonGroup.setName(null);

        // Create the CarrierPersonGroup, which fails.
        CarrierPersonGroupDTO carrierPersonGroupDTO = carrierPersonGroupMapper.toDto(carrierPersonGroup);

        restCarrierPersonGroupMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(carrierPersonGroupDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void getAllCarrierPersonGroups() throws Exception {
        // Initialize the database
        carrierPersonGroup.setId(UUID.randomUUID());
        carrierPersonGroupRepository.save(carrierPersonGroup);

        // Get all the carrierPersonGroupList
        restCarrierPersonGroupMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(carrierPersonGroup.getId().toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    void getCarrierPersonGroup() throws Exception {
        // Initialize the database
        carrierPersonGroup.setId(UUID.randomUUID());
        carrierPersonGroupRepository.save(carrierPersonGroup);

        // Get the carrierPersonGroup
        restCarrierPersonGroupMockMvc
            .perform(get(ENTITY_API_URL_ID, carrierPersonGroup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(carrierPersonGroup.getId().toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    void getNonExistingCarrierPersonGroup() throws Exception {
        // Get the carrierPersonGroup
        restCarrierPersonGroupMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    void putExistingCarrierPersonGroup() throws Exception {
        // Initialize the database
        carrierPersonGroup.setId(UUID.randomUUID());
        carrierPersonGroupRepository.save(carrierPersonGroup);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the carrierPersonGroup
        CarrierPersonGroup updatedCarrierPersonGroup = carrierPersonGroupRepository.findById(carrierPersonGroup.getId()).orElseThrow();
        updatedCarrierPersonGroup.name(UPDATED_NAME);
        CarrierPersonGroupDTO carrierPersonGroupDTO = carrierPersonGroupMapper.toDto(updatedCarrierPersonGroup);

        restCarrierPersonGroupMockMvc
            .perform(
                put(ENTITY_API_URL_ID, carrierPersonGroupDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(carrierPersonGroupDTO))
            )
            .andExpect(status().isOk());

        // Validate the CarrierPersonGroup in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedCarrierPersonGroupToMatchAllProperties(updatedCarrierPersonGroup);
    }

    @Test
    void putNonExistingCarrierPersonGroup() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        carrierPersonGroup.setId(UUID.randomUUID());

        // Create the CarrierPersonGroup
        CarrierPersonGroupDTO carrierPersonGroupDTO = carrierPersonGroupMapper.toDto(carrierPersonGroup);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCarrierPersonGroupMockMvc
            .perform(
                put(ENTITY_API_URL_ID, carrierPersonGroupDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(carrierPersonGroupDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CarrierPersonGroup in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchCarrierPersonGroup() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        carrierPersonGroup.setId(UUID.randomUUID());

        // Create the CarrierPersonGroup
        CarrierPersonGroupDTO carrierPersonGroupDTO = carrierPersonGroupMapper.toDto(carrierPersonGroup);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCarrierPersonGroupMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(carrierPersonGroupDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CarrierPersonGroup in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamCarrierPersonGroup() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        carrierPersonGroup.setId(UUID.randomUUID());

        // Create the CarrierPersonGroup
        CarrierPersonGroupDTO carrierPersonGroupDTO = carrierPersonGroupMapper.toDto(carrierPersonGroup);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCarrierPersonGroupMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(carrierPersonGroupDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CarrierPersonGroup in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateCarrierPersonGroupWithPatch() throws Exception {
        // Initialize the database
        carrierPersonGroup.setId(UUID.randomUUID());
        carrierPersonGroupRepository.save(carrierPersonGroup);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the carrierPersonGroup using partial update
        CarrierPersonGroup partialUpdatedCarrierPersonGroup = new CarrierPersonGroup();
        partialUpdatedCarrierPersonGroup.setId(carrierPersonGroup.getId());

        partialUpdatedCarrierPersonGroup.name(UPDATED_NAME);

        restCarrierPersonGroupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCarrierPersonGroup.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCarrierPersonGroup))
            )
            .andExpect(status().isOk());

        // Validate the CarrierPersonGroup in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCarrierPersonGroupUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedCarrierPersonGroup, carrierPersonGroup),
            getPersistedCarrierPersonGroup(carrierPersonGroup)
        );
    }

    @Test
    void fullUpdateCarrierPersonGroupWithPatch() throws Exception {
        // Initialize the database
        carrierPersonGroup.setId(UUID.randomUUID());
        carrierPersonGroupRepository.save(carrierPersonGroup);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the carrierPersonGroup using partial update
        CarrierPersonGroup partialUpdatedCarrierPersonGroup = new CarrierPersonGroup();
        partialUpdatedCarrierPersonGroup.setId(carrierPersonGroup.getId());

        partialUpdatedCarrierPersonGroup.name(UPDATED_NAME);

        restCarrierPersonGroupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCarrierPersonGroup.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCarrierPersonGroup))
            )
            .andExpect(status().isOk());

        // Validate the CarrierPersonGroup in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCarrierPersonGroupUpdatableFieldsEquals(
            partialUpdatedCarrierPersonGroup,
            getPersistedCarrierPersonGroup(partialUpdatedCarrierPersonGroup)
        );
    }

    @Test
    void patchNonExistingCarrierPersonGroup() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        carrierPersonGroup.setId(UUID.randomUUID());

        // Create the CarrierPersonGroup
        CarrierPersonGroupDTO carrierPersonGroupDTO = carrierPersonGroupMapper.toDto(carrierPersonGroup);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCarrierPersonGroupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, carrierPersonGroupDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(carrierPersonGroupDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CarrierPersonGroup in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchCarrierPersonGroup() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        carrierPersonGroup.setId(UUID.randomUUID());

        // Create the CarrierPersonGroup
        CarrierPersonGroupDTO carrierPersonGroupDTO = carrierPersonGroupMapper.toDto(carrierPersonGroup);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCarrierPersonGroupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(carrierPersonGroupDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CarrierPersonGroup in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamCarrierPersonGroup() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        carrierPersonGroup.setId(UUID.randomUUID());

        // Create the CarrierPersonGroup
        CarrierPersonGroupDTO carrierPersonGroupDTO = carrierPersonGroupMapper.toDto(carrierPersonGroup);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCarrierPersonGroupMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(carrierPersonGroupDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CarrierPersonGroup in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteCarrierPersonGroup() throws Exception {
        // Initialize the database
        carrierPersonGroup.setId(UUID.randomUUID());
        carrierPersonGroupRepository.save(carrierPersonGroup);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the carrierPersonGroup
        restCarrierPersonGroupMockMvc
            .perform(delete(ENTITY_API_URL_ID, carrierPersonGroup.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return carrierPersonGroupRepository.count();
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

    protected CarrierPersonGroup getPersistedCarrierPersonGroup(CarrierPersonGroup carrierPersonGroup) {
        return carrierPersonGroupRepository.findById(carrierPersonGroup.getId()).orElseThrow();
    }

    protected void assertPersistedCarrierPersonGroupToMatchAllProperties(CarrierPersonGroup expectedCarrierPersonGroup) {
        assertCarrierPersonGroupAllPropertiesEquals(expectedCarrierPersonGroup, getPersistedCarrierPersonGroup(expectedCarrierPersonGroup));
    }

    protected void assertPersistedCarrierPersonGroupToMatchUpdatableProperties(CarrierPersonGroup expectedCarrierPersonGroup) {
        assertCarrierPersonGroupAllUpdatablePropertiesEquals(
            expectedCarrierPersonGroup,
            getPersistedCarrierPersonGroup(expectedCarrierPersonGroup)
        );
    }
}

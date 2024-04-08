package vn.containergo.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static vn.containergo.domain.ShipperPersonGroupAsserts.*;
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
import vn.containergo.domain.ShipperPersonGroup;
import vn.containergo.repository.ShipperPersonGroupRepository;
import vn.containergo.service.dto.ShipperPersonGroupDTO;
import vn.containergo.service.mapper.ShipperPersonGroupMapper;

/**
 * Integration tests for the {@link ShipperPersonGroupResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ShipperPersonGroupResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/shipper-person-groups";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ShipperPersonGroupRepository shipperPersonGroupRepository;

    @Autowired
    private ShipperPersonGroupMapper shipperPersonGroupMapper;

    @Autowired
    private MockMvc restShipperPersonGroupMockMvc;

    private ShipperPersonGroup shipperPersonGroup;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ShipperPersonGroup createEntity() {
        ShipperPersonGroup shipperPersonGroup = new ShipperPersonGroup().name(DEFAULT_NAME);
        return shipperPersonGroup;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ShipperPersonGroup createUpdatedEntity() {
        ShipperPersonGroup shipperPersonGroup = new ShipperPersonGroup().name(UPDATED_NAME);
        return shipperPersonGroup;
    }

    @BeforeEach
    public void initTest() {
        shipperPersonGroupRepository.deleteAll();
        shipperPersonGroup = createEntity();
    }

    @Test
    void createShipperPersonGroup() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the ShipperPersonGroup
        ShipperPersonGroupDTO shipperPersonGroupDTO = shipperPersonGroupMapper.toDto(shipperPersonGroup);
        var returnedShipperPersonGroupDTO = om.readValue(
            restShipperPersonGroupMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(shipperPersonGroupDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ShipperPersonGroupDTO.class
        );

        // Validate the ShipperPersonGroup in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedShipperPersonGroup = shipperPersonGroupMapper.toEntity(returnedShipperPersonGroupDTO);
        assertShipperPersonGroupUpdatableFieldsEquals(
            returnedShipperPersonGroup,
            getPersistedShipperPersonGroup(returnedShipperPersonGroup)
        );
    }

    @Test
    void createShipperPersonGroupWithExistingId() throws Exception {
        // Create the ShipperPersonGroup with an existing ID
        shipperPersonGroup.setId(UUID.randomUUID());
        ShipperPersonGroupDTO shipperPersonGroupDTO = shipperPersonGroupMapper.toDto(shipperPersonGroup);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restShipperPersonGroupMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(shipperPersonGroupDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ShipperPersonGroup in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    void checkNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        shipperPersonGroup.setName(null);

        // Create the ShipperPersonGroup, which fails.
        ShipperPersonGroupDTO shipperPersonGroupDTO = shipperPersonGroupMapper.toDto(shipperPersonGroup);

        restShipperPersonGroupMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(shipperPersonGroupDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void getAllShipperPersonGroups() throws Exception {
        // Initialize the database
        shipperPersonGroup.setId(UUID.randomUUID());
        shipperPersonGroupRepository.save(shipperPersonGroup);

        // Get all the shipperPersonGroupList
        restShipperPersonGroupMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(shipperPersonGroup.getId().toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    void getShipperPersonGroup() throws Exception {
        // Initialize the database
        shipperPersonGroup.setId(UUID.randomUUID());
        shipperPersonGroupRepository.save(shipperPersonGroup);

        // Get the shipperPersonGroup
        restShipperPersonGroupMockMvc
            .perform(get(ENTITY_API_URL_ID, shipperPersonGroup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(shipperPersonGroup.getId().toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    void getNonExistingShipperPersonGroup() throws Exception {
        // Get the shipperPersonGroup
        restShipperPersonGroupMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    void putExistingShipperPersonGroup() throws Exception {
        // Initialize the database
        shipperPersonGroup.setId(UUID.randomUUID());
        shipperPersonGroupRepository.save(shipperPersonGroup);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the shipperPersonGroup
        ShipperPersonGroup updatedShipperPersonGroup = shipperPersonGroupRepository.findById(shipperPersonGroup.getId()).orElseThrow();
        updatedShipperPersonGroup.name(UPDATED_NAME);
        ShipperPersonGroupDTO shipperPersonGroupDTO = shipperPersonGroupMapper.toDto(updatedShipperPersonGroup);

        restShipperPersonGroupMockMvc
            .perform(
                put(ENTITY_API_URL_ID, shipperPersonGroupDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(shipperPersonGroupDTO))
            )
            .andExpect(status().isOk());

        // Validate the ShipperPersonGroup in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedShipperPersonGroupToMatchAllProperties(updatedShipperPersonGroup);
    }

    @Test
    void putNonExistingShipperPersonGroup() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        shipperPersonGroup.setId(UUID.randomUUID());

        // Create the ShipperPersonGroup
        ShipperPersonGroupDTO shipperPersonGroupDTO = shipperPersonGroupMapper.toDto(shipperPersonGroup);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restShipperPersonGroupMockMvc
            .perform(
                put(ENTITY_API_URL_ID, shipperPersonGroupDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(shipperPersonGroupDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ShipperPersonGroup in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchShipperPersonGroup() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        shipperPersonGroup.setId(UUID.randomUUID());

        // Create the ShipperPersonGroup
        ShipperPersonGroupDTO shipperPersonGroupDTO = shipperPersonGroupMapper.toDto(shipperPersonGroup);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restShipperPersonGroupMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(shipperPersonGroupDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ShipperPersonGroup in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamShipperPersonGroup() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        shipperPersonGroup.setId(UUID.randomUUID());

        // Create the ShipperPersonGroup
        ShipperPersonGroupDTO shipperPersonGroupDTO = shipperPersonGroupMapper.toDto(shipperPersonGroup);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restShipperPersonGroupMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(shipperPersonGroupDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ShipperPersonGroup in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateShipperPersonGroupWithPatch() throws Exception {
        // Initialize the database
        shipperPersonGroup.setId(UUID.randomUUID());
        shipperPersonGroupRepository.save(shipperPersonGroup);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the shipperPersonGroup using partial update
        ShipperPersonGroup partialUpdatedShipperPersonGroup = new ShipperPersonGroup();
        partialUpdatedShipperPersonGroup.setId(shipperPersonGroup.getId());

        partialUpdatedShipperPersonGroup.name(UPDATED_NAME);

        restShipperPersonGroupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedShipperPersonGroup.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedShipperPersonGroup))
            )
            .andExpect(status().isOk());

        // Validate the ShipperPersonGroup in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertShipperPersonGroupUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedShipperPersonGroup, shipperPersonGroup),
            getPersistedShipperPersonGroup(shipperPersonGroup)
        );
    }

    @Test
    void fullUpdateShipperPersonGroupWithPatch() throws Exception {
        // Initialize the database
        shipperPersonGroup.setId(UUID.randomUUID());
        shipperPersonGroupRepository.save(shipperPersonGroup);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the shipperPersonGroup using partial update
        ShipperPersonGroup partialUpdatedShipperPersonGroup = new ShipperPersonGroup();
        partialUpdatedShipperPersonGroup.setId(shipperPersonGroup.getId());

        partialUpdatedShipperPersonGroup.name(UPDATED_NAME);

        restShipperPersonGroupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedShipperPersonGroup.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedShipperPersonGroup))
            )
            .andExpect(status().isOk());

        // Validate the ShipperPersonGroup in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertShipperPersonGroupUpdatableFieldsEquals(
            partialUpdatedShipperPersonGroup,
            getPersistedShipperPersonGroup(partialUpdatedShipperPersonGroup)
        );
    }

    @Test
    void patchNonExistingShipperPersonGroup() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        shipperPersonGroup.setId(UUID.randomUUID());

        // Create the ShipperPersonGroup
        ShipperPersonGroupDTO shipperPersonGroupDTO = shipperPersonGroupMapper.toDto(shipperPersonGroup);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restShipperPersonGroupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, shipperPersonGroupDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(shipperPersonGroupDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ShipperPersonGroup in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchShipperPersonGroup() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        shipperPersonGroup.setId(UUID.randomUUID());

        // Create the ShipperPersonGroup
        ShipperPersonGroupDTO shipperPersonGroupDTO = shipperPersonGroupMapper.toDto(shipperPersonGroup);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restShipperPersonGroupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(shipperPersonGroupDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ShipperPersonGroup in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamShipperPersonGroup() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        shipperPersonGroup.setId(UUID.randomUUID());

        // Create the ShipperPersonGroup
        ShipperPersonGroupDTO shipperPersonGroupDTO = shipperPersonGroupMapper.toDto(shipperPersonGroup);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restShipperPersonGroupMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(shipperPersonGroupDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ShipperPersonGroup in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteShipperPersonGroup() throws Exception {
        // Initialize the database
        shipperPersonGroup.setId(UUID.randomUUID());
        shipperPersonGroupRepository.save(shipperPersonGroup);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the shipperPersonGroup
        restShipperPersonGroupMockMvc
            .perform(delete(ENTITY_API_URL_ID, shipperPersonGroup.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return shipperPersonGroupRepository.count();
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

    protected ShipperPersonGroup getPersistedShipperPersonGroup(ShipperPersonGroup shipperPersonGroup) {
        return shipperPersonGroupRepository.findById(shipperPersonGroup.getId()).orElseThrow();
    }

    protected void assertPersistedShipperPersonGroupToMatchAllProperties(ShipperPersonGroup expectedShipperPersonGroup) {
        assertShipperPersonGroupAllPropertiesEquals(expectedShipperPersonGroup, getPersistedShipperPersonGroup(expectedShipperPersonGroup));
    }

    protected void assertPersistedShipperPersonGroupToMatchUpdatableProperties(ShipperPersonGroup expectedShipperPersonGroup) {
        assertShipperPersonGroupAllUpdatablePropertiesEquals(
            expectedShipperPersonGroup,
            getPersistedShipperPersonGroup(expectedShipperPersonGroup)
        );
    }
}

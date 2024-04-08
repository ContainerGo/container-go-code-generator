package vn.containergo.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static vn.containergo.domain.ShipperPersonAsserts.*;
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
import vn.containergo.domain.ShipperPerson;
import vn.containergo.domain.ShipperPersonGroup;
import vn.containergo.repository.ShipperPersonRepository;
import vn.containergo.service.dto.ShipperPersonDTO;
import vn.containergo.service.mapper.ShipperPersonMapper;

/**
 * Integration tests for the {@link ShipperPersonResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ShipperPersonResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/shipper-people";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ShipperPersonRepository shipperPersonRepository;

    @Autowired
    private ShipperPersonMapper shipperPersonMapper;

    @Autowired
    private MockMvc restShipperPersonMockMvc;

    private ShipperPerson shipperPerson;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ShipperPerson createEntity() {
        ShipperPerson shipperPerson = new ShipperPerson()
            .name(DEFAULT_NAME)
            .phone(DEFAULT_PHONE)
            .email(DEFAULT_EMAIL)
            .address(DEFAULT_ADDRESS);
        // Add required entity
        ShipperPersonGroup shipperPersonGroup;
        shipperPersonGroup = ShipperPersonGroupResourceIT.createEntity();
        shipperPersonGroup.setId("fixed-id-for-tests");
        shipperPerson.setGroup(shipperPersonGroup);
        return shipperPerson;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ShipperPerson createUpdatedEntity() {
        ShipperPerson shipperPerson = new ShipperPerson()
            .name(UPDATED_NAME)
            .phone(UPDATED_PHONE)
            .email(UPDATED_EMAIL)
            .address(UPDATED_ADDRESS);
        // Add required entity
        ShipperPersonGroup shipperPersonGroup;
        shipperPersonGroup = ShipperPersonGroupResourceIT.createUpdatedEntity();
        shipperPersonGroup.setId("fixed-id-for-tests");
        shipperPerson.setGroup(shipperPersonGroup);
        return shipperPerson;
    }

    @BeforeEach
    public void initTest() {
        shipperPersonRepository.deleteAll();
        shipperPerson = createEntity();
    }

    @Test
    void createShipperPerson() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the ShipperPerson
        ShipperPersonDTO shipperPersonDTO = shipperPersonMapper.toDto(shipperPerson);
        var returnedShipperPersonDTO = om.readValue(
            restShipperPersonMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(shipperPersonDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ShipperPersonDTO.class
        );

        // Validate the ShipperPerson in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedShipperPerson = shipperPersonMapper.toEntity(returnedShipperPersonDTO);
        assertShipperPersonUpdatableFieldsEquals(returnedShipperPerson, getPersistedShipperPerson(returnedShipperPerson));
    }

    @Test
    void createShipperPersonWithExistingId() throws Exception {
        // Create the ShipperPerson with an existing ID
        shipperPerson.setId(UUID.randomUUID());
        ShipperPersonDTO shipperPersonDTO = shipperPersonMapper.toDto(shipperPerson);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restShipperPersonMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(shipperPersonDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ShipperPerson in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    void checkNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        shipperPerson.setName(null);

        // Create the ShipperPerson, which fails.
        ShipperPersonDTO shipperPersonDTO = shipperPersonMapper.toDto(shipperPerson);

        restShipperPersonMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(shipperPersonDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkPhoneIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        shipperPerson.setPhone(null);

        // Create the ShipperPerson, which fails.
        ShipperPersonDTO shipperPersonDTO = shipperPersonMapper.toDto(shipperPerson);

        restShipperPersonMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(shipperPersonDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void getAllShipperPeople() throws Exception {
        // Initialize the database
        shipperPerson.setId(UUID.randomUUID());
        shipperPersonRepository.save(shipperPerson);

        // Get all the shipperPersonList
        restShipperPersonMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(shipperPerson.getId().toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)));
    }

    @Test
    void getShipperPerson() throws Exception {
        // Initialize the database
        shipperPerson.setId(UUID.randomUUID());
        shipperPersonRepository.save(shipperPerson);

        // Get the shipperPerson
        restShipperPersonMockMvc
            .perform(get(ENTITY_API_URL_ID, shipperPerson.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(shipperPerson.getId().toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS));
    }

    @Test
    void getNonExistingShipperPerson() throws Exception {
        // Get the shipperPerson
        restShipperPersonMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    void putExistingShipperPerson() throws Exception {
        // Initialize the database
        shipperPerson.setId(UUID.randomUUID());
        shipperPersonRepository.save(shipperPerson);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the shipperPerson
        ShipperPerson updatedShipperPerson = shipperPersonRepository.findById(shipperPerson.getId()).orElseThrow();
        updatedShipperPerson.name(UPDATED_NAME).phone(UPDATED_PHONE).email(UPDATED_EMAIL).address(UPDATED_ADDRESS);
        ShipperPersonDTO shipperPersonDTO = shipperPersonMapper.toDto(updatedShipperPerson);

        restShipperPersonMockMvc
            .perform(
                put(ENTITY_API_URL_ID, shipperPersonDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(shipperPersonDTO))
            )
            .andExpect(status().isOk());

        // Validate the ShipperPerson in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedShipperPersonToMatchAllProperties(updatedShipperPerson);
    }

    @Test
    void putNonExistingShipperPerson() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        shipperPerson.setId(UUID.randomUUID());

        // Create the ShipperPerson
        ShipperPersonDTO shipperPersonDTO = shipperPersonMapper.toDto(shipperPerson);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restShipperPersonMockMvc
            .perform(
                put(ENTITY_API_URL_ID, shipperPersonDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(shipperPersonDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ShipperPerson in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchShipperPerson() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        shipperPerson.setId(UUID.randomUUID());

        // Create the ShipperPerson
        ShipperPersonDTO shipperPersonDTO = shipperPersonMapper.toDto(shipperPerson);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restShipperPersonMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(shipperPersonDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ShipperPerson in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamShipperPerson() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        shipperPerson.setId(UUID.randomUUID());

        // Create the ShipperPerson
        ShipperPersonDTO shipperPersonDTO = shipperPersonMapper.toDto(shipperPerson);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restShipperPersonMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(shipperPersonDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ShipperPerson in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateShipperPersonWithPatch() throws Exception {
        // Initialize the database
        shipperPerson.setId(UUID.randomUUID());
        shipperPersonRepository.save(shipperPerson);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the shipperPerson using partial update
        ShipperPerson partialUpdatedShipperPerson = new ShipperPerson();
        partialUpdatedShipperPerson.setId(shipperPerson.getId());

        partialUpdatedShipperPerson.phone(UPDATED_PHONE).email(UPDATED_EMAIL).address(UPDATED_ADDRESS);

        restShipperPersonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedShipperPerson.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedShipperPerson))
            )
            .andExpect(status().isOk());

        // Validate the ShipperPerson in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertShipperPersonUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedShipperPerson, shipperPerson),
            getPersistedShipperPerson(shipperPerson)
        );
    }

    @Test
    void fullUpdateShipperPersonWithPatch() throws Exception {
        // Initialize the database
        shipperPerson.setId(UUID.randomUUID());
        shipperPersonRepository.save(shipperPerson);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the shipperPerson using partial update
        ShipperPerson partialUpdatedShipperPerson = new ShipperPerson();
        partialUpdatedShipperPerson.setId(shipperPerson.getId());

        partialUpdatedShipperPerson.name(UPDATED_NAME).phone(UPDATED_PHONE).email(UPDATED_EMAIL).address(UPDATED_ADDRESS);

        restShipperPersonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedShipperPerson.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedShipperPerson))
            )
            .andExpect(status().isOk());

        // Validate the ShipperPerson in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertShipperPersonUpdatableFieldsEquals(partialUpdatedShipperPerson, getPersistedShipperPerson(partialUpdatedShipperPerson));
    }

    @Test
    void patchNonExistingShipperPerson() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        shipperPerson.setId(UUID.randomUUID());

        // Create the ShipperPerson
        ShipperPersonDTO shipperPersonDTO = shipperPersonMapper.toDto(shipperPerson);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restShipperPersonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, shipperPersonDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(shipperPersonDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ShipperPerson in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchShipperPerson() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        shipperPerson.setId(UUID.randomUUID());

        // Create the ShipperPerson
        ShipperPersonDTO shipperPersonDTO = shipperPersonMapper.toDto(shipperPerson);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restShipperPersonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(shipperPersonDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ShipperPerson in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamShipperPerson() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        shipperPerson.setId(UUID.randomUUID());

        // Create the ShipperPerson
        ShipperPersonDTO shipperPersonDTO = shipperPersonMapper.toDto(shipperPerson);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restShipperPersonMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(shipperPersonDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ShipperPerson in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteShipperPerson() throws Exception {
        // Initialize the database
        shipperPerson.setId(UUID.randomUUID());
        shipperPersonRepository.save(shipperPerson);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the shipperPerson
        restShipperPersonMockMvc
            .perform(delete(ENTITY_API_URL_ID, shipperPerson.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return shipperPersonRepository.count();
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

    protected ShipperPerson getPersistedShipperPerson(ShipperPerson shipperPerson) {
        return shipperPersonRepository.findById(shipperPerson.getId()).orElseThrow();
    }

    protected void assertPersistedShipperPersonToMatchAllProperties(ShipperPerson expectedShipperPerson) {
        assertShipperPersonAllPropertiesEquals(expectedShipperPerson, getPersistedShipperPerson(expectedShipperPerson));
    }

    protected void assertPersistedShipperPersonToMatchUpdatableProperties(ShipperPerson expectedShipperPerson) {
        assertShipperPersonAllUpdatablePropertiesEquals(expectedShipperPerson, getPersistedShipperPerson(expectedShipperPerson));
    }
}

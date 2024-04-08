package vn.containergo.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static vn.containergo.domain.ShipperNotificationAsserts.*;
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
import vn.containergo.domain.ShipperNotification;
import vn.containergo.repository.ShipperNotificationRepository;
import vn.containergo.service.dto.ShipperNotificationDTO;
import vn.containergo.service.mapper.ShipperNotificationMapper;

/**
 * Integration tests for the {@link ShipperNotificationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ShipperNotificationResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_EMAIL_NOTIFICATION_ENABLED = false;
    private static final Boolean UPDATED_IS_EMAIL_NOTIFICATION_ENABLED = true;

    private static final Boolean DEFAULT_IS_SMS_NOTIFICATION_ENABLED = false;
    private static final Boolean UPDATED_IS_SMS_NOTIFICATION_ENABLED = true;

    private static final Boolean DEFAULT_IS_APP_NOTIFICATION_ENABLED = false;
    private static final Boolean UPDATED_IS_APP_NOTIFICATION_ENABLED = true;

    private static final String ENTITY_API_URL = "/api/shipper-notifications";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ShipperNotificationRepository shipperNotificationRepository;

    @Autowired
    private ShipperNotificationMapper shipperNotificationMapper;

    @Autowired
    private MockMvc restShipperNotificationMockMvc;

    private ShipperNotification shipperNotification;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ShipperNotification createEntity() {
        ShipperNotification shipperNotification = new ShipperNotification()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .isEmailNotificationEnabled(DEFAULT_IS_EMAIL_NOTIFICATION_ENABLED)
            .isSmsNotificationEnabled(DEFAULT_IS_SMS_NOTIFICATION_ENABLED)
            .isAppNotificationEnabled(DEFAULT_IS_APP_NOTIFICATION_ENABLED);
        return shipperNotification;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ShipperNotification createUpdatedEntity() {
        ShipperNotification shipperNotification = new ShipperNotification()
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .isEmailNotificationEnabled(UPDATED_IS_EMAIL_NOTIFICATION_ENABLED)
            .isSmsNotificationEnabled(UPDATED_IS_SMS_NOTIFICATION_ENABLED)
            .isAppNotificationEnabled(UPDATED_IS_APP_NOTIFICATION_ENABLED);
        return shipperNotification;
    }

    @BeforeEach
    public void initTest() {
        shipperNotificationRepository.deleteAll();
        shipperNotification = createEntity();
    }

    @Test
    void createShipperNotification() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the ShipperNotification
        ShipperNotificationDTO shipperNotificationDTO = shipperNotificationMapper.toDto(shipperNotification);
        var returnedShipperNotificationDTO = om.readValue(
            restShipperNotificationMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(shipperNotificationDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ShipperNotificationDTO.class
        );

        // Validate the ShipperNotification in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedShipperNotification = shipperNotificationMapper.toEntity(returnedShipperNotificationDTO);
        assertShipperNotificationUpdatableFieldsEquals(
            returnedShipperNotification,
            getPersistedShipperNotification(returnedShipperNotification)
        );
    }

    @Test
    void createShipperNotificationWithExistingId() throws Exception {
        // Create the ShipperNotification with an existing ID
        shipperNotification.setId(UUID.randomUUID());
        ShipperNotificationDTO shipperNotificationDTO = shipperNotificationMapper.toDto(shipperNotification);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restShipperNotificationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(shipperNotificationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ShipperNotification in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    void checkCodeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        shipperNotification.setCode(null);

        // Create the ShipperNotification, which fails.
        ShipperNotificationDTO shipperNotificationDTO = shipperNotificationMapper.toDto(shipperNotification);

        restShipperNotificationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(shipperNotificationDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        shipperNotification.setName(null);

        // Create the ShipperNotification, which fails.
        ShipperNotificationDTO shipperNotificationDTO = shipperNotificationMapper.toDto(shipperNotification);

        restShipperNotificationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(shipperNotificationDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void getAllShipperNotifications() throws Exception {
        // Initialize the database
        shipperNotification.setId(UUID.randomUUID());
        shipperNotificationRepository.save(shipperNotification);

        // Get all the shipperNotificationList
        restShipperNotificationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(shipperNotification.getId().toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].isEmailNotificationEnabled").value(hasItem(DEFAULT_IS_EMAIL_NOTIFICATION_ENABLED.booleanValue())))
            .andExpect(jsonPath("$.[*].isSmsNotificationEnabled").value(hasItem(DEFAULT_IS_SMS_NOTIFICATION_ENABLED.booleanValue())))
            .andExpect(jsonPath("$.[*].isAppNotificationEnabled").value(hasItem(DEFAULT_IS_APP_NOTIFICATION_ENABLED.booleanValue())));
    }

    @Test
    void getShipperNotification() throws Exception {
        // Initialize the database
        shipperNotification.setId(UUID.randomUUID());
        shipperNotificationRepository.save(shipperNotification);

        // Get the shipperNotification
        restShipperNotificationMockMvc
            .perform(get(ENTITY_API_URL_ID, shipperNotification.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(shipperNotification.getId().toString()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.isEmailNotificationEnabled").value(DEFAULT_IS_EMAIL_NOTIFICATION_ENABLED.booleanValue()))
            .andExpect(jsonPath("$.isSmsNotificationEnabled").value(DEFAULT_IS_SMS_NOTIFICATION_ENABLED.booleanValue()))
            .andExpect(jsonPath("$.isAppNotificationEnabled").value(DEFAULT_IS_APP_NOTIFICATION_ENABLED.booleanValue()));
    }

    @Test
    void getNonExistingShipperNotification() throws Exception {
        // Get the shipperNotification
        restShipperNotificationMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    void putExistingShipperNotification() throws Exception {
        // Initialize the database
        shipperNotification.setId(UUID.randomUUID());
        shipperNotificationRepository.save(shipperNotification);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the shipperNotification
        ShipperNotification updatedShipperNotification = shipperNotificationRepository.findById(shipperNotification.getId()).orElseThrow();
        updatedShipperNotification
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .isEmailNotificationEnabled(UPDATED_IS_EMAIL_NOTIFICATION_ENABLED)
            .isSmsNotificationEnabled(UPDATED_IS_SMS_NOTIFICATION_ENABLED)
            .isAppNotificationEnabled(UPDATED_IS_APP_NOTIFICATION_ENABLED);
        ShipperNotificationDTO shipperNotificationDTO = shipperNotificationMapper.toDto(updatedShipperNotification);

        restShipperNotificationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, shipperNotificationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(shipperNotificationDTO))
            )
            .andExpect(status().isOk());

        // Validate the ShipperNotification in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedShipperNotificationToMatchAllProperties(updatedShipperNotification);
    }

    @Test
    void putNonExistingShipperNotification() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        shipperNotification.setId(UUID.randomUUID());

        // Create the ShipperNotification
        ShipperNotificationDTO shipperNotificationDTO = shipperNotificationMapper.toDto(shipperNotification);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restShipperNotificationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, shipperNotificationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(shipperNotificationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ShipperNotification in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchShipperNotification() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        shipperNotification.setId(UUID.randomUUID());

        // Create the ShipperNotification
        ShipperNotificationDTO shipperNotificationDTO = shipperNotificationMapper.toDto(shipperNotification);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restShipperNotificationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(shipperNotificationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ShipperNotification in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamShipperNotification() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        shipperNotification.setId(UUID.randomUUID());

        // Create the ShipperNotification
        ShipperNotificationDTO shipperNotificationDTO = shipperNotificationMapper.toDto(shipperNotification);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restShipperNotificationMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(shipperNotificationDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ShipperNotification in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateShipperNotificationWithPatch() throws Exception {
        // Initialize the database
        shipperNotification.setId(UUID.randomUUID());
        shipperNotificationRepository.save(shipperNotification);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the shipperNotification using partial update
        ShipperNotification partialUpdatedShipperNotification = new ShipperNotification();
        partialUpdatedShipperNotification.setId(shipperNotification.getId());

        partialUpdatedShipperNotification.code(UPDATED_CODE).isAppNotificationEnabled(UPDATED_IS_APP_NOTIFICATION_ENABLED);

        restShipperNotificationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedShipperNotification.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedShipperNotification))
            )
            .andExpect(status().isOk());

        // Validate the ShipperNotification in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertShipperNotificationUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedShipperNotification, shipperNotification),
            getPersistedShipperNotification(shipperNotification)
        );
    }

    @Test
    void fullUpdateShipperNotificationWithPatch() throws Exception {
        // Initialize the database
        shipperNotification.setId(UUID.randomUUID());
        shipperNotificationRepository.save(shipperNotification);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the shipperNotification using partial update
        ShipperNotification partialUpdatedShipperNotification = new ShipperNotification();
        partialUpdatedShipperNotification.setId(shipperNotification.getId());

        partialUpdatedShipperNotification
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .isEmailNotificationEnabled(UPDATED_IS_EMAIL_NOTIFICATION_ENABLED)
            .isSmsNotificationEnabled(UPDATED_IS_SMS_NOTIFICATION_ENABLED)
            .isAppNotificationEnabled(UPDATED_IS_APP_NOTIFICATION_ENABLED);

        restShipperNotificationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedShipperNotification.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedShipperNotification))
            )
            .andExpect(status().isOk());

        // Validate the ShipperNotification in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertShipperNotificationUpdatableFieldsEquals(
            partialUpdatedShipperNotification,
            getPersistedShipperNotification(partialUpdatedShipperNotification)
        );
    }

    @Test
    void patchNonExistingShipperNotification() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        shipperNotification.setId(UUID.randomUUID());

        // Create the ShipperNotification
        ShipperNotificationDTO shipperNotificationDTO = shipperNotificationMapper.toDto(shipperNotification);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restShipperNotificationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, shipperNotificationDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(shipperNotificationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ShipperNotification in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchShipperNotification() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        shipperNotification.setId(UUID.randomUUID());

        // Create the ShipperNotification
        ShipperNotificationDTO shipperNotificationDTO = shipperNotificationMapper.toDto(shipperNotification);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restShipperNotificationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(shipperNotificationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ShipperNotification in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamShipperNotification() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        shipperNotification.setId(UUID.randomUUID());

        // Create the ShipperNotification
        ShipperNotificationDTO shipperNotificationDTO = shipperNotificationMapper.toDto(shipperNotification);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restShipperNotificationMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(shipperNotificationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ShipperNotification in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteShipperNotification() throws Exception {
        // Initialize the database
        shipperNotification.setId(UUID.randomUUID());
        shipperNotificationRepository.save(shipperNotification);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the shipperNotification
        restShipperNotificationMockMvc
            .perform(delete(ENTITY_API_URL_ID, shipperNotification.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return shipperNotificationRepository.count();
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

    protected ShipperNotification getPersistedShipperNotification(ShipperNotification shipperNotification) {
        return shipperNotificationRepository.findById(shipperNotification.getId()).orElseThrow();
    }

    protected void assertPersistedShipperNotificationToMatchAllProperties(ShipperNotification expectedShipperNotification) {
        assertShipperNotificationAllPropertiesEquals(
            expectedShipperNotification,
            getPersistedShipperNotification(expectedShipperNotification)
        );
    }

    protected void assertPersistedShipperNotificationToMatchUpdatableProperties(ShipperNotification expectedShipperNotification) {
        assertShipperNotificationAllUpdatablePropertiesEquals(
            expectedShipperNotification,
            getPersistedShipperNotification(expectedShipperNotification)
        );
    }
}

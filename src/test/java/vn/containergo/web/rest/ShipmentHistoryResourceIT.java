package vn.containergo.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static vn.containergo.domain.ShipmentHistoryAsserts.*;
import static vn.containergo.web.rest.TestUtil.createUpdateProxyForBean;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import vn.containergo.IntegrationTest;
import vn.containergo.domain.Container;
import vn.containergo.domain.ShipmentHistory;
import vn.containergo.repository.ShipmentHistoryRepository;
import vn.containergo.service.dto.ShipmentHistoryDTO;
import vn.containergo.service.mapper.ShipmentHistoryMapper;

/**
 * Integration tests for the {@link ShipmentHistoryResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ShipmentHistoryResourceIT {

    private static final String DEFAULT_EVENT = "AAAAAAAAAA";
    private static final String UPDATED_EVENT = "BBBBBBBBBB";

    private static final Instant DEFAULT_TIMESTAMP = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_TIMESTAMP = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_EXECUTED_BY = "AAAAAAAAAA";
    private static final String UPDATED_EXECUTED_BY = "BBBBBBBBBB";

    private static final String DEFAULT_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_LOCATION = "BBBBBBBBBB";

    private static final Double DEFAULT_LAT = 1D;
    private static final Double UPDATED_LAT = 2D;

    private static final Double DEFAULT_LNG = 1D;
    private static final Double UPDATED_LNG = 2D;

    private static final String ENTITY_API_URL = "/api/shipment-histories";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ShipmentHistoryRepository shipmentHistoryRepository;

    @Autowired
    private ShipmentHistoryMapper shipmentHistoryMapper;

    @Autowired
    private MockMvc restShipmentHistoryMockMvc;

    private ShipmentHistory shipmentHistory;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ShipmentHistory createEntity() {
        ShipmentHistory shipmentHistory = new ShipmentHistory()
            .event(DEFAULT_EVENT)
            .timestamp(DEFAULT_TIMESTAMP)
            .executedBy(DEFAULT_EXECUTED_BY)
            .location(DEFAULT_LOCATION)
            .lat(DEFAULT_LAT)
            .lng(DEFAULT_LNG);
        // Add required entity
        Container container;
        container = ContainerResourceIT.createEntity();
        container.setId("fixed-id-for-tests");
        shipmentHistory.setContainer(container);
        return shipmentHistory;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ShipmentHistory createUpdatedEntity() {
        ShipmentHistory shipmentHistory = new ShipmentHistory()
            .event(UPDATED_EVENT)
            .timestamp(UPDATED_TIMESTAMP)
            .executedBy(UPDATED_EXECUTED_BY)
            .location(UPDATED_LOCATION)
            .lat(UPDATED_LAT)
            .lng(UPDATED_LNG);
        // Add required entity
        Container container;
        container = ContainerResourceIT.createUpdatedEntity();
        container.setId("fixed-id-for-tests");
        shipmentHistory.setContainer(container);
        return shipmentHistory;
    }

    @BeforeEach
    public void initTest() {
        shipmentHistoryRepository.deleteAll();
        shipmentHistory = createEntity();
    }

    @Test
    void createShipmentHistory() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the ShipmentHistory
        ShipmentHistoryDTO shipmentHistoryDTO = shipmentHistoryMapper.toDto(shipmentHistory);
        var returnedShipmentHistoryDTO = om.readValue(
            restShipmentHistoryMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(shipmentHistoryDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ShipmentHistoryDTO.class
        );

        // Validate the ShipmentHistory in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedShipmentHistory = shipmentHistoryMapper.toEntity(returnedShipmentHistoryDTO);
        assertShipmentHistoryUpdatableFieldsEquals(returnedShipmentHistory, getPersistedShipmentHistory(returnedShipmentHistory));
    }

    @Test
    void createShipmentHistoryWithExistingId() throws Exception {
        // Create the ShipmentHistory with an existing ID
        shipmentHistory.setId(UUID.randomUUID());
        ShipmentHistoryDTO shipmentHistoryDTO = shipmentHistoryMapper.toDto(shipmentHistory);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restShipmentHistoryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(shipmentHistoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ShipmentHistory in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    void checkEventIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        shipmentHistory.setEvent(null);

        // Create the ShipmentHistory, which fails.
        ShipmentHistoryDTO shipmentHistoryDTO = shipmentHistoryMapper.toDto(shipmentHistory);

        restShipmentHistoryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(shipmentHistoryDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkTimestampIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        shipmentHistory.setTimestamp(null);

        // Create the ShipmentHistory, which fails.
        ShipmentHistoryDTO shipmentHistoryDTO = shipmentHistoryMapper.toDto(shipmentHistory);

        restShipmentHistoryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(shipmentHistoryDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkExecutedByIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        shipmentHistory.setExecutedBy(null);

        // Create the ShipmentHistory, which fails.
        ShipmentHistoryDTO shipmentHistoryDTO = shipmentHistoryMapper.toDto(shipmentHistory);

        restShipmentHistoryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(shipmentHistoryDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void getAllShipmentHistories() throws Exception {
        // Initialize the database
        shipmentHistory.setId(UUID.randomUUID());
        shipmentHistoryRepository.save(shipmentHistory);

        // Get all the shipmentHistoryList
        restShipmentHistoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(shipmentHistory.getId().toString())))
            .andExpect(jsonPath("$.[*].event").value(hasItem(DEFAULT_EVENT)))
            .andExpect(jsonPath("$.[*].timestamp").value(hasItem(DEFAULT_TIMESTAMP.toString())))
            .andExpect(jsonPath("$.[*].executedBy").value(hasItem(DEFAULT_EXECUTED_BY)))
            .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION)))
            .andExpect(jsonPath("$.[*].lat").value(hasItem(DEFAULT_LAT.doubleValue())))
            .andExpect(jsonPath("$.[*].lng").value(hasItem(DEFAULT_LNG.doubleValue())));
    }

    @Test
    void getShipmentHistory() throws Exception {
        // Initialize the database
        shipmentHistory.setId(UUID.randomUUID());
        shipmentHistoryRepository.save(shipmentHistory);

        // Get the shipmentHistory
        restShipmentHistoryMockMvc
            .perform(get(ENTITY_API_URL_ID, shipmentHistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(shipmentHistory.getId().toString()))
            .andExpect(jsonPath("$.event").value(DEFAULT_EVENT))
            .andExpect(jsonPath("$.timestamp").value(DEFAULT_TIMESTAMP.toString()))
            .andExpect(jsonPath("$.executedBy").value(DEFAULT_EXECUTED_BY))
            .andExpect(jsonPath("$.location").value(DEFAULT_LOCATION))
            .andExpect(jsonPath("$.lat").value(DEFAULT_LAT.doubleValue()))
            .andExpect(jsonPath("$.lng").value(DEFAULT_LNG.doubleValue()));
    }

    @Test
    void getNonExistingShipmentHistory() throws Exception {
        // Get the shipmentHistory
        restShipmentHistoryMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    void putExistingShipmentHistory() throws Exception {
        // Initialize the database
        shipmentHistory.setId(UUID.randomUUID());
        shipmentHistoryRepository.save(shipmentHistory);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the shipmentHistory
        ShipmentHistory updatedShipmentHistory = shipmentHistoryRepository.findById(shipmentHistory.getId()).orElseThrow();
        updatedShipmentHistory
            .event(UPDATED_EVENT)
            .timestamp(UPDATED_TIMESTAMP)
            .executedBy(UPDATED_EXECUTED_BY)
            .location(UPDATED_LOCATION)
            .lat(UPDATED_LAT)
            .lng(UPDATED_LNG);
        ShipmentHistoryDTO shipmentHistoryDTO = shipmentHistoryMapper.toDto(updatedShipmentHistory);

        restShipmentHistoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, shipmentHistoryDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(shipmentHistoryDTO))
            )
            .andExpect(status().isOk());

        // Validate the ShipmentHistory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedShipmentHistoryToMatchAllProperties(updatedShipmentHistory);
    }

    @Test
    void putNonExistingShipmentHistory() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        shipmentHistory.setId(UUID.randomUUID());

        // Create the ShipmentHistory
        ShipmentHistoryDTO shipmentHistoryDTO = shipmentHistoryMapper.toDto(shipmentHistory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restShipmentHistoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, shipmentHistoryDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(shipmentHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ShipmentHistory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchShipmentHistory() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        shipmentHistory.setId(UUID.randomUUID());

        // Create the ShipmentHistory
        ShipmentHistoryDTO shipmentHistoryDTO = shipmentHistoryMapper.toDto(shipmentHistory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restShipmentHistoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(shipmentHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ShipmentHistory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamShipmentHistory() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        shipmentHistory.setId(UUID.randomUUID());

        // Create the ShipmentHistory
        ShipmentHistoryDTO shipmentHistoryDTO = shipmentHistoryMapper.toDto(shipmentHistory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restShipmentHistoryMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(shipmentHistoryDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ShipmentHistory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateShipmentHistoryWithPatch() throws Exception {
        // Initialize the database
        shipmentHistory.setId(UUID.randomUUID());
        shipmentHistoryRepository.save(shipmentHistory);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the shipmentHistory using partial update
        ShipmentHistory partialUpdatedShipmentHistory = new ShipmentHistory();
        partialUpdatedShipmentHistory.setId(shipmentHistory.getId());

        partialUpdatedShipmentHistory.event(UPDATED_EVENT).timestamp(UPDATED_TIMESTAMP);

        restShipmentHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedShipmentHistory.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedShipmentHistory))
            )
            .andExpect(status().isOk());

        // Validate the ShipmentHistory in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertShipmentHistoryUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedShipmentHistory, shipmentHistory),
            getPersistedShipmentHistory(shipmentHistory)
        );
    }

    @Test
    void fullUpdateShipmentHistoryWithPatch() throws Exception {
        // Initialize the database
        shipmentHistory.setId(UUID.randomUUID());
        shipmentHistoryRepository.save(shipmentHistory);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the shipmentHistory using partial update
        ShipmentHistory partialUpdatedShipmentHistory = new ShipmentHistory();
        partialUpdatedShipmentHistory.setId(shipmentHistory.getId());

        partialUpdatedShipmentHistory
            .event(UPDATED_EVENT)
            .timestamp(UPDATED_TIMESTAMP)
            .executedBy(UPDATED_EXECUTED_BY)
            .location(UPDATED_LOCATION)
            .lat(UPDATED_LAT)
            .lng(UPDATED_LNG);

        restShipmentHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedShipmentHistory.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedShipmentHistory))
            )
            .andExpect(status().isOk());

        // Validate the ShipmentHistory in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertShipmentHistoryUpdatableFieldsEquals(
            partialUpdatedShipmentHistory,
            getPersistedShipmentHistory(partialUpdatedShipmentHistory)
        );
    }

    @Test
    void patchNonExistingShipmentHistory() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        shipmentHistory.setId(UUID.randomUUID());

        // Create the ShipmentHistory
        ShipmentHistoryDTO shipmentHistoryDTO = shipmentHistoryMapper.toDto(shipmentHistory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restShipmentHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, shipmentHistoryDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(shipmentHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ShipmentHistory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchShipmentHistory() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        shipmentHistory.setId(UUID.randomUUID());

        // Create the ShipmentHistory
        ShipmentHistoryDTO shipmentHistoryDTO = shipmentHistoryMapper.toDto(shipmentHistory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restShipmentHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(shipmentHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ShipmentHistory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamShipmentHistory() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        shipmentHistory.setId(UUID.randomUUID());

        // Create the ShipmentHistory
        ShipmentHistoryDTO shipmentHistoryDTO = shipmentHistoryMapper.toDto(shipmentHistory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restShipmentHistoryMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(shipmentHistoryDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ShipmentHistory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteShipmentHistory() throws Exception {
        // Initialize the database
        shipmentHistory.setId(UUID.randomUUID());
        shipmentHistoryRepository.save(shipmentHistory);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the shipmentHistory
        restShipmentHistoryMockMvc
            .perform(delete(ENTITY_API_URL_ID, shipmentHistory.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return shipmentHistoryRepository.count();
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

    protected ShipmentHistory getPersistedShipmentHistory(ShipmentHistory shipmentHistory) {
        return shipmentHistoryRepository.findById(shipmentHistory.getId()).orElseThrow();
    }

    protected void assertPersistedShipmentHistoryToMatchAllProperties(ShipmentHistory expectedShipmentHistory) {
        assertShipmentHistoryAllPropertiesEquals(expectedShipmentHistory, getPersistedShipmentHistory(expectedShipmentHistory));
    }

    protected void assertPersistedShipmentHistoryToMatchUpdatableProperties(ShipmentHistory expectedShipmentHistory) {
        assertShipmentHistoryAllUpdatablePropertiesEquals(expectedShipmentHistory, getPersistedShipmentHistory(expectedShipmentHistory));
    }
}

package vn.containergo.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static vn.containergo.domain.ShipmentPlanAsserts.*;
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
import vn.containergo.domain.ShipmentPlan;
import vn.containergo.repository.ShipmentPlanRepository;
import vn.containergo.service.dto.ShipmentPlanDTO;
import vn.containergo.service.mapper.ShipmentPlanMapper;

/**
 * Integration tests for the {@link ShipmentPlanResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ShipmentPlanResourceIT {

    private static final Instant DEFAULT_ESTIMATED_PICKUP_FROM_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ESTIMATED_PICKUP_FROM_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_ESTIMATED_PICKUP_UNTIL_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ESTIMATED_PICKUP_UNTIL_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_ESTIMATED_DROPOFF_FROM_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ESTIMATED_DROPOFF_FROM_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_ESTIMATED_DROPOFF_UNTIL_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ESTIMATED_DROPOFF_UNTIL_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final UUID DEFAULT_DRIVER_ID = UUID.randomUUID();
    private static final UUID UPDATED_DRIVER_ID = UUID.randomUUID();

    private static final UUID DEFAULT_TRUCK_ID = UUID.randomUUID();
    private static final UUID UPDATED_TRUCK_ID = UUID.randomUUID();

    private static final String ENTITY_API_URL = "/api/shipment-plans";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ShipmentPlanRepository shipmentPlanRepository;

    @Autowired
    private ShipmentPlanMapper shipmentPlanMapper;

    @Autowired
    private MockMvc restShipmentPlanMockMvc;

    private ShipmentPlan shipmentPlan;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ShipmentPlan createEntity() {
        ShipmentPlan shipmentPlan = new ShipmentPlan()
            .estimatedPickupFromDate(DEFAULT_ESTIMATED_PICKUP_FROM_DATE)
            .estimatedPickupUntilDate(DEFAULT_ESTIMATED_PICKUP_UNTIL_DATE)
            .estimatedDropoffFromDate(DEFAULT_ESTIMATED_DROPOFF_FROM_DATE)
            .estimatedDropoffUntilDate(DEFAULT_ESTIMATED_DROPOFF_UNTIL_DATE)
            .driverId(DEFAULT_DRIVER_ID)
            .truckId(DEFAULT_TRUCK_ID);
        return shipmentPlan;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ShipmentPlan createUpdatedEntity() {
        ShipmentPlan shipmentPlan = new ShipmentPlan()
            .estimatedPickupFromDate(UPDATED_ESTIMATED_PICKUP_FROM_DATE)
            .estimatedPickupUntilDate(UPDATED_ESTIMATED_PICKUP_UNTIL_DATE)
            .estimatedDropoffFromDate(UPDATED_ESTIMATED_DROPOFF_FROM_DATE)
            .estimatedDropoffUntilDate(UPDATED_ESTIMATED_DROPOFF_UNTIL_DATE)
            .driverId(UPDATED_DRIVER_ID)
            .truckId(UPDATED_TRUCK_ID);
        return shipmentPlan;
    }

    @BeforeEach
    public void initTest() {
        shipmentPlanRepository.deleteAll();
        shipmentPlan = createEntity();
    }

    @Test
    void createShipmentPlan() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the ShipmentPlan
        ShipmentPlanDTO shipmentPlanDTO = shipmentPlanMapper.toDto(shipmentPlan);
        var returnedShipmentPlanDTO = om.readValue(
            restShipmentPlanMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(shipmentPlanDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ShipmentPlanDTO.class
        );

        // Validate the ShipmentPlan in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedShipmentPlan = shipmentPlanMapper.toEntity(returnedShipmentPlanDTO);
        assertShipmentPlanUpdatableFieldsEquals(returnedShipmentPlan, getPersistedShipmentPlan(returnedShipmentPlan));
    }

    @Test
    void createShipmentPlanWithExistingId() throws Exception {
        // Create the ShipmentPlan with an existing ID
        shipmentPlan.setId(UUID.randomUUID());
        ShipmentPlanDTO shipmentPlanDTO = shipmentPlanMapper.toDto(shipmentPlan);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restShipmentPlanMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(shipmentPlanDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ShipmentPlan in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    void checkEstimatedPickupFromDateIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        shipmentPlan.setEstimatedPickupFromDate(null);

        // Create the ShipmentPlan, which fails.
        ShipmentPlanDTO shipmentPlanDTO = shipmentPlanMapper.toDto(shipmentPlan);

        restShipmentPlanMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(shipmentPlanDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkEstimatedPickupUntilDateIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        shipmentPlan.setEstimatedPickupUntilDate(null);

        // Create the ShipmentPlan, which fails.
        ShipmentPlanDTO shipmentPlanDTO = shipmentPlanMapper.toDto(shipmentPlan);

        restShipmentPlanMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(shipmentPlanDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkEstimatedDropoffFromDateIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        shipmentPlan.setEstimatedDropoffFromDate(null);

        // Create the ShipmentPlan, which fails.
        ShipmentPlanDTO shipmentPlanDTO = shipmentPlanMapper.toDto(shipmentPlan);

        restShipmentPlanMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(shipmentPlanDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkEstimatedDropoffUntilDateIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        shipmentPlan.setEstimatedDropoffUntilDate(null);

        // Create the ShipmentPlan, which fails.
        ShipmentPlanDTO shipmentPlanDTO = shipmentPlanMapper.toDto(shipmentPlan);

        restShipmentPlanMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(shipmentPlanDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkDriverIdIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        shipmentPlan.setDriverId(null);

        // Create the ShipmentPlan, which fails.
        ShipmentPlanDTO shipmentPlanDTO = shipmentPlanMapper.toDto(shipmentPlan);

        restShipmentPlanMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(shipmentPlanDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkTruckIdIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        shipmentPlan.setTruckId(null);

        // Create the ShipmentPlan, which fails.
        ShipmentPlanDTO shipmentPlanDTO = shipmentPlanMapper.toDto(shipmentPlan);

        restShipmentPlanMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(shipmentPlanDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void getAllShipmentPlans() throws Exception {
        // Initialize the database
        shipmentPlan.setId(UUID.randomUUID());
        shipmentPlanRepository.save(shipmentPlan);

        // Get all the shipmentPlanList
        restShipmentPlanMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(shipmentPlan.getId().toString())))
            .andExpect(jsonPath("$.[*].estimatedPickupFromDate").value(hasItem(DEFAULT_ESTIMATED_PICKUP_FROM_DATE.toString())))
            .andExpect(jsonPath("$.[*].estimatedPickupUntilDate").value(hasItem(DEFAULT_ESTIMATED_PICKUP_UNTIL_DATE.toString())))
            .andExpect(jsonPath("$.[*].estimatedDropoffFromDate").value(hasItem(DEFAULT_ESTIMATED_DROPOFF_FROM_DATE.toString())))
            .andExpect(jsonPath("$.[*].estimatedDropoffUntilDate").value(hasItem(DEFAULT_ESTIMATED_DROPOFF_UNTIL_DATE.toString())))
            .andExpect(jsonPath("$.[*].driverId").value(hasItem(DEFAULT_DRIVER_ID.toString())))
            .andExpect(jsonPath("$.[*].truckId").value(hasItem(DEFAULT_TRUCK_ID.toString())));
    }

    @Test
    void getShipmentPlan() throws Exception {
        // Initialize the database
        shipmentPlan.setId(UUID.randomUUID());
        shipmentPlanRepository.save(shipmentPlan);

        // Get the shipmentPlan
        restShipmentPlanMockMvc
            .perform(get(ENTITY_API_URL_ID, shipmentPlan.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(shipmentPlan.getId().toString()))
            .andExpect(jsonPath("$.estimatedPickupFromDate").value(DEFAULT_ESTIMATED_PICKUP_FROM_DATE.toString()))
            .andExpect(jsonPath("$.estimatedPickupUntilDate").value(DEFAULT_ESTIMATED_PICKUP_UNTIL_DATE.toString()))
            .andExpect(jsonPath("$.estimatedDropoffFromDate").value(DEFAULT_ESTIMATED_DROPOFF_FROM_DATE.toString()))
            .andExpect(jsonPath("$.estimatedDropoffUntilDate").value(DEFAULT_ESTIMATED_DROPOFF_UNTIL_DATE.toString()))
            .andExpect(jsonPath("$.driverId").value(DEFAULT_DRIVER_ID.toString()))
            .andExpect(jsonPath("$.truckId").value(DEFAULT_TRUCK_ID.toString()));
    }

    @Test
    void getNonExistingShipmentPlan() throws Exception {
        // Get the shipmentPlan
        restShipmentPlanMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    void putExistingShipmentPlan() throws Exception {
        // Initialize the database
        shipmentPlan.setId(UUID.randomUUID());
        shipmentPlanRepository.save(shipmentPlan);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the shipmentPlan
        ShipmentPlan updatedShipmentPlan = shipmentPlanRepository.findById(shipmentPlan.getId()).orElseThrow();
        updatedShipmentPlan
            .estimatedPickupFromDate(UPDATED_ESTIMATED_PICKUP_FROM_DATE)
            .estimatedPickupUntilDate(UPDATED_ESTIMATED_PICKUP_UNTIL_DATE)
            .estimatedDropoffFromDate(UPDATED_ESTIMATED_DROPOFF_FROM_DATE)
            .estimatedDropoffUntilDate(UPDATED_ESTIMATED_DROPOFF_UNTIL_DATE)
            .driverId(UPDATED_DRIVER_ID)
            .truckId(UPDATED_TRUCK_ID);
        ShipmentPlanDTO shipmentPlanDTO = shipmentPlanMapper.toDto(updatedShipmentPlan);

        restShipmentPlanMockMvc
            .perform(
                put(ENTITY_API_URL_ID, shipmentPlanDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(shipmentPlanDTO))
            )
            .andExpect(status().isOk());

        // Validate the ShipmentPlan in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedShipmentPlanToMatchAllProperties(updatedShipmentPlan);
    }

    @Test
    void putNonExistingShipmentPlan() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        shipmentPlan.setId(UUID.randomUUID());

        // Create the ShipmentPlan
        ShipmentPlanDTO shipmentPlanDTO = shipmentPlanMapper.toDto(shipmentPlan);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restShipmentPlanMockMvc
            .perform(
                put(ENTITY_API_URL_ID, shipmentPlanDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(shipmentPlanDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ShipmentPlan in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchShipmentPlan() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        shipmentPlan.setId(UUID.randomUUID());

        // Create the ShipmentPlan
        ShipmentPlanDTO shipmentPlanDTO = shipmentPlanMapper.toDto(shipmentPlan);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restShipmentPlanMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(shipmentPlanDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ShipmentPlan in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamShipmentPlan() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        shipmentPlan.setId(UUID.randomUUID());

        // Create the ShipmentPlan
        ShipmentPlanDTO shipmentPlanDTO = shipmentPlanMapper.toDto(shipmentPlan);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restShipmentPlanMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(shipmentPlanDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ShipmentPlan in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateShipmentPlanWithPatch() throws Exception {
        // Initialize the database
        shipmentPlan.setId(UUID.randomUUID());
        shipmentPlanRepository.save(shipmentPlan);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the shipmentPlan using partial update
        ShipmentPlan partialUpdatedShipmentPlan = new ShipmentPlan();
        partialUpdatedShipmentPlan.setId(shipmentPlan.getId());

        partialUpdatedShipmentPlan
            .estimatedPickupFromDate(UPDATED_ESTIMATED_PICKUP_FROM_DATE)
            .estimatedPickupUntilDate(UPDATED_ESTIMATED_PICKUP_UNTIL_DATE)
            .estimatedDropoffFromDate(UPDATED_ESTIMATED_DROPOFF_FROM_DATE)
            .estimatedDropoffUntilDate(UPDATED_ESTIMATED_DROPOFF_UNTIL_DATE)
            .truckId(UPDATED_TRUCK_ID);

        restShipmentPlanMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedShipmentPlan.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedShipmentPlan))
            )
            .andExpect(status().isOk());

        // Validate the ShipmentPlan in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertShipmentPlanUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedShipmentPlan, shipmentPlan),
            getPersistedShipmentPlan(shipmentPlan)
        );
    }

    @Test
    void fullUpdateShipmentPlanWithPatch() throws Exception {
        // Initialize the database
        shipmentPlan.setId(UUID.randomUUID());
        shipmentPlanRepository.save(shipmentPlan);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the shipmentPlan using partial update
        ShipmentPlan partialUpdatedShipmentPlan = new ShipmentPlan();
        partialUpdatedShipmentPlan.setId(shipmentPlan.getId());

        partialUpdatedShipmentPlan
            .estimatedPickupFromDate(UPDATED_ESTIMATED_PICKUP_FROM_DATE)
            .estimatedPickupUntilDate(UPDATED_ESTIMATED_PICKUP_UNTIL_DATE)
            .estimatedDropoffFromDate(UPDATED_ESTIMATED_DROPOFF_FROM_DATE)
            .estimatedDropoffUntilDate(UPDATED_ESTIMATED_DROPOFF_UNTIL_DATE)
            .driverId(UPDATED_DRIVER_ID)
            .truckId(UPDATED_TRUCK_ID);

        restShipmentPlanMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedShipmentPlan.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedShipmentPlan))
            )
            .andExpect(status().isOk());

        // Validate the ShipmentPlan in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertShipmentPlanUpdatableFieldsEquals(partialUpdatedShipmentPlan, getPersistedShipmentPlan(partialUpdatedShipmentPlan));
    }

    @Test
    void patchNonExistingShipmentPlan() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        shipmentPlan.setId(UUID.randomUUID());

        // Create the ShipmentPlan
        ShipmentPlanDTO shipmentPlanDTO = shipmentPlanMapper.toDto(shipmentPlan);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restShipmentPlanMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, shipmentPlanDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(shipmentPlanDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ShipmentPlan in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchShipmentPlan() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        shipmentPlan.setId(UUID.randomUUID());

        // Create the ShipmentPlan
        ShipmentPlanDTO shipmentPlanDTO = shipmentPlanMapper.toDto(shipmentPlan);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restShipmentPlanMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(shipmentPlanDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ShipmentPlan in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamShipmentPlan() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        shipmentPlan.setId(UUID.randomUUID());

        // Create the ShipmentPlan
        ShipmentPlanDTO shipmentPlanDTO = shipmentPlanMapper.toDto(shipmentPlan);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restShipmentPlanMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(shipmentPlanDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ShipmentPlan in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteShipmentPlan() throws Exception {
        // Initialize the database
        shipmentPlan.setId(UUID.randomUUID());
        shipmentPlanRepository.save(shipmentPlan);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the shipmentPlan
        restShipmentPlanMockMvc
            .perform(delete(ENTITY_API_URL_ID, shipmentPlan.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return shipmentPlanRepository.count();
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

    protected ShipmentPlan getPersistedShipmentPlan(ShipmentPlan shipmentPlan) {
        return shipmentPlanRepository.findById(shipmentPlan.getId()).orElseThrow();
    }

    protected void assertPersistedShipmentPlanToMatchAllProperties(ShipmentPlan expectedShipmentPlan) {
        assertShipmentPlanAllPropertiesEquals(expectedShipmentPlan, getPersistedShipmentPlan(expectedShipmentPlan));
    }

    protected void assertPersistedShipmentPlanToMatchUpdatableProperties(ShipmentPlan expectedShipmentPlan) {
        assertShipmentPlanAllUpdatablePropertiesEquals(expectedShipmentPlan, getPersistedShipmentPlan(expectedShipmentPlan));
    }
}

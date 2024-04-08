package vn.containergo.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static vn.containergo.domain.TruckAsserts.*;
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
import vn.containergo.domain.Truck;
import vn.containergo.domain.TruckType;
import vn.containergo.domain.enumeration.TruckStatus;
import vn.containergo.repository.TruckRepository;
import vn.containergo.service.dto.TruckDTO;
import vn.containergo.service.mapper.TruckMapper;

/**
 * Integration tests for the {@link TruckResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TruckResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_MODEL = "AAAAAAAAAA";
    private static final String UPDATED_MODEL = "BBBBBBBBBB";

    private static final String DEFAULT_MANUFACTURER = "AAAAAAAAAA";
    private static final String UPDATED_MANUFACTURER = "BBBBBBBBBB";

    private static final Integer DEFAULT_YEAR = 1;
    private static final Integer UPDATED_YEAR = 2;

    private static final Double DEFAULT_CAPACITY = 1D;
    private static final Double UPDATED_CAPACITY = 2D;

    private static final TruckStatus DEFAULT_STATUS = TruckStatus.AVAILABLE;
    private static final TruckStatus UPDATED_STATUS = TruckStatus.IN_TRANSIT;

    private static final Double DEFAULT_MILEAGE = 1D;
    private static final Double UPDATED_MILEAGE = 2D;

    private static final String DEFAULT_NUMBER_PLATE = "AAAAAAAAAA";
    private static final String UPDATED_NUMBER_PLATE = "BBBBBBBBBB";

    private static final Double DEFAULT_LAT = 1D;
    private static final Double UPDATED_LAT = 2D;

    private static final Double DEFAULT_LNG = 1D;
    private static final Double UPDATED_LNG = 2D;

    private static final String ENTITY_API_URL = "/api/trucks";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private TruckRepository truckRepository;

    @Autowired
    private TruckMapper truckMapper;

    @Autowired
    private MockMvc restTruckMockMvc;

    private Truck truck;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Truck createEntity() {
        Truck truck = new Truck()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .model(DEFAULT_MODEL)
            .manufacturer(DEFAULT_MANUFACTURER)
            .year(DEFAULT_YEAR)
            .capacity(DEFAULT_CAPACITY)
            .status(DEFAULT_STATUS)
            .mileage(DEFAULT_MILEAGE)
            .numberPlate(DEFAULT_NUMBER_PLATE)
            .lat(DEFAULT_LAT)
            .lng(DEFAULT_LNG);
        // Add required entity
        TruckType truckType;
        truckType = TruckTypeResourceIT.createEntity();
        truckType.setId("fixed-id-for-tests");
        truck.setType(truckType);
        return truck;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Truck createUpdatedEntity() {
        Truck truck = new Truck()
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .model(UPDATED_MODEL)
            .manufacturer(UPDATED_MANUFACTURER)
            .year(UPDATED_YEAR)
            .capacity(UPDATED_CAPACITY)
            .status(UPDATED_STATUS)
            .mileage(UPDATED_MILEAGE)
            .numberPlate(UPDATED_NUMBER_PLATE)
            .lat(UPDATED_LAT)
            .lng(UPDATED_LNG);
        // Add required entity
        TruckType truckType;
        truckType = TruckTypeResourceIT.createUpdatedEntity();
        truckType.setId("fixed-id-for-tests");
        truck.setType(truckType);
        return truck;
    }

    @BeforeEach
    public void initTest() {
        truckRepository.deleteAll();
        truck = createEntity();
    }

    @Test
    void createTruck() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Truck
        TruckDTO truckDTO = truckMapper.toDto(truck);
        var returnedTruckDTO = om.readValue(
            restTruckMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(truckDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            TruckDTO.class
        );

        // Validate the Truck in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedTruck = truckMapper.toEntity(returnedTruckDTO);
        assertTruckUpdatableFieldsEquals(returnedTruck, getPersistedTruck(returnedTruck));
    }

    @Test
    void createTruckWithExistingId() throws Exception {
        // Create the Truck with an existing ID
        truck.setId(UUID.randomUUID());
        TruckDTO truckDTO = truckMapper.toDto(truck);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTruckMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(truckDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Truck in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    void checkCodeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        truck.setCode(null);

        // Create the Truck, which fails.
        TruckDTO truckDTO = truckMapper.toDto(truck);

        restTruckMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(truckDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        truck.setName(null);

        // Create the Truck, which fails.
        TruckDTO truckDTO = truckMapper.toDto(truck);

        restTruckMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(truckDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkStatusIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        truck.setStatus(null);

        // Create the Truck, which fails.
        TruckDTO truckDTO = truckMapper.toDto(truck);

        restTruckMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(truckDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkNumberPlateIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        truck.setNumberPlate(null);

        // Create the Truck, which fails.
        TruckDTO truckDTO = truckMapper.toDto(truck);

        restTruckMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(truckDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void getAllTrucks() throws Exception {
        // Initialize the database
        truck.setId(UUID.randomUUID());
        truckRepository.save(truck);

        // Get all the truckList
        restTruckMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(truck.getId().toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].model").value(hasItem(DEFAULT_MODEL)))
            .andExpect(jsonPath("$.[*].manufacturer").value(hasItem(DEFAULT_MANUFACTURER)))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR)))
            .andExpect(jsonPath("$.[*].capacity").value(hasItem(DEFAULT_CAPACITY.doubleValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].mileage").value(hasItem(DEFAULT_MILEAGE.doubleValue())))
            .andExpect(jsonPath("$.[*].numberPlate").value(hasItem(DEFAULT_NUMBER_PLATE)))
            .andExpect(jsonPath("$.[*].lat").value(hasItem(DEFAULT_LAT.doubleValue())))
            .andExpect(jsonPath("$.[*].lng").value(hasItem(DEFAULT_LNG.doubleValue())));
    }

    @Test
    void getTruck() throws Exception {
        // Initialize the database
        truck.setId(UUID.randomUUID());
        truckRepository.save(truck);

        // Get the truck
        restTruckMockMvc
            .perform(get(ENTITY_API_URL_ID, truck.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(truck.getId().toString()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.model").value(DEFAULT_MODEL))
            .andExpect(jsonPath("$.manufacturer").value(DEFAULT_MANUFACTURER))
            .andExpect(jsonPath("$.year").value(DEFAULT_YEAR))
            .andExpect(jsonPath("$.capacity").value(DEFAULT_CAPACITY.doubleValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.mileage").value(DEFAULT_MILEAGE.doubleValue()))
            .andExpect(jsonPath("$.numberPlate").value(DEFAULT_NUMBER_PLATE))
            .andExpect(jsonPath("$.lat").value(DEFAULT_LAT.doubleValue()))
            .andExpect(jsonPath("$.lng").value(DEFAULT_LNG.doubleValue()));
    }

    @Test
    void getNonExistingTruck() throws Exception {
        // Get the truck
        restTruckMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    void putExistingTruck() throws Exception {
        // Initialize the database
        truck.setId(UUID.randomUUID());
        truckRepository.save(truck);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the truck
        Truck updatedTruck = truckRepository.findById(truck.getId()).orElseThrow();
        updatedTruck
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .model(UPDATED_MODEL)
            .manufacturer(UPDATED_MANUFACTURER)
            .year(UPDATED_YEAR)
            .capacity(UPDATED_CAPACITY)
            .status(UPDATED_STATUS)
            .mileage(UPDATED_MILEAGE)
            .numberPlate(UPDATED_NUMBER_PLATE)
            .lat(UPDATED_LAT)
            .lng(UPDATED_LNG);
        TruckDTO truckDTO = truckMapper.toDto(updatedTruck);

        restTruckMockMvc
            .perform(
                put(ENTITY_API_URL_ID, truckDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(truckDTO))
            )
            .andExpect(status().isOk());

        // Validate the Truck in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedTruckToMatchAllProperties(updatedTruck);
    }

    @Test
    void putNonExistingTruck() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        truck.setId(UUID.randomUUID());

        // Create the Truck
        TruckDTO truckDTO = truckMapper.toDto(truck);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTruckMockMvc
            .perform(
                put(ENTITY_API_URL_ID, truckDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(truckDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Truck in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchTruck() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        truck.setId(UUID.randomUUID());

        // Create the Truck
        TruckDTO truckDTO = truckMapper.toDto(truck);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTruckMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(truckDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Truck in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamTruck() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        truck.setId(UUID.randomUUID());

        // Create the Truck
        TruckDTO truckDTO = truckMapper.toDto(truck);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTruckMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(truckDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Truck in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateTruckWithPatch() throws Exception {
        // Initialize the database
        truck.setId(UUID.randomUUID());
        truckRepository.save(truck);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the truck using partial update
        Truck partialUpdatedTruck = new Truck();
        partialUpdatedTruck.setId(truck.getId());

        partialUpdatedTruck.code(UPDATED_CODE).name(UPDATED_NAME).mileage(UPDATED_MILEAGE);

        restTruckMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTruck.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTruck))
            )
            .andExpect(status().isOk());

        // Validate the Truck in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTruckUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedTruck, truck), getPersistedTruck(truck));
    }

    @Test
    void fullUpdateTruckWithPatch() throws Exception {
        // Initialize the database
        truck.setId(UUID.randomUUID());
        truckRepository.save(truck);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the truck using partial update
        Truck partialUpdatedTruck = new Truck();
        partialUpdatedTruck.setId(truck.getId());

        partialUpdatedTruck
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .model(UPDATED_MODEL)
            .manufacturer(UPDATED_MANUFACTURER)
            .year(UPDATED_YEAR)
            .capacity(UPDATED_CAPACITY)
            .status(UPDATED_STATUS)
            .mileage(UPDATED_MILEAGE)
            .numberPlate(UPDATED_NUMBER_PLATE)
            .lat(UPDATED_LAT)
            .lng(UPDATED_LNG);

        restTruckMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTruck.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTruck))
            )
            .andExpect(status().isOk());

        // Validate the Truck in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTruckUpdatableFieldsEquals(partialUpdatedTruck, getPersistedTruck(partialUpdatedTruck));
    }

    @Test
    void patchNonExistingTruck() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        truck.setId(UUID.randomUUID());

        // Create the Truck
        TruckDTO truckDTO = truckMapper.toDto(truck);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTruckMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, truckDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(truckDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Truck in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchTruck() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        truck.setId(UUID.randomUUID());

        // Create the Truck
        TruckDTO truckDTO = truckMapper.toDto(truck);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTruckMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(truckDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Truck in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamTruck() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        truck.setId(UUID.randomUUID());

        // Create the Truck
        TruckDTO truckDTO = truckMapper.toDto(truck);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTruckMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(truckDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Truck in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteTruck() throws Exception {
        // Initialize the database
        truck.setId(UUID.randomUUID());
        truckRepository.save(truck);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the truck
        restTruckMockMvc
            .perform(delete(ENTITY_API_URL_ID, truck.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return truckRepository.count();
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

    protected Truck getPersistedTruck(Truck truck) {
        return truckRepository.findById(truck.getId()).orElseThrow();
    }

    protected void assertPersistedTruckToMatchAllProperties(Truck expectedTruck) {
        assertTruckAllPropertiesEquals(expectedTruck, getPersistedTruck(expectedTruck));
    }

    protected void assertPersistedTruckToMatchUpdatableProperties(Truck expectedTruck) {
        assertTruckAllUpdatablePropertiesEquals(expectedTruck, getPersistedTruck(expectedTruck));
    }
}

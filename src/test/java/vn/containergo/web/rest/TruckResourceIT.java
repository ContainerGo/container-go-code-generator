package vn.containergo.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
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

    private static final String ENTITY_API_URL = "/api/trucks";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

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
            .numberPlate(DEFAULT_NUMBER_PLATE);
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
            .numberPlate(UPDATED_NUMBER_PLATE);
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
        int databaseSizeBeforeCreate = truckRepository.findAll().size();
        // Create the Truck
        TruckDTO truckDTO = truckMapper.toDto(truck);
        restTruckMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(truckDTO)))
            .andExpect(status().isCreated());

        // Validate the Truck in the database
        List<Truck> truckList = truckRepository.findAll();
        assertThat(truckList).hasSize(databaseSizeBeforeCreate + 1);
        Truck testTruck = truckList.get(truckList.size() - 1);
        assertThat(testTruck.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testTruck.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTruck.getModel()).isEqualTo(DEFAULT_MODEL);
        assertThat(testTruck.getManufacturer()).isEqualTo(DEFAULT_MANUFACTURER);
        assertThat(testTruck.getYear()).isEqualTo(DEFAULT_YEAR);
        assertThat(testTruck.getCapacity()).isEqualTo(DEFAULT_CAPACITY);
        assertThat(testTruck.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testTruck.getMileage()).isEqualTo(DEFAULT_MILEAGE);
        assertThat(testTruck.getNumberPlate()).isEqualTo(DEFAULT_NUMBER_PLATE);
    }

    @Test
    void createTruckWithExistingId() throws Exception {
        // Create the Truck with an existing ID
        truck.setId(1L);
        TruckDTO truckDTO = truckMapper.toDto(truck);

        int databaseSizeBeforeCreate = truckRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTruckMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(truckDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Truck in the database
        List<Truck> truckList = truckRepository.findAll();
        assertThat(truckList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = truckRepository.findAll().size();
        // set the field null
        truck.setCode(null);

        // Create the Truck, which fails.
        TruckDTO truckDTO = truckMapper.toDto(truck);

        restTruckMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(truckDTO)))
            .andExpect(status().isBadRequest());

        List<Truck> truckList = truckRepository.findAll();
        assertThat(truckList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = truckRepository.findAll().size();
        // set the field null
        truck.setName(null);

        // Create the Truck, which fails.
        TruckDTO truckDTO = truckMapper.toDto(truck);

        restTruckMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(truckDTO)))
            .andExpect(status().isBadRequest());

        List<Truck> truckList = truckRepository.findAll();
        assertThat(truckList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = truckRepository.findAll().size();
        // set the field null
        truck.setStatus(null);

        // Create the Truck, which fails.
        TruckDTO truckDTO = truckMapper.toDto(truck);

        restTruckMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(truckDTO)))
            .andExpect(status().isBadRequest());

        List<Truck> truckList = truckRepository.findAll();
        assertThat(truckList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkNumberPlateIsRequired() throws Exception {
        int databaseSizeBeforeTest = truckRepository.findAll().size();
        // set the field null
        truck.setNumberPlate(null);

        // Create the Truck, which fails.
        TruckDTO truckDTO = truckMapper.toDto(truck);

        restTruckMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(truckDTO)))
            .andExpect(status().isBadRequest());

        List<Truck> truckList = truckRepository.findAll();
        assertThat(truckList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void getAllTrucks() throws Exception {
        // Initialize the database
        truckRepository.save(truck);

        // Get all the truckList
        restTruckMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(truck.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].model").value(hasItem(DEFAULT_MODEL)))
            .andExpect(jsonPath("$.[*].manufacturer").value(hasItem(DEFAULT_MANUFACTURER)))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR)))
            .andExpect(jsonPath("$.[*].capacity").value(hasItem(DEFAULT_CAPACITY.doubleValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].mileage").value(hasItem(DEFAULT_MILEAGE.doubleValue())))
            .andExpect(jsonPath("$.[*].numberPlate").value(hasItem(DEFAULT_NUMBER_PLATE)));
    }

    @Test
    void getTruck() throws Exception {
        // Initialize the database
        truckRepository.save(truck);

        // Get the truck
        restTruckMockMvc
            .perform(get(ENTITY_API_URL_ID, truck.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(truck.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.model").value(DEFAULT_MODEL))
            .andExpect(jsonPath("$.manufacturer").value(DEFAULT_MANUFACTURER))
            .andExpect(jsonPath("$.year").value(DEFAULT_YEAR))
            .andExpect(jsonPath("$.capacity").value(DEFAULT_CAPACITY.doubleValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.mileage").value(DEFAULT_MILEAGE.doubleValue()))
            .andExpect(jsonPath("$.numberPlate").value(DEFAULT_NUMBER_PLATE));
    }

    @Test
    void getNonExistingTruck() throws Exception {
        // Get the truck
        restTruckMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingTruck() throws Exception {
        // Initialize the database
        truckRepository.save(truck);

        int databaseSizeBeforeUpdate = truckRepository.findAll().size();

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
            .numberPlate(UPDATED_NUMBER_PLATE);
        TruckDTO truckDTO = truckMapper.toDto(updatedTruck);

        restTruckMockMvc
            .perform(
                put(ENTITY_API_URL_ID, truckDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(truckDTO))
            )
            .andExpect(status().isOk());

        // Validate the Truck in the database
        List<Truck> truckList = truckRepository.findAll();
        assertThat(truckList).hasSize(databaseSizeBeforeUpdate);
        Truck testTruck = truckList.get(truckList.size() - 1);
        assertThat(testTruck.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testTruck.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTruck.getModel()).isEqualTo(UPDATED_MODEL);
        assertThat(testTruck.getManufacturer()).isEqualTo(UPDATED_MANUFACTURER);
        assertThat(testTruck.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testTruck.getCapacity()).isEqualTo(UPDATED_CAPACITY);
        assertThat(testTruck.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testTruck.getMileage()).isEqualTo(UPDATED_MILEAGE);
        assertThat(testTruck.getNumberPlate()).isEqualTo(UPDATED_NUMBER_PLATE);
    }

    @Test
    void putNonExistingTruck() throws Exception {
        int databaseSizeBeforeUpdate = truckRepository.findAll().size();
        truck.setId(longCount.incrementAndGet());

        // Create the Truck
        TruckDTO truckDTO = truckMapper.toDto(truck);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTruckMockMvc
            .perform(
                put(ENTITY_API_URL_ID, truckDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(truckDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Truck in the database
        List<Truck> truckList = truckRepository.findAll();
        assertThat(truckList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchTruck() throws Exception {
        int databaseSizeBeforeUpdate = truckRepository.findAll().size();
        truck.setId(longCount.incrementAndGet());

        // Create the Truck
        TruckDTO truckDTO = truckMapper.toDto(truck);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTruckMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(truckDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Truck in the database
        List<Truck> truckList = truckRepository.findAll();
        assertThat(truckList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamTruck() throws Exception {
        int databaseSizeBeforeUpdate = truckRepository.findAll().size();
        truck.setId(longCount.incrementAndGet());

        // Create the Truck
        TruckDTO truckDTO = truckMapper.toDto(truck);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTruckMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(truckDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Truck in the database
        List<Truck> truckList = truckRepository.findAll();
        assertThat(truckList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateTruckWithPatch() throws Exception {
        // Initialize the database
        truckRepository.save(truck);

        int databaseSizeBeforeUpdate = truckRepository.findAll().size();

        // Update the truck using partial update
        Truck partialUpdatedTruck = new Truck();
        partialUpdatedTruck.setId(truck.getId());

        partialUpdatedTruck.name(UPDATED_NAME).year(UPDATED_YEAR).mileage(UPDATED_MILEAGE).numberPlate(UPDATED_NUMBER_PLATE);

        restTruckMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTruck.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTruck))
            )
            .andExpect(status().isOk());

        // Validate the Truck in the database
        List<Truck> truckList = truckRepository.findAll();
        assertThat(truckList).hasSize(databaseSizeBeforeUpdate);
        Truck testTruck = truckList.get(truckList.size() - 1);
        assertThat(testTruck.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testTruck.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTruck.getModel()).isEqualTo(DEFAULT_MODEL);
        assertThat(testTruck.getManufacturer()).isEqualTo(DEFAULT_MANUFACTURER);
        assertThat(testTruck.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testTruck.getCapacity()).isEqualTo(DEFAULT_CAPACITY);
        assertThat(testTruck.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testTruck.getMileage()).isEqualTo(UPDATED_MILEAGE);
        assertThat(testTruck.getNumberPlate()).isEqualTo(UPDATED_NUMBER_PLATE);
    }

    @Test
    void fullUpdateTruckWithPatch() throws Exception {
        // Initialize the database
        truckRepository.save(truck);

        int databaseSizeBeforeUpdate = truckRepository.findAll().size();

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
            .numberPlate(UPDATED_NUMBER_PLATE);

        restTruckMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTruck.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTruck))
            )
            .andExpect(status().isOk());

        // Validate the Truck in the database
        List<Truck> truckList = truckRepository.findAll();
        assertThat(truckList).hasSize(databaseSizeBeforeUpdate);
        Truck testTruck = truckList.get(truckList.size() - 1);
        assertThat(testTruck.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testTruck.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTruck.getModel()).isEqualTo(UPDATED_MODEL);
        assertThat(testTruck.getManufacturer()).isEqualTo(UPDATED_MANUFACTURER);
        assertThat(testTruck.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testTruck.getCapacity()).isEqualTo(UPDATED_CAPACITY);
        assertThat(testTruck.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testTruck.getMileage()).isEqualTo(UPDATED_MILEAGE);
        assertThat(testTruck.getNumberPlate()).isEqualTo(UPDATED_NUMBER_PLATE);
    }

    @Test
    void patchNonExistingTruck() throws Exception {
        int databaseSizeBeforeUpdate = truckRepository.findAll().size();
        truck.setId(longCount.incrementAndGet());

        // Create the Truck
        TruckDTO truckDTO = truckMapper.toDto(truck);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTruckMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, truckDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(truckDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Truck in the database
        List<Truck> truckList = truckRepository.findAll();
        assertThat(truckList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchTruck() throws Exception {
        int databaseSizeBeforeUpdate = truckRepository.findAll().size();
        truck.setId(longCount.incrementAndGet());

        // Create the Truck
        TruckDTO truckDTO = truckMapper.toDto(truck);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTruckMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(truckDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Truck in the database
        List<Truck> truckList = truckRepository.findAll();
        assertThat(truckList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamTruck() throws Exception {
        int databaseSizeBeforeUpdate = truckRepository.findAll().size();
        truck.setId(longCount.incrementAndGet());

        // Create the Truck
        TruckDTO truckDTO = truckMapper.toDto(truck);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTruckMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(truckDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Truck in the database
        List<Truck> truckList = truckRepository.findAll();
        assertThat(truckList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteTruck() throws Exception {
        // Initialize the database
        truckRepository.save(truck);

        int databaseSizeBeforeDelete = truckRepository.findAll().size();

        // Delete the truck
        restTruckMockMvc
            .perform(delete(ENTITY_API_URL_ID, truck.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Truck> truckList = truckRepository.findAll();
        assertThat(truckList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

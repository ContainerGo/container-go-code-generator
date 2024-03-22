package vn.containergo.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
import vn.containergo.domain.Container;
import vn.containergo.domain.ContainerStatus;
import vn.containergo.domain.ContainerType;
import vn.containergo.domain.enumeration.ContainerState;
import vn.containergo.repository.ContainerRepository;
import vn.containergo.service.dto.ContainerDTO;
import vn.containergo.service.mapper.ContainerMapper;

/**
 * Integration tests for the {@link ContainerResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ContainerResourceIT {

    private static final String DEFAULT_CONT_NO = "AAAAAAAAAA";
    private static final String UPDATED_CONT_NO = "BBBBBBBBBB";

    private static final Double DEFAULT_ESTIMATED_PRICE = 1D;
    private static final Double UPDATED_ESTIMATED_PRICE = 2D;

    private static final Double DEFAULT_DISTANCE = 1D;
    private static final Double UPDATED_DISTANCE = 2D;

    private static final Double DEFAULT_DESIRED_PRICE = 1D;
    private static final Double UPDATED_DESIRED_PRICE = 2D;

    private static final String DEFAULT_ADDITIONAL_REQUIREMENTS = "AAAAAAAAAA";
    private static final String UPDATED_ADDITIONAL_REQUIREMENTS = "BBBBBBBBBB";

    private static final String DEFAULT_DROPOFF_CONTACT = "AAAAAAAAAA";
    private static final String UPDATED_DROPOFF_CONTACT = "BBBBBBBBBB";

    private static final String DEFAULT_DROPOFF_CONTACT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_DROPOFF_CONTACT_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_DROPOFF_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_DROPOFF_ADDRESS = "BBBBBBBBBB";

    private static final Double DEFAULT_DROPOFF_LAT = 1D;
    private static final Double UPDATED_DROPOFF_LAT = 2D;

    private static final Double DEFAULT_DROPOFF_LNG = 1D;
    private static final Double UPDATED_DROPOFF_LNG = 2D;

    private static final Instant DEFAULT_DROPOFF_UNTIL_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DROPOFF_UNTIL_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final ContainerState DEFAULT_STATE = ContainerState.NEW;
    private static final ContainerState UPDATED_STATE = ContainerState.BIDDING;

    private static final Long DEFAULT_SHIPPER_ID = 1L;
    private static final Long UPDATED_SHIPPER_ID = 2L;

    private static final Long DEFAULT_CARRIER_ID = 1L;
    private static final Long UPDATED_CARRIER_ID = 2L;

    private static final Double DEFAULT_TOTAL_WEIGHT = 1D;
    private static final Double UPDATED_TOTAL_WEIGHT = 2D;

    private static final Instant DEFAULT_PICKUP_FROM_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_PICKUP_FROM_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_BIDDING_FROM_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_BIDDING_FROM_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_BIDDING_UNTIL_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_BIDDING_UNTIL_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/containers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ContainerRepository containerRepository;

    @Autowired
    private ContainerMapper containerMapper;

    @Autowired
    private MockMvc restContainerMockMvc;

    private Container container;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Container createEntity() {
        Container container = new Container()
            .contNo(DEFAULT_CONT_NO)
            .estimatedPrice(DEFAULT_ESTIMATED_PRICE)
            .distance(DEFAULT_DISTANCE)
            .desiredPrice(DEFAULT_DESIRED_PRICE)
            .additionalRequirements(DEFAULT_ADDITIONAL_REQUIREMENTS)
            .dropoffContact(DEFAULT_DROPOFF_CONTACT)
            .dropoffContactPhone(DEFAULT_DROPOFF_CONTACT_PHONE)
            .dropoffAddress(DEFAULT_DROPOFF_ADDRESS)
            .dropoffLat(DEFAULT_DROPOFF_LAT)
            .dropoffLng(DEFAULT_DROPOFF_LNG)
            .dropoffUntilDate(DEFAULT_DROPOFF_UNTIL_DATE)
            .state(DEFAULT_STATE)
            .shipperId(DEFAULT_SHIPPER_ID)
            .carrierId(DEFAULT_CARRIER_ID)
            .totalWeight(DEFAULT_TOTAL_WEIGHT)
            .pickupFromDate(DEFAULT_PICKUP_FROM_DATE)
            .biddingFromDate(DEFAULT_BIDDING_FROM_DATE)
            .biddingUntilDate(DEFAULT_BIDDING_UNTIL_DATE);
        // Add required entity
        ContainerType containerType;
        containerType = ContainerTypeResourceIT.createEntity();
        containerType.setId("fixed-id-for-tests");
        container.setType(containerType);
        // Add required entity
        ContainerStatus containerStatus;
        containerStatus = ContainerStatusResourceIT.createEntity();
        containerStatus.setId("fixed-id-for-tests");
        container.setStatus(containerStatus);
        return container;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Container createUpdatedEntity() {
        Container container = new Container()
            .contNo(UPDATED_CONT_NO)
            .estimatedPrice(UPDATED_ESTIMATED_PRICE)
            .distance(UPDATED_DISTANCE)
            .desiredPrice(UPDATED_DESIRED_PRICE)
            .additionalRequirements(UPDATED_ADDITIONAL_REQUIREMENTS)
            .dropoffContact(UPDATED_DROPOFF_CONTACT)
            .dropoffContactPhone(UPDATED_DROPOFF_CONTACT_PHONE)
            .dropoffAddress(UPDATED_DROPOFF_ADDRESS)
            .dropoffLat(UPDATED_DROPOFF_LAT)
            .dropoffLng(UPDATED_DROPOFF_LNG)
            .dropoffUntilDate(UPDATED_DROPOFF_UNTIL_DATE)
            .state(UPDATED_STATE)
            .shipperId(UPDATED_SHIPPER_ID)
            .carrierId(UPDATED_CARRIER_ID)
            .totalWeight(UPDATED_TOTAL_WEIGHT)
            .pickupFromDate(UPDATED_PICKUP_FROM_DATE)
            .biddingFromDate(UPDATED_BIDDING_FROM_DATE)
            .biddingUntilDate(UPDATED_BIDDING_UNTIL_DATE);
        // Add required entity
        ContainerType containerType;
        containerType = ContainerTypeResourceIT.createUpdatedEntity();
        containerType.setId("fixed-id-for-tests");
        container.setType(containerType);
        // Add required entity
        ContainerStatus containerStatus;
        containerStatus = ContainerStatusResourceIT.createUpdatedEntity();
        containerStatus.setId("fixed-id-for-tests");
        container.setStatus(containerStatus);
        return container;
    }

    @BeforeEach
    public void initTest() {
        containerRepository.deleteAll();
        container = createEntity();
    }

    @Test
    void createContainer() throws Exception {
        int databaseSizeBeforeCreate = containerRepository.findAll().size();
        // Create the Container
        ContainerDTO containerDTO = containerMapper.toDto(container);
        restContainerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(containerDTO)))
            .andExpect(status().isCreated());

        // Validate the Container in the database
        List<Container> containerList = containerRepository.findAll();
        assertThat(containerList).hasSize(databaseSizeBeforeCreate + 1);
        Container testContainer = containerList.get(containerList.size() - 1);
        assertThat(testContainer.getContNo()).isEqualTo(DEFAULT_CONT_NO);
        assertThat(testContainer.getEstimatedPrice()).isEqualTo(DEFAULT_ESTIMATED_PRICE);
        assertThat(testContainer.getDistance()).isEqualTo(DEFAULT_DISTANCE);
        assertThat(testContainer.getDesiredPrice()).isEqualTo(DEFAULT_DESIRED_PRICE);
        assertThat(testContainer.getAdditionalRequirements()).isEqualTo(DEFAULT_ADDITIONAL_REQUIREMENTS);
        assertThat(testContainer.getDropoffContact()).isEqualTo(DEFAULT_DROPOFF_CONTACT);
        assertThat(testContainer.getDropoffContactPhone()).isEqualTo(DEFAULT_DROPOFF_CONTACT_PHONE);
        assertThat(testContainer.getDropoffAddress()).isEqualTo(DEFAULT_DROPOFF_ADDRESS);
        assertThat(testContainer.getDropoffLat()).isEqualTo(DEFAULT_DROPOFF_LAT);
        assertThat(testContainer.getDropoffLng()).isEqualTo(DEFAULT_DROPOFF_LNG);
        assertThat(testContainer.getDropoffUntilDate()).isEqualTo(DEFAULT_DROPOFF_UNTIL_DATE);
        assertThat(testContainer.getState()).isEqualTo(DEFAULT_STATE);
        assertThat(testContainer.getShipperId()).isEqualTo(DEFAULT_SHIPPER_ID);
        assertThat(testContainer.getCarrierId()).isEqualTo(DEFAULT_CARRIER_ID);
        assertThat(testContainer.getTotalWeight()).isEqualTo(DEFAULT_TOTAL_WEIGHT);
        assertThat(testContainer.getPickupFromDate()).isEqualTo(DEFAULT_PICKUP_FROM_DATE);
        assertThat(testContainer.getBiddingFromDate()).isEqualTo(DEFAULT_BIDDING_FROM_DATE);
        assertThat(testContainer.getBiddingUntilDate()).isEqualTo(DEFAULT_BIDDING_UNTIL_DATE);
    }

    @Test
    void createContainerWithExistingId() throws Exception {
        // Create the Container with an existing ID
        container.setId(1L);
        ContainerDTO containerDTO = containerMapper.toDto(container);

        int databaseSizeBeforeCreate = containerRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restContainerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(containerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Container in the database
        List<Container> containerList = containerRepository.findAll();
        assertThat(containerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void checkContNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = containerRepository.findAll().size();
        // set the field null
        container.setContNo(null);

        // Create the Container, which fails.
        ContainerDTO containerDTO = containerMapper.toDto(container);

        restContainerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(containerDTO)))
            .andExpect(status().isBadRequest());

        List<Container> containerList = containerRepository.findAll();
        assertThat(containerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkEstimatedPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = containerRepository.findAll().size();
        // set the field null
        container.setEstimatedPrice(null);

        // Create the Container, which fails.
        ContainerDTO containerDTO = containerMapper.toDto(container);

        restContainerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(containerDTO)))
            .andExpect(status().isBadRequest());

        List<Container> containerList = containerRepository.findAll();
        assertThat(containerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkDistanceIsRequired() throws Exception {
        int databaseSizeBeforeTest = containerRepository.findAll().size();
        // set the field null
        container.setDistance(null);

        // Create the Container, which fails.
        ContainerDTO containerDTO = containerMapper.toDto(container);

        restContainerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(containerDTO)))
            .andExpect(status().isBadRequest());

        List<Container> containerList = containerRepository.findAll();
        assertThat(containerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkDesiredPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = containerRepository.findAll().size();
        // set the field null
        container.setDesiredPrice(null);

        // Create the Container, which fails.
        ContainerDTO containerDTO = containerMapper.toDto(container);

        restContainerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(containerDTO)))
            .andExpect(status().isBadRequest());

        List<Container> containerList = containerRepository.findAll();
        assertThat(containerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkStateIsRequired() throws Exception {
        int databaseSizeBeforeTest = containerRepository.findAll().size();
        // set the field null
        container.setState(null);

        // Create the Container, which fails.
        ContainerDTO containerDTO = containerMapper.toDto(container);

        restContainerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(containerDTO)))
            .andExpect(status().isBadRequest());

        List<Container> containerList = containerRepository.findAll();
        assertThat(containerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkShipperIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = containerRepository.findAll().size();
        // set the field null
        container.setShipperId(null);

        // Create the Container, which fails.
        ContainerDTO containerDTO = containerMapper.toDto(container);

        restContainerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(containerDTO)))
            .andExpect(status().isBadRequest());

        List<Container> containerList = containerRepository.findAll();
        assertThat(containerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkTotalWeightIsRequired() throws Exception {
        int databaseSizeBeforeTest = containerRepository.findAll().size();
        // set the field null
        container.setTotalWeight(null);

        // Create the Container, which fails.
        ContainerDTO containerDTO = containerMapper.toDto(container);

        restContainerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(containerDTO)))
            .andExpect(status().isBadRequest());

        List<Container> containerList = containerRepository.findAll();
        assertThat(containerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void getAllContainers() throws Exception {
        // Initialize the database
        containerRepository.save(container);

        // Get all the containerList
        restContainerMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(container.getId().intValue())))
            .andExpect(jsonPath("$.[*].contNo").value(hasItem(DEFAULT_CONT_NO)))
            .andExpect(jsonPath("$.[*].estimatedPrice").value(hasItem(DEFAULT_ESTIMATED_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].distance").value(hasItem(DEFAULT_DISTANCE.doubleValue())))
            .andExpect(jsonPath("$.[*].desiredPrice").value(hasItem(DEFAULT_DESIRED_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].additionalRequirements").value(hasItem(DEFAULT_ADDITIONAL_REQUIREMENTS)))
            .andExpect(jsonPath("$.[*].dropoffContact").value(hasItem(DEFAULT_DROPOFF_CONTACT)))
            .andExpect(jsonPath("$.[*].dropoffContactPhone").value(hasItem(DEFAULT_DROPOFF_CONTACT_PHONE)))
            .andExpect(jsonPath("$.[*].dropoffAddress").value(hasItem(DEFAULT_DROPOFF_ADDRESS)))
            .andExpect(jsonPath("$.[*].dropoffLat").value(hasItem(DEFAULT_DROPOFF_LAT.doubleValue())))
            .andExpect(jsonPath("$.[*].dropoffLng").value(hasItem(DEFAULT_DROPOFF_LNG.doubleValue())))
            .andExpect(jsonPath("$.[*].dropoffUntilDate").value(hasItem(DEFAULT_DROPOFF_UNTIL_DATE.toString())))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE.toString())))
            .andExpect(jsonPath("$.[*].shipperId").value(hasItem(DEFAULT_SHIPPER_ID.intValue())))
            .andExpect(jsonPath("$.[*].carrierId").value(hasItem(DEFAULT_CARRIER_ID.intValue())))
            .andExpect(jsonPath("$.[*].totalWeight").value(hasItem(DEFAULT_TOTAL_WEIGHT.doubleValue())))
            .andExpect(jsonPath("$.[*].pickupFromDate").value(hasItem(DEFAULT_PICKUP_FROM_DATE.toString())))
            .andExpect(jsonPath("$.[*].biddingFromDate").value(hasItem(DEFAULT_BIDDING_FROM_DATE.toString())))
            .andExpect(jsonPath("$.[*].biddingUntilDate").value(hasItem(DEFAULT_BIDDING_UNTIL_DATE.toString())));
    }

    @Test
    void getContainer() throws Exception {
        // Initialize the database
        containerRepository.save(container);

        // Get the container
        restContainerMockMvc
            .perform(get(ENTITY_API_URL_ID, container.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(container.getId().intValue()))
            .andExpect(jsonPath("$.contNo").value(DEFAULT_CONT_NO))
            .andExpect(jsonPath("$.estimatedPrice").value(DEFAULT_ESTIMATED_PRICE.doubleValue()))
            .andExpect(jsonPath("$.distance").value(DEFAULT_DISTANCE.doubleValue()))
            .andExpect(jsonPath("$.desiredPrice").value(DEFAULT_DESIRED_PRICE.doubleValue()))
            .andExpect(jsonPath("$.additionalRequirements").value(DEFAULT_ADDITIONAL_REQUIREMENTS))
            .andExpect(jsonPath("$.dropoffContact").value(DEFAULT_DROPOFF_CONTACT))
            .andExpect(jsonPath("$.dropoffContactPhone").value(DEFAULT_DROPOFF_CONTACT_PHONE))
            .andExpect(jsonPath("$.dropoffAddress").value(DEFAULT_DROPOFF_ADDRESS))
            .andExpect(jsonPath("$.dropoffLat").value(DEFAULT_DROPOFF_LAT.doubleValue()))
            .andExpect(jsonPath("$.dropoffLng").value(DEFAULT_DROPOFF_LNG.doubleValue()))
            .andExpect(jsonPath("$.dropoffUntilDate").value(DEFAULT_DROPOFF_UNTIL_DATE.toString()))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE.toString()))
            .andExpect(jsonPath("$.shipperId").value(DEFAULT_SHIPPER_ID.intValue()))
            .andExpect(jsonPath("$.carrierId").value(DEFAULT_CARRIER_ID.intValue()))
            .andExpect(jsonPath("$.totalWeight").value(DEFAULT_TOTAL_WEIGHT.doubleValue()))
            .andExpect(jsonPath("$.pickupFromDate").value(DEFAULT_PICKUP_FROM_DATE.toString()))
            .andExpect(jsonPath("$.biddingFromDate").value(DEFAULT_BIDDING_FROM_DATE.toString()))
            .andExpect(jsonPath("$.biddingUntilDate").value(DEFAULT_BIDDING_UNTIL_DATE.toString()));
    }

    @Test
    void getNonExistingContainer() throws Exception {
        // Get the container
        restContainerMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingContainer() throws Exception {
        // Initialize the database
        containerRepository.save(container);

        int databaseSizeBeforeUpdate = containerRepository.findAll().size();

        // Update the container
        Container updatedContainer = containerRepository.findById(container.getId()).orElseThrow();
        updatedContainer
            .contNo(UPDATED_CONT_NO)
            .estimatedPrice(UPDATED_ESTIMATED_PRICE)
            .distance(UPDATED_DISTANCE)
            .desiredPrice(UPDATED_DESIRED_PRICE)
            .additionalRequirements(UPDATED_ADDITIONAL_REQUIREMENTS)
            .dropoffContact(UPDATED_DROPOFF_CONTACT)
            .dropoffContactPhone(UPDATED_DROPOFF_CONTACT_PHONE)
            .dropoffAddress(UPDATED_DROPOFF_ADDRESS)
            .dropoffLat(UPDATED_DROPOFF_LAT)
            .dropoffLng(UPDATED_DROPOFF_LNG)
            .dropoffUntilDate(UPDATED_DROPOFF_UNTIL_DATE)
            .state(UPDATED_STATE)
            .shipperId(UPDATED_SHIPPER_ID)
            .carrierId(UPDATED_CARRIER_ID)
            .totalWeight(UPDATED_TOTAL_WEIGHT)
            .pickupFromDate(UPDATED_PICKUP_FROM_DATE)
            .biddingFromDate(UPDATED_BIDDING_FROM_DATE)
            .biddingUntilDate(UPDATED_BIDDING_UNTIL_DATE);
        ContainerDTO containerDTO = containerMapper.toDto(updatedContainer);

        restContainerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, containerDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(containerDTO))
            )
            .andExpect(status().isOk());

        // Validate the Container in the database
        List<Container> containerList = containerRepository.findAll();
        assertThat(containerList).hasSize(databaseSizeBeforeUpdate);
        Container testContainer = containerList.get(containerList.size() - 1);
        assertThat(testContainer.getContNo()).isEqualTo(UPDATED_CONT_NO);
        assertThat(testContainer.getEstimatedPrice()).isEqualTo(UPDATED_ESTIMATED_PRICE);
        assertThat(testContainer.getDistance()).isEqualTo(UPDATED_DISTANCE);
        assertThat(testContainer.getDesiredPrice()).isEqualTo(UPDATED_DESIRED_PRICE);
        assertThat(testContainer.getAdditionalRequirements()).isEqualTo(UPDATED_ADDITIONAL_REQUIREMENTS);
        assertThat(testContainer.getDropoffContact()).isEqualTo(UPDATED_DROPOFF_CONTACT);
        assertThat(testContainer.getDropoffContactPhone()).isEqualTo(UPDATED_DROPOFF_CONTACT_PHONE);
        assertThat(testContainer.getDropoffAddress()).isEqualTo(UPDATED_DROPOFF_ADDRESS);
        assertThat(testContainer.getDropoffLat()).isEqualTo(UPDATED_DROPOFF_LAT);
        assertThat(testContainer.getDropoffLng()).isEqualTo(UPDATED_DROPOFF_LNG);
        assertThat(testContainer.getDropoffUntilDate()).isEqualTo(UPDATED_DROPOFF_UNTIL_DATE);
        assertThat(testContainer.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testContainer.getShipperId()).isEqualTo(UPDATED_SHIPPER_ID);
        assertThat(testContainer.getCarrierId()).isEqualTo(UPDATED_CARRIER_ID);
        assertThat(testContainer.getTotalWeight()).isEqualTo(UPDATED_TOTAL_WEIGHT);
        assertThat(testContainer.getPickupFromDate()).isEqualTo(UPDATED_PICKUP_FROM_DATE);
        assertThat(testContainer.getBiddingFromDate()).isEqualTo(UPDATED_BIDDING_FROM_DATE);
        assertThat(testContainer.getBiddingUntilDate()).isEqualTo(UPDATED_BIDDING_UNTIL_DATE);
    }

    @Test
    void putNonExistingContainer() throws Exception {
        int databaseSizeBeforeUpdate = containerRepository.findAll().size();
        container.setId(longCount.incrementAndGet());

        // Create the Container
        ContainerDTO containerDTO = containerMapper.toDto(container);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContainerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, containerDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(containerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Container in the database
        List<Container> containerList = containerRepository.findAll();
        assertThat(containerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchContainer() throws Exception {
        int databaseSizeBeforeUpdate = containerRepository.findAll().size();
        container.setId(longCount.incrementAndGet());

        // Create the Container
        ContainerDTO containerDTO = containerMapper.toDto(container);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContainerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(containerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Container in the database
        List<Container> containerList = containerRepository.findAll();
        assertThat(containerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamContainer() throws Exception {
        int databaseSizeBeforeUpdate = containerRepository.findAll().size();
        container.setId(longCount.incrementAndGet());

        // Create the Container
        ContainerDTO containerDTO = containerMapper.toDto(container);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContainerMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(containerDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Container in the database
        List<Container> containerList = containerRepository.findAll();
        assertThat(containerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateContainerWithPatch() throws Exception {
        // Initialize the database
        containerRepository.save(container);

        int databaseSizeBeforeUpdate = containerRepository.findAll().size();

        // Update the container using partial update
        Container partialUpdatedContainer = new Container();
        partialUpdatedContainer.setId(container.getId());

        partialUpdatedContainer
            .distance(UPDATED_DISTANCE)
            .desiredPrice(UPDATED_DESIRED_PRICE)
            .dropoffContact(UPDATED_DROPOFF_CONTACT)
            .dropoffContactPhone(UPDATED_DROPOFF_CONTACT_PHONE)
            .dropoffAddress(UPDATED_DROPOFF_ADDRESS)
            .dropoffLng(UPDATED_DROPOFF_LNG)
            .state(UPDATED_STATE)
            .shipperId(UPDATED_SHIPPER_ID);

        restContainerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedContainer.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedContainer))
            )
            .andExpect(status().isOk());

        // Validate the Container in the database
        List<Container> containerList = containerRepository.findAll();
        assertThat(containerList).hasSize(databaseSizeBeforeUpdate);
        Container testContainer = containerList.get(containerList.size() - 1);
        assertThat(testContainer.getContNo()).isEqualTo(DEFAULT_CONT_NO);
        assertThat(testContainer.getEstimatedPrice()).isEqualTo(DEFAULT_ESTIMATED_PRICE);
        assertThat(testContainer.getDistance()).isEqualTo(UPDATED_DISTANCE);
        assertThat(testContainer.getDesiredPrice()).isEqualTo(UPDATED_DESIRED_PRICE);
        assertThat(testContainer.getAdditionalRequirements()).isEqualTo(DEFAULT_ADDITIONAL_REQUIREMENTS);
        assertThat(testContainer.getDropoffContact()).isEqualTo(UPDATED_DROPOFF_CONTACT);
        assertThat(testContainer.getDropoffContactPhone()).isEqualTo(UPDATED_DROPOFF_CONTACT_PHONE);
        assertThat(testContainer.getDropoffAddress()).isEqualTo(UPDATED_DROPOFF_ADDRESS);
        assertThat(testContainer.getDropoffLat()).isEqualTo(DEFAULT_DROPOFF_LAT);
        assertThat(testContainer.getDropoffLng()).isEqualTo(UPDATED_DROPOFF_LNG);
        assertThat(testContainer.getDropoffUntilDate()).isEqualTo(DEFAULT_DROPOFF_UNTIL_DATE);
        assertThat(testContainer.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testContainer.getShipperId()).isEqualTo(UPDATED_SHIPPER_ID);
        assertThat(testContainer.getCarrierId()).isEqualTo(DEFAULT_CARRIER_ID);
        assertThat(testContainer.getTotalWeight()).isEqualTo(DEFAULT_TOTAL_WEIGHT);
        assertThat(testContainer.getPickupFromDate()).isEqualTo(DEFAULT_PICKUP_FROM_DATE);
        assertThat(testContainer.getBiddingFromDate()).isEqualTo(DEFAULT_BIDDING_FROM_DATE);
        assertThat(testContainer.getBiddingUntilDate()).isEqualTo(DEFAULT_BIDDING_UNTIL_DATE);
    }

    @Test
    void fullUpdateContainerWithPatch() throws Exception {
        // Initialize the database
        containerRepository.save(container);

        int databaseSizeBeforeUpdate = containerRepository.findAll().size();

        // Update the container using partial update
        Container partialUpdatedContainer = new Container();
        partialUpdatedContainer.setId(container.getId());

        partialUpdatedContainer
            .contNo(UPDATED_CONT_NO)
            .estimatedPrice(UPDATED_ESTIMATED_PRICE)
            .distance(UPDATED_DISTANCE)
            .desiredPrice(UPDATED_DESIRED_PRICE)
            .additionalRequirements(UPDATED_ADDITIONAL_REQUIREMENTS)
            .dropoffContact(UPDATED_DROPOFF_CONTACT)
            .dropoffContactPhone(UPDATED_DROPOFF_CONTACT_PHONE)
            .dropoffAddress(UPDATED_DROPOFF_ADDRESS)
            .dropoffLat(UPDATED_DROPOFF_LAT)
            .dropoffLng(UPDATED_DROPOFF_LNG)
            .dropoffUntilDate(UPDATED_DROPOFF_UNTIL_DATE)
            .state(UPDATED_STATE)
            .shipperId(UPDATED_SHIPPER_ID)
            .carrierId(UPDATED_CARRIER_ID)
            .totalWeight(UPDATED_TOTAL_WEIGHT)
            .pickupFromDate(UPDATED_PICKUP_FROM_DATE)
            .biddingFromDate(UPDATED_BIDDING_FROM_DATE)
            .biddingUntilDate(UPDATED_BIDDING_UNTIL_DATE);

        restContainerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedContainer.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedContainer))
            )
            .andExpect(status().isOk());

        // Validate the Container in the database
        List<Container> containerList = containerRepository.findAll();
        assertThat(containerList).hasSize(databaseSizeBeforeUpdate);
        Container testContainer = containerList.get(containerList.size() - 1);
        assertThat(testContainer.getContNo()).isEqualTo(UPDATED_CONT_NO);
        assertThat(testContainer.getEstimatedPrice()).isEqualTo(UPDATED_ESTIMATED_PRICE);
        assertThat(testContainer.getDistance()).isEqualTo(UPDATED_DISTANCE);
        assertThat(testContainer.getDesiredPrice()).isEqualTo(UPDATED_DESIRED_PRICE);
        assertThat(testContainer.getAdditionalRequirements()).isEqualTo(UPDATED_ADDITIONAL_REQUIREMENTS);
        assertThat(testContainer.getDropoffContact()).isEqualTo(UPDATED_DROPOFF_CONTACT);
        assertThat(testContainer.getDropoffContactPhone()).isEqualTo(UPDATED_DROPOFF_CONTACT_PHONE);
        assertThat(testContainer.getDropoffAddress()).isEqualTo(UPDATED_DROPOFF_ADDRESS);
        assertThat(testContainer.getDropoffLat()).isEqualTo(UPDATED_DROPOFF_LAT);
        assertThat(testContainer.getDropoffLng()).isEqualTo(UPDATED_DROPOFF_LNG);
        assertThat(testContainer.getDropoffUntilDate()).isEqualTo(UPDATED_DROPOFF_UNTIL_DATE);
        assertThat(testContainer.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testContainer.getShipperId()).isEqualTo(UPDATED_SHIPPER_ID);
        assertThat(testContainer.getCarrierId()).isEqualTo(UPDATED_CARRIER_ID);
        assertThat(testContainer.getTotalWeight()).isEqualTo(UPDATED_TOTAL_WEIGHT);
        assertThat(testContainer.getPickupFromDate()).isEqualTo(UPDATED_PICKUP_FROM_DATE);
        assertThat(testContainer.getBiddingFromDate()).isEqualTo(UPDATED_BIDDING_FROM_DATE);
        assertThat(testContainer.getBiddingUntilDate()).isEqualTo(UPDATED_BIDDING_UNTIL_DATE);
    }

    @Test
    void patchNonExistingContainer() throws Exception {
        int databaseSizeBeforeUpdate = containerRepository.findAll().size();
        container.setId(longCount.incrementAndGet());

        // Create the Container
        ContainerDTO containerDTO = containerMapper.toDto(container);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContainerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, containerDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(containerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Container in the database
        List<Container> containerList = containerRepository.findAll();
        assertThat(containerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchContainer() throws Exception {
        int databaseSizeBeforeUpdate = containerRepository.findAll().size();
        container.setId(longCount.incrementAndGet());

        // Create the Container
        ContainerDTO containerDTO = containerMapper.toDto(container);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContainerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(containerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Container in the database
        List<Container> containerList = containerRepository.findAll();
        assertThat(containerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamContainer() throws Exception {
        int databaseSizeBeforeUpdate = containerRepository.findAll().size();
        container.setId(longCount.incrementAndGet());

        // Create the Container
        ContainerDTO containerDTO = containerMapper.toDto(container);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContainerMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(containerDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Container in the database
        List<Container> containerList = containerRepository.findAll();
        assertThat(containerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteContainer() throws Exception {
        // Initialize the database
        containerRepository.save(container);

        int databaseSizeBeforeDelete = containerRepository.findAll().size();

        // Delete the container
        restContainerMockMvc
            .perform(delete(ENTITY_API_URL_ID, container.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Container> containerList = containerRepository.findAll();
        assertThat(containerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

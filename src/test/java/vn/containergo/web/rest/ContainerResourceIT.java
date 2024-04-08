package vn.containergo.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static vn.containergo.domain.ContainerAsserts.*;
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
import vn.containergo.domain.ContainerStatus;
import vn.containergo.domain.ContainerType;
import vn.containergo.domain.District;
import vn.containergo.domain.Provice;
import vn.containergo.domain.Ward;
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

    private static final String DEFAULT_PICKUP_CONTACT = "AAAAAAAAAA";
    private static final String UPDATED_PICKUP_CONTACT = "BBBBBBBBBB";

    private static final String DEFAULT_PICKUP_CONTACT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PICKUP_CONTACT_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_PICKUP_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_PICKUP_ADDRESS = "BBBBBBBBBB";

    private static final Double DEFAULT_PICKUP_LAT = 1D;
    private static final Double UPDATED_PICKUP_LAT = 2D;

    private static final Double DEFAULT_PICKUP_LNG = 1D;
    private static final Double UPDATED_PICKUP_LNG = 2D;

    private static final Instant DEFAULT_PICKUP_FROM_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_PICKUP_FROM_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

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

    private static final String DEFAULT_POINTS = "AAAAAAAAAA";
    private static final String UPDATED_POINTS = "BBBBBBBBBB";

    private static final Instant DEFAULT_DROPOFF_UNTIL_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DROPOFF_UNTIL_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final ContainerState DEFAULT_STATE = ContainerState.NEW;
    private static final ContainerState UPDATED_STATE = ContainerState.BIDDING;

    private static final UUID DEFAULT_SHIPPER_ID = UUID.randomUUID();
    private static final UUID UPDATED_SHIPPER_ID = UUID.randomUUID();

    private static final UUID DEFAULT_CARRIER_ID = UUID.randomUUID();
    private static final UUID UPDATED_CARRIER_ID = UUID.randomUUID();

    private static final Double DEFAULT_TOTAL_WEIGHT = 1D;
    private static final Double UPDATED_TOTAL_WEIGHT = 2D;

    private static final Instant DEFAULT_BIDDING_FROM_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_BIDDING_FROM_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_BIDDING_UNTIL_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_BIDDING_UNTIL_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/containers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

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
            .pickupContact(DEFAULT_PICKUP_CONTACT)
            .pickupContactPhone(DEFAULT_PICKUP_CONTACT_PHONE)
            .pickupAddress(DEFAULT_PICKUP_ADDRESS)
            .pickupLat(DEFAULT_PICKUP_LAT)
            .pickupLng(DEFAULT_PICKUP_LNG)
            .pickupFromDate(DEFAULT_PICKUP_FROM_DATE)
            .dropoffContact(DEFAULT_DROPOFF_CONTACT)
            .dropoffContactPhone(DEFAULT_DROPOFF_CONTACT_PHONE)
            .dropoffAddress(DEFAULT_DROPOFF_ADDRESS)
            .dropoffLat(DEFAULT_DROPOFF_LAT)
            .dropoffLng(DEFAULT_DROPOFF_LNG)
            .points(DEFAULT_POINTS)
            .dropoffUntilDate(DEFAULT_DROPOFF_UNTIL_DATE)
            .state(DEFAULT_STATE)
            .shipperId(DEFAULT_SHIPPER_ID)
            .carrierId(DEFAULT_CARRIER_ID)
            .totalWeight(DEFAULT_TOTAL_WEIGHT)
            .biddingFromDate(DEFAULT_BIDDING_FROM_DATE)
            .biddingUntilDate(DEFAULT_BIDDING_UNTIL_DATE);
        // Add required entity
        Provice provice;
        provice = ProviceResourceIT.createEntity();
        provice.setId("fixed-id-for-tests");
        container.setPickupProvice(provice);
        // Add required entity
        District district;
        district = DistrictResourceIT.createEntity();
        district.setId("fixed-id-for-tests");
        container.setPickupDistrict(district);
        // Add required entity
        Ward ward;
        ward = WardResourceIT.createEntity();
        ward.setId("fixed-id-for-tests");
        container.setPickupWard(ward);
        // Add required entity
        container.setDropoffProvice(provice);
        // Add required entity
        container.setDropoffDistrict(district);
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
            .pickupContact(UPDATED_PICKUP_CONTACT)
            .pickupContactPhone(UPDATED_PICKUP_CONTACT_PHONE)
            .pickupAddress(UPDATED_PICKUP_ADDRESS)
            .pickupLat(UPDATED_PICKUP_LAT)
            .pickupLng(UPDATED_PICKUP_LNG)
            .pickupFromDate(UPDATED_PICKUP_FROM_DATE)
            .dropoffContact(UPDATED_DROPOFF_CONTACT)
            .dropoffContactPhone(UPDATED_DROPOFF_CONTACT_PHONE)
            .dropoffAddress(UPDATED_DROPOFF_ADDRESS)
            .dropoffLat(UPDATED_DROPOFF_LAT)
            .dropoffLng(UPDATED_DROPOFF_LNG)
            .points(UPDATED_POINTS)
            .dropoffUntilDate(UPDATED_DROPOFF_UNTIL_DATE)
            .state(UPDATED_STATE)
            .shipperId(UPDATED_SHIPPER_ID)
            .carrierId(UPDATED_CARRIER_ID)
            .totalWeight(UPDATED_TOTAL_WEIGHT)
            .biddingFromDate(UPDATED_BIDDING_FROM_DATE)
            .biddingUntilDate(UPDATED_BIDDING_UNTIL_DATE);
        // Add required entity
        Provice provice;
        provice = ProviceResourceIT.createUpdatedEntity();
        provice.setId("fixed-id-for-tests");
        container.setPickupProvice(provice);
        // Add required entity
        District district;
        district = DistrictResourceIT.createUpdatedEntity();
        district.setId("fixed-id-for-tests");
        container.setPickupDistrict(district);
        // Add required entity
        Ward ward;
        ward = WardResourceIT.createUpdatedEntity();
        ward.setId("fixed-id-for-tests");
        container.setPickupWard(ward);
        // Add required entity
        container.setDropoffProvice(provice);
        // Add required entity
        container.setDropoffDistrict(district);
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
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Container
        ContainerDTO containerDTO = containerMapper.toDto(container);
        var returnedContainerDTO = om.readValue(
            restContainerMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(containerDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ContainerDTO.class
        );

        // Validate the Container in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedContainer = containerMapper.toEntity(returnedContainerDTO);
        assertContainerUpdatableFieldsEquals(returnedContainer, getPersistedContainer(returnedContainer));
    }

    @Test
    void createContainerWithExistingId() throws Exception {
        // Create the Container with an existing ID
        container.setId(UUID.randomUUID());
        ContainerDTO containerDTO = containerMapper.toDto(container);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restContainerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(containerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Container in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    void checkContNoIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        container.setContNo(null);

        // Create the Container, which fails.
        ContainerDTO containerDTO = containerMapper.toDto(container);

        restContainerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(containerDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkEstimatedPriceIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        container.setEstimatedPrice(null);

        // Create the Container, which fails.
        ContainerDTO containerDTO = containerMapper.toDto(container);

        restContainerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(containerDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkDistanceIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        container.setDistance(null);

        // Create the Container, which fails.
        ContainerDTO containerDTO = containerMapper.toDto(container);

        restContainerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(containerDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkDesiredPriceIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        container.setDesiredPrice(null);

        // Create the Container, which fails.
        ContainerDTO containerDTO = containerMapper.toDto(container);

        restContainerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(containerDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkPickupContactIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        container.setPickupContact(null);

        // Create the Container, which fails.
        ContainerDTO containerDTO = containerMapper.toDto(container);

        restContainerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(containerDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkPickupContactPhoneIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        container.setPickupContactPhone(null);

        // Create the Container, which fails.
        ContainerDTO containerDTO = containerMapper.toDto(container);

        restContainerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(containerDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkPickupAddressIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        container.setPickupAddress(null);

        // Create the Container, which fails.
        ContainerDTO containerDTO = containerMapper.toDto(container);

        restContainerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(containerDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkPickupLatIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        container.setPickupLat(null);

        // Create the Container, which fails.
        ContainerDTO containerDTO = containerMapper.toDto(container);

        restContainerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(containerDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkPickupLngIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        container.setPickupLng(null);

        // Create the Container, which fails.
        ContainerDTO containerDTO = containerMapper.toDto(container);

        restContainerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(containerDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkPickupFromDateIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        container.setPickupFromDate(null);

        // Create the Container, which fails.
        ContainerDTO containerDTO = containerMapper.toDto(container);

        restContainerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(containerDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkDropoffAddressIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        container.setDropoffAddress(null);

        // Create the Container, which fails.
        ContainerDTO containerDTO = containerMapper.toDto(container);

        restContainerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(containerDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkStateIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        container.setState(null);

        // Create the Container, which fails.
        ContainerDTO containerDTO = containerMapper.toDto(container);

        restContainerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(containerDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkShipperIdIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        container.setShipperId(null);

        // Create the Container, which fails.
        ContainerDTO containerDTO = containerMapper.toDto(container);

        restContainerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(containerDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkTotalWeightIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        container.setTotalWeight(null);

        // Create the Container, which fails.
        ContainerDTO containerDTO = containerMapper.toDto(container);

        restContainerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(containerDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void getAllContainers() throws Exception {
        // Initialize the database
        container.setId(UUID.randomUUID());
        containerRepository.save(container);

        // Get all the containerList
        restContainerMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(container.getId().toString())))
            .andExpect(jsonPath("$.[*].contNo").value(hasItem(DEFAULT_CONT_NO)))
            .andExpect(jsonPath("$.[*].estimatedPrice").value(hasItem(DEFAULT_ESTIMATED_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].distance").value(hasItem(DEFAULT_DISTANCE.doubleValue())))
            .andExpect(jsonPath("$.[*].desiredPrice").value(hasItem(DEFAULT_DESIRED_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].additionalRequirements").value(hasItem(DEFAULT_ADDITIONAL_REQUIREMENTS)))
            .andExpect(jsonPath("$.[*].pickupContact").value(hasItem(DEFAULT_PICKUP_CONTACT)))
            .andExpect(jsonPath("$.[*].pickupContactPhone").value(hasItem(DEFAULT_PICKUP_CONTACT_PHONE)))
            .andExpect(jsonPath("$.[*].pickupAddress").value(hasItem(DEFAULT_PICKUP_ADDRESS)))
            .andExpect(jsonPath("$.[*].pickupLat").value(hasItem(DEFAULT_PICKUP_LAT.doubleValue())))
            .andExpect(jsonPath("$.[*].pickupLng").value(hasItem(DEFAULT_PICKUP_LNG.doubleValue())))
            .andExpect(jsonPath("$.[*].pickupFromDate").value(hasItem(DEFAULT_PICKUP_FROM_DATE.toString())))
            .andExpect(jsonPath("$.[*].dropoffContact").value(hasItem(DEFAULT_DROPOFF_CONTACT)))
            .andExpect(jsonPath("$.[*].dropoffContactPhone").value(hasItem(DEFAULT_DROPOFF_CONTACT_PHONE)))
            .andExpect(jsonPath("$.[*].dropoffAddress").value(hasItem(DEFAULT_DROPOFF_ADDRESS)))
            .andExpect(jsonPath("$.[*].dropoffLat").value(hasItem(DEFAULT_DROPOFF_LAT.doubleValue())))
            .andExpect(jsonPath("$.[*].dropoffLng").value(hasItem(DEFAULT_DROPOFF_LNG.doubleValue())))
            .andExpect(jsonPath("$.[*].points").value(hasItem(DEFAULT_POINTS)))
            .andExpect(jsonPath("$.[*].dropoffUntilDate").value(hasItem(DEFAULT_DROPOFF_UNTIL_DATE.toString())))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE.toString())))
            .andExpect(jsonPath("$.[*].shipperId").value(hasItem(DEFAULT_SHIPPER_ID.toString())))
            .andExpect(jsonPath("$.[*].carrierId").value(hasItem(DEFAULT_CARRIER_ID.toString())))
            .andExpect(jsonPath("$.[*].totalWeight").value(hasItem(DEFAULT_TOTAL_WEIGHT.doubleValue())))
            .andExpect(jsonPath("$.[*].biddingFromDate").value(hasItem(DEFAULT_BIDDING_FROM_DATE.toString())))
            .andExpect(jsonPath("$.[*].biddingUntilDate").value(hasItem(DEFAULT_BIDDING_UNTIL_DATE.toString())));
    }

    @Test
    void getContainer() throws Exception {
        // Initialize the database
        container.setId(UUID.randomUUID());
        containerRepository.save(container);

        // Get the container
        restContainerMockMvc
            .perform(get(ENTITY_API_URL_ID, container.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(container.getId().toString()))
            .andExpect(jsonPath("$.contNo").value(DEFAULT_CONT_NO))
            .andExpect(jsonPath("$.estimatedPrice").value(DEFAULT_ESTIMATED_PRICE.doubleValue()))
            .andExpect(jsonPath("$.distance").value(DEFAULT_DISTANCE.doubleValue()))
            .andExpect(jsonPath("$.desiredPrice").value(DEFAULT_DESIRED_PRICE.doubleValue()))
            .andExpect(jsonPath("$.additionalRequirements").value(DEFAULT_ADDITIONAL_REQUIREMENTS))
            .andExpect(jsonPath("$.pickupContact").value(DEFAULT_PICKUP_CONTACT))
            .andExpect(jsonPath("$.pickupContactPhone").value(DEFAULT_PICKUP_CONTACT_PHONE))
            .andExpect(jsonPath("$.pickupAddress").value(DEFAULT_PICKUP_ADDRESS))
            .andExpect(jsonPath("$.pickupLat").value(DEFAULT_PICKUP_LAT.doubleValue()))
            .andExpect(jsonPath("$.pickupLng").value(DEFAULT_PICKUP_LNG.doubleValue()))
            .andExpect(jsonPath("$.pickupFromDate").value(DEFAULT_PICKUP_FROM_DATE.toString()))
            .andExpect(jsonPath("$.dropoffContact").value(DEFAULT_DROPOFF_CONTACT))
            .andExpect(jsonPath("$.dropoffContactPhone").value(DEFAULT_DROPOFF_CONTACT_PHONE))
            .andExpect(jsonPath("$.dropoffAddress").value(DEFAULT_DROPOFF_ADDRESS))
            .andExpect(jsonPath("$.dropoffLat").value(DEFAULT_DROPOFF_LAT.doubleValue()))
            .andExpect(jsonPath("$.dropoffLng").value(DEFAULT_DROPOFF_LNG.doubleValue()))
            .andExpect(jsonPath("$.points").value(DEFAULT_POINTS))
            .andExpect(jsonPath("$.dropoffUntilDate").value(DEFAULT_DROPOFF_UNTIL_DATE.toString()))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE.toString()))
            .andExpect(jsonPath("$.shipperId").value(DEFAULT_SHIPPER_ID.toString()))
            .andExpect(jsonPath("$.carrierId").value(DEFAULT_CARRIER_ID.toString()))
            .andExpect(jsonPath("$.totalWeight").value(DEFAULT_TOTAL_WEIGHT.doubleValue()))
            .andExpect(jsonPath("$.biddingFromDate").value(DEFAULT_BIDDING_FROM_DATE.toString()))
            .andExpect(jsonPath("$.biddingUntilDate").value(DEFAULT_BIDDING_UNTIL_DATE.toString()));
    }

    @Test
    void getNonExistingContainer() throws Exception {
        // Get the container
        restContainerMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    void putExistingContainer() throws Exception {
        // Initialize the database
        container.setId(UUID.randomUUID());
        containerRepository.save(container);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the container
        Container updatedContainer = containerRepository.findById(container.getId()).orElseThrow();
        updatedContainer
            .contNo(UPDATED_CONT_NO)
            .estimatedPrice(UPDATED_ESTIMATED_PRICE)
            .distance(UPDATED_DISTANCE)
            .desiredPrice(UPDATED_DESIRED_PRICE)
            .additionalRequirements(UPDATED_ADDITIONAL_REQUIREMENTS)
            .pickupContact(UPDATED_PICKUP_CONTACT)
            .pickupContactPhone(UPDATED_PICKUP_CONTACT_PHONE)
            .pickupAddress(UPDATED_PICKUP_ADDRESS)
            .pickupLat(UPDATED_PICKUP_LAT)
            .pickupLng(UPDATED_PICKUP_LNG)
            .pickupFromDate(UPDATED_PICKUP_FROM_DATE)
            .dropoffContact(UPDATED_DROPOFF_CONTACT)
            .dropoffContactPhone(UPDATED_DROPOFF_CONTACT_PHONE)
            .dropoffAddress(UPDATED_DROPOFF_ADDRESS)
            .dropoffLat(UPDATED_DROPOFF_LAT)
            .dropoffLng(UPDATED_DROPOFF_LNG)
            .points(UPDATED_POINTS)
            .dropoffUntilDate(UPDATED_DROPOFF_UNTIL_DATE)
            .state(UPDATED_STATE)
            .shipperId(UPDATED_SHIPPER_ID)
            .carrierId(UPDATED_CARRIER_ID)
            .totalWeight(UPDATED_TOTAL_WEIGHT)
            .biddingFromDate(UPDATED_BIDDING_FROM_DATE)
            .biddingUntilDate(UPDATED_BIDDING_UNTIL_DATE);
        ContainerDTO containerDTO = containerMapper.toDto(updatedContainer);

        restContainerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, containerDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(containerDTO))
            )
            .andExpect(status().isOk());

        // Validate the Container in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedContainerToMatchAllProperties(updatedContainer);
    }

    @Test
    void putNonExistingContainer() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        container.setId(UUID.randomUUID());

        // Create the Container
        ContainerDTO containerDTO = containerMapper.toDto(container);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContainerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, containerDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(containerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Container in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchContainer() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        container.setId(UUID.randomUUID());

        // Create the Container
        ContainerDTO containerDTO = containerMapper.toDto(container);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContainerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(containerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Container in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamContainer() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        container.setId(UUID.randomUUID());

        // Create the Container
        ContainerDTO containerDTO = containerMapper.toDto(container);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContainerMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(containerDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Container in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateContainerWithPatch() throws Exception {
        // Initialize the database
        container.setId(UUID.randomUUID());
        containerRepository.save(container);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the container using partial update
        Container partialUpdatedContainer = new Container();
        partialUpdatedContainer.setId(container.getId());

        partialUpdatedContainer
            .contNo(UPDATED_CONT_NO)
            .estimatedPrice(UPDATED_ESTIMATED_PRICE)
            .additionalRequirements(UPDATED_ADDITIONAL_REQUIREMENTS)
            .pickupContactPhone(UPDATED_PICKUP_CONTACT_PHONE)
            .pickupAddress(UPDATED_PICKUP_ADDRESS)
            .pickupLat(UPDATED_PICKUP_LAT)
            .pickupFromDate(UPDATED_PICKUP_FROM_DATE)
            .dropoffContactPhone(UPDATED_DROPOFF_CONTACT_PHONE)
            .dropoffLat(UPDATED_DROPOFF_LAT)
            .dropoffUntilDate(UPDATED_DROPOFF_UNTIL_DATE)
            .state(UPDATED_STATE)
            .carrierId(UPDATED_CARRIER_ID)
            .biddingFromDate(UPDATED_BIDDING_FROM_DATE)
            .biddingUntilDate(UPDATED_BIDDING_UNTIL_DATE);

        restContainerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedContainer.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedContainer))
            )
            .andExpect(status().isOk());

        // Validate the Container in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertContainerUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedContainer, container),
            getPersistedContainer(container)
        );
    }

    @Test
    void fullUpdateContainerWithPatch() throws Exception {
        // Initialize the database
        container.setId(UUID.randomUUID());
        containerRepository.save(container);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the container using partial update
        Container partialUpdatedContainer = new Container();
        partialUpdatedContainer.setId(container.getId());

        partialUpdatedContainer
            .contNo(UPDATED_CONT_NO)
            .estimatedPrice(UPDATED_ESTIMATED_PRICE)
            .distance(UPDATED_DISTANCE)
            .desiredPrice(UPDATED_DESIRED_PRICE)
            .additionalRequirements(UPDATED_ADDITIONAL_REQUIREMENTS)
            .pickupContact(UPDATED_PICKUP_CONTACT)
            .pickupContactPhone(UPDATED_PICKUP_CONTACT_PHONE)
            .pickupAddress(UPDATED_PICKUP_ADDRESS)
            .pickupLat(UPDATED_PICKUP_LAT)
            .pickupLng(UPDATED_PICKUP_LNG)
            .pickupFromDate(UPDATED_PICKUP_FROM_DATE)
            .dropoffContact(UPDATED_DROPOFF_CONTACT)
            .dropoffContactPhone(UPDATED_DROPOFF_CONTACT_PHONE)
            .dropoffAddress(UPDATED_DROPOFF_ADDRESS)
            .dropoffLat(UPDATED_DROPOFF_LAT)
            .dropoffLng(UPDATED_DROPOFF_LNG)
            .points(UPDATED_POINTS)
            .dropoffUntilDate(UPDATED_DROPOFF_UNTIL_DATE)
            .state(UPDATED_STATE)
            .shipperId(UPDATED_SHIPPER_ID)
            .carrierId(UPDATED_CARRIER_ID)
            .totalWeight(UPDATED_TOTAL_WEIGHT)
            .biddingFromDate(UPDATED_BIDDING_FROM_DATE)
            .biddingUntilDate(UPDATED_BIDDING_UNTIL_DATE);

        restContainerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedContainer.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedContainer))
            )
            .andExpect(status().isOk());

        // Validate the Container in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertContainerUpdatableFieldsEquals(partialUpdatedContainer, getPersistedContainer(partialUpdatedContainer));
    }

    @Test
    void patchNonExistingContainer() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        container.setId(UUID.randomUUID());

        // Create the Container
        ContainerDTO containerDTO = containerMapper.toDto(container);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContainerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, containerDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(containerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Container in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchContainer() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        container.setId(UUID.randomUUID());

        // Create the Container
        ContainerDTO containerDTO = containerMapper.toDto(container);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContainerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(containerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Container in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamContainer() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        container.setId(UUID.randomUUID());

        // Create the Container
        ContainerDTO containerDTO = containerMapper.toDto(container);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContainerMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(containerDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Container in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteContainer() throws Exception {
        // Initialize the database
        container.setId(UUID.randomUUID());
        containerRepository.save(container);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the container
        restContainerMockMvc
            .perform(delete(ENTITY_API_URL_ID, container.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return containerRepository.count();
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

    protected Container getPersistedContainer(Container container) {
        return containerRepository.findById(container.getId()).orElseThrow();
    }

    protected void assertPersistedContainerToMatchAllProperties(Container expectedContainer) {
        assertContainerAllPropertiesEquals(expectedContainer, getPersistedContainer(expectedContainer));
    }

    protected void assertPersistedContainerToMatchUpdatableProperties(Container expectedContainer) {
        assertContainerAllUpdatablePropertiesEquals(expectedContainer, getPersistedContainer(expectedContainer));
    }
}

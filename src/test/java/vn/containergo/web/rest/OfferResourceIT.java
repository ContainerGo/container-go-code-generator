package vn.containergo.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static vn.containergo.domain.OfferAsserts.*;
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
import vn.containergo.domain.Offer;
import vn.containergo.domain.enumeration.OfferState;
import vn.containergo.repository.OfferRepository;
import vn.containergo.service.dto.OfferDTO;
import vn.containergo.service.mapper.OfferMapper;

/**
 * Integration tests for the {@link OfferResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OfferResourceIT {

    private static final String DEFAULT_MESSAGE = "AAAAAAAAAA";
    private static final String UPDATED_MESSAGE = "BBBBBBBBBB";

    private static final Instant DEFAULT_ESTIMATED_PICKUP_FROM_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ESTIMATED_PICKUP_FROM_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_ESTIMATED_PICKUP_UNTIL_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ESTIMATED_PICKUP_UNTIL_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_ESTIMATED_DROPOFF_FROM_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ESTIMATED_DROPOFF_FROM_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_ESTIMATED_DROPOFF_UNTIL_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ESTIMATED_DROPOFF_UNTIL_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final OfferState DEFAULT_STATE = OfferState.PENDING;
    private static final OfferState UPDATED_STATE = OfferState.ACCEPTED;

    private static final Double DEFAULT_PRICE = 1D;
    private static final Double UPDATED_PRICE = 2D;

    private static final UUID DEFAULT_CARRIER_ID = UUID.randomUUID();
    private static final UUID UPDATED_CARRIER_ID = UUID.randomUUID();

    private static final UUID DEFAULT_TRUCK_ID = UUID.randomUUID();
    private static final UUID UPDATED_TRUCK_ID = UUID.randomUUID();

    private static final String ENTITY_API_URL = "/api/offers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private OfferRepository offerRepository;

    @Autowired
    private OfferMapper offerMapper;

    @Autowired
    private MockMvc restOfferMockMvc;

    private Offer offer;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Offer createEntity() {
        Offer offer = new Offer()
            .message(DEFAULT_MESSAGE)
            .estimatedPickupFromDate(DEFAULT_ESTIMATED_PICKUP_FROM_DATE)
            .estimatedPickupUntilDate(DEFAULT_ESTIMATED_PICKUP_UNTIL_DATE)
            .estimatedDropoffFromDate(DEFAULT_ESTIMATED_DROPOFF_FROM_DATE)
            .estimatedDropoffUntilDate(DEFAULT_ESTIMATED_DROPOFF_UNTIL_DATE)
            .state(DEFAULT_STATE)
            .price(DEFAULT_PRICE)
            .carrierId(DEFAULT_CARRIER_ID)
            .truckId(DEFAULT_TRUCK_ID);
        // Add required entity
        Container container;
        container = ContainerResourceIT.createEntity();
        container.setId("fixed-id-for-tests");
        offer.setContainer(container);
        return offer;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Offer createUpdatedEntity() {
        Offer offer = new Offer()
            .message(UPDATED_MESSAGE)
            .estimatedPickupFromDate(UPDATED_ESTIMATED_PICKUP_FROM_DATE)
            .estimatedPickupUntilDate(UPDATED_ESTIMATED_PICKUP_UNTIL_DATE)
            .estimatedDropoffFromDate(UPDATED_ESTIMATED_DROPOFF_FROM_DATE)
            .estimatedDropoffUntilDate(UPDATED_ESTIMATED_DROPOFF_UNTIL_DATE)
            .state(UPDATED_STATE)
            .price(UPDATED_PRICE)
            .carrierId(UPDATED_CARRIER_ID)
            .truckId(UPDATED_TRUCK_ID);
        // Add required entity
        Container container;
        container = ContainerResourceIT.createUpdatedEntity();
        container.setId("fixed-id-for-tests");
        offer.setContainer(container);
        return offer;
    }

    @BeforeEach
    public void initTest() {
        offerRepository.deleteAll();
        offer = createEntity();
    }

    @Test
    void createOffer() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Offer
        OfferDTO offerDTO = offerMapper.toDto(offer);
        var returnedOfferDTO = om.readValue(
            restOfferMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(offerDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            OfferDTO.class
        );

        // Validate the Offer in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedOffer = offerMapper.toEntity(returnedOfferDTO);
        assertOfferUpdatableFieldsEquals(returnedOffer, getPersistedOffer(returnedOffer));
    }

    @Test
    void createOfferWithExistingId() throws Exception {
        // Create the Offer with an existing ID
        offer.setId(UUID.randomUUID());
        OfferDTO offerDTO = offerMapper.toDto(offer);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOfferMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(offerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Offer in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    void checkEstimatedPickupFromDateIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        offer.setEstimatedPickupFromDate(null);

        // Create the Offer, which fails.
        OfferDTO offerDTO = offerMapper.toDto(offer);

        restOfferMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(offerDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkEstimatedPickupUntilDateIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        offer.setEstimatedPickupUntilDate(null);

        // Create the Offer, which fails.
        OfferDTO offerDTO = offerMapper.toDto(offer);

        restOfferMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(offerDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkEstimatedDropoffFromDateIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        offer.setEstimatedDropoffFromDate(null);

        // Create the Offer, which fails.
        OfferDTO offerDTO = offerMapper.toDto(offer);

        restOfferMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(offerDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkEstimatedDropoffUntilDateIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        offer.setEstimatedDropoffUntilDate(null);

        // Create the Offer, which fails.
        OfferDTO offerDTO = offerMapper.toDto(offer);

        restOfferMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(offerDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkStateIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        offer.setState(null);

        // Create the Offer, which fails.
        OfferDTO offerDTO = offerMapper.toDto(offer);

        restOfferMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(offerDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkPriceIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        offer.setPrice(null);

        // Create the Offer, which fails.
        OfferDTO offerDTO = offerMapper.toDto(offer);

        restOfferMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(offerDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkCarrierIdIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        offer.setCarrierId(null);

        // Create the Offer, which fails.
        OfferDTO offerDTO = offerMapper.toDto(offer);

        restOfferMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(offerDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void getAllOffers() throws Exception {
        // Initialize the database
        offer.setId(UUID.randomUUID());
        offerRepository.save(offer);

        // Get all the offerList
        restOfferMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(offer.getId().toString())))
            .andExpect(jsonPath("$.[*].message").value(hasItem(DEFAULT_MESSAGE)))
            .andExpect(jsonPath("$.[*].estimatedPickupFromDate").value(hasItem(DEFAULT_ESTIMATED_PICKUP_FROM_DATE.toString())))
            .andExpect(jsonPath("$.[*].estimatedPickupUntilDate").value(hasItem(DEFAULT_ESTIMATED_PICKUP_UNTIL_DATE.toString())))
            .andExpect(jsonPath("$.[*].estimatedDropoffFromDate").value(hasItem(DEFAULT_ESTIMATED_DROPOFF_FROM_DATE.toString())))
            .andExpect(jsonPath("$.[*].estimatedDropoffUntilDate").value(hasItem(DEFAULT_ESTIMATED_DROPOFF_UNTIL_DATE.toString())))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE.toString())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].carrierId").value(hasItem(DEFAULT_CARRIER_ID.toString())))
            .andExpect(jsonPath("$.[*].truckId").value(hasItem(DEFAULT_TRUCK_ID.toString())));
    }

    @Test
    void getOffer() throws Exception {
        // Initialize the database
        offer.setId(UUID.randomUUID());
        offerRepository.save(offer);

        // Get the offer
        restOfferMockMvc
            .perform(get(ENTITY_API_URL_ID, offer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(offer.getId().toString()))
            .andExpect(jsonPath("$.message").value(DEFAULT_MESSAGE))
            .andExpect(jsonPath("$.estimatedPickupFromDate").value(DEFAULT_ESTIMATED_PICKUP_FROM_DATE.toString()))
            .andExpect(jsonPath("$.estimatedPickupUntilDate").value(DEFAULT_ESTIMATED_PICKUP_UNTIL_DATE.toString()))
            .andExpect(jsonPath("$.estimatedDropoffFromDate").value(DEFAULT_ESTIMATED_DROPOFF_FROM_DATE.toString()))
            .andExpect(jsonPath("$.estimatedDropoffUntilDate").value(DEFAULT_ESTIMATED_DROPOFF_UNTIL_DATE.toString()))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE.toString()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.doubleValue()))
            .andExpect(jsonPath("$.carrierId").value(DEFAULT_CARRIER_ID.toString()))
            .andExpect(jsonPath("$.truckId").value(DEFAULT_TRUCK_ID.toString()));
    }

    @Test
    void getNonExistingOffer() throws Exception {
        // Get the offer
        restOfferMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    void putExistingOffer() throws Exception {
        // Initialize the database
        offer.setId(UUID.randomUUID());
        offerRepository.save(offer);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the offer
        Offer updatedOffer = offerRepository.findById(offer.getId()).orElseThrow();
        updatedOffer
            .message(UPDATED_MESSAGE)
            .estimatedPickupFromDate(UPDATED_ESTIMATED_PICKUP_FROM_DATE)
            .estimatedPickupUntilDate(UPDATED_ESTIMATED_PICKUP_UNTIL_DATE)
            .estimatedDropoffFromDate(UPDATED_ESTIMATED_DROPOFF_FROM_DATE)
            .estimatedDropoffUntilDate(UPDATED_ESTIMATED_DROPOFF_UNTIL_DATE)
            .state(UPDATED_STATE)
            .price(UPDATED_PRICE)
            .carrierId(UPDATED_CARRIER_ID)
            .truckId(UPDATED_TRUCK_ID);
        OfferDTO offerDTO = offerMapper.toDto(updatedOffer);

        restOfferMockMvc
            .perform(
                put(ENTITY_API_URL_ID, offerDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(offerDTO))
            )
            .andExpect(status().isOk());

        // Validate the Offer in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedOfferToMatchAllProperties(updatedOffer);
    }

    @Test
    void putNonExistingOffer() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        offer.setId(UUID.randomUUID());

        // Create the Offer
        OfferDTO offerDTO = offerMapper.toDto(offer);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOfferMockMvc
            .perform(
                put(ENTITY_API_URL_ID, offerDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(offerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Offer in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchOffer() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        offer.setId(UUID.randomUUID());

        // Create the Offer
        OfferDTO offerDTO = offerMapper.toDto(offer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOfferMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(offerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Offer in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamOffer() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        offer.setId(UUID.randomUUID());

        // Create the Offer
        OfferDTO offerDTO = offerMapper.toDto(offer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOfferMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(offerDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Offer in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateOfferWithPatch() throws Exception {
        // Initialize the database
        offer.setId(UUID.randomUUID());
        offerRepository.save(offer);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the offer using partial update
        Offer partialUpdatedOffer = new Offer();
        partialUpdatedOffer.setId(offer.getId());

        partialUpdatedOffer
            .message(UPDATED_MESSAGE)
            .estimatedDropoffFromDate(UPDATED_ESTIMATED_DROPOFF_FROM_DATE)
            .estimatedDropoffUntilDate(UPDATED_ESTIMATED_DROPOFF_UNTIL_DATE)
            .state(UPDATED_STATE)
            .carrierId(UPDATED_CARRIER_ID)
            .truckId(UPDATED_TRUCK_ID);

        restOfferMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOffer.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedOffer))
            )
            .andExpect(status().isOk());

        // Validate the Offer in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertOfferUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedOffer, offer), getPersistedOffer(offer));
    }

    @Test
    void fullUpdateOfferWithPatch() throws Exception {
        // Initialize the database
        offer.setId(UUID.randomUUID());
        offerRepository.save(offer);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the offer using partial update
        Offer partialUpdatedOffer = new Offer();
        partialUpdatedOffer.setId(offer.getId());

        partialUpdatedOffer
            .message(UPDATED_MESSAGE)
            .estimatedPickupFromDate(UPDATED_ESTIMATED_PICKUP_FROM_DATE)
            .estimatedPickupUntilDate(UPDATED_ESTIMATED_PICKUP_UNTIL_DATE)
            .estimatedDropoffFromDate(UPDATED_ESTIMATED_DROPOFF_FROM_DATE)
            .estimatedDropoffUntilDate(UPDATED_ESTIMATED_DROPOFF_UNTIL_DATE)
            .state(UPDATED_STATE)
            .price(UPDATED_PRICE)
            .carrierId(UPDATED_CARRIER_ID)
            .truckId(UPDATED_TRUCK_ID);

        restOfferMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOffer.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedOffer))
            )
            .andExpect(status().isOk());

        // Validate the Offer in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertOfferUpdatableFieldsEquals(partialUpdatedOffer, getPersistedOffer(partialUpdatedOffer));
    }

    @Test
    void patchNonExistingOffer() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        offer.setId(UUID.randomUUID());

        // Create the Offer
        OfferDTO offerDTO = offerMapper.toDto(offer);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOfferMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, offerDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(offerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Offer in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchOffer() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        offer.setId(UUID.randomUUID());

        // Create the Offer
        OfferDTO offerDTO = offerMapper.toDto(offer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOfferMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(offerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Offer in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamOffer() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        offer.setId(UUID.randomUUID());

        // Create the Offer
        OfferDTO offerDTO = offerMapper.toDto(offer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOfferMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(offerDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Offer in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteOffer() throws Exception {
        // Initialize the database
        offer.setId(UUID.randomUUID());
        offerRepository.save(offer);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the offer
        restOfferMockMvc
            .perform(delete(ENTITY_API_URL_ID, offer.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return offerRepository.count();
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

    protected Offer getPersistedOffer(Offer offer) {
        return offerRepository.findById(offer.getId()).orElseThrow();
    }

    protected void assertPersistedOfferToMatchAllProperties(Offer expectedOffer) {
        assertOfferAllPropertiesEquals(expectedOffer, getPersistedOffer(expectedOffer));
    }

    protected void assertPersistedOfferToMatchUpdatableProperties(Offer expectedOffer) {
        assertOfferAllUpdatablePropertiesEquals(expectedOffer, getPersistedOffer(expectedOffer));
    }
}

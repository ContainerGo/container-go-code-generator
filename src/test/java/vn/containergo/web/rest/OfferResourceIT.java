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
import vn.containergo.domain.Offer;
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

    private static final Instant DEFAULT_PICKUP_FROM_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_PICKUP_FROM_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_PICKUP_UNTIL_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_PICKUP_UNTIL_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DROPOFF_FROM_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DROPOFF_FROM_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DROPOFF_UNTIL_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DROPOFF_UNTIL_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Double DEFAULT_PRICE = 1D;
    private static final Double UPDATED_PRICE = 2D;

    private static final Long DEFAULT_CARRIER_ID = 1L;
    private static final Long UPDATED_CARRIER_ID = 2L;

    private static final String ENTITY_API_URL = "/api/offers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

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
            .pickupFromDate(DEFAULT_PICKUP_FROM_DATE)
            .pickupUntilDate(DEFAULT_PICKUP_UNTIL_DATE)
            .dropoffFromDate(DEFAULT_DROPOFF_FROM_DATE)
            .dropoffUntilDate(DEFAULT_DROPOFF_UNTIL_DATE)
            .price(DEFAULT_PRICE)
            .carrierId(DEFAULT_CARRIER_ID);
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
            .pickupFromDate(UPDATED_PICKUP_FROM_DATE)
            .pickupUntilDate(UPDATED_PICKUP_UNTIL_DATE)
            .dropoffFromDate(UPDATED_DROPOFF_FROM_DATE)
            .dropoffUntilDate(UPDATED_DROPOFF_UNTIL_DATE)
            .price(UPDATED_PRICE)
            .carrierId(UPDATED_CARRIER_ID);
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
        int databaseSizeBeforeCreate = offerRepository.findAll().size();
        // Create the Offer
        OfferDTO offerDTO = offerMapper.toDto(offer);
        restOfferMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(offerDTO)))
            .andExpect(status().isCreated());

        // Validate the Offer in the database
        List<Offer> offerList = offerRepository.findAll();
        assertThat(offerList).hasSize(databaseSizeBeforeCreate + 1);
        Offer testOffer = offerList.get(offerList.size() - 1);
        assertThat(testOffer.getMessage()).isEqualTo(DEFAULT_MESSAGE);
        assertThat(testOffer.getPickupFromDate()).isEqualTo(DEFAULT_PICKUP_FROM_DATE);
        assertThat(testOffer.getPickupUntilDate()).isEqualTo(DEFAULT_PICKUP_UNTIL_DATE);
        assertThat(testOffer.getDropoffFromDate()).isEqualTo(DEFAULT_DROPOFF_FROM_DATE);
        assertThat(testOffer.getDropoffUntilDate()).isEqualTo(DEFAULT_DROPOFF_UNTIL_DATE);
        assertThat(testOffer.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testOffer.getCarrierId()).isEqualTo(DEFAULT_CARRIER_ID);
    }

    @Test
    void createOfferWithExistingId() throws Exception {
        // Create the Offer with an existing ID
        offer.setId(1L);
        OfferDTO offerDTO = offerMapper.toDto(offer);

        int databaseSizeBeforeCreate = offerRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOfferMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(offerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Offer in the database
        List<Offer> offerList = offerRepository.findAll();
        assertThat(offerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void checkPickupFromDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = offerRepository.findAll().size();
        // set the field null
        offer.setPickupFromDate(null);

        // Create the Offer, which fails.
        OfferDTO offerDTO = offerMapper.toDto(offer);

        restOfferMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(offerDTO)))
            .andExpect(status().isBadRequest());

        List<Offer> offerList = offerRepository.findAll();
        assertThat(offerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkPickupUntilDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = offerRepository.findAll().size();
        // set the field null
        offer.setPickupUntilDate(null);

        // Create the Offer, which fails.
        OfferDTO offerDTO = offerMapper.toDto(offer);

        restOfferMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(offerDTO)))
            .andExpect(status().isBadRequest());

        List<Offer> offerList = offerRepository.findAll();
        assertThat(offerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkDropoffFromDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = offerRepository.findAll().size();
        // set the field null
        offer.setDropoffFromDate(null);

        // Create the Offer, which fails.
        OfferDTO offerDTO = offerMapper.toDto(offer);

        restOfferMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(offerDTO)))
            .andExpect(status().isBadRequest());

        List<Offer> offerList = offerRepository.findAll();
        assertThat(offerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkDropoffUntilDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = offerRepository.findAll().size();
        // set the field null
        offer.setDropoffUntilDate(null);

        // Create the Offer, which fails.
        OfferDTO offerDTO = offerMapper.toDto(offer);

        restOfferMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(offerDTO)))
            .andExpect(status().isBadRequest());

        List<Offer> offerList = offerRepository.findAll();
        assertThat(offerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = offerRepository.findAll().size();
        // set the field null
        offer.setPrice(null);

        // Create the Offer, which fails.
        OfferDTO offerDTO = offerMapper.toDto(offer);

        restOfferMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(offerDTO)))
            .andExpect(status().isBadRequest());

        List<Offer> offerList = offerRepository.findAll();
        assertThat(offerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkCarrierIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = offerRepository.findAll().size();
        // set the field null
        offer.setCarrierId(null);

        // Create the Offer, which fails.
        OfferDTO offerDTO = offerMapper.toDto(offer);

        restOfferMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(offerDTO)))
            .andExpect(status().isBadRequest());

        List<Offer> offerList = offerRepository.findAll();
        assertThat(offerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void getAllOffers() throws Exception {
        // Initialize the database
        offerRepository.save(offer);

        // Get all the offerList
        restOfferMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(offer.getId().intValue())))
            .andExpect(jsonPath("$.[*].message").value(hasItem(DEFAULT_MESSAGE)))
            .andExpect(jsonPath("$.[*].pickupFromDate").value(hasItem(DEFAULT_PICKUP_FROM_DATE.toString())))
            .andExpect(jsonPath("$.[*].pickupUntilDate").value(hasItem(DEFAULT_PICKUP_UNTIL_DATE.toString())))
            .andExpect(jsonPath("$.[*].dropoffFromDate").value(hasItem(DEFAULT_DROPOFF_FROM_DATE.toString())))
            .andExpect(jsonPath("$.[*].dropoffUntilDate").value(hasItem(DEFAULT_DROPOFF_UNTIL_DATE.toString())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].carrierId").value(hasItem(DEFAULT_CARRIER_ID.intValue())));
    }

    @Test
    void getOffer() throws Exception {
        // Initialize the database
        offerRepository.save(offer);

        // Get the offer
        restOfferMockMvc
            .perform(get(ENTITY_API_URL_ID, offer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(offer.getId().intValue()))
            .andExpect(jsonPath("$.message").value(DEFAULT_MESSAGE))
            .andExpect(jsonPath("$.pickupFromDate").value(DEFAULT_PICKUP_FROM_DATE.toString()))
            .andExpect(jsonPath("$.pickupUntilDate").value(DEFAULT_PICKUP_UNTIL_DATE.toString()))
            .andExpect(jsonPath("$.dropoffFromDate").value(DEFAULT_DROPOFF_FROM_DATE.toString()))
            .andExpect(jsonPath("$.dropoffUntilDate").value(DEFAULT_DROPOFF_UNTIL_DATE.toString()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.doubleValue()))
            .andExpect(jsonPath("$.carrierId").value(DEFAULT_CARRIER_ID.intValue()));
    }

    @Test
    void getNonExistingOffer() throws Exception {
        // Get the offer
        restOfferMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingOffer() throws Exception {
        // Initialize the database
        offerRepository.save(offer);

        int databaseSizeBeforeUpdate = offerRepository.findAll().size();

        // Update the offer
        Offer updatedOffer = offerRepository.findById(offer.getId()).orElseThrow();
        updatedOffer
            .message(UPDATED_MESSAGE)
            .pickupFromDate(UPDATED_PICKUP_FROM_DATE)
            .pickupUntilDate(UPDATED_PICKUP_UNTIL_DATE)
            .dropoffFromDate(UPDATED_DROPOFF_FROM_DATE)
            .dropoffUntilDate(UPDATED_DROPOFF_UNTIL_DATE)
            .price(UPDATED_PRICE)
            .carrierId(UPDATED_CARRIER_ID);
        OfferDTO offerDTO = offerMapper.toDto(updatedOffer);

        restOfferMockMvc
            .perform(
                put(ENTITY_API_URL_ID, offerDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(offerDTO))
            )
            .andExpect(status().isOk());

        // Validate the Offer in the database
        List<Offer> offerList = offerRepository.findAll();
        assertThat(offerList).hasSize(databaseSizeBeforeUpdate);
        Offer testOffer = offerList.get(offerList.size() - 1);
        assertThat(testOffer.getMessage()).isEqualTo(UPDATED_MESSAGE);
        assertThat(testOffer.getPickupFromDate()).isEqualTo(UPDATED_PICKUP_FROM_DATE);
        assertThat(testOffer.getPickupUntilDate()).isEqualTo(UPDATED_PICKUP_UNTIL_DATE);
        assertThat(testOffer.getDropoffFromDate()).isEqualTo(UPDATED_DROPOFF_FROM_DATE);
        assertThat(testOffer.getDropoffUntilDate()).isEqualTo(UPDATED_DROPOFF_UNTIL_DATE);
        assertThat(testOffer.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testOffer.getCarrierId()).isEqualTo(UPDATED_CARRIER_ID);
    }

    @Test
    void putNonExistingOffer() throws Exception {
        int databaseSizeBeforeUpdate = offerRepository.findAll().size();
        offer.setId(longCount.incrementAndGet());

        // Create the Offer
        OfferDTO offerDTO = offerMapper.toDto(offer);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOfferMockMvc
            .perform(
                put(ENTITY_API_URL_ID, offerDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(offerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Offer in the database
        List<Offer> offerList = offerRepository.findAll();
        assertThat(offerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchOffer() throws Exception {
        int databaseSizeBeforeUpdate = offerRepository.findAll().size();
        offer.setId(longCount.incrementAndGet());

        // Create the Offer
        OfferDTO offerDTO = offerMapper.toDto(offer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOfferMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(offerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Offer in the database
        List<Offer> offerList = offerRepository.findAll();
        assertThat(offerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamOffer() throws Exception {
        int databaseSizeBeforeUpdate = offerRepository.findAll().size();
        offer.setId(longCount.incrementAndGet());

        // Create the Offer
        OfferDTO offerDTO = offerMapper.toDto(offer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOfferMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(offerDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Offer in the database
        List<Offer> offerList = offerRepository.findAll();
        assertThat(offerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateOfferWithPatch() throws Exception {
        // Initialize the database
        offerRepository.save(offer);

        int databaseSizeBeforeUpdate = offerRepository.findAll().size();

        // Update the offer using partial update
        Offer partialUpdatedOffer = new Offer();
        partialUpdatedOffer.setId(offer.getId());

        partialUpdatedOffer
            .pickupFromDate(UPDATED_PICKUP_FROM_DATE)
            .dropoffFromDate(UPDATED_DROPOFF_FROM_DATE)
            .dropoffUntilDate(UPDATED_DROPOFF_UNTIL_DATE)
            .price(UPDATED_PRICE);

        restOfferMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOffer.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOffer))
            )
            .andExpect(status().isOk());

        // Validate the Offer in the database
        List<Offer> offerList = offerRepository.findAll();
        assertThat(offerList).hasSize(databaseSizeBeforeUpdate);
        Offer testOffer = offerList.get(offerList.size() - 1);
        assertThat(testOffer.getMessage()).isEqualTo(DEFAULT_MESSAGE);
        assertThat(testOffer.getPickupFromDate()).isEqualTo(UPDATED_PICKUP_FROM_DATE);
        assertThat(testOffer.getPickupUntilDate()).isEqualTo(DEFAULT_PICKUP_UNTIL_DATE);
        assertThat(testOffer.getDropoffFromDate()).isEqualTo(UPDATED_DROPOFF_FROM_DATE);
        assertThat(testOffer.getDropoffUntilDate()).isEqualTo(UPDATED_DROPOFF_UNTIL_DATE);
        assertThat(testOffer.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testOffer.getCarrierId()).isEqualTo(DEFAULT_CARRIER_ID);
    }

    @Test
    void fullUpdateOfferWithPatch() throws Exception {
        // Initialize the database
        offerRepository.save(offer);

        int databaseSizeBeforeUpdate = offerRepository.findAll().size();

        // Update the offer using partial update
        Offer partialUpdatedOffer = new Offer();
        partialUpdatedOffer.setId(offer.getId());

        partialUpdatedOffer
            .message(UPDATED_MESSAGE)
            .pickupFromDate(UPDATED_PICKUP_FROM_DATE)
            .pickupUntilDate(UPDATED_PICKUP_UNTIL_DATE)
            .dropoffFromDate(UPDATED_DROPOFF_FROM_DATE)
            .dropoffUntilDate(UPDATED_DROPOFF_UNTIL_DATE)
            .price(UPDATED_PRICE)
            .carrierId(UPDATED_CARRIER_ID);

        restOfferMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOffer.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOffer))
            )
            .andExpect(status().isOk());

        // Validate the Offer in the database
        List<Offer> offerList = offerRepository.findAll();
        assertThat(offerList).hasSize(databaseSizeBeforeUpdate);
        Offer testOffer = offerList.get(offerList.size() - 1);
        assertThat(testOffer.getMessage()).isEqualTo(UPDATED_MESSAGE);
        assertThat(testOffer.getPickupFromDate()).isEqualTo(UPDATED_PICKUP_FROM_DATE);
        assertThat(testOffer.getPickupUntilDate()).isEqualTo(UPDATED_PICKUP_UNTIL_DATE);
        assertThat(testOffer.getDropoffFromDate()).isEqualTo(UPDATED_DROPOFF_FROM_DATE);
        assertThat(testOffer.getDropoffUntilDate()).isEqualTo(UPDATED_DROPOFF_UNTIL_DATE);
        assertThat(testOffer.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testOffer.getCarrierId()).isEqualTo(UPDATED_CARRIER_ID);
    }

    @Test
    void patchNonExistingOffer() throws Exception {
        int databaseSizeBeforeUpdate = offerRepository.findAll().size();
        offer.setId(longCount.incrementAndGet());

        // Create the Offer
        OfferDTO offerDTO = offerMapper.toDto(offer);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOfferMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, offerDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(offerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Offer in the database
        List<Offer> offerList = offerRepository.findAll();
        assertThat(offerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchOffer() throws Exception {
        int databaseSizeBeforeUpdate = offerRepository.findAll().size();
        offer.setId(longCount.incrementAndGet());

        // Create the Offer
        OfferDTO offerDTO = offerMapper.toDto(offer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOfferMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(offerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Offer in the database
        List<Offer> offerList = offerRepository.findAll();
        assertThat(offerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamOffer() throws Exception {
        int databaseSizeBeforeUpdate = offerRepository.findAll().size();
        offer.setId(longCount.incrementAndGet());

        // Create the Offer
        OfferDTO offerDTO = offerMapper.toDto(offer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOfferMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(offerDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Offer in the database
        List<Offer> offerList = offerRepository.findAll();
        assertThat(offerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteOffer() throws Exception {
        // Initialize the database
        offerRepository.save(offer);

        int databaseSizeBeforeDelete = offerRepository.findAll().size();

        // Delete the offer
        restOfferMockMvc
            .perform(delete(ENTITY_API_URL_ID, offer.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Offer> offerList = offerRepository.findAll();
        assertThat(offerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

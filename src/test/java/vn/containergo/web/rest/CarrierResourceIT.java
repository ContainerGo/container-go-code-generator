package vn.containergo.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static vn.containergo.domain.CarrierAsserts.*;
import static vn.containergo.web.rest.TestUtil.createUpdateProxyForBean;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
import vn.containergo.domain.Carrier;
import vn.containergo.repository.CarrierRepository;
import vn.containergo.service.dto.CarrierDTO;
import vn.containergo.service.mapper.CarrierMapper;

/**
 * Integration tests for the {@link CarrierResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CarrierResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_TAX_CODE = "AAAAAAAAAA";
    private static final String UPDATED_TAX_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_BANK_ACCOUNT = "AAAAAAAAAA";
    private static final String UPDATED_BANK_ACCOUNT = "BBBBBBBBBB";

    private static final String DEFAULT_BANK_NAME = "AAAAAAAAAA";
    private static final String UPDATED_BANK_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ACCOUNT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_BRANCH_NAME = "AAAAAAAAAA";
    private static final String UPDATED_BRANCH_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_COMPANY_SIZE = 1;
    private static final Integer UPDATED_COMPANY_SIZE = 2;

    private static final Boolean DEFAULT_IS_APPROVED = false;
    private static final Boolean UPDATED_IS_APPROVED = true;

    private static final Integer DEFAULT_VEHICLES = 1;
    private static final Integer UPDATED_VEHICLES = 2;

    private static final Integer DEFAULT_SHIPMENTS_LEFT_FOR_DAY = 1;
    private static final Integer UPDATED_SHIPMENTS_LEFT_FOR_DAY = 2;

    private static final Instant DEFAULT_VERIFIED_SINCE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_VERIFIED_SINCE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/carriers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private CarrierRepository carrierRepository;

    @Autowired
    private CarrierMapper carrierMapper;

    @Autowired
    private MockMvc restCarrierMockMvc;

    private Carrier carrier;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Carrier createEntity() {
        Carrier carrier = new Carrier()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .address(DEFAULT_ADDRESS)
            .taxCode(DEFAULT_TAX_CODE)
            .bankAccount(DEFAULT_BANK_ACCOUNT)
            .bankName(DEFAULT_BANK_NAME)
            .accountName(DEFAULT_ACCOUNT_NAME)
            .branchName(DEFAULT_BRANCH_NAME)
            .companySize(DEFAULT_COMPANY_SIZE)
            .isApproved(DEFAULT_IS_APPROVED)
            .vehicles(DEFAULT_VEHICLES)
            .shipmentsLeftForDay(DEFAULT_SHIPMENTS_LEFT_FOR_DAY)
            .verifiedSince(DEFAULT_VERIFIED_SINCE);
        return carrier;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Carrier createUpdatedEntity() {
        Carrier carrier = new Carrier()
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .address(UPDATED_ADDRESS)
            .taxCode(UPDATED_TAX_CODE)
            .bankAccount(UPDATED_BANK_ACCOUNT)
            .bankName(UPDATED_BANK_NAME)
            .accountName(UPDATED_ACCOUNT_NAME)
            .branchName(UPDATED_BRANCH_NAME)
            .companySize(UPDATED_COMPANY_SIZE)
            .isApproved(UPDATED_IS_APPROVED)
            .vehicles(UPDATED_VEHICLES)
            .shipmentsLeftForDay(UPDATED_SHIPMENTS_LEFT_FOR_DAY)
            .verifiedSince(UPDATED_VERIFIED_SINCE);
        return carrier;
    }

    @BeforeEach
    public void initTest() {
        carrierRepository.deleteAll();
        carrier = createEntity();
    }

    @Test
    void createCarrier() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Carrier
        CarrierDTO carrierDTO = carrierMapper.toDto(carrier);
        var returnedCarrierDTO = om.readValue(
            restCarrierMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(carrierDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            CarrierDTO.class
        );

        // Validate the Carrier in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedCarrier = carrierMapper.toEntity(returnedCarrierDTO);
        assertCarrierUpdatableFieldsEquals(returnedCarrier, getPersistedCarrier(returnedCarrier));
    }

    @Test
    void createCarrierWithExistingId() throws Exception {
        // Create the Carrier with an existing ID
        carrier.setId(1L);
        CarrierDTO carrierDTO = carrierMapper.toDto(carrier);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCarrierMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(carrierDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Carrier in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    void checkCodeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        carrier.setCode(null);

        // Create the Carrier, which fails.
        CarrierDTO carrierDTO = carrierMapper.toDto(carrier);

        restCarrierMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(carrierDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        carrier.setName(null);

        // Create the Carrier, which fails.
        CarrierDTO carrierDTO = carrierMapper.toDto(carrier);

        restCarrierMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(carrierDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkAddressIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        carrier.setAddress(null);

        // Create the Carrier, which fails.
        CarrierDTO carrierDTO = carrierMapper.toDto(carrier);

        restCarrierMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(carrierDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void getAllCarriers() throws Exception {
        // Initialize the database
        carrierRepository.save(carrier);

        // Get all the carrierList
        restCarrierMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(carrier.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].taxCode").value(hasItem(DEFAULT_TAX_CODE)))
            .andExpect(jsonPath("$.[*].bankAccount").value(hasItem(DEFAULT_BANK_ACCOUNT)))
            .andExpect(jsonPath("$.[*].bankName").value(hasItem(DEFAULT_BANK_NAME)))
            .andExpect(jsonPath("$.[*].accountName").value(hasItem(DEFAULT_ACCOUNT_NAME)))
            .andExpect(jsonPath("$.[*].branchName").value(hasItem(DEFAULT_BRANCH_NAME)))
            .andExpect(jsonPath("$.[*].companySize").value(hasItem(DEFAULT_COMPANY_SIZE)))
            .andExpect(jsonPath("$.[*].isApproved").value(hasItem(DEFAULT_IS_APPROVED.booleanValue())))
            .andExpect(jsonPath("$.[*].vehicles").value(hasItem(DEFAULT_VEHICLES)))
            .andExpect(jsonPath("$.[*].shipmentsLeftForDay").value(hasItem(DEFAULT_SHIPMENTS_LEFT_FOR_DAY)))
            .andExpect(jsonPath("$.[*].verifiedSince").value(hasItem(DEFAULT_VERIFIED_SINCE.toString())));
    }

    @Test
    void getCarrier() throws Exception {
        // Initialize the database
        carrierRepository.save(carrier);

        // Get the carrier
        restCarrierMockMvc
            .perform(get(ENTITY_API_URL_ID, carrier.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(carrier.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.taxCode").value(DEFAULT_TAX_CODE))
            .andExpect(jsonPath("$.bankAccount").value(DEFAULT_BANK_ACCOUNT))
            .andExpect(jsonPath("$.bankName").value(DEFAULT_BANK_NAME))
            .andExpect(jsonPath("$.accountName").value(DEFAULT_ACCOUNT_NAME))
            .andExpect(jsonPath("$.branchName").value(DEFAULT_BRANCH_NAME))
            .andExpect(jsonPath("$.companySize").value(DEFAULT_COMPANY_SIZE))
            .andExpect(jsonPath("$.isApproved").value(DEFAULT_IS_APPROVED.booleanValue()))
            .andExpect(jsonPath("$.vehicles").value(DEFAULT_VEHICLES))
            .andExpect(jsonPath("$.shipmentsLeftForDay").value(DEFAULT_SHIPMENTS_LEFT_FOR_DAY))
            .andExpect(jsonPath("$.verifiedSince").value(DEFAULT_VERIFIED_SINCE.toString()));
    }

    @Test
    void getNonExistingCarrier() throws Exception {
        // Get the carrier
        restCarrierMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingCarrier() throws Exception {
        // Initialize the database
        carrierRepository.save(carrier);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the carrier
        Carrier updatedCarrier = carrierRepository.findById(carrier.getId()).orElseThrow();
        updatedCarrier
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .address(UPDATED_ADDRESS)
            .taxCode(UPDATED_TAX_CODE)
            .bankAccount(UPDATED_BANK_ACCOUNT)
            .bankName(UPDATED_BANK_NAME)
            .accountName(UPDATED_ACCOUNT_NAME)
            .branchName(UPDATED_BRANCH_NAME)
            .companySize(UPDATED_COMPANY_SIZE)
            .isApproved(UPDATED_IS_APPROVED)
            .vehicles(UPDATED_VEHICLES)
            .shipmentsLeftForDay(UPDATED_SHIPMENTS_LEFT_FOR_DAY)
            .verifiedSince(UPDATED_VERIFIED_SINCE);
        CarrierDTO carrierDTO = carrierMapper.toDto(updatedCarrier);

        restCarrierMockMvc
            .perform(
                put(ENTITY_API_URL_ID, carrierDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(carrierDTO))
            )
            .andExpect(status().isOk());

        // Validate the Carrier in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedCarrierToMatchAllProperties(updatedCarrier);
    }

    @Test
    void putNonExistingCarrier() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        carrier.setId(longCount.incrementAndGet());

        // Create the Carrier
        CarrierDTO carrierDTO = carrierMapper.toDto(carrier);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCarrierMockMvc
            .perform(
                put(ENTITY_API_URL_ID, carrierDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(carrierDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Carrier in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchCarrier() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        carrier.setId(longCount.incrementAndGet());

        // Create the Carrier
        CarrierDTO carrierDTO = carrierMapper.toDto(carrier);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCarrierMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(carrierDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Carrier in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamCarrier() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        carrier.setId(longCount.incrementAndGet());

        // Create the Carrier
        CarrierDTO carrierDTO = carrierMapper.toDto(carrier);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCarrierMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(carrierDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Carrier in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateCarrierWithPatch() throws Exception {
        // Initialize the database
        carrierRepository.save(carrier);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the carrier using partial update
        Carrier partialUpdatedCarrier = new Carrier();
        partialUpdatedCarrier.setId(carrier.getId());

        partialUpdatedCarrier
            .name(UPDATED_NAME)
            .address(UPDATED_ADDRESS)
            .accountName(UPDATED_ACCOUNT_NAME)
            .companySize(UPDATED_COMPANY_SIZE)
            .shipmentsLeftForDay(UPDATED_SHIPMENTS_LEFT_FOR_DAY);

        restCarrierMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCarrier.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCarrier))
            )
            .andExpect(status().isOk());

        // Validate the Carrier in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCarrierUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedCarrier, carrier), getPersistedCarrier(carrier));
    }

    @Test
    void fullUpdateCarrierWithPatch() throws Exception {
        // Initialize the database
        carrierRepository.save(carrier);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the carrier using partial update
        Carrier partialUpdatedCarrier = new Carrier();
        partialUpdatedCarrier.setId(carrier.getId());

        partialUpdatedCarrier
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .address(UPDATED_ADDRESS)
            .taxCode(UPDATED_TAX_CODE)
            .bankAccount(UPDATED_BANK_ACCOUNT)
            .bankName(UPDATED_BANK_NAME)
            .accountName(UPDATED_ACCOUNT_NAME)
            .branchName(UPDATED_BRANCH_NAME)
            .companySize(UPDATED_COMPANY_SIZE)
            .isApproved(UPDATED_IS_APPROVED)
            .vehicles(UPDATED_VEHICLES)
            .shipmentsLeftForDay(UPDATED_SHIPMENTS_LEFT_FOR_DAY)
            .verifiedSince(UPDATED_VERIFIED_SINCE);

        restCarrierMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCarrier.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCarrier))
            )
            .andExpect(status().isOk());

        // Validate the Carrier in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCarrierUpdatableFieldsEquals(partialUpdatedCarrier, getPersistedCarrier(partialUpdatedCarrier));
    }

    @Test
    void patchNonExistingCarrier() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        carrier.setId(longCount.incrementAndGet());

        // Create the Carrier
        CarrierDTO carrierDTO = carrierMapper.toDto(carrier);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCarrierMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, carrierDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(carrierDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Carrier in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchCarrier() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        carrier.setId(longCount.incrementAndGet());

        // Create the Carrier
        CarrierDTO carrierDTO = carrierMapper.toDto(carrier);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCarrierMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(carrierDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Carrier in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamCarrier() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        carrier.setId(longCount.incrementAndGet());

        // Create the Carrier
        CarrierDTO carrierDTO = carrierMapper.toDto(carrier);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCarrierMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(carrierDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Carrier in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteCarrier() throws Exception {
        // Initialize the database
        carrierRepository.save(carrier);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the carrier
        restCarrierMockMvc
            .perform(delete(ENTITY_API_URL_ID, carrier.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return carrierRepository.count();
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

    protected Carrier getPersistedCarrier(Carrier carrier) {
        return carrierRepository.findById(carrier.getId()).orElseThrow();
    }

    protected void assertPersistedCarrierToMatchAllProperties(Carrier expectedCarrier) {
        assertCarrierAllPropertiesEquals(expectedCarrier, getPersistedCarrier(expectedCarrier));
    }

    protected void assertPersistedCarrierToMatchUpdatableProperties(Carrier expectedCarrier) {
        assertCarrierAllUpdatablePropertiesEquals(expectedCarrier, getPersistedCarrier(expectedCarrier));
    }
}

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

    private static final String ENTITY_API_URL = "/api/carriers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

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
            .isApproved(DEFAULT_IS_APPROVED);
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
            .isApproved(UPDATED_IS_APPROVED);
        return carrier;
    }

    @BeforeEach
    public void initTest() {
        carrierRepository.deleteAll();
        carrier = createEntity();
    }

    @Test
    void createCarrier() throws Exception {
        int databaseSizeBeforeCreate = carrierRepository.findAll().size();
        // Create the Carrier
        CarrierDTO carrierDTO = carrierMapper.toDto(carrier);
        restCarrierMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(carrierDTO)))
            .andExpect(status().isCreated());

        // Validate the Carrier in the database
        List<Carrier> carrierList = carrierRepository.findAll();
        assertThat(carrierList).hasSize(databaseSizeBeforeCreate + 1);
        Carrier testCarrier = carrierList.get(carrierList.size() - 1);
        assertThat(testCarrier.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testCarrier.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCarrier.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testCarrier.getTaxCode()).isEqualTo(DEFAULT_TAX_CODE);
        assertThat(testCarrier.getBankAccount()).isEqualTo(DEFAULT_BANK_ACCOUNT);
        assertThat(testCarrier.getBankName()).isEqualTo(DEFAULT_BANK_NAME);
        assertThat(testCarrier.getAccountName()).isEqualTo(DEFAULT_ACCOUNT_NAME);
        assertThat(testCarrier.getBranchName()).isEqualTo(DEFAULT_BRANCH_NAME);
        assertThat(testCarrier.getCompanySize()).isEqualTo(DEFAULT_COMPANY_SIZE);
        assertThat(testCarrier.getIsApproved()).isEqualTo(DEFAULT_IS_APPROVED);
    }

    @Test
    void createCarrierWithExistingId() throws Exception {
        // Create the Carrier with an existing ID
        carrier.setId(1L);
        CarrierDTO carrierDTO = carrierMapper.toDto(carrier);

        int databaseSizeBeforeCreate = carrierRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCarrierMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(carrierDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Carrier in the database
        List<Carrier> carrierList = carrierRepository.findAll();
        assertThat(carrierList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = carrierRepository.findAll().size();
        // set the field null
        carrier.setCode(null);

        // Create the Carrier, which fails.
        CarrierDTO carrierDTO = carrierMapper.toDto(carrier);

        restCarrierMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(carrierDTO)))
            .andExpect(status().isBadRequest());

        List<Carrier> carrierList = carrierRepository.findAll();
        assertThat(carrierList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = carrierRepository.findAll().size();
        // set the field null
        carrier.setName(null);

        // Create the Carrier, which fails.
        CarrierDTO carrierDTO = carrierMapper.toDto(carrier);

        restCarrierMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(carrierDTO)))
            .andExpect(status().isBadRequest());

        List<Carrier> carrierList = carrierRepository.findAll();
        assertThat(carrierList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkAddressIsRequired() throws Exception {
        int databaseSizeBeforeTest = carrierRepository.findAll().size();
        // set the field null
        carrier.setAddress(null);

        // Create the Carrier, which fails.
        CarrierDTO carrierDTO = carrierMapper.toDto(carrier);

        restCarrierMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(carrierDTO)))
            .andExpect(status().isBadRequest());

        List<Carrier> carrierList = carrierRepository.findAll();
        assertThat(carrierList).hasSize(databaseSizeBeforeTest);
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
            .andExpect(jsonPath("$.[*].isApproved").value(hasItem(DEFAULT_IS_APPROVED.booleanValue())));
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
            .andExpect(jsonPath("$.isApproved").value(DEFAULT_IS_APPROVED.booleanValue()));
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

        int databaseSizeBeforeUpdate = carrierRepository.findAll().size();

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
            .isApproved(UPDATED_IS_APPROVED);
        CarrierDTO carrierDTO = carrierMapper.toDto(updatedCarrier);

        restCarrierMockMvc
            .perform(
                put(ENTITY_API_URL_ID, carrierDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(carrierDTO))
            )
            .andExpect(status().isOk());

        // Validate the Carrier in the database
        List<Carrier> carrierList = carrierRepository.findAll();
        assertThat(carrierList).hasSize(databaseSizeBeforeUpdate);
        Carrier testCarrier = carrierList.get(carrierList.size() - 1);
        assertThat(testCarrier.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testCarrier.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCarrier.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testCarrier.getTaxCode()).isEqualTo(UPDATED_TAX_CODE);
        assertThat(testCarrier.getBankAccount()).isEqualTo(UPDATED_BANK_ACCOUNT);
        assertThat(testCarrier.getBankName()).isEqualTo(UPDATED_BANK_NAME);
        assertThat(testCarrier.getAccountName()).isEqualTo(UPDATED_ACCOUNT_NAME);
        assertThat(testCarrier.getBranchName()).isEqualTo(UPDATED_BRANCH_NAME);
        assertThat(testCarrier.getCompanySize()).isEqualTo(UPDATED_COMPANY_SIZE);
        assertThat(testCarrier.getIsApproved()).isEqualTo(UPDATED_IS_APPROVED);
    }

    @Test
    void putNonExistingCarrier() throws Exception {
        int databaseSizeBeforeUpdate = carrierRepository.findAll().size();
        carrier.setId(longCount.incrementAndGet());

        // Create the Carrier
        CarrierDTO carrierDTO = carrierMapper.toDto(carrier);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCarrierMockMvc
            .perform(
                put(ENTITY_API_URL_ID, carrierDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(carrierDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Carrier in the database
        List<Carrier> carrierList = carrierRepository.findAll();
        assertThat(carrierList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchCarrier() throws Exception {
        int databaseSizeBeforeUpdate = carrierRepository.findAll().size();
        carrier.setId(longCount.incrementAndGet());

        // Create the Carrier
        CarrierDTO carrierDTO = carrierMapper.toDto(carrier);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCarrierMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(carrierDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Carrier in the database
        List<Carrier> carrierList = carrierRepository.findAll();
        assertThat(carrierList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamCarrier() throws Exception {
        int databaseSizeBeforeUpdate = carrierRepository.findAll().size();
        carrier.setId(longCount.incrementAndGet());

        // Create the Carrier
        CarrierDTO carrierDTO = carrierMapper.toDto(carrier);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCarrierMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(carrierDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Carrier in the database
        List<Carrier> carrierList = carrierRepository.findAll();
        assertThat(carrierList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateCarrierWithPatch() throws Exception {
        // Initialize the database
        carrierRepository.save(carrier);

        int databaseSizeBeforeUpdate = carrierRepository.findAll().size();

        // Update the carrier using partial update
        Carrier partialUpdatedCarrier = new Carrier();
        partialUpdatedCarrier.setId(carrier.getId());

        partialUpdatedCarrier
            .code(UPDATED_CODE)
            .taxCode(UPDATED_TAX_CODE)
            .accountName(UPDATED_ACCOUNT_NAME)
            .branchName(UPDATED_BRANCH_NAME)
            .companySize(UPDATED_COMPANY_SIZE);

        restCarrierMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCarrier.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCarrier))
            )
            .andExpect(status().isOk());

        // Validate the Carrier in the database
        List<Carrier> carrierList = carrierRepository.findAll();
        assertThat(carrierList).hasSize(databaseSizeBeforeUpdate);
        Carrier testCarrier = carrierList.get(carrierList.size() - 1);
        assertThat(testCarrier.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testCarrier.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCarrier.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testCarrier.getTaxCode()).isEqualTo(UPDATED_TAX_CODE);
        assertThat(testCarrier.getBankAccount()).isEqualTo(DEFAULT_BANK_ACCOUNT);
        assertThat(testCarrier.getBankName()).isEqualTo(DEFAULT_BANK_NAME);
        assertThat(testCarrier.getAccountName()).isEqualTo(UPDATED_ACCOUNT_NAME);
        assertThat(testCarrier.getBranchName()).isEqualTo(UPDATED_BRANCH_NAME);
        assertThat(testCarrier.getCompanySize()).isEqualTo(UPDATED_COMPANY_SIZE);
        assertThat(testCarrier.getIsApproved()).isEqualTo(DEFAULT_IS_APPROVED);
    }

    @Test
    void fullUpdateCarrierWithPatch() throws Exception {
        // Initialize the database
        carrierRepository.save(carrier);

        int databaseSizeBeforeUpdate = carrierRepository.findAll().size();

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
            .isApproved(UPDATED_IS_APPROVED);

        restCarrierMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCarrier.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCarrier))
            )
            .andExpect(status().isOk());

        // Validate the Carrier in the database
        List<Carrier> carrierList = carrierRepository.findAll();
        assertThat(carrierList).hasSize(databaseSizeBeforeUpdate);
        Carrier testCarrier = carrierList.get(carrierList.size() - 1);
        assertThat(testCarrier.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testCarrier.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCarrier.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testCarrier.getTaxCode()).isEqualTo(UPDATED_TAX_CODE);
        assertThat(testCarrier.getBankAccount()).isEqualTo(UPDATED_BANK_ACCOUNT);
        assertThat(testCarrier.getBankName()).isEqualTo(UPDATED_BANK_NAME);
        assertThat(testCarrier.getAccountName()).isEqualTo(UPDATED_ACCOUNT_NAME);
        assertThat(testCarrier.getBranchName()).isEqualTo(UPDATED_BRANCH_NAME);
        assertThat(testCarrier.getCompanySize()).isEqualTo(UPDATED_COMPANY_SIZE);
        assertThat(testCarrier.getIsApproved()).isEqualTo(UPDATED_IS_APPROVED);
    }

    @Test
    void patchNonExistingCarrier() throws Exception {
        int databaseSizeBeforeUpdate = carrierRepository.findAll().size();
        carrier.setId(longCount.incrementAndGet());

        // Create the Carrier
        CarrierDTO carrierDTO = carrierMapper.toDto(carrier);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCarrierMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, carrierDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(carrierDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Carrier in the database
        List<Carrier> carrierList = carrierRepository.findAll();
        assertThat(carrierList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchCarrier() throws Exception {
        int databaseSizeBeforeUpdate = carrierRepository.findAll().size();
        carrier.setId(longCount.incrementAndGet());

        // Create the Carrier
        CarrierDTO carrierDTO = carrierMapper.toDto(carrier);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCarrierMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(carrierDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Carrier in the database
        List<Carrier> carrierList = carrierRepository.findAll();
        assertThat(carrierList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamCarrier() throws Exception {
        int databaseSizeBeforeUpdate = carrierRepository.findAll().size();
        carrier.setId(longCount.incrementAndGet());

        // Create the Carrier
        CarrierDTO carrierDTO = carrierMapper.toDto(carrier);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCarrierMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(carrierDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Carrier in the database
        List<Carrier> carrierList = carrierRepository.findAll();
        assertThat(carrierList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteCarrier() throws Exception {
        // Initialize the database
        carrierRepository.save(carrier);

        int databaseSizeBeforeDelete = carrierRepository.findAll().size();

        // Delete the carrier
        restCarrierMockMvc
            .perform(delete(ENTITY_API_URL_ID, carrier.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Carrier> carrierList = carrierRepository.findAll();
        assertThat(carrierList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

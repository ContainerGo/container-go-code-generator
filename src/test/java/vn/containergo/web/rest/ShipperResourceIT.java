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
import vn.containergo.domain.Shipper;
import vn.containergo.repository.ShipperRepository;
import vn.containergo.service.dto.ShipperDTO;
import vn.containergo.service.mapper.ShipperMapper;

/**
 * Integration tests for the {@link ShipperResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ShipperResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_TAX_CODE = "AAAAAAAAAA";
    private static final String UPDATED_TAX_CODE = "BBBBBBBBBB";

    private static final Integer DEFAULT_COMPANY_SIZE = 1;
    private static final Integer UPDATED_COMPANY_SIZE = 2;

    private static final String DEFAULT_PAYMENT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_PAYMENT_TYPE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_APPROVED = false;
    private static final Boolean UPDATED_IS_APPROVED = true;

    private static final Boolean DEFAULT_IS_BILLING_INFORMATION_COMPLETE = false;
    private static final Boolean UPDATED_IS_BILLING_INFORMATION_COMPLETE = true;

    private static final Boolean DEFAULT_IS_PROFILE_COMPLETE = false;
    private static final Boolean UPDATED_IS_PROFILE_COMPLETE = true;

    private static final String ENTITY_API_URL = "/api/shippers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ShipperRepository shipperRepository;

    @Autowired
    private ShipperMapper shipperMapper;

    @Autowired
    private MockMvc restShipperMockMvc;

    private Shipper shipper;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Shipper createEntity() {
        Shipper shipper = new Shipper()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .address(DEFAULT_ADDRESS)
            .taxCode(DEFAULT_TAX_CODE)
            .companySize(DEFAULT_COMPANY_SIZE)
            .paymentType(DEFAULT_PAYMENT_TYPE)
            .isApproved(DEFAULT_IS_APPROVED)
            .isBillingInformationComplete(DEFAULT_IS_BILLING_INFORMATION_COMPLETE)
            .isProfileComplete(DEFAULT_IS_PROFILE_COMPLETE);
        return shipper;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Shipper createUpdatedEntity() {
        Shipper shipper = new Shipper()
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .address(UPDATED_ADDRESS)
            .taxCode(UPDATED_TAX_CODE)
            .companySize(UPDATED_COMPANY_SIZE)
            .paymentType(UPDATED_PAYMENT_TYPE)
            .isApproved(UPDATED_IS_APPROVED)
            .isBillingInformationComplete(UPDATED_IS_BILLING_INFORMATION_COMPLETE)
            .isProfileComplete(UPDATED_IS_PROFILE_COMPLETE);
        return shipper;
    }

    @BeforeEach
    public void initTest() {
        shipperRepository.deleteAll();
        shipper = createEntity();
    }

    @Test
    void createShipper() throws Exception {
        int databaseSizeBeforeCreate = shipperRepository.findAll().size();
        // Create the Shipper
        ShipperDTO shipperDTO = shipperMapper.toDto(shipper);
        restShipperMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(shipperDTO)))
            .andExpect(status().isCreated());

        // Validate the Shipper in the database
        List<Shipper> shipperList = shipperRepository.findAll();
        assertThat(shipperList).hasSize(databaseSizeBeforeCreate + 1);
        Shipper testShipper = shipperList.get(shipperList.size() - 1);
        assertThat(testShipper.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testShipper.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testShipper.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testShipper.getTaxCode()).isEqualTo(DEFAULT_TAX_CODE);
        assertThat(testShipper.getCompanySize()).isEqualTo(DEFAULT_COMPANY_SIZE);
        assertThat(testShipper.getPaymentType()).isEqualTo(DEFAULT_PAYMENT_TYPE);
        assertThat(testShipper.getIsApproved()).isEqualTo(DEFAULT_IS_APPROVED);
        assertThat(testShipper.getIsBillingInformationComplete()).isEqualTo(DEFAULT_IS_BILLING_INFORMATION_COMPLETE);
        assertThat(testShipper.getIsProfileComplete()).isEqualTo(DEFAULT_IS_PROFILE_COMPLETE);
    }

    @Test
    void createShipperWithExistingId() throws Exception {
        // Create the Shipper with an existing ID
        shipper.setId(1L);
        ShipperDTO shipperDTO = shipperMapper.toDto(shipper);

        int databaseSizeBeforeCreate = shipperRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restShipperMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(shipperDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Shipper in the database
        List<Shipper> shipperList = shipperRepository.findAll();
        assertThat(shipperList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = shipperRepository.findAll().size();
        // set the field null
        shipper.setCode(null);

        // Create the Shipper, which fails.
        ShipperDTO shipperDTO = shipperMapper.toDto(shipper);

        restShipperMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(shipperDTO)))
            .andExpect(status().isBadRequest());

        List<Shipper> shipperList = shipperRepository.findAll();
        assertThat(shipperList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = shipperRepository.findAll().size();
        // set the field null
        shipper.setName(null);

        // Create the Shipper, which fails.
        ShipperDTO shipperDTO = shipperMapper.toDto(shipper);

        restShipperMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(shipperDTO)))
            .andExpect(status().isBadRequest());

        List<Shipper> shipperList = shipperRepository.findAll();
        assertThat(shipperList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkAddressIsRequired() throws Exception {
        int databaseSizeBeforeTest = shipperRepository.findAll().size();
        // set the field null
        shipper.setAddress(null);

        // Create the Shipper, which fails.
        ShipperDTO shipperDTO = shipperMapper.toDto(shipper);

        restShipperMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(shipperDTO)))
            .andExpect(status().isBadRequest());

        List<Shipper> shipperList = shipperRepository.findAll();
        assertThat(shipperList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkPaymentTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = shipperRepository.findAll().size();
        // set the field null
        shipper.setPaymentType(null);

        // Create the Shipper, which fails.
        ShipperDTO shipperDTO = shipperMapper.toDto(shipper);

        restShipperMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(shipperDTO)))
            .andExpect(status().isBadRequest());

        List<Shipper> shipperList = shipperRepository.findAll();
        assertThat(shipperList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void getAllShippers() throws Exception {
        // Initialize the database
        shipperRepository.save(shipper);

        // Get all the shipperList
        restShipperMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(shipper.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].taxCode").value(hasItem(DEFAULT_TAX_CODE)))
            .andExpect(jsonPath("$.[*].companySize").value(hasItem(DEFAULT_COMPANY_SIZE)))
            .andExpect(jsonPath("$.[*].paymentType").value(hasItem(DEFAULT_PAYMENT_TYPE)))
            .andExpect(jsonPath("$.[*].isApproved").value(hasItem(DEFAULT_IS_APPROVED.booleanValue())))
            .andExpect(
                jsonPath("$.[*].isBillingInformationComplete").value(hasItem(DEFAULT_IS_BILLING_INFORMATION_COMPLETE.booleanValue()))
            )
            .andExpect(jsonPath("$.[*].isProfileComplete").value(hasItem(DEFAULT_IS_PROFILE_COMPLETE.booleanValue())));
    }

    @Test
    void getShipper() throws Exception {
        // Initialize the database
        shipperRepository.save(shipper);

        // Get the shipper
        restShipperMockMvc
            .perform(get(ENTITY_API_URL_ID, shipper.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(shipper.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.taxCode").value(DEFAULT_TAX_CODE))
            .andExpect(jsonPath("$.companySize").value(DEFAULT_COMPANY_SIZE))
            .andExpect(jsonPath("$.paymentType").value(DEFAULT_PAYMENT_TYPE))
            .andExpect(jsonPath("$.isApproved").value(DEFAULT_IS_APPROVED.booleanValue()))
            .andExpect(jsonPath("$.isBillingInformationComplete").value(DEFAULT_IS_BILLING_INFORMATION_COMPLETE.booleanValue()))
            .andExpect(jsonPath("$.isProfileComplete").value(DEFAULT_IS_PROFILE_COMPLETE.booleanValue()));
    }

    @Test
    void getNonExistingShipper() throws Exception {
        // Get the shipper
        restShipperMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingShipper() throws Exception {
        // Initialize the database
        shipperRepository.save(shipper);

        int databaseSizeBeforeUpdate = shipperRepository.findAll().size();

        // Update the shipper
        Shipper updatedShipper = shipperRepository.findById(shipper.getId()).orElseThrow();
        updatedShipper
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .address(UPDATED_ADDRESS)
            .taxCode(UPDATED_TAX_CODE)
            .companySize(UPDATED_COMPANY_SIZE)
            .paymentType(UPDATED_PAYMENT_TYPE)
            .isApproved(UPDATED_IS_APPROVED)
            .isBillingInformationComplete(UPDATED_IS_BILLING_INFORMATION_COMPLETE)
            .isProfileComplete(UPDATED_IS_PROFILE_COMPLETE);
        ShipperDTO shipperDTO = shipperMapper.toDto(updatedShipper);

        restShipperMockMvc
            .perform(
                put(ENTITY_API_URL_ID, shipperDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(shipperDTO))
            )
            .andExpect(status().isOk());

        // Validate the Shipper in the database
        List<Shipper> shipperList = shipperRepository.findAll();
        assertThat(shipperList).hasSize(databaseSizeBeforeUpdate);
        Shipper testShipper = shipperList.get(shipperList.size() - 1);
        assertThat(testShipper.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testShipper.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testShipper.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testShipper.getTaxCode()).isEqualTo(UPDATED_TAX_CODE);
        assertThat(testShipper.getCompanySize()).isEqualTo(UPDATED_COMPANY_SIZE);
        assertThat(testShipper.getPaymentType()).isEqualTo(UPDATED_PAYMENT_TYPE);
        assertThat(testShipper.getIsApproved()).isEqualTo(UPDATED_IS_APPROVED);
        assertThat(testShipper.getIsBillingInformationComplete()).isEqualTo(UPDATED_IS_BILLING_INFORMATION_COMPLETE);
        assertThat(testShipper.getIsProfileComplete()).isEqualTo(UPDATED_IS_PROFILE_COMPLETE);
    }

    @Test
    void putNonExistingShipper() throws Exception {
        int databaseSizeBeforeUpdate = shipperRepository.findAll().size();
        shipper.setId(longCount.incrementAndGet());

        // Create the Shipper
        ShipperDTO shipperDTO = shipperMapper.toDto(shipper);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restShipperMockMvc
            .perform(
                put(ENTITY_API_URL_ID, shipperDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(shipperDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Shipper in the database
        List<Shipper> shipperList = shipperRepository.findAll();
        assertThat(shipperList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchShipper() throws Exception {
        int databaseSizeBeforeUpdate = shipperRepository.findAll().size();
        shipper.setId(longCount.incrementAndGet());

        // Create the Shipper
        ShipperDTO shipperDTO = shipperMapper.toDto(shipper);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restShipperMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(shipperDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Shipper in the database
        List<Shipper> shipperList = shipperRepository.findAll();
        assertThat(shipperList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamShipper() throws Exception {
        int databaseSizeBeforeUpdate = shipperRepository.findAll().size();
        shipper.setId(longCount.incrementAndGet());

        // Create the Shipper
        ShipperDTO shipperDTO = shipperMapper.toDto(shipper);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restShipperMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(shipperDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Shipper in the database
        List<Shipper> shipperList = shipperRepository.findAll();
        assertThat(shipperList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateShipperWithPatch() throws Exception {
        // Initialize the database
        shipperRepository.save(shipper);

        int databaseSizeBeforeUpdate = shipperRepository.findAll().size();

        // Update the shipper using partial update
        Shipper partialUpdatedShipper = new Shipper();
        partialUpdatedShipper.setId(shipper.getId());

        partialUpdatedShipper
            .name(UPDATED_NAME)
            .companySize(UPDATED_COMPANY_SIZE)
            .isBillingInformationComplete(UPDATED_IS_BILLING_INFORMATION_COMPLETE);

        restShipperMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedShipper.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedShipper))
            )
            .andExpect(status().isOk());

        // Validate the Shipper in the database
        List<Shipper> shipperList = shipperRepository.findAll();
        assertThat(shipperList).hasSize(databaseSizeBeforeUpdate);
        Shipper testShipper = shipperList.get(shipperList.size() - 1);
        assertThat(testShipper.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testShipper.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testShipper.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testShipper.getTaxCode()).isEqualTo(DEFAULT_TAX_CODE);
        assertThat(testShipper.getCompanySize()).isEqualTo(UPDATED_COMPANY_SIZE);
        assertThat(testShipper.getPaymentType()).isEqualTo(DEFAULT_PAYMENT_TYPE);
        assertThat(testShipper.getIsApproved()).isEqualTo(DEFAULT_IS_APPROVED);
        assertThat(testShipper.getIsBillingInformationComplete()).isEqualTo(UPDATED_IS_BILLING_INFORMATION_COMPLETE);
        assertThat(testShipper.getIsProfileComplete()).isEqualTo(DEFAULT_IS_PROFILE_COMPLETE);
    }

    @Test
    void fullUpdateShipperWithPatch() throws Exception {
        // Initialize the database
        shipperRepository.save(shipper);

        int databaseSizeBeforeUpdate = shipperRepository.findAll().size();

        // Update the shipper using partial update
        Shipper partialUpdatedShipper = new Shipper();
        partialUpdatedShipper.setId(shipper.getId());

        partialUpdatedShipper
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .address(UPDATED_ADDRESS)
            .taxCode(UPDATED_TAX_CODE)
            .companySize(UPDATED_COMPANY_SIZE)
            .paymentType(UPDATED_PAYMENT_TYPE)
            .isApproved(UPDATED_IS_APPROVED)
            .isBillingInformationComplete(UPDATED_IS_BILLING_INFORMATION_COMPLETE)
            .isProfileComplete(UPDATED_IS_PROFILE_COMPLETE);

        restShipperMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedShipper.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedShipper))
            )
            .andExpect(status().isOk());

        // Validate the Shipper in the database
        List<Shipper> shipperList = shipperRepository.findAll();
        assertThat(shipperList).hasSize(databaseSizeBeforeUpdate);
        Shipper testShipper = shipperList.get(shipperList.size() - 1);
        assertThat(testShipper.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testShipper.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testShipper.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testShipper.getTaxCode()).isEqualTo(UPDATED_TAX_CODE);
        assertThat(testShipper.getCompanySize()).isEqualTo(UPDATED_COMPANY_SIZE);
        assertThat(testShipper.getPaymentType()).isEqualTo(UPDATED_PAYMENT_TYPE);
        assertThat(testShipper.getIsApproved()).isEqualTo(UPDATED_IS_APPROVED);
        assertThat(testShipper.getIsBillingInformationComplete()).isEqualTo(UPDATED_IS_BILLING_INFORMATION_COMPLETE);
        assertThat(testShipper.getIsProfileComplete()).isEqualTo(UPDATED_IS_PROFILE_COMPLETE);
    }

    @Test
    void patchNonExistingShipper() throws Exception {
        int databaseSizeBeforeUpdate = shipperRepository.findAll().size();
        shipper.setId(longCount.incrementAndGet());

        // Create the Shipper
        ShipperDTO shipperDTO = shipperMapper.toDto(shipper);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restShipperMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, shipperDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(shipperDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Shipper in the database
        List<Shipper> shipperList = shipperRepository.findAll();
        assertThat(shipperList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchShipper() throws Exception {
        int databaseSizeBeforeUpdate = shipperRepository.findAll().size();
        shipper.setId(longCount.incrementAndGet());

        // Create the Shipper
        ShipperDTO shipperDTO = shipperMapper.toDto(shipper);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restShipperMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(shipperDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Shipper in the database
        List<Shipper> shipperList = shipperRepository.findAll();
        assertThat(shipperList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamShipper() throws Exception {
        int databaseSizeBeforeUpdate = shipperRepository.findAll().size();
        shipper.setId(longCount.incrementAndGet());

        // Create the Shipper
        ShipperDTO shipperDTO = shipperMapper.toDto(shipper);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restShipperMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(shipperDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Shipper in the database
        List<Shipper> shipperList = shipperRepository.findAll();
        assertThat(shipperList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteShipper() throws Exception {
        // Initialize the database
        shipperRepository.save(shipper);

        int databaseSizeBeforeDelete = shipperRepository.findAll().size();

        // Delete the shipper
        restShipperMockMvc
            .perform(delete(ENTITY_API_URL_ID, shipper.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Shipper> shipperList = shipperRepository.findAll();
        assertThat(shipperList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

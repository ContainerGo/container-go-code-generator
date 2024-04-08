package vn.containergo.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static vn.containergo.domain.ShipperAsserts.*;
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
import vn.containergo.domain.Shipper;
import vn.containergo.domain.enumeration.ContractType;
import vn.containergo.domain.enumeration.PaymentType;
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

    private static final PaymentType DEFAULT_PAYMENT_TYPE = PaymentType.CASH_ON_DELIVERY;
    private static final PaymentType UPDATED_PAYMENT_TYPE = PaymentType.END_OF_MONTH;

    private static final ContractType DEFAULT_CONTRACT_TYPE = ContractType.SIGNED_CONTRACT;
    private static final ContractType UPDATED_CONTRACT_TYPE = ContractType.INDIVIDUAL;

    private static final Instant DEFAULT_CONTRACT_VALID_UNTIL = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CONTRACT_VALID_UNTIL = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_IS_APPROVED = false;
    private static final Boolean UPDATED_IS_APPROVED = true;

    private static final Boolean DEFAULT_IS_BILLING_INFORMATION_COMPLETE = false;
    private static final Boolean UPDATED_IS_BILLING_INFORMATION_COMPLETE = true;

    private static final Boolean DEFAULT_IS_PROFILE_COMPLETE = false;
    private static final Boolean UPDATED_IS_PROFILE_COMPLETE = true;

    private static final String ENTITY_API_URL = "/api/shippers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

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
            .contractType(DEFAULT_CONTRACT_TYPE)
            .contractValidUntil(DEFAULT_CONTRACT_VALID_UNTIL)
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
            .contractType(UPDATED_CONTRACT_TYPE)
            .contractValidUntil(UPDATED_CONTRACT_VALID_UNTIL)
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
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Shipper
        ShipperDTO shipperDTO = shipperMapper.toDto(shipper);
        var returnedShipperDTO = om.readValue(
            restShipperMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(shipperDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ShipperDTO.class
        );

        // Validate the Shipper in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedShipper = shipperMapper.toEntity(returnedShipperDTO);
        assertShipperUpdatableFieldsEquals(returnedShipper, getPersistedShipper(returnedShipper));
    }

    @Test
    void createShipperWithExistingId() throws Exception {
        // Create the Shipper with an existing ID
        shipper.setId(UUID.randomUUID());
        ShipperDTO shipperDTO = shipperMapper.toDto(shipper);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restShipperMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(shipperDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Shipper in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    void checkCodeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        shipper.setCode(null);

        // Create the Shipper, which fails.
        ShipperDTO shipperDTO = shipperMapper.toDto(shipper);

        restShipperMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(shipperDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        shipper.setName(null);

        // Create the Shipper, which fails.
        ShipperDTO shipperDTO = shipperMapper.toDto(shipper);

        restShipperMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(shipperDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkAddressIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        shipper.setAddress(null);

        // Create the Shipper, which fails.
        ShipperDTO shipperDTO = shipperMapper.toDto(shipper);

        restShipperMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(shipperDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkPaymentTypeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        shipper.setPaymentType(null);

        // Create the Shipper, which fails.
        ShipperDTO shipperDTO = shipperMapper.toDto(shipper);

        restShipperMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(shipperDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkContractTypeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        shipper.setContractType(null);

        // Create the Shipper, which fails.
        ShipperDTO shipperDTO = shipperMapper.toDto(shipper);

        restShipperMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(shipperDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void getAllShippers() throws Exception {
        // Initialize the database
        shipper.setId(UUID.randomUUID());
        shipperRepository.save(shipper);

        // Get all the shipperList
        restShipperMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(shipper.getId().toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].taxCode").value(hasItem(DEFAULT_TAX_CODE)))
            .andExpect(jsonPath("$.[*].companySize").value(hasItem(DEFAULT_COMPANY_SIZE)))
            .andExpect(jsonPath("$.[*].paymentType").value(hasItem(DEFAULT_PAYMENT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].contractType").value(hasItem(DEFAULT_CONTRACT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].contractValidUntil").value(hasItem(DEFAULT_CONTRACT_VALID_UNTIL.toString())))
            .andExpect(jsonPath("$.[*].isApproved").value(hasItem(DEFAULT_IS_APPROVED.booleanValue())))
            .andExpect(
                jsonPath("$.[*].isBillingInformationComplete").value(hasItem(DEFAULT_IS_BILLING_INFORMATION_COMPLETE.booleanValue()))
            )
            .andExpect(jsonPath("$.[*].isProfileComplete").value(hasItem(DEFAULT_IS_PROFILE_COMPLETE.booleanValue())));
    }

    @Test
    void getShipper() throws Exception {
        // Initialize the database
        shipper.setId(UUID.randomUUID());
        shipperRepository.save(shipper);

        // Get the shipper
        restShipperMockMvc
            .perform(get(ENTITY_API_URL_ID, shipper.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(shipper.getId().toString()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.taxCode").value(DEFAULT_TAX_CODE))
            .andExpect(jsonPath("$.companySize").value(DEFAULT_COMPANY_SIZE))
            .andExpect(jsonPath("$.paymentType").value(DEFAULT_PAYMENT_TYPE.toString()))
            .andExpect(jsonPath("$.contractType").value(DEFAULT_CONTRACT_TYPE.toString()))
            .andExpect(jsonPath("$.contractValidUntil").value(DEFAULT_CONTRACT_VALID_UNTIL.toString()))
            .andExpect(jsonPath("$.isApproved").value(DEFAULT_IS_APPROVED.booleanValue()))
            .andExpect(jsonPath("$.isBillingInformationComplete").value(DEFAULT_IS_BILLING_INFORMATION_COMPLETE.booleanValue()))
            .andExpect(jsonPath("$.isProfileComplete").value(DEFAULT_IS_PROFILE_COMPLETE.booleanValue()));
    }

    @Test
    void getNonExistingShipper() throws Exception {
        // Get the shipper
        restShipperMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    void putExistingShipper() throws Exception {
        // Initialize the database
        shipper.setId(UUID.randomUUID());
        shipperRepository.save(shipper);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the shipper
        Shipper updatedShipper = shipperRepository.findById(shipper.getId()).orElseThrow();
        updatedShipper
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .address(UPDATED_ADDRESS)
            .taxCode(UPDATED_TAX_CODE)
            .companySize(UPDATED_COMPANY_SIZE)
            .paymentType(UPDATED_PAYMENT_TYPE)
            .contractType(UPDATED_CONTRACT_TYPE)
            .contractValidUntil(UPDATED_CONTRACT_VALID_UNTIL)
            .isApproved(UPDATED_IS_APPROVED)
            .isBillingInformationComplete(UPDATED_IS_BILLING_INFORMATION_COMPLETE)
            .isProfileComplete(UPDATED_IS_PROFILE_COMPLETE);
        ShipperDTO shipperDTO = shipperMapper.toDto(updatedShipper);

        restShipperMockMvc
            .perform(
                put(ENTITY_API_URL_ID, shipperDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(shipperDTO))
            )
            .andExpect(status().isOk());

        // Validate the Shipper in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedShipperToMatchAllProperties(updatedShipper);
    }

    @Test
    void putNonExistingShipper() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        shipper.setId(UUID.randomUUID());

        // Create the Shipper
        ShipperDTO shipperDTO = shipperMapper.toDto(shipper);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restShipperMockMvc
            .perform(
                put(ENTITY_API_URL_ID, shipperDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(shipperDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Shipper in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchShipper() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        shipper.setId(UUID.randomUUID());

        // Create the Shipper
        ShipperDTO shipperDTO = shipperMapper.toDto(shipper);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restShipperMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(shipperDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Shipper in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamShipper() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        shipper.setId(UUID.randomUUID());

        // Create the Shipper
        ShipperDTO shipperDTO = shipperMapper.toDto(shipper);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restShipperMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(shipperDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Shipper in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateShipperWithPatch() throws Exception {
        // Initialize the database
        shipper.setId(UUID.randomUUID());
        shipperRepository.save(shipper);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the shipper using partial update
        Shipper partialUpdatedShipper = new Shipper();
        partialUpdatedShipper.setId(shipper.getId());

        partialUpdatedShipper
            .name(UPDATED_NAME)
            .address(UPDATED_ADDRESS)
            .taxCode(UPDATED_TAX_CODE)
            .paymentType(UPDATED_PAYMENT_TYPE)
            .contractType(UPDATED_CONTRACT_TYPE)
            .contractValidUntil(UPDATED_CONTRACT_VALID_UNTIL);

        restShipperMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedShipper.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedShipper))
            )
            .andExpect(status().isOk());

        // Validate the Shipper in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertShipperUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedShipper, shipper), getPersistedShipper(shipper));
    }

    @Test
    void fullUpdateShipperWithPatch() throws Exception {
        // Initialize the database
        shipper.setId(UUID.randomUUID());
        shipperRepository.save(shipper);

        long databaseSizeBeforeUpdate = getRepositoryCount();

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
            .contractType(UPDATED_CONTRACT_TYPE)
            .contractValidUntil(UPDATED_CONTRACT_VALID_UNTIL)
            .isApproved(UPDATED_IS_APPROVED)
            .isBillingInformationComplete(UPDATED_IS_BILLING_INFORMATION_COMPLETE)
            .isProfileComplete(UPDATED_IS_PROFILE_COMPLETE);

        restShipperMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedShipper.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedShipper))
            )
            .andExpect(status().isOk());

        // Validate the Shipper in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertShipperUpdatableFieldsEquals(partialUpdatedShipper, getPersistedShipper(partialUpdatedShipper));
    }

    @Test
    void patchNonExistingShipper() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        shipper.setId(UUID.randomUUID());

        // Create the Shipper
        ShipperDTO shipperDTO = shipperMapper.toDto(shipper);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restShipperMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, shipperDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(shipperDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Shipper in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchShipper() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        shipper.setId(UUID.randomUUID());

        // Create the Shipper
        ShipperDTO shipperDTO = shipperMapper.toDto(shipper);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restShipperMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(shipperDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Shipper in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamShipper() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        shipper.setId(UUID.randomUUID());

        // Create the Shipper
        ShipperDTO shipperDTO = shipperMapper.toDto(shipper);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restShipperMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(shipperDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Shipper in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteShipper() throws Exception {
        // Initialize the database
        shipper.setId(UUID.randomUUID());
        shipperRepository.save(shipper);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the shipper
        restShipperMockMvc
            .perform(delete(ENTITY_API_URL_ID, shipper.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return shipperRepository.count();
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

    protected Shipper getPersistedShipper(Shipper shipper) {
        return shipperRepository.findById(shipper.getId()).orElseThrow();
    }

    protected void assertPersistedShipperToMatchAllProperties(Shipper expectedShipper) {
        assertShipperAllPropertiesEquals(expectedShipper, getPersistedShipper(expectedShipper));
    }

    protected void assertPersistedShipperToMatchUpdatableProperties(Shipper expectedShipper) {
        assertShipperAllUpdatablePropertiesEquals(expectedShipper, getPersistedShipper(expectedShipper));
    }
}

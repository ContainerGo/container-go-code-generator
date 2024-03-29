package vn.containergo.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static vn.containergo.domain.CarrierAccountAsserts.*;
import static vn.containergo.web.rest.TestUtil.createUpdateProxyForBean;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import vn.containergo.domain.CarrierAccount;
import vn.containergo.repository.CarrierAccountRepository;
import vn.containergo.service.dto.CarrierAccountDTO;
import vn.containergo.service.mapper.CarrierAccountMapper;

/**
 * Integration tests for the {@link CarrierAccountResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CarrierAccountResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/carrier-accounts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private CarrierAccountRepository carrierAccountRepository;

    @Autowired
    private CarrierAccountMapper carrierAccountMapper;

    @Autowired
    private MockMvc restCarrierAccountMockMvc;

    private CarrierAccount carrierAccount;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CarrierAccount createEntity() {
        CarrierAccount carrierAccount = new CarrierAccount()
            .name(DEFAULT_NAME)
            .phone(DEFAULT_PHONE)
            .email(DEFAULT_EMAIL)
            .address(DEFAULT_ADDRESS);
        // Add required entity
        Carrier carrier;
        carrier = CarrierResourceIT.createEntity();
        carrier.setId("fixed-id-for-tests");
        carrierAccount.setCarrier(carrier);
        return carrierAccount;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CarrierAccount createUpdatedEntity() {
        CarrierAccount carrierAccount = new CarrierAccount()
            .name(UPDATED_NAME)
            .phone(UPDATED_PHONE)
            .email(UPDATED_EMAIL)
            .address(UPDATED_ADDRESS);
        // Add required entity
        Carrier carrier;
        carrier = CarrierResourceIT.createUpdatedEntity();
        carrier.setId("fixed-id-for-tests");
        carrierAccount.setCarrier(carrier);
        return carrierAccount;
    }

    @BeforeEach
    public void initTest() {
        carrierAccountRepository.deleteAll();
        carrierAccount = createEntity();
    }

    @Test
    void createCarrierAccount() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the CarrierAccount
        CarrierAccountDTO carrierAccountDTO = carrierAccountMapper.toDto(carrierAccount);
        var returnedCarrierAccountDTO = om.readValue(
            restCarrierAccountMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(carrierAccountDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            CarrierAccountDTO.class
        );

        // Validate the CarrierAccount in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedCarrierAccount = carrierAccountMapper.toEntity(returnedCarrierAccountDTO);
        assertCarrierAccountUpdatableFieldsEquals(returnedCarrierAccount, getPersistedCarrierAccount(returnedCarrierAccount));
    }

    @Test
    void createCarrierAccountWithExistingId() throws Exception {
        // Create the CarrierAccount with an existing ID
        carrierAccount.setId(1L);
        CarrierAccountDTO carrierAccountDTO = carrierAccountMapper.toDto(carrierAccount);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCarrierAccountMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(carrierAccountDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CarrierAccount in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    void checkNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        carrierAccount.setName(null);

        // Create the CarrierAccount, which fails.
        CarrierAccountDTO carrierAccountDTO = carrierAccountMapper.toDto(carrierAccount);

        restCarrierAccountMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(carrierAccountDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkPhoneIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        carrierAccount.setPhone(null);

        // Create the CarrierAccount, which fails.
        CarrierAccountDTO carrierAccountDTO = carrierAccountMapper.toDto(carrierAccount);

        restCarrierAccountMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(carrierAccountDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void getAllCarrierAccounts() throws Exception {
        // Initialize the database
        carrierAccountRepository.save(carrierAccount);

        // Get all the carrierAccountList
        restCarrierAccountMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(carrierAccount.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)));
    }

    @Test
    void getCarrierAccount() throws Exception {
        // Initialize the database
        carrierAccountRepository.save(carrierAccount);

        // Get the carrierAccount
        restCarrierAccountMockMvc
            .perform(get(ENTITY_API_URL_ID, carrierAccount.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(carrierAccount.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS));
    }

    @Test
    void getNonExistingCarrierAccount() throws Exception {
        // Get the carrierAccount
        restCarrierAccountMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingCarrierAccount() throws Exception {
        // Initialize the database
        carrierAccountRepository.save(carrierAccount);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the carrierAccount
        CarrierAccount updatedCarrierAccount = carrierAccountRepository.findById(carrierAccount.getId()).orElseThrow();
        updatedCarrierAccount.name(UPDATED_NAME).phone(UPDATED_PHONE).email(UPDATED_EMAIL).address(UPDATED_ADDRESS);
        CarrierAccountDTO carrierAccountDTO = carrierAccountMapper.toDto(updatedCarrierAccount);

        restCarrierAccountMockMvc
            .perform(
                put(ENTITY_API_URL_ID, carrierAccountDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(carrierAccountDTO))
            )
            .andExpect(status().isOk());

        // Validate the CarrierAccount in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedCarrierAccountToMatchAllProperties(updatedCarrierAccount);
    }

    @Test
    void putNonExistingCarrierAccount() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        carrierAccount.setId(longCount.incrementAndGet());

        // Create the CarrierAccount
        CarrierAccountDTO carrierAccountDTO = carrierAccountMapper.toDto(carrierAccount);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCarrierAccountMockMvc
            .perform(
                put(ENTITY_API_URL_ID, carrierAccountDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(carrierAccountDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CarrierAccount in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchCarrierAccount() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        carrierAccount.setId(longCount.incrementAndGet());

        // Create the CarrierAccount
        CarrierAccountDTO carrierAccountDTO = carrierAccountMapper.toDto(carrierAccount);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCarrierAccountMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(carrierAccountDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CarrierAccount in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamCarrierAccount() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        carrierAccount.setId(longCount.incrementAndGet());

        // Create the CarrierAccount
        CarrierAccountDTO carrierAccountDTO = carrierAccountMapper.toDto(carrierAccount);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCarrierAccountMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(carrierAccountDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CarrierAccount in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateCarrierAccountWithPatch() throws Exception {
        // Initialize the database
        carrierAccountRepository.save(carrierAccount);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the carrierAccount using partial update
        CarrierAccount partialUpdatedCarrierAccount = new CarrierAccount();
        partialUpdatedCarrierAccount.setId(carrierAccount.getId());

        partialUpdatedCarrierAccount.name(UPDATED_NAME);

        restCarrierAccountMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCarrierAccount.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCarrierAccount))
            )
            .andExpect(status().isOk());

        // Validate the CarrierAccount in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCarrierAccountUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedCarrierAccount, carrierAccount),
            getPersistedCarrierAccount(carrierAccount)
        );
    }

    @Test
    void fullUpdateCarrierAccountWithPatch() throws Exception {
        // Initialize the database
        carrierAccountRepository.save(carrierAccount);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the carrierAccount using partial update
        CarrierAccount partialUpdatedCarrierAccount = new CarrierAccount();
        partialUpdatedCarrierAccount.setId(carrierAccount.getId());

        partialUpdatedCarrierAccount.name(UPDATED_NAME).phone(UPDATED_PHONE).email(UPDATED_EMAIL).address(UPDATED_ADDRESS);

        restCarrierAccountMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCarrierAccount.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCarrierAccount))
            )
            .andExpect(status().isOk());

        // Validate the CarrierAccount in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCarrierAccountUpdatableFieldsEquals(partialUpdatedCarrierAccount, getPersistedCarrierAccount(partialUpdatedCarrierAccount));
    }

    @Test
    void patchNonExistingCarrierAccount() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        carrierAccount.setId(longCount.incrementAndGet());

        // Create the CarrierAccount
        CarrierAccountDTO carrierAccountDTO = carrierAccountMapper.toDto(carrierAccount);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCarrierAccountMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, carrierAccountDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(carrierAccountDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CarrierAccount in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchCarrierAccount() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        carrierAccount.setId(longCount.incrementAndGet());

        // Create the CarrierAccount
        CarrierAccountDTO carrierAccountDTO = carrierAccountMapper.toDto(carrierAccount);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCarrierAccountMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(carrierAccountDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CarrierAccount in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamCarrierAccount() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        carrierAccount.setId(longCount.incrementAndGet());

        // Create the CarrierAccount
        CarrierAccountDTO carrierAccountDTO = carrierAccountMapper.toDto(carrierAccount);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCarrierAccountMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(carrierAccountDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CarrierAccount in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteCarrierAccount() throws Exception {
        // Initialize the database
        carrierAccountRepository.save(carrierAccount);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the carrierAccount
        restCarrierAccountMockMvc
            .perform(delete(ENTITY_API_URL_ID, carrierAccount.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return carrierAccountRepository.count();
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

    protected CarrierAccount getPersistedCarrierAccount(CarrierAccount carrierAccount) {
        return carrierAccountRepository.findById(carrierAccount.getId()).orElseThrow();
    }

    protected void assertPersistedCarrierAccountToMatchAllProperties(CarrierAccount expectedCarrierAccount) {
        assertCarrierAccountAllPropertiesEquals(expectedCarrierAccount, getPersistedCarrierAccount(expectedCarrierAccount));
    }

    protected void assertPersistedCarrierAccountToMatchUpdatableProperties(CarrierAccount expectedCarrierAccount) {
        assertCarrierAccountAllUpdatablePropertiesEquals(expectedCarrierAccount, getPersistedCarrierAccount(expectedCarrierAccount));
    }
}

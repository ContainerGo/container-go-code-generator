package vn.containergo.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static vn.containergo.domain.ShipperAccountAsserts.*;
import static vn.containergo.web.rest.TestUtil.createUpdateProxyForBean;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import vn.containergo.domain.ShipperAccount;
import vn.containergo.domain.enumeration.ShipperAccountType;
import vn.containergo.repository.ShipperAccountRepository;
import vn.containergo.service.dto.ShipperAccountDTO;
import vn.containergo.service.mapper.ShipperAccountMapper;

/**
 * Integration tests for the {@link ShipperAccountResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ShipperAccountResourceIT {

    private static final Double DEFAULT_BALANCE = 1D;
    private static final Double UPDATED_BALANCE = 2D;

    private static final ShipperAccountType DEFAULT_ACCOUNT_TYPE = ShipperAccountType.DEPOSIT;
    private static final ShipperAccountType UPDATED_ACCOUNT_TYPE = ShipperAccountType.DEPOSIT;

    private static final String ENTITY_API_URL = "/api/shipper-accounts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ShipperAccountRepository shipperAccountRepository;

    @Autowired
    private ShipperAccountMapper shipperAccountMapper;

    @Autowired
    private MockMvc restShipperAccountMockMvc;

    private ShipperAccount shipperAccount;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ShipperAccount createEntity() {
        ShipperAccount shipperAccount = new ShipperAccount().balance(DEFAULT_BALANCE).accountType(DEFAULT_ACCOUNT_TYPE);
        // Add required entity
        Shipper shipper;
        shipper = ShipperResourceIT.createEntity();
        shipper.setId("fixed-id-for-tests");
        shipperAccount.setShipper(shipper);
        return shipperAccount;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ShipperAccount createUpdatedEntity() {
        ShipperAccount shipperAccount = new ShipperAccount().balance(UPDATED_BALANCE).accountType(UPDATED_ACCOUNT_TYPE);
        // Add required entity
        Shipper shipper;
        shipper = ShipperResourceIT.createUpdatedEntity();
        shipper.setId("fixed-id-for-tests");
        shipperAccount.setShipper(shipper);
        return shipperAccount;
    }

    @BeforeEach
    public void initTest() {
        shipperAccountRepository.deleteAll();
        shipperAccount = createEntity();
    }

    @Test
    void createShipperAccount() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the ShipperAccount
        ShipperAccountDTO shipperAccountDTO = shipperAccountMapper.toDto(shipperAccount);
        var returnedShipperAccountDTO = om.readValue(
            restShipperAccountMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(shipperAccountDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ShipperAccountDTO.class
        );

        // Validate the ShipperAccount in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedShipperAccount = shipperAccountMapper.toEntity(returnedShipperAccountDTO);
        assertShipperAccountUpdatableFieldsEquals(returnedShipperAccount, getPersistedShipperAccount(returnedShipperAccount));
    }

    @Test
    void createShipperAccountWithExistingId() throws Exception {
        // Create the ShipperAccount with an existing ID
        shipperAccount.setId(UUID.randomUUID());
        ShipperAccountDTO shipperAccountDTO = shipperAccountMapper.toDto(shipperAccount);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restShipperAccountMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(shipperAccountDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ShipperAccount in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    void checkBalanceIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        shipperAccount.setBalance(null);

        // Create the ShipperAccount, which fails.
        ShipperAccountDTO shipperAccountDTO = shipperAccountMapper.toDto(shipperAccount);

        restShipperAccountMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(shipperAccountDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkAccountTypeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        shipperAccount.setAccountType(null);

        // Create the ShipperAccount, which fails.
        ShipperAccountDTO shipperAccountDTO = shipperAccountMapper.toDto(shipperAccount);

        restShipperAccountMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(shipperAccountDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void getAllShipperAccounts() throws Exception {
        // Initialize the database
        shipperAccount.setId(UUID.randomUUID());
        shipperAccountRepository.save(shipperAccount);

        // Get all the shipperAccountList
        restShipperAccountMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(shipperAccount.getId().toString())))
            .andExpect(jsonPath("$.[*].balance").value(hasItem(DEFAULT_BALANCE.doubleValue())))
            .andExpect(jsonPath("$.[*].accountType").value(hasItem(DEFAULT_ACCOUNT_TYPE.toString())));
    }

    @Test
    void getShipperAccount() throws Exception {
        // Initialize the database
        shipperAccount.setId(UUID.randomUUID());
        shipperAccountRepository.save(shipperAccount);

        // Get the shipperAccount
        restShipperAccountMockMvc
            .perform(get(ENTITY_API_URL_ID, shipperAccount.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(shipperAccount.getId().toString()))
            .andExpect(jsonPath("$.balance").value(DEFAULT_BALANCE.doubleValue()))
            .andExpect(jsonPath("$.accountType").value(DEFAULT_ACCOUNT_TYPE.toString()));
    }

    @Test
    void getNonExistingShipperAccount() throws Exception {
        // Get the shipperAccount
        restShipperAccountMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    void putExistingShipperAccount() throws Exception {
        // Initialize the database
        shipperAccount.setId(UUID.randomUUID());
        shipperAccountRepository.save(shipperAccount);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the shipperAccount
        ShipperAccount updatedShipperAccount = shipperAccountRepository.findById(shipperAccount.getId()).orElseThrow();
        updatedShipperAccount.balance(UPDATED_BALANCE).accountType(UPDATED_ACCOUNT_TYPE);
        ShipperAccountDTO shipperAccountDTO = shipperAccountMapper.toDto(updatedShipperAccount);

        restShipperAccountMockMvc
            .perform(
                put(ENTITY_API_URL_ID, shipperAccountDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(shipperAccountDTO))
            )
            .andExpect(status().isOk());

        // Validate the ShipperAccount in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedShipperAccountToMatchAllProperties(updatedShipperAccount);
    }

    @Test
    void putNonExistingShipperAccount() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        shipperAccount.setId(UUID.randomUUID());

        // Create the ShipperAccount
        ShipperAccountDTO shipperAccountDTO = shipperAccountMapper.toDto(shipperAccount);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restShipperAccountMockMvc
            .perform(
                put(ENTITY_API_URL_ID, shipperAccountDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(shipperAccountDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ShipperAccount in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchShipperAccount() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        shipperAccount.setId(UUID.randomUUID());

        // Create the ShipperAccount
        ShipperAccountDTO shipperAccountDTO = shipperAccountMapper.toDto(shipperAccount);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restShipperAccountMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(shipperAccountDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ShipperAccount in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamShipperAccount() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        shipperAccount.setId(UUID.randomUUID());

        // Create the ShipperAccount
        ShipperAccountDTO shipperAccountDTO = shipperAccountMapper.toDto(shipperAccount);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restShipperAccountMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(shipperAccountDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ShipperAccount in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateShipperAccountWithPatch() throws Exception {
        // Initialize the database
        shipperAccount.setId(UUID.randomUUID());
        shipperAccountRepository.save(shipperAccount);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the shipperAccount using partial update
        ShipperAccount partialUpdatedShipperAccount = new ShipperAccount();
        partialUpdatedShipperAccount.setId(shipperAccount.getId());

        partialUpdatedShipperAccount.accountType(UPDATED_ACCOUNT_TYPE);

        restShipperAccountMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedShipperAccount.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedShipperAccount))
            )
            .andExpect(status().isOk());

        // Validate the ShipperAccount in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertShipperAccountUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedShipperAccount, shipperAccount),
            getPersistedShipperAccount(shipperAccount)
        );
    }

    @Test
    void fullUpdateShipperAccountWithPatch() throws Exception {
        // Initialize the database
        shipperAccount.setId(UUID.randomUUID());
        shipperAccountRepository.save(shipperAccount);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the shipperAccount using partial update
        ShipperAccount partialUpdatedShipperAccount = new ShipperAccount();
        partialUpdatedShipperAccount.setId(shipperAccount.getId());

        partialUpdatedShipperAccount.balance(UPDATED_BALANCE).accountType(UPDATED_ACCOUNT_TYPE);

        restShipperAccountMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedShipperAccount.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedShipperAccount))
            )
            .andExpect(status().isOk());

        // Validate the ShipperAccount in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertShipperAccountUpdatableFieldsEquals(partialUpdatedShipperAccount, getPersistedShipperAccount(partialUpdatedShipperAccount));
    }

    @Test
    void patchNonExistingShipperAccount() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        shipperAccount.setId(UUID.randomUUID());

        // Create the ShipperAccount
        ShipperAccountDTO shipperAccountDTO = shipperAccountMapper.toDto(shipperAccount);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restShipperAccountMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, shipperAccountDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(shipperAccountDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ShipperAccount in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchShipperAccount() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        shipperAccount.setId(UUID.randomUUID());

        // Create the ShipperAccount
        ShipperAccountDTO shipperAccountDTO = shipperAccountMapper.toDto(shipperAccount);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restShipperAccountMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(shipperAccountDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ShipperAccount in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamShipperAccount() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        shipperAccount.setId(UUID.randomUUID());

        // Create the ShipperAccount
        ShipperAccountDTO shipperAccountDTO = shipperAccountMapper.toDto(shipperAccount);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restShipperAccountMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(shipperAccountDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ShipperAccount in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteShipperAccount() throws Exception {
        // Initialize the database
        shipperAccount.setId(UUID.randomUUID());
        shipperAccountRepository.save(shipperAccount);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the shipperAccount
        restShipperAccountMockMvc
            .perform(delete(ENTITY_API_URL_ID, shipperAccount.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return shipperAccountRepository.count();
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

    protected ShipperAccount getPersistedShipperAccount(ShipperAccount shipperAccount) {
        return shipperAccountRepository.findById(shipperAccount.getId()).orElseThrow();
    }

    protected void assertPersistedShipperAccountToMatchAllProperties(ShipperAccount expectedShipperAccount) {
        assertShipperAccountAllPropertiesEquals(expectedShipperAccount, getPersistedShipperAccount(expectedShipperAccount));
    }

    protected void assertPersistedShipperAccountToMatchUpdatableProperties(ShipperAccount expectedShipperAccount) {
        assertShipperAccountAllUpdatablePropertiesEquals(expectedShipperAccount, getPersistedShipperAccount(expectedShipperAccount));
    }
}

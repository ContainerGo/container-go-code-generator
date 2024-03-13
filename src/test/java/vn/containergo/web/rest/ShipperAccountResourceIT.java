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
import vn.containergo.domain.ShipperAccount;
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

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/shipper-accounts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

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
        ShipperAccount shipperAccount = new ShipperAccount()
            .name(DEFAULT_NAME)
            .phone(DEFAULT_PHONE)
            .email(DEFAULT_EMAIL)
            .address(DEFAULT_ADDRESS);
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
        ShipperAccount shipperAccount = new ShipperAccount()
            .name(UPDATED_NAME)
            .phone(UPDATED_PHONE)
            .email(UPDATED_EMAIL)
            .address(UPDATED_ADDRESS);
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
        int databaseSizeBeforeCreate = shipperAccountRepository.findAll().size();
        // Create the ShipperAccount
        ShipperAccountDTO shipperAccountDTO = shipperAccountMapper.toDto(shipperAccount);
        restShipperAccountMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(shipperAccountDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ShipperAccount in the database
        List<ShipperAccount> shipperAccountList = shipperAccountRepository.findAll();
        assertThat(shipperAccountList).hasSize(databaseSizeBeforeCreate + 1);
        ShipperAccount testShipperAccount = shipperAccountList.get(shipperAccountList.size() - 1);
        assertThat(testShipperAccount.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testShipperAccount.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testShipperAccount.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testShipperAccount.getAddress()).isEqualTo(DEFAULT_ADDRESS);
    }

    @Test
    void createShipperAccountWithExistingId() throws Exception {
        // Create the ShipperAccount with an existing ID
        shipperAccount.setId(1L);
        ShipperAccountDTO shipperAccountDTO = shipperAccountMapper.toDto(shipperAccount);

        int databaseSizeBeforeCreate = shipperAccountRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restShipperAccountMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(shipperAccountDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ShipperAccount in the database
        List<ShipperAccount> shipperAccountList = shipperAccountRepository.findAll();
        assertThat(shipperAccountList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = shipperAccountRepository.findAll().size();
        // set the field null
        shipperAccount.setName(null);

        // Create the ShipperAccount, which fails.
        ShipperAccountDTO shipperAccountDTO = shipperAccountMapper.toDto(shipperAccount);

        restShipperAccountMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(shipperAccountDTO))
            )
            .andExpect(status().isBadRequest());

        List<ShipperAccount> shipperAccountList = shipperAccountRepository.findAll();
        assertThat(shipperAccountList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkPhoneIsRequired() throws Exception {
        int databaseSizeBeforeTest = shipperAccountRepository.findAll().size();
        // set the field null
        shipperAccount.setPhone(null);

        // Create the ShipperAccount, which fails.
        ShipperAccountDTO shipperAccountDTO = shipperAccountMapper.toDto(shipperAccount);

        restShipperAccountMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(shipperAccountDTO))
            )
            .andExpect(status().isBadRequest());

        List<ShipperAccount> shipperAccountList = shipperAccountRepository.findAll();
        assertThat(shipperAccountList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void getAllShipperAccounts() throws Exception {
        // Initialize the database
        shipperAccountRepository.save(shipperAccount);

        // Get all the shipperAccountList
        restShipperAccountMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(shipperAccount.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)));
    }

    @Test
    void getShipperAccount() throws Exception {
        // Initialize the database
        shipperAccountRepository.save(shipperAccount);

        // Get the shipperAccount
        restShipperAccountMockMvc
            .perform(get(ENTITY_API_URL_ID, shipperAccount.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(shipperAccount.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS));
    }

    @Test
    void getNonExistingShipperAccount() throws Exception {
        // Get the shipperAccount
        restShipperAccountMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingShipperAccount() throws Exception {
        // Initialize the database
        shipperAccountRepository.save(shipperAccount);

        int databaseSizeBeforeUpdate = shipperAccountRepository.findAll().size();

        // Update the shipperAccount
        ShipperAccount updatedShipperAccount = shipperAccountRepository.findById(shipperAccount.getId()).orElseThrow();
        updatedShipperAccount.name(UPDATED_NAME).phone(UPDATED_PHONE).email(UPDATED_EMAIL).address(UPDATED_ADDRESS);
        ShipperAccountDTO shipperAccountDTO = shipperAccountMapper.toDto(updatedShipperAccount);

        restShipperAccountMockMvc
            .perform(
                put(ENTITY_API_URL_ID, shipperAccountDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(shipperAccountDTO))
            )
            .andExpect(status().isOk());

        // Validate the ShipperAccount in the database
        List<ShipperAccount> shipperAccountList = shipperAccountRepository.findAll();
        assertThat(shipperAccountList).hasSize(databaseSizeBeforeUpdate);
        ShipperAccount testShipperAccount = shipperAccountList.get(shipperAccountList.size() - 1);
        assertThat(testShipperAccount.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testShipperAccount.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testShipperAccount.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testShipperAccount.getAddress()).isEqualTo(UPDATED_ADDRESS);
    }

    @Test
    void putNonExistingShipperAccount() throws Exception {
        int databaseSizeBeforeUpdate = shipperAccountRepository.findAll().size();
        shipperAccount.setId(longCount.incrementAndGet());

        // Create the ShipperAccount
        ShipperAccountDTO shipperAccountDTO = shipperAccountMapper.toDto(shipperAccount);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restShipperAccountMockMvc
            .perform(
                put(ENTITY_API_URL_ID, shipperAccountDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(shipperAccountDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ShipperAccount in the database
        List<ShipperAccount> shipperAccountList = shipperAccountRepository.findAll();
        assertThat(shipperAccountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchShipperAccount() throws Exception {
        int databaseSizeBeforeUpdate = shipperAccountRepository.findAll().size();
        shipperAccount.setId(longCount.incrementAndGet());

        // Create the ShipperAccount
        ShipperAccountDTO shipperAccountDTO = shipperAccountMapper.toDto(shipperAccount);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restShipperAccountMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(shipperAccountDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ShipperAccount in the database
        List<ShipperAccount> shipperAccountList = shipperAccountRepository.findAll();
        assertThat(shipperAccountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamShipperAccount() throws Exception {
        int databaseSizeBeforeUpdate = shipperAccountRepository.findAll().size();
        shipperAccount.setId(longCount.incrementAndGet());

        // Create the ShipperAccount
        ShipperAccountDTO shipperAccountDTO = shipperAccountMapper.toDto(shipperAccount);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restShipperAccountMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(shipperAccountDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ShipperAccount in the database
        List<ShipperAccount> shipperAccountList = shipperAccountRepository.findAll();
        assertThat(shipperAccountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateShipperAccountWithPatch() throws Exception {
        // Initialize the database
        shipperAccountRepository.save(shipperAccount);

        int databaseSizeBeforeUpdate = shipperAccountRepository.findAll().size();

        // Update the shipperAccount using partial update
        ShipperAccount partialUpdatedShipperAccount = new ShipperAccount();
        partialUpdatedShipperAccount.setId(shipperAccount.getId());

        partialUpdatedShipperAccount.name(UPDATED_NAME).phone(UPDATED_PHONE).address(UPDATED_ADDRESS);

        restShipperAccountMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedShipperAccount.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedShipperAccount))
            )
            .andExpect(status().isOk());

        // Validate the ShipperAccount in the database
        List<ShipperAccount> shipperAccountList = shipperAccountRepository.findAll();
        assertThat(shipperAccountList).hasSize(databaseSizeBeforeUpdate);
        ShipperAccount testShipperAccount = shipperAccountList.get(shipperAccountList.size() - 1);
        assertThat(testShipperAccount.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testShipperAccount.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testShipperAccount.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testShipperAccount.getAddress()).isEqualTo(UPDATED_ADDRESS);
    }

    @Test
    void fullUpdateShipperAccountWithPatch() throws Exception {
        // Initialize the database
        shipperAccountRepository.save(shipperAccount);

        int databaseSizeBeforeUpdate = shipperAccountRepository.findAll().size();

        // Update the shipperAccount using partial update
        ShipperAccount partialUpdatedShipperAccount = new ShipperAccount();
        partialUpdatedShipperAccount.setId(shipperAccount.getId());

        partialUpdatedShipperAccount.name(UPDATED_NAME).phone(UPDATED_PHONE).email(UPDATED_EMAIL).address(UPDATED_ADDRESS);

        restShipperAccountMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedShipperAccount.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedShipperAccount))
            )
            .andExpect(status().isOk());

        // Validate the ShipperAccount in the database
        List<ShipperAccount> shipperAccountList = shipperAccountRepository.findAll();
        assertThat(shipperAccountList).hasSize(databaseSizeBeforeUpdate);
        ShipperAccount testShipperAccount = shipperAccountList.get(shipperAccountList.size() - 1);
        assertThat(testShipperAccount.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testShipperAccount.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testShipperAccount.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testShipperAccount.getAddress()).isEqualTo(UPDATED_ADDRESS);
    }

    @Test
    void patchNonExistingShipperAccount() throws Exception {
        int databaseSizeBeforeUpdate = shipperAccountRepository.findAll().size();
        shipperAccount.setId(longCount.incrementAndGet());

        // Create the ShipperAccount
        ShipperAccountDTO shipperAccountDTO = shipperAccountMapper.toDto(shipperAccount);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restShipperAccountMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, shipperAccountDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(shipperAccountDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ShipperAccount in the database
        List<ShipperAccount> shipperAccountList = shipperAccountRepository.findAll();
        assertThat(shipperAccountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchShipperAccount() throws Exception {
        int databaseSizeBeforeUpdate = shipperAccountRepository.findAll().size();
        shipperAccount.setId(longCount.incrementAndGet());

        // Create the ShipperAccount
        ShipperAccountDTO shipperAccountDTO = shipperAccountMapper.toDto(shipperAccount);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restShipperAccountMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(shipperAccountDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ShipperAccount in the database
        List<ShipperAccount> shipperAccountList = shipperAccountRepository.findAll();
        assertThat(shipperAccountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamShipperAccount() throws Exception {
        int databaseSizeBeforeUpdate = shipperAccountRepository.findAll().size();
        shipperAccount.setId(longCount.incrementAndGet());

        // Create the ShipperAccount
        ShipperAccountDTO shipperAccountDTO = shipperAccountMapper.toDto(shipperAccount);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restShipperAccountMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(shipperAccountDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ShipperAccount in the database
        List<ShipperAccount> shipperAccountList = shipperAccountRepository.findAll();
        assertThat(shipperAccountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteShipperAccount() throws Exception {
        // Initialize the database
        shipperAccountRepository.save(shipperAccount);

        int databaseSizeBeforeDelete = shipperAccountRepository.findAll().size();

        // Delete the shipperAccount
        restShipperAccountMockMvc
            .perform(delete(ENTITY_API_URL_ID, shipperAccount.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ShipperAccount> shipperAccountList = shipperAccountRepository.findAll();
        assertThat(shipperAccountList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

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
import vn.containergo.domain.ShipperPerson;
import vn.containergo.repository.ShipperPersonRepository;
import vn.containergo.service.dto.ShipperPersonDTO;
import vn.containergo.service.mapper.ShipperPersonMapper;

/**
 * Integration tests for the {@link ShipperPersonResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ShipperPersonResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/shipper-people";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ShipperPersonRepository shipperPersonRepository;

    @Autowired
    private ShipperPersonMapper shipperPersonMapper;

    @Autowired
    private MockMvc restShipperPersonMockMvc;

    private ShipperPerson shipperPerson;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ShipperPerson createEntity() {
        ShipperPerson shipperPerson = new ShipperPerson()
            .name(DEFAULT_NAME)
            .phone(DEFAULT_PHONE)
            .email(DEFAULT_EMAIL)
            .address(DEFAULT_ADDRESS);
        return shipperPerson;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ShipperPerson createUpdatedEntity() {
        ShipperPerson shipperPerson = new ShipperPerson()
            .name(UPDATED_NAME)
            .phone(UPDATED_PHONE)
            .email(UPDATED_EMAIL)
            .address(UPDATED_ADDRESS);
        return shipperPerson;
    }

    @BeforeEach
    public void initTest() {
        shipperPersonRepository.deleteAll();
        shipperPerson = createEntity();
    }

    @Test
    void createShipperPerson() throws Exception {
        int databaseSizeBeforeCreate = shipperPersonRepository.findAll().size();
        // Create the ShipperPerson
        ShipperPersonDTO shipperPersonDTO = shipperPersonMapper.toDto(shipperPerson);
        restShipperPersonMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(shipperPersonDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ShipperPerson in the database
        List<ShipperPerson> shipperPersonList = shipperPersonRepository.findAll();
        assertThat(shipperPersonList).hasSize(databaseSizeBeforeCreate + 1);
        ShipperPerson testShipperPerson = shipperPersonList.get(shipperPersonList.size() - 1);
        assertThat(testShipperPerson.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testShipperPerson.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testShipperPerson.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testShipperPerson.getAddress()).isEqualTo(DEFAULT_ADDRESS);
    }

    @Test
    void createShipperPersonWithExistingId() throws Exception {
        // Create the ShipperPerson with an existing ID
        shipperPerson.setId(1L);
        ShipperPersonDTO shipperPersonDTO = shipperPersonMapper.toDto(shipperPerson);

        int databaseSizeBeforeCreate = shipperPersonRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restShipperPersonMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(shipperPersonDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ShipperPerson in the database
        List<ShipperPerson> shipperPersonList = shipperPersonRepository.findAll();
        assertThat(shipperPersonList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = shipperPersonRepository.findAll().size();
        // set the field null
        shipperPerson.setName(null);

        // Create the ShipperPerson, which fails.
        ShipperPersonDTO shipperPersonDTO = shipperPersonMapper.toDto(shipperPerson);

        restShipperPersonMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(shipperPersonDTO))
            )
            .andExpect(status().isBadRequest());

        List<ShipperPerson> shipperPersonList = shipperPersonRepository.findAll();
        assertThat(shipperPersonList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkPhoneIsRequired() throws Exception {
        int databaseSizeBeforeTest = shipperPersonRepository.findAll().size();
        // set the field null
        shipperPerson.setPhone(null);

        // Create the ShipperPerson, which fails.
        ShipperPersonDTO shipperPersonDTO = shipperPersonMapper.toDto(shipperPerson);

        restShipperPersonMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(shipperPersonDTO))
            )
            .andExpect(status().isBadRequest());

        List<ShipperPerson> shipperPersonList = shipperPersonRepository.findAll();
        assertThat(shipperPersonList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void getAllShipperPeople() throws Exception {
        // Initialize the database
        shipperPersonRepository.save(shipperPerson);

        // Get all the shipperPersonList
        restShipperPersonMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(shipperPerson.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)));
    }

    @Test
    void getShipperPerson() throws Exception {
        // Initialize the database
        shipperPersonRepository.save(shipperPerson);

        // Get the shipperPerson
        restShipperPersonMockMvc
            .perform(get(ENTITY_API_URL_ID, shipperPerson.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(shipperPerson.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS));
    }

    @Test
    void getNonExistingShipperPerson() throws Exception {
        // Get the shipperPerson
        restShipperPersonMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingShipperPerson() throws Exception {
        // Initialize the database
        shipperPersonRepository.save(shipperPerson);

        int databaseSizeBeforeUpdate = shipperPersonRepository.findAll().size();

        // Update the shipperPerson
        ShipperPerson updatedShipperPerson = shipperPersonRepository.findById(shipperPerson.getId()).orElseThrow();
        updatedShipperPerson.name(UPDATED_NAME).phone(UPDATED_PHONE).email(UPDATED_EMAIL).address(UPDATED_ADDRESS);
        ShipperPersonDTO shipperPersonDTO = shipperPersonMapper.toDto(updatedShipperPerson);

        restShipperPersonMockMvc
            .perform(
                put(ENTITY_API_URL_ID, shipperPersonDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(shipperPersonDTO))
            )
            .andExpect(status().isOk());

        // Validate the ShipperPerson in the database
        List<ShipperPerson> shipperPersonList = shipperPersonRepository.findAll();
        assertThat(shipperPersonList).hasSize(databaseSizeBeforeUpdate);
        ShipperPerson testShipperPerson = shipperPersonList.get(shipperPersonList.size() - 1);
        assertThat(testShipperPerson.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testShipperPerson.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testShipperPerson.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testShipperPerson.getAddress()).isEqualTo(UPDATED_ADDRESS);
    }

    @Test
    void putNonExistingShipperPerson() throws Exception {
        int databaseSizeBeforeUpdate = shipperPersonRepository.findAll().size();
        shipperPerson.setId(longCount.incrementAndGet());

        // Create the ShipperPerson
        ShipperPersonDTO shipperPersonDTO = shipperPersonMapper.toDto(shipperPerson);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restShipperPersonMockMvc
            .perform(
                put(ENTITY_API_URL_ID, shipperPersonDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(shipperPersonDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ShipperPerson in the database
        List<ShipperPerson> shipperPersonList = shipperPersonRepository.findAll();
        assertThat(shipperPersonList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchShipperPerson() throws Exception {
        int databaseSizeBeforeUpdate = shipperPersonRepository.findAll().size();
        shipperPerson.setId(longCount.incrementAndGet());

        // Create the ShipperPerson
        ShipperPersonDTO shipperPersonDTO = shipperPersonMapper.toDto(shipperPerson);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restShipperPersonMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(shipperPersonDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ShipperPerson in the database
        List<ShipperPerson> shipperPersonList = shipperPersonRepository.findAll();
        assertThat(shipperPersonList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamShipperPerson() throws Exception {
        int databaseSizeBeforeUpdate = shipperPersonRepository.findAll().size();
        shipperPerson.setId(longCount.incrementAndGet());

        // Create the ShipperPerson
        ShipperPersonDTO shipperPersonDTO = shipperPersonMapper.toDto(shipperPerson);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restShipperPersonMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(shipperPersonDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ShipperPerson in the database
        List<ShipperPerson> shipperPersonList = shipperPersonRepository.findAll();
        assertThat(shipperPersonList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateShipperPersonWithPatch() throws Exception {
        // Initialize the database
        shipperPersonRepository.save(shipperPerson);

        int databaseSizeBeforeUpdate = shipperPersonRepository.findAll().size();

        // Update the shipperPerson using partial update
        ShipperPerson partialUpdatedShipperPerson = new ShipperPerson();
        partialUpdatedShipperPerson.setId(shipperPerson.getId());

        restShipperPersonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedShipperPerson.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedShipperPerson))
            )
            .andExpect(status().isOk());

        // Validate the ShipperPerson in the database
        List<ShipperPerson> shipperPersonList = shipperPersonRepository.findAll();
        assertThat(shipperPersonList).hasSize(databaseSizeBeforeUpdate);
        ShipperPerson testShipperPerson = shipperPersonList.get(shipperPersonList.size() - 1);
        assertThat(testShipperPerson.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testShipperPerson.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testShipperPerson.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testShipperPerson.getAddress()).isEqualTo(DEFAULT_ADDRESS);
    }

    @Test
    void fullUpdateShipperPersonWithPatch() throws Exception {
        // Initialize the database
        shipperPersonRepository.save(shipperPerson);

        int databaseSizeBeforeUpdate = shipperPersonRepository.findAll().size();

        // Update the shipperPerson using partial update
        ShipperPerson partialUpdatedShipperPerson = new ShipperPerson();
        partialUpdatedShipperPerson.setId(shipperPerson.getId());

        partialUpdatedShipperPerson.name(UPDATED_NAME).phone(UPDATED_PHONE).email(UPDATED_EMAIL).address(UPDATED_ADDRESS);

        restShipperPersonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedShipperPerson.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedShipperPerson))
            )
            .andExpect(status().isOk());

        // Validate the ShipperPerson in the database
        List<ShipperPerson> shipperPersonList = shipperPersonRepository.findAll();
        assertThat(shipperPersonList).hasSize(databaseSizeBeforeUpdate);
        ShipperPerson testShipperPerson = shipperPersonList.get(shipperPersonList.size() - 1);
        assertThat(testShipperPerson.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testShipperPerson.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testShipperPerson.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testShipperPerson.getAddress()).isEqualTo(UPDATED_ADDRESS);
    }

    @Test
    void patchNonExistingShipperPerson() throws Exception {
        int databaseSizeBeforeUpdate = shipperPersonRepository.findAll().size();
        shipperPerson.setId(longCount.incrementAndGet());

        // Create the ShipperPerson
        ShipperPersonDTO shipperPersonDTO = shipperPersonMapper.toDto(shipperPerson);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restShipperPersonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, shipperPersonDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(shipperPersonDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ShipperPerson in the database
        List<ShipperPerson> shipperPersonList = shipperPersonRepository.findAll();
        assertThat(shipperPersonList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchShipperPerson() throws Exception {
        int databaseSizeBeforeUpdate = shipperPersonRepository.findAll().size();
        shipperPerson.setId(longCount.incrementAndGet());

        // Create the ShipperPerson
        ShipperPersonDTO shipperPersonDTO = shipperPersonMapper.toDto(shipperPerson);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restShipperPersonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(shipperPersonDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ShipperPerson in the database
        List<ShipperPerson> shipperPersonList = shipperPersonRepository.findAll();
        assertThat(shipperPersonList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamShipperPerson() throws Exception {
        int databaseSizeBeforeUpdate = shipperPersonRepository.findAll().size();
        shipperPerson.setId(longCount.incrementAndGet());

        // Create the ShipperPerson
        ShipperPersonDTO shipperPersonDTO = shipperPersonMapper.toDto(shipperPerson);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restShipperPersonMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(shipperPersonDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ShipperPerson in the database
        List<ShipperPerson> shipperPersonList = shipperPersonRepository.findAll();
        assertThat(shipperPersonList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteShipperPerson() throws Exception {
        // Initialize the database
        shipperPersonRepository.save(shipperPerson);

        int databaseSizeBeforeDelete = shipperPersonRepository.findAll().size();

        // Delete the shipperPerson
        restShipperPersonMockMvc
            .perform(delete(ENTITY_API_URL_ID, shipperPerson.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ShipperPerson> shipperPersonList = shipperPersonRepository.findAll();
        assertThat(shipperPersonList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

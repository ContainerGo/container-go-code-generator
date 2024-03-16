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
import vn.containergo.domain.CarrierPerson;
import vn.containergo.repository.CarrierPersonRepository;
import vn.containergo.service.dto.CarrierPersonDTO;
import vn.containergo.service.mapper.CarrierPersonMapper;

/**
 * Integration tests for the {@link CarrierPersonResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CarrierPersonResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/carrier-people";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CarrierPersonRepository carrierPersonRepository;

    @Autowired
    private CarrierPersonMapper carrierPersonMapper;

    @Autowired
    private MockMvc restCarrierPersonMockMvc;

    private CarrierPerson carrierPerson;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CarrierPerson createEntity() {
        CarrierPerson carrierPerson = new CarrierPerson()
            .name(DEFAULT_NAME)
            .phone(DEFAULT_PHONE)
            .email(DEFAULT_EMAIL)
            .address(DEFAULT_ADDRESS);
        // Add required entity
        Carrier carrier;
        carrier = CarrierResourceIT.createEntity();
        carrier.setId("fixed-id-for-tests");
        carrierPerson.setCarrier(carrier);
        return carrierPerson;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CarrierPerson createUpdatedEntity() {
        CarrierPerson carrierPerson = new CarrierPerson()
            .name(UPDATED_NAME)
            .phone(UPDATED_PHONE)
            .email(UPDATED_EMAIL)
            .address(UPDATED_ADDRESS);
        // Add required entity
        Carrier carrier;
        carrier = CarrierResourceIT.createUpdatedEntity();
        carrier.setId("fixed-id-for-tests");
        carrierPerson.setCarrier(carrier);
        return carrierPerson;
    }

    @BeforeEach
    public void initTest() {
        carrierPersonRepository.deleteAll();
        carrierPerson = createEntity();
    }

    @Test
    void createCarrierPerson() throws Exception {
        int databaseSizeBeforeCreate = carrierPersonRepository.findAll().size();
        // Create the CarrierPerson
        CarrierPersonDTO carrierPersonDTO = carrierPersonMapper.toDto(carrierPerson);
        restCarrierPersonMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(carrierPersonDTO))
            )
            .andExpect(status().isCreated());

        // Validate the CarrierPerson in the database
        List<CarrierPerson> carrierPersonList = carrierPersonRepository.findAll();
        assertThat(carrierPersonList).hasSize(databaseSizeBeforeCreate + 1);
        CarrierPerson testCarrierPerson = carrierPersonList.get(carrierPersonList.size() - 1);
        assertThat(testCarrierPerson.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCarrierPerson.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testCarrierPerson.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testCarrierPerson.getAddress()).isEqualTo(DEFAULT_ADDRESS);
    }

    @Test
    void createCarrierPersonWithExistingId() throws Exception {
        // Create the CarrierPerson with an existing ID
        carrierPerson.setId(1L);
        CarrierPersonDTO carrierPersonDTO = carrierPersonMapper.toDto(carrierPerson);

        int databaseSizeBeforeCreate = carrierPersonRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCarrierPersonMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(carrierPersonDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CarrierPerson in the database
        List<CarrierPerson> carrierPersonList = carrierPersonRepository.findAll();
        assertThat(carrierPersonList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = carrierPersonRepository.findAll().size();
        // set the field null
        carrierPerson.setName(null);

        // Create the CarrierPerson, which fails.
        CarrierPersonDTO carrierPersonDTO = carrierPersonMapper.toDto(carrierPerson);

        restCarrierPersonMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(carrierPersonDTO))
            )
            .andExpect(status().isBadRequest());

        List<CarrierPerson> carrierPersonList = carrierPersonRepository.findAll();
        assertThat(carrierPersonList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkPhoneIsRequired() throws Exception {
        int databaseSizeBeforeTest = carrierPersonRepository.findAll().size();
        // set the field null
        carrierPerson.setPhone(null);

        // Create the CarrierPerson, which fails.
        CarrierPersonDTO carrierPersonDTO = carrierPersonMapper.toDto(carrierPerson);

        restCarrierPersonMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(carrierPersonDTO))
            )
            .andExpect(status().isBadRequest());

        List<CarrierPerson> carrierPersonList = carrierPersonRepository.findAll();
        assertThat(carrierPersonList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void getAllCarrierPeople() throws Exception {
        // Initialize the database
        carrierPersonRepository.save(carrierPerson);

        // Get all the carrierPersonList
        restCarrierPersonMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(carrierPerson.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)));
    }

    @Test
    void getCarrierPerson() throws Exception {
        // Initialize the database
        carrierPersonRepository.save(carrierPerson);

        // Get the carrierPerson
        restCarrierPersonMockMvc
            .perform(get(ENTITY_API_URL_ID, carrierPerson.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(carrierPerson.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS));
    }

    @Test
    void getNonExistingCarrierPerson() throws Exception {
        // Get the carrierPerson
        restCarrierPersonMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingCarrierPerson() throws Exception {
        // Initialize the database
        carrierPersonRepository.save(carrierPerson);

        int databaseSizeBeforeUpdate = carrierPersonRepository.findAll().size();

        // Update the carrierPerson
        CarrierPerson updatedCarrierPerson = carrierPersonRepository.findById(carrierPerson.getId()).orElseThrow();
        updatedCarrierPerson.name(UPDATED_NAME).phone(UPDATED_PHONE).email(UPDATED_EMAIL).address(UPDATED_ADDRESS);
        CarrierPersonDTO carrierPersonDTO = carrierPersonMapper.toDto(updatedCarrierPerson);

        restCarrierPersonMockMvc
            .perform(
                put(ENTITY_API_URL_ID, carrierPersonDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(carrierPersonDTO))
            )
            .andExpect(status().isOk());

        // Validate the CarrierPerson in the database
        List<CarrierPerson> carrierPersonList = carrierPersonRepository.findAll();
        assertThat(carrierPersonList).hasSize(databaseSizeBeforeUpdate);
        CarrierPerson testCarrierPerson = carrierPersonList.get(carrierPersonList.size() - 1);
        assertThat(testCarrierPerson.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCarrierPerson.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testCarrierPerson.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testCarrierPerson.getAddress()).isEqualTo(UPDATED_ADDRESS);
    }

    @Test
    void putNonExistingCarrierPerson() throws Exception {
        int databaseSizeBeforeUpdate = carrierPersonRepository.findAll().size();
        carrierPerson.setId(longCount.incrementAndGet());

        // Create the CarrierPerson
        CarrierPersonDTO carrierPersonDTO = carrierPersonMapper.toDto(carrierPerson);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCarrierPersonMockMvc
            .perform(
                put(ENTITY_API_URL_ID, carrierPersonDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(carrierPersonDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CarrierPerson in the database
        List<CarrierPerson> carrierPersonList = carrierPersonRepository.findAll();
        assertThat(carrierPersonList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchCarrierPerson() throws Exception {
        int databaseSizeBeforeUpdate = carrierPersonRepository.findAll().size();
        carrierPerson.setId(longCount.incrementAndGet());

        // Create the CarrierPerson
        CarrierPersonDTO carrierPersonDTO = carrierPersonMapper.toDto(carrierPerson);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCarrierPersonMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(carrierPersonDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CarrierPerson in the database
        List<CarrierPerson> carrierPersonList = carrierPersonRepository.findAll();
        assertThat(carrierPersonList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamCarrierPerson() throws Exception {
        int databaseSizeBeforeUpdate = carrierPersonRepository.findAll().size();
        carrierPerson.setId(longCount.incrementAndGet());

        // Create the CarrierPerson
        CarrierPersonDTO carrierPersonDTO = carrierPersonMapper.toDto(carrierPerson);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCarrierPersonMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(carrierPersonDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CarrierPerson in the database
        List<CarrierPerson> carrierPersonList = carrierPersonRepository.findAll();
        assertThat(carrierPersonList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateCarrierPersonWithPatch() throws Exception {
        // Initialize the database
        carrierPersonRepository.save(carrierPerson);

        int databaseSizeBeforeUpdate = carrierPersonRepository.findAll().size();

        // Update the carrierPerson using partial update
        CarrierPerson partialUpdatedCarrierPerson = new CarrierPerson();
        partialUpdatedCarrierPerson.setId(carrierPerson.getId());

        restCarrierPersonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCarrierPerson.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCarrierPerson))
            )
            .andExpect(status().isOk());

        // Validate the CarrierPerson in the database
        List<CarrierPerson> carrierPersonList = carrierPersonRepository.findAll();
        assertThat(carrierPersonList).hasSize(databaseSizeBeforeUpdate);
        CarrierPerson testCarrierPerson = carrierPersonList.get(carrierPersonList.size() - 1);
        assertThat(testCarrierPerson.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCarrierPerson.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testCarrierPerson.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testCarrierPerson.getAddress()).isEqualTo(DEFAULT_ADDRESS);
    }

    @Test
    void fullUpdateCarrierPersonWithPatch() throws Exception {
        // Initialize the database
        carrierPersonRepository.save(carrierPerson);

        int databaseSizeBeforeUpdate = carrierPersonRepository.findAll().size();

        // Update the carrierPerson using partial update
        CarrierPerson partialUpdatedCarrierPerson = new CarrierPerson();
        partialUpdatedCarrierPerson.setId(carrierPerson.getId());

        partialUpdatedCarrierPerson.name(UPDATED_NAME).phone(UPDATED_PHONE).email(UPDATED_EMAIL).address(UPDATED_ADDRESS);

        restCarrierPersonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCarrierPerson.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCarrierPerson))
            )
            .andExpect(status().isOk());

        // Validate the CarrierPerson in the database
        List<CarrierPerson> carrierPersonList = carrierPersonRepository.findAll();
        assertThat(carrierPersonList).hasSize(databaseSizeBeforeUpdate);
        CarrierPerson testCarrierPerson = carrierPersonList.get(carrierPersonList.size() - 1);
        assertThat(testCarrierPerson.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCarrierPerson.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testCarrierPerson.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testCarrierPerson.getAddress()).isEqualTo(UPDATED_ADDRESS);
    }

    @Test
    void patchNonExistingCarrierPerson() throws Exception {
        int databaseSizeBeforeUpdate = carrierPersonRepository.findAll().size();
        carrierPerson.setId(longCount.incrementAndGet());

        // Create the CarrierPerson
        CarrierPersonDTO carrierPersonDTO = carrierPersonMapper.toDto(carrierPerson);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCarrierPersonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, carrierPersonDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(carrierPersonDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CarrierPerson in the database
        List<CarrierPerson> carrierPersonList = carrierPersonRepository.findAll();
        assertThat(carrierPersonList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchCarrierPerson() throws Exception {
        int databaseSizeBeforeUpdate = carrierPersonRepository.findAll().size();
        carrierPerson.setId(longCount.incrementAndGet());

        // Create the CarrierPerson
        CarrierPersonDTO carrierPersonDTO = carrierPersonMapper.toDto(carrierPerson);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCarrierPersonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(carrierPersonDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CarrierPerson in the database
        List<CarrierPerson> carrierPersonList = carrierPersonRepository.findAll();
        assertThat(carrierPersonList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamCarrierPerson() throws Exception {
        int databaseSizeBeforeUpdate = carrierPersonRepository.findAll().size();
        carrierPerson.setId(longCount.incrementAndGet());

        // Create the CarrierPerson
        CarrierPersonDTO carrierPersonDTO = carrierPersonMapper.toDto(carrierPerson);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCarrierPersonMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(carrierPersonDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CarrierPerson in the database
        List<CarrierPerson> carrierPersonList = carrierPersonRepository.findAll();
        assertThat(carrierPersonList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteCarrierPerson() throws Exception {
        // Initialize the database
        carrierPersonRepository.save(carrierPerson);

        int databaseSizeBeforeDelete = carrierPersonRepository.findAll().size();

        // Delete the carrierPerson
        restCarrierPersonMockMvc
            .perform(delete(ENTITY_API_URL_ID, carrierPerson.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CarrierPerson> carrierPersonList = carrierPersonRepository.findAll();
        assertThat(carrierPersonList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

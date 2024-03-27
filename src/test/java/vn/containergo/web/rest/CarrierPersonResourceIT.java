package vn.containergo.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static vn.containergo.domain.CarrierPersonAsserts.*;
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
    private ObjectMapper om;

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
        return carrierPerson;
    }

    @BeforeEach
    public void initTest() {
        carrierPersonRepository.deleteAll();
        carrierPerson = createEntity();
    }

    @Test
    void createCarrierPerson() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the CarrierPerson
        CarrierPersonDTO carrierPersonDTO = carrierPersonMapper.toDto(carrierPerson);
        var returnedCarrierPersonDTO = om.readValue(
            restCarrierPersonMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(carrierPersonDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            CarrierPersonDTO.class
        );

        // Validate the CarrierPerson in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedCarrierPerson = carrierPersonMapper.toEntity(returnedCarrierPersonDTO);
        assertCarrierPersonUpdatableFieldsEquals(returnedCarrierPerson, getPersistedCarrierPerson(returnedCarrierPerson));
    }

    @Test
    void createCarrierPersonWithExistingId() throws Exception {
        // Create the CarrierPerson with an existing ID
        carrierPerson.setId(1L);
        CarrierPersonDTO carrierPersonDTO = carrierPersonMapper.toDto(carrierPerson);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCarrierPersonMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(carrierPersonDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CarrierPerson in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    void checkNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        carrierPerson.setName(null);

        // Create the CarrierPerson, which fails.
        CarrierPersonDTO carrierPersonDTO = carrierPersonMapper.toDto(carrierPerson);

        restCarrierPersonMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(carrierPersonDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkPhoneIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        carrierPerson.setPhone(null);

        // Create the CarrierPerson, which fails.
        CarrierPersonDTO carrierPersonDTO = carrierPersonMapper.toDto(carrierPerson);

        restCarrierPersonMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(carrierPersonDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
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

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the carrierPerson
        CarrierPerson updatedCarrierPerson = carrierPersonRepository.findById(carrierPerson.getId()).orElseThrow();
        updatedCarrierPerson.name(UPDATED_NAME).phone(UPDATED_PHONE).email(UPDATED_EMAIL).address(UPDATED_ADDRESS);
        CarrierPersonDTO carrierPersonDTO = carrierPersonMapper.toDto(updatedCarrierPerson);

        restCarrierPersonMockMvc
            .perform(
                put(ENTITY_API_URL_ID, carrierPersonDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(carrierPersonDTO))
            )
            .andExpect(status().isOk());

        // Validate the CarrierPerson in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedCarrierPersonToMatchAllProperties(updatedCarrierPerson);
    }

    @Test
    void putNonExistingCarrierPerson() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        carrierPerson.setId(longCount.incrementAndGet());

        // Create the CarrierPerson
        CarrierPersonDTO carrierPersonDTO = carrierPersonMapper.toDto(carrierPerson);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCarrierPersonMockMvc
            .perform(
                put(ENTITY_API_URL_ID, carrierPersonDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(carrierPersonDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CarrierPerson in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchCarrierPerson() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        carrierPerson.setId(longCount.incrementAndGet());

        // Create the CarrierPerson
        CarrierPersonDTO carrierPersonDTO = carrierPersonMapper.toDto(carrierPerson);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCarrierPersonMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(carrierPersonDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CarrierPerson in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamCarrierPerson() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        carrierPerson.setId(longCount.incrementAndGet());

        // Create the CarrierPerson
        CarrierPersonDTO carrierPersonDTO = carrierPersonMapper.toDto(carrierPerson);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCarrierPersonMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(carrierPersonDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CarrierPerson in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateCarrierPersonWithPatch() throws Exception {
        // Initialize the database
        carrierPersonRepository.save(carrierPerson);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the carrierPerson using partial update
        CarrierPerson partialUpdatedCarrierPerson = new CarrierPerson();
        partialUpdatedCarrierPerson.setId(carrierPerson.getId());

        partialUpdatedCarrierPerson.name(UPDATED_NAME);

        restCarrierPersonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCarrierPerson.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCarrierPerson))
            )
            .andExpect(status().isOk());

        // Validate the CarrierPerson in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCarrierPersonUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedCarrierPerson, carrierPerson),
            getPersistedCarrierPerson(carrierPerson)
        );
    }

    @Test
    void fullUpdateCarrierPersonWithPatch() throws Exception {
        // Initialize the database
        carrierPersonRepository.save(carrierPerson);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the carrierPerson using partial update
        CarrierPerson partialUpdatedCarrierPerson = new CarrierPerson();
        partialUpdatedCarrierPerson.setId(carrierPerson.getId());

        partialUpdatedCarrierPerson.name(UPDATED_NAME).phone(UPDATED_PHONE).email(UPDATED_EMAIL).address(UPDATED_ADDRESS);

        restCarrierPersonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCarrierPerson.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCarrierPerson))
            )
            .andExpect(status().isOk());

        // Validate the CarrierPerson in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCarrierPersonUpdatableFieldsEquals(partialUpdatedCarrierPerson, getPersistedCarrierPerson(partialUpdatedCarrierPerson));
    }

    @Test
    void patchNonExistingCarrierPerson() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        carrierPerson.setId(longCount.incrementAndGet());

        // Create the CarrierPerson
        CarrierPersonDTO carrierPersonDTO = carrierPersonMapper.toDto(carrierPerson);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCarrierPersonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, carrierPersonDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(carrierPersonDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CarrierPerson in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchCarrierPerson() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        carrierPerson.setId(longCount.incrementAndGet());

        // Create the CarrierPerson
        CarrierPersonDTO carrierPersonDTO = carrierPersonMapper.toDto(carrierPerson);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCarrierPersonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(carrierPersonDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CarrierPerson in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamCarrierPerson() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        carrierPerson.setId(longCount.incrementAndGet());

        // Create the CarrierPerson
        CarrierPersonDTO carrierPersonDTO = carrierPersonMapper.toDto(carrierPerson);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCarrierPersonMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(carrierPersonDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CarrierPerson in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteCarrierPerson() throws Exception {
        // Initialize the database
        carrierPersonRepository.save(carrierPerson);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the carrierPerson
        restCarrierPersonMockMvc
            .perform(delete(ENTITY_API_URL_ID, carrierPerson.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return carrierPersonRepository.count();
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

    protected CarrierPerson getPersistedCarrierPerson(CarrierPerson carrierPerson) {
        return carrierPersonRepository.findById(carrierPerson.getId()).orElseThrow();
    }

    protected void assertPersistedCarrierPersonToMatchAllProperties(CarrierPerson expectedCarrierPerson) {
        assertCarrierPersonAllPropertiesEquals(expectedCarrierPerson, getPersistedCarrierPerson(expectedCarrierPerson));
    }

    protected void assertPersistedCarrierPersonToMatchUpdatableProperties(CarrierPerson expectedCarrierPerson) {
        assertCarrierPersonAllUpdatablePropertiesEquals(expectedCarrierPerson, getPersistedCarrierPerson(expectedCarrierPerson));
    }
}

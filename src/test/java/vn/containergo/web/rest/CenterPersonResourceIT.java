package vn.containergo.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import vn.containergo.IntegrationTest;
import vn.containergo.domain.CenterPerson;
import vn.containergo.repository.CenterPersonRepository;
import vn.containergo.service.CenterPersonService;
import vn.containergo.service.dto.CenterPersonDTO;
import vn.containergo.service.mapper.CenterPersonMapper;

/**
 * Integration tests for the {@link CenterPersonResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class CenterPersonResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/center-people";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CenterPersonRepository centerPersonRepository;

    @Mock
    private CenterPersonRepository centerPersonRepositoryMock;

    @Autowired
    private CenterPersonMapper centerPersonMapper;

    @Mock
    private CenterPersonService centerPersonServiceMock;

    @Autowired
    private MockMvc restCenterPersonMockMvc;

    private CenterPerson centerPerson;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CenterPerson createEntity() {
        CenterPerson centerPerson = new CenterPerson()
            .name(DEFAULT_NAME)
            .phone(DEFAULT_PHONE)
            .email(DEFAULT_EMAIL)
            .address(DEFAULT_ADDRESS);
        return centerPerson;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CenterPerson createUpdatedEntity() {
        CenterPerson centerPerson = new CenterPerson()
            .name(UPDATED_NAME)
            .phone(UPDATED_PHONE)
            .email(UPDATED_EMAIL)
            .address(UPDATED_ADDRESS);
        return centerPerson;
    }

    @BeforeEach
    public void initTest() {
        centerPersonRepository.deleteAll();
        centerPerson = createEntity();
    }

    @Test
    void createCenterPerson() throws Exception {
        int databaseSizeBeforeCreate = centerPersonRepository.findAll().size();
        // Create the CenterPerson
        CenterPersonDTO centerPersonDTO = centerPersonMapper.toDto(centerPerson);
        restCenterPersonMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(centerPersonDTO))
            )
            .andExpect(status().isCreated());

        // Validate the CenterPerson in the database
        List<CenterPerson> centerPersonList = centerPersonRepository.findAll();
        assertThat(centerPersonList).hasSize(databaseSizeBeforeCreate + 1);
        CenterPerson testCenterPerson = centerPersonList.get(centerPersonList.size() - 1);
        assertThat(testCenterPerson.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCenterPerson.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testCenterPerson.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testCenterPerson.getAddress()).isEqualTo(DEFAULT_ADDRESS);
    }

    @Test
    void createCenterPersonWithExistingId() throws Exception {
        // Create the CenterPerson with an existing ID
        centerPerson.setId(1L);
        CenterPersonDTO centerPersonDTO = centerPersonMapper.toDto(centerPerson);

        int databaseSizeBeforeCreate = centerPersonRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCenterPersonMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(centerPersonDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CenterPerson in the database
        List<CenterPerson> centerPersonList = centerPersonRepository.findAll();
        assertThat(centerPersonList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = centerPersonRepository.findAll().size();
        // set the field null
        centerPerson.setName(null);

        // Create the CenterPerson, which fails.
        CenterPersonDTO centerPersonDTO = centerPersonMapper.toDto(centerPerson);

        restCenterPersonMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(centerPersonDTO))
            )
            .andExpect(status().isBadRequest());

        List<CenterPerson> centerPersonList = centerPersonRepository.findAll();
        assertThat(centerPersonList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkPhoneIsRequired() throws Exception {
        int databaseSizeBeforeTest = centerPersonRepository.findAll().size();
        // set the field null
        centerPerson.setPhone(null);

        // Create the CenterPerson, which fails.
        CenterPersonDTO centerPersonDTO = centerPersonMapper.toDto(centerPerson);

        restCenterPersonMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(centerPersonDTO))
            )
            .andExpect(status().isBadRequest());

        List<CenterPerson> centerPersonList = centerPersonRepository.findAll();
        assertThat(centerPersonList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void getAllCenterPeople() throws Exception {
        // Initialize the database
        centerPersonRepository.save(centerPerson);

        // Get all the centerPersonList
        restCenterPersonMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(centerPerson.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllCenterPeopleWithEagerRelationshipsIsEnabled() throws Exception {
        when(centerPersonServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restCenterPersonMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(centerPersonServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllCenterPeopleWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(centerPersonServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restCenterPersonMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(centerPersonRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void getCenterPerson() throws Exception {
        // Initialize the database
        centerPersonRepository.save(centerPerson);

        // Get the centerPerson
        restCenterPersonMockMvc
            .perform(get(ENTITY_API_URL_ID, centerPerson.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(centerPerson.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS));
    }

    @Test
    void getNonExistingCenterPerson() throws Exception {
        // Get the centerPerson
        restCenterPersonMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingCenterPerson() throws Exception {
        // Initialize the database
        centerPersonRepository.save(centerPerson);

        int databaseSizeBeforeUpdate = centerPersonRepository.findAll().size();

        // Update the centerPerson
        CenterPerson updatedCenterPerson = centerPersonRepository.findById(centerPerson.getId()).orElseThrow();
        updatedCenterPerson.name(UPDATED_NAME).phone(UPDATED_PHONE).email(UPDATED_EMAIL).address(UPDATED_ADDRESS);
        CenterPersonDTO centerPersonDTO = centerPersonMapper.toDto(updatedCenterPerson);

        restCenterPersonMockMvc
            .perform(
                put(ENTITY_API_URL_ID, centerPersonDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(centerPersonDTO))
            )
            .andExpect(status().isOk());

        // Validate the CenterPerson in the database
        List<CenterPerson> centerPersonList = centerPersonRepository.findAll();
        assertThat(centerPersonList).hasSize(databaseSizeBeforeUpdate);
        CenterPerson testCenterPerson = centerPersonList.get(centerPersonList.size() - 1);
        assertThat(testCenterPerson.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCenterPerson.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testCenterPerson.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testCenterPerson.getAddress()).isEqualTo(UPDATED_ADDRESS);
    }

    @Test
    void putNonExistingCenterPerson() throws Exception {
        int databaseSizeBeforeUpdate = centerPersonRepository.findAll().size();
        centerPerson.setId(longCount.incrementAndGet());

        // Create the CenterPerson
        CenterPersonDTO centerPersonDTO = centerPersonMapper.toDto(centerPerson);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCenterPersonMockMvc
            .perform(
                put(ENTITY_API_URL_ID, centerPersonDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(centerPersonDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CenterPerson in the database
        List<CenterPerson> centerPersonList = centerPersonRepository.findAll();
        assertThat(centerPersonList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchCenterPerson() throws Exception {
        int databaseSizeBeforeUpdate = centerPersonRepository.findAll().size();
        centerPerson.setId(longCount.incrementAndGet());

        // Create the CenterPerson
        CenterPersonDTO centerPersonDTO = centerPersonMapper.toDto(centerPerson);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCenterPersonMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(centerPersonDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CenterPerson in the database
        List<CenterPerson> centerPersonList = centerPersonRepository.findAll();
        assertThat(centerPersonList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamCenterPerson() throws Exception {
        int databaseSizeBeforeUpdate = centerPersonRepository.findAll().size();
        centerPerson.setId(longCount.incrementAndGet());

        // Create the CenterPerson
        CenterPersonDTO centerPersonDTO = centerPersonMapper.toDto(centerPerson);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCenterPersonMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(centerPersonDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CenterPerson in the database
        List<CenterPerson> centerPersonList = centerPersonRepository.findAll();
        assertThat(centerPersonList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateCenterPersonWithPatch() throws Exception {
        // Initialize the database
        centerPersonRepository.save(centerPerson);

        int databaseSizeBeforeUpdate = centerPersonRepository.findAll().size();

        // Update the centerPerson using partial update
        CenterPerson partialUpdatedCenterPerson = new CenterPerson();
        partialUpdatedCenterPerson.setId(centerPerson.getId());

        partialUpdatedCenterPerson.name(UPDATED_NAME).address(UPDATED_ADDRESS);

        restCenterPersonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCenterPerson.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCenterPerson))
            )
            .andExpect(status().isOk());

        // Validate the CenterPerson in the database
        List<CenterPerson> centerPersonList = centerPersonRepository.findAll();
        assertThat(centerPersonList).hasSize(databaseSizeBeforeUpdate);
        CenterPerson testCenterPerson = centerPersonList.get(centerPersonList.size() - 1);
        assertThat(testCenterPerson.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCenterPerson.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testCenterPerson.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testCenterPerson.getAddress()).isEqualTo(UPDATED_ADDRESS);
    }

    @Test
    void fullUpdateCenterPersonWithPatch() throws Exception {
        // Initialize the database
        centerPersonRepository.save(centerPerson);

        int databaseSizeBeforeUpdate = centerPersonRepository.findAll().size();

        // Update the centerPerson using partial update
        CenterPerson partialUpdatedCenterPerson = new CenterPerson();
        partialUpdatedCenterPerson.setId(centerPerson.getId());

        partialUpdatedCenterPerson.name(UPDATED_NAME).phone(UPDATED_PHONE).email(UPDATED_EMAIL).address(UPDATED_ADDRESS);

        restCenterPersonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCenterPerson.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCenterPerson))
            )
            .andExpect(status().isOk());

        // Validate the CenterPerson in the database
        List<CenterPerson> centerPersonList = centerPersonRepository.findAll();
        assertThat(centerPersonList).hasSize(databaseSizeBeforeUpdate);
        CenterPerson testCenterPerson = centerPersonList.get(centerPersonList.size() - 1);
        assertThat(testCenterPerson.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCenterPerson.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testCenterPerson.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testCenterPerson.getAddress()).isEqualTo(UPDATED_ADDRESS);
    }

    @Test
    void patchNonExistingCenterPerson() throws Exception {
        int databaseSizeBeforeUpdate = centerPersonRepository.findAll().size();
        centerPerson.setId(longCount.incrementAndGet());

        // Create the CenterPerson
        CenterPersonDTO centerPersonDTO = centerPersonMapper.toDto(centerPerson);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCenterPersonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, centerPersonDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(centerPersonDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CenterPerson in the database
        List<CenterPerson> centerPersonList = centerPersonRepository.findAll();
        assertThat(centerPersonList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchCenterPerson() throws Exception {
        int databaseSizeBeforeUpdate = centerPersonRepository.findAll().size();
        centerPerson.setId(longCount.incrementAndGet());

        // Create the CenterPerson
        CenterPersonDTO centerPersonDTO = centerPersonMapper.toDto(centerPerson);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCenterPersonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(centerPersonDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CenterPerson in the database
        List<CenterPerson> centerPersonList = centerPersonRepository.findAll();
        assertThat(centerPersonList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamCenterPerson() throws Exception {
        int databaseSizeBeforeUpdate = centerPersonRepository.findAll().size();
        centerPerson.setId(longCount.incrementAndGet());

        // Create the CenterPerson
        CenterPersonDTO centerPersonDTO = centerPersonMapper.toDto(centerPerson);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCenterPersonMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(centerPersonDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CenterPerson in the database
        List<CenterPerson> centerPersonList = centerPersonRepository.findAll();
        assertThat(centerPersonList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteCenterPerson() throws Exception {
        // Initialize the database
        centerPersonRepository.save(centerPerson);

        int databaseSizeBeforeDelete = centerPersonRepository.findAll().size();

        // Delete the centerPerson
        restCenterPersonMockMvc
            .perform(delete(ENTITY_API_URL_ID, centerPerson.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CenterPerson> centerPersonList = centerPersonRepository.findAll();
        assertThat(centerPersonList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

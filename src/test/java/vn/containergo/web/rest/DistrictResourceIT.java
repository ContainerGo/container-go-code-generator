package vn.containergo.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static vn.containergo.domain.DistrictAsserts.*;
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
import vn.containergo.domain.District;
import vn.containergo.repository.DistrictRepository;
import vn.containergo.service.dto.DistrictDTO;
import vn.containergo.service.mapper.DistrictMapper;

/**
 * Integration tests for the {@link DistrictResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DistrictResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/districts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private DistrictRepository districtRepository;

    @Autowired
    private DistrictMapper districtMapper;

    @Autowired
    private MockMvc restDistrictMockMvc;

    private District district;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static District createEntity() {
        District district = new District().code(DEFAULT_CODE).name(DEFAULT_NAME).description(DEFAULT_DESCRIPTION);
        return district;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static District createUpdatedEntity() {
        District district = new District().code(UPDATED_CODE).name(UPDATED_NAME).description(UPDATED_DESCRIPTION);
        return district;
    }

    @BeforeEach
    public void initTest() {
        districtRepository.deleteAll();
        district = createEntity();
    }

    @Test
    void createDistrict() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the District
        DistrictDTO districtDTO = districtMapper.toDto(district);
        var returnedDistrictDTO = om.readValue(
            restDistrictMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(districtDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            DistrictDTO.class
        );

        // Validate the District in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedDistrict = districtMapper.toEntity(returnedDistrictDTO);
        assertDistrictUpdatableFieldsEquals(returnedDistrict, getPersistedDistrict(returnedDistrict));
    }

    @Test
    void createDistrictWithExistingId() throws Exception {
        // Create the District with an existing ID
        district.setId(1L);
        DistrictDTO districtDTO = districtMapper.toDto(district);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDistrictMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(districtDTO)))
            .andExpect(status().isBadRequest());

        // Validate the District in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    void checkCodeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        district.setCode(null);

        // Create the District, which fails.
        DistrictDTO districtDTO = districtMapper.toDto(district);

        restDistrictMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(districtDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        district.setName(null);

        // Create the District, which fails.
        DistrictDTO districtDTO = districtMapper.toDto(district);

        restDistrictMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(districtDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void getAllDistricts() throws Exception {
        // Initialize the database
        districtRepository.save(district);

        // Get all the districtList
        restDistrictMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(district.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    void getDistrict() throws Exception {
        // Initialize the database
        districtRepository.save(district);

        // Get the district
        restDistrictMockMvc
            .perform(get(ENTITY_API_URL_ID, district.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(district.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    void getNonExistingDistrict() throws Exception {
        // Get the district
        restDistrictMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingDistrict() throws Exception {
        // Initialize the database
        districtRepository.save(district);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the district
        District updatedDistrict = districtRepository.findById(district.getId()).orElseThrow();
        updatedDistrict.code(UPDATED_CODE).name(UPDATED_NAME).description(UPDATED_DESCRIPTION);
        DistrictDTO districtDTO = districtMapper.toDto(updatedDistrict);

        restDistrictMockMvc
            .perform(
                put(ENTITY_API_URL_ID, districtDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(districtDTO))
            )
            .andExpect(status().isOk());

        // Validate the District in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedDistrictToMatchAllProperties(updatedDistrict);
    }

    @Test
    void putNonExistingDistrict() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        district.setId(longCount.incrementAndGet());

        // Create the District
        DistrictDTO districtDTO = districtMapper.toDto(district);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDistrictMockMvc
            .perform(
                put(ENTITY_API_URL_ID, districtDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(districtDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the District in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchDistrict() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        district.setId(longCount.incrementAndGet());

        // Create the District
        DistrictDTO districtDTO = districtMapper.toDto(district);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDistrictMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(districtDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the District in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamDistrict() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        district.setId(longCount.incrementAndGet());

        // Create the District
        DistrictDTO districtDTO = districtMapper.toDto(district);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDistrictMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(districtDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the District in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateDistrictWithPatch() throws Exception {
        // Initialize the database
        districtRepository.save(district);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the district using partial update
        District partialUpdatedDistrict = new District();
        partialUpdatedDistrict.setId(district.getId());

        partialUpdatedDistrict.name(UPDATED_NAME).description(UPDATED_DESCRIPTION);

        restDistrictMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDistrict.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDistrict))
            )
            .andExpect(status().isOk());

        // Validate the District in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDistrictUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedDistrict, district), getPersistedDistrict(district));
    }

    @Test
    void fullUpdateDistrictWithPatch() throws Exception {
        // Initialize the database
        districtRepository.save(district);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the district using partial update
        District partialUpdatedDistrict = new District();
        partialUpdatedDistrict.setId(district.getId());

        partialUpdatedDistrict.code(UPDATED_CODE).name(UPDATED_NAME).description(UPDATED_DESCRIPTION);

        restDistrictMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDistrict.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDistrict))
            )
            .andExpect(status().isOk());

        // Validate the District in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDistrictUpdatableFieldsEquals(partialUpdatedDistrict, getPersistedDistrict(partialUpdatedDistrict));
    }

    @Test
    void patchNonExistingDistrict() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        district.setId(longCount.incrementAndGet());

        // Create the District
        DistrictDTO districtDTO = districtMapper.toDto(district);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDistrictMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, districtDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(districtDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the District in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchDistrict() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        district.setId(longCount.incrementAndGet());

        // Create the District
        DistrictDTO districtDTO = districtMapper.toDto(district);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDistrictMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(districtDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the District in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamDistrict() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        district.setId(longCount.incrementAndGet());

        // Create the District
        DistrictDTO districtDTO = districtMapper.toDto(district);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDistrictMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(districtDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the District in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteDistrict() throws Exception {
        // Initialize the database
        districtRepository.save(district);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the district
        restDistrictMockMvc
            .perform(delete(ENTITY_API_URL_ID, district.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return districtRepository.count();
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

    protected District getPersistedDistrict(District district) {
        return districtRepository.findById(district.getId()).orElseThrow();
    }

    protected void assertPersistedDistrictToMatchAllProperties(District expectedDistrict) {
        assertDistrictAllPropertiesEquals(expectedDistrict, getPersistedDistrict(expectedDistrict));
    }

    protected void assertPersistedDistrictToMatchUpdatableProperties(District expectedDistrict) {
        assertDistrictAllUpdatablePropertiesEquals(expectedDistrict, getPersistedDistrict(expectedDistrict));
    }
}

package vn.containergo.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static vn.containergo.domain.WardAsserts.*;
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
import vn.containergo.domain.Ward;
import vn.containergo.repository.WardRepository;
import vn.containergo.service.dto.WardDTO;
import vn.containergo.service.mapper.WardMapper;

/**
 * Integration tests for the {@link WardResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class WardResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/wards";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private WardRepository wardRepository;

    @Autowired
    private WardMapper wardMapper;

    @Autowired
    private MockMvc restWardMockMvc;

    private Ward ward;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ward createEntity() {
        Ward ward = new Ward().code(DEFAULT_CODE).name(DEFAULT_NAME).description(DEFAULT_DESCRIPTION);
        return ward;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ward createUpdatedEntity() {
        Ward ward = new Ward().code(UPDATED_CODE).name(UPDATED_NAME).description(UPDATED_DESCRIPTION);
        return ward;
    }

    @BeforeEach
    public void initTest() {
        wardRepository.deleteAll();
        ward = createEntity();
    }

    @Test
    void createWard() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Ward
        WardDTO wardDTO = wardMapper.toDto(ward);
        var returnedWardDTO = om.readValue(
            restWardMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(wardDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            WardDTO.class
        );

        // Validate the Ward in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedWard = wardMapper.toEntity(returnedWardDTO);
        assertWardUpdatableFieldsEquals(returnedWard, getPersistedWard(returnedWard));
    }

    @Test
    void createWardWithExistingId() throws Exception {
        // Create the Ward with an existing ID
        ward.setId(UUID.randomUUID());
        WardDTO wardDTO = wardMapper.toDto(ward);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restWardMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(wardDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Ward in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    void checkCodeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        ward.setCode(null);

        // Create the Ward, which fails.
        WardDTO wardDTO = wardMapper.toDto(ward);

        restWardMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(wardDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        ward.setName(null);

        // Create the Ward, which fails.
        WardDTO wardDTO = wardMapper.toDto(ward);

        restWardMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(wardDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void getAllWards() throws Exception {
        // Initialize the database
        ward.setId(UUID.randomUUID());
        wardRepository.save(ward);

        // Get all the wardList
        restWardMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ward.getId().toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    void getWard() throws Exception {
        // Initialize the database
        ward.setId(UUID.randomUUID());
        wardRepository.save(ward);

        // Get the ward
        restWardMockMvc
            .perform(get(ENTITY_API_URL_ID, ward.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ward.getId().toString()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    void getNonExistingWard() throws Exception {
        // Get the ward
        restWardMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    void putExistingWard() throws Exception {
        // Initialize the database
        ward.setId(UUID.randomUUID());
        wardRepository.save(ward);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the ward
        Ward updatedWard = wardRepository.findById(ward.getId()).orElseThrow();
        updatedWard.code(UPDATED_CODE).name(UPDATED_NAME).description(UPDATED_DESCRIPTION);
        WardDTO wardDTO = wardMapper.toDto(updatedWard);

        restWardMockMvc
            .perform(put(ENTITY_API_URL_ID, wardDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(wardDTO)))
            .andExpect(status().isOk());

        // Validate the Ward in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedWardToMatchAllProperties(updatedWard);
    }

    @Test
    void putNonExistingWard() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ward.setId(UUID.randomUUID());

        // Create the Ward
        WardDTO wardDTO = wardMapper.toDto(ward);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWardMockMvc
            .perform(put(ENTITY_API_URL_ID, wardDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(wardDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Ward in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchWard() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ward.setId(UUID.randomUUID());

        // Create the Ward
        WardDTO wardDTO = wardMapper.toDto(ward);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWardMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(wardDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ward in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamWard() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ward.setId(UUID.randomUUID());

        // Create the Ward
        WardDTO wardDTO = wardMapper.toDto(ward);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWardMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(wardDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Ward in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateWardWithPatch() throws Exception {
        // Initialize the database
        ward.setId(UUID.randomUUID());
        wardRepository.save(ward);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the ward using partial update
        Ward partialUpdatedWard = new Ward();
        partialUpdatedWard.setId(ward.getId());

        restWardMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWard.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedWard))
            )
            .andExpect(status().isOk());

        // Validate the Ward in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertWardUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedWard, ward), getPersistedWard(ward));
    }

    @Test
    void fullUpdateWardWithPatch() throws Exception {
        // Initialize the database
        ward.setId(UUID.randomUUID());
        wardRepository.save(ward);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the ward using partial update
        Ward partialUpdatedWard = new Ward();
        partialUpdatedWard.setId(ward.getId());

        partialUpdatedWard.code(UPDATED_CODE).name(UPDATED_NAME).description(UPDATED_DESCRIPTION);

        restWardMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWard.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedWard))
            )
            .andExpect(status().isOk());

        // Validate the Ward in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertWardUpdatableFieldsEquals(partialUpdatedWard, getPersistedWard(partialUpdatedWard));
    }

    @Test
    void patchNonExistingWard() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ward.setId(UUID.randomUUID());

        // Create the Ward
        WardDTO wardDTO = wardMapper.toDto(ward);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWardMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, wardDTO.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(wardDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ward in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchWard() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ward.setId(UUID.randomUUID());

        // Create the Ward
        WardDTO wardDTO = wardMapper.toDto(ward);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWardMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(wardDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ward in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamWard() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ward.setId(UUID.randomUUID());

        // Create the Ward
        WardDTO wardDTO = wardMapper.toDto(ward);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWardMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(wardDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Ward in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteWard() throws Exception {
        // Initialize the database
        ward.setId(UUID.randomUUID());
        wardRepository.save(ward);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the ward
        restWardMockMvc
            .perform(delete(ENTITY_API_URL_ID, ward.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return wardRepository.count();
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

    protected Ward getPersistedWard(Ward ward) {
        return wardRepository.findById(ward.getId()).orElseThrow();
    }

    protected void assertPersistedWardToMatchAllProperties(Ward expectedWard) {
        assertWardAllPropertiesEquals(expectedWard, getPersistedWard(expectedWard));
    }

    protected void assertPersistedWardToMatchUpdatableProperties(Ward expectedWard) {
        assertWardAllUpdatablePropertiesEquals(expectedWard, getPersistedWard(expectedWard));
    }
}

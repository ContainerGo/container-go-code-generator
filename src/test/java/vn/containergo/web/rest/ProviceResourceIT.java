package vn.containergo.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static vn.containergo.domain.ProviceAsserts.*;
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
import vn.containergo.domain.Provice;
import vn.containergo.repository.ProviceRepository;
import vn.containergo.service.dto.ProviceDTO;
import vn.containergo.service.mapper.ProviceMapper;

/**
 * Integration tests for the {@link ProviceResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ProviceResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/provices";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ProviceRepository proviceRepository;

    @Autowired
    private ProviceMapper proviceMapper;

    @Autowired
    private MockMvc restProviceMockMvc;

    private Provice provice;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Provice createEntity() {
        Provice provice = new Provice().code(DEFAULT_CODE).name(DEFAULT_NAME).description(DEFAULT_DESCRIPTION);
        return provice;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Provice createUpdatedEntity() {
        Provice provice = new Provice().code(UPDATED_CODE).name(UPDATED_NAME).description(UPDATED_DESCRIPTION);
        return provice;
    }

    @BeforeEach
    public void initTest() {
        proviceRepository.deleteAll();
        provice = createEntity();
    }

    @Test
    void createProvice() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Provice
        ProviceDTO proviceDTO = proviceMapper.toDto(provice);
        var returnedProviceDTO = om.readValue(
            restProviceMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(proviceDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ProviceDTO.class
        );

        // Validate the Provice in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedProvice = proviceMapper.toEntity(returnedProviceDTO);
        assertProviceUpdatableFieldsEquals(returnedProvice, getPersistedProvice(returnedProvice));
    }

    @Test
    void createProviceWithExistingId() throws Exception {
        // Create the Provice with an existing ID
        provice.setId(1L);
        ProviceDTO proviceDTO = proviceMapper.toDto(provice);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProviceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(proviceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Provice in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    void checkCodeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        provice.setCode(null);

        // Create the Provice, which fails.
        ProviceDTO proviceDTO = proviceMapper.toDto(provice);

        restProviceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(proviceDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        provice.setName(null);

        // Create the Provice, which fails.
        ProviceDTO proviceDTO = proviceMapper.toDto(provice);

        restProviceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(proviceDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void getAllProvices() throws Exception {
        // Initialize the database
        proviceRepository.save(provice);

        // Get all the proviceList
        restProviceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(provice.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    void getProvice() throws Exception {
        // Initialize the database
        proviceRepository.save(provice);

        // Get the provice
        restProviceMockMvc
            .perform(get(ENTITY_API_URL_ID, provice.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(provice.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    void getNonExistingProvice() throws Exception {
        // Get the provice
        restProviceMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingProvice() throws Exception {
        // Initialize the database
        proviceRepository.save(provice);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the provice
        Provice updatedProvice = proviceRepository.findById(provice.getId()).orElseThrow();
        updatedProvice.code(UPDATED_CODE).name(UPDATED_NAME).description(UPDATED_DESCRIPTION);
        ProviceDTO proviceDTO = proviceMapper.toDto(updatedProvice);

        restProviceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, proviceDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(proviceDTO))
            )
            .andExpect(status().isOk());

        // Validate the Provice in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedProviceToMatchAllProperties(updatedProvice);
    }

    @Test
    void putNonExistingProvice() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        provice.setId(longCount.incrementAndGet());

        // Create the Provice
        ProviceDTO proviceDTO = proviceMapper.toDto(provice);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProviceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, proviceDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(proviceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Provice in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchProvice() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        provice.setId(longCount.incrementAndGet());

        // Create the Provice
        ProviceDTO proviceDTO = proviceMapper.toDto(provice);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProviceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(proviceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Provice in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamProvice() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        provice.setId(longCount.incrementAndGet());

        // Create the Provice
        ProviceDTO proviceDTO = proviceMapper.toDto(provice);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProviceMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(proviceDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Provice in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateProviceWithPatch() throws Exception {
        // Initialize the database
        proviceRepository.save(provice);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the provice using partial update
        Provice partialUpdatedProvice = new Provice();
        partialUpdatedProvice.setId(provice.getId());

        partialUpdatedProvice.code(UPDATED_CODE);

        restProviceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProvice.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedProvice))
            )
            .andExpect(status().isOk());

        // Validate the Provice in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertProviceUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedProvice, provice), getPersistedProvice(provice));
    }

    @Test
    void fullUpdateProviceWithPatch() throws Exception {
        // Initialize the database
        proviceRepository.save(provice);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the provice using partial update
        Provice partialUpdatedProvice = new Provice();
        partialUpdatedProvice.setId(provice.getId());

        partialUpdatedProvice.code(UPDATED_CODE).name(UPDATED_NAME).description(UPDATED_DESCRIPTION);

        restProviceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProvice.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedProvice))
            )
            .andExpect(status().isOk());

        // Validate the Provice in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertProviceUpdatableFieldsEquals(partialUpdatedProvice, getPersistedProvice(partialUpdatedProvice));
    }

    @Test
    void patchNonExistingProvice() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        provice.setId(longCount.incrementAndGet());

        // Create the Provice
        ProviceDTO proviceDTO = proviceMapper.toDto(provice);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProviceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, proviceDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(proviceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Provice in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchProvice() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        provice.setId(longCount.incrementAndGet());

        // Create the Provice
        ProviceDTO proviceDTO = proviceMapper.toDto(provice);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProviceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(proviceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Provice in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamProvice() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        provice.setId(longCount.incrementAndGet());

        // Create the Provice
        ProviceDTO proviceDTO = proviceMapper.toDto(provice);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProviceMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(proviceDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Provice in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteProvice() throws Exception {
        // Initialize the database
        proviceRepository.save(provice);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the provice
        restProviceMockMvc
            .perform(delete(ENTITY_API_URL_ID, provice.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return proviceRepository.count();
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

    protected Provice getPersistedProvice(Provice provice) {
        return proviceRepository.findById(provice.getId()).orElseThrow();
    }

    protected void assertPersistedProviceToMatchAllProperties(Provice expectedProvice) {
        assertProviceAllPropertiesEquals(expectedProvice, getPersistedProvice(expectedProvice));
    }

    protected void assertPersistedProviceToMatchUpdatableProperties(Provice expectedProvice) {
        assertProviceAllUpdatablePropertiesEquals(expectedProvice, getPersistedProvice(expectedProvice));
    }
}

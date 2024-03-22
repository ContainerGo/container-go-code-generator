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
import vn.containergo.domain.TruckType;
import vn.containergo.repository.TruckTypeRepository;
import vn.containergo.service.dto.TruckTypeDTO;
import vn.containergo.service.mapper.TruckTypeMapper;

/**
 * Integration tests for the {@link TruckTypeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TruckTypeResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CATEGORY = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORY = "BBBBBBBBBB";

    private static final Integer DEFAULT_HEIGHT = 1;
    private static final Integer UPDATED_HEIGHT = 2;

    private static final Integer DEFAULT_LENGTH = 1;
    private static final Integer UPDATED_LENGTH = 2;

    private static final Double DEFAULT_MAX_SPEED = 1D;
    private static final Double UPDATED_MAX_SPEED = 2D;

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final Integer DEFAULT_WEIGHT = 1;
    private static final Integer UPDATED_WEIGHT = 2;

    private static final Integer DEFAULT_WIDTH = 1;
    private static final Integer UPDATED_WIDTH = 2;

    private static final String ENTITY_API_URL = "/api/truck-types";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TruckTypeRepository truckTypeRepository;

    @Autowired
    private TruckTypeMapper truckTypeMapper;

    @Autowired
    private MockMvc restTruckTypeMockMvc;

    private TruckType truckType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TruckType createEntity() {
        TruckType truckType = new TruckType()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .category(DEFAULT_CATEGORY)
            .height(DEFAULT_HEIGHT)
            .length(DEFAULT_LENGTH)
            .maxSpeed(DEFAULT_MAX_SPEED)
            .type(DEFAULT_TYPE)
            .weight(DEFAULT_WEIGHT)
            .width(DEFAULT_WIDTH);
        return truckType;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TruckType createUpdatedEntity() {
        TruckType truckType = new TruckType()
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .category(UPDATED_CATEGORY)
            .height(UPDATED_HEIGHT)
            .length(UPDATED_LENGTH)
            .maxSpeed(UPDATED_MAX_SPEED)
            .type(UPDATED_TYPE)
            .weight(UPDATED_WEIGHT)
            .width(UPDATED_WIDTH);
        return truckType;
    }

    @BeforeEach
    public void initTest() {
        truckTypeRepository.deleteAll();
        truckType = createEntity();
    }

    @Test
    void createTruckType() throws Exception {
        int databaseSizeBeforeCreate = truckTypeRepository.findAll().size();
        // Create the TruckType
        TruckTypeDTO truckTypeDTO = truckTypeMapper.toDto(truckType);
        restTruckTypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(truckTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the TruckType in the database
        List<TruckType> truckTypeList = truckTypeRepository.findAll();
        assertThat(truckTypeList).hasSize(databaseSizeBeforeCreate + 1);
        TruckType testTruckType = truckTypeList.get(truckTypeList.size() - 1);
        assertThat(testTruckType.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testTruckType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTruckType.getCategory()).isEqualTo(DEFAULT_CATEGORY);
        assertThat(testTruckType.getHeight()).isEqualTo(DEFAULT_HEIGHT);
        assertThat(testTruckType.getLength()).isEqualTo(DEFAULT_LENGTH);
        assertThat(testTruckType.getMaxSpeed()).isEqualTo(DEFAULT_MAX_SPEED);
        assertThat(testTruckType.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testTruckType.getWeight()).isEqualTo(DEFAULT_WEIGHT);
        assertThat(testTruckType.getWidth()).isEqualTo(DEFAULT_WIDTH);
    }

    @Test
    void createTruckTypeWithExistingId() throws Exception {
        // Create the TruckType with an existing ID
        truckType.setId(1L);
        TruckTypeDTO truckTypeDTO = truckTypeMapper.toDto(truckType);

        int databaseSizeBeforeCreate = truckTypeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTruckTypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(truckTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TruckType in the database
        List<TruckType> truckTypeList = truckTypeRepository.findAll();
        assertThat(truckTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = truckTypeRepository.findAll().size();
        // set the field null
        truckType.setCode(null);

        // Create the TruckType, which fails.
        TruckTypeDTO truckTypeDTO = truckTypeMapper.toDto(truckType);

        restTruckTypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(truckTypeDTO)))
            .andExpect(status().isBadRequest());

        List<TruckType> truckTypeList = truckTypeRepository.findAll();
        assertThat(truckTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = truckTypeRepository.findAll().size();
        // set the field null
        truckType.setName(null);

        // Create the TruckType, which fails.
        TruckTypeDTO truckTypeDTO = truckTypeMapper.toDto(truckType);

        restTruckTypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(truckTypeDTO)))
            .andExpect(status().isBadRequest());

        List<TruckType> truckTypeList = truckTypeRepository.findAll();
        assertThat(truckTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void getAllTruckTypes() throws Exception {
        // Initialize the database
        truckTypeRepository.save(truckType);

        // Get all the truckTypeList
        restTruckTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(truckType.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY)))
            .andExpect(jsonPath("$.[*].height").value(hasItem(DEFAULT_HEIGHT)))
            .andExpect(jsonPath("$.[*].length").value(hasItem(DEFAULT_LENGTH)))
            .andExpect(jsonPath("$.[*].maxSpeed").value(hasItem(DEFAULT_MAX_SPEED.doubleValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].weight").value(hasItem(DEFAULT_WEIGHT)))
            .andExpect(jsonPath("$.[*].width").value(hasItem(DEFAULT_WIDTH)));
    }

    @Test
    void getTruckType() throws Exception {
        // Initialize the database
        truckTypeRepository.save(truckType);

        // Get the truckType
        restTruckTypeMockMvc
            .perform(get(ENTITY_API_URL_ID, truckType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(truckType.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.category").value(DEFAULT_CATEGORY))
            .andExpect(jsonPath("$.height").value(DEFAULT_HEIGHT))
            .andExpect(jsonPath("$.length").value(DEFAULT_LENGTH))
            .andExpect(jsonPath("$.maxSpeed").value(DEFAULT_MAX_SPEED.doubleValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.weight").value(DEFAULT_WEIGHT))
            .andExpect(jsonPath("$.width").value(DEFAULT_WIDTH));
    }

    @Test
    void getNonExistingTruckType() throws Exception {
        // Get the truckType
        restTruckTypeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingTruckType() throws Exception {
        // Initialize the database
        truckTypeRepository.save(truckType);

        int databaseSizeBeforeUpdate = truckTypeRepository.findAll().size();

        // Update the truckType
        TruckType updatedTruckType = truckTypeRepository.findById(truckType.getId()).orElseThrow();
        updatedTruckType
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .category(UPDATED_CATEGORY)
            .height(UPDATED_HEIGHT)
            .length(UPDATED_LENGTH)
            .maxSpeed(UPDATED_MAX_SPEED)
            .type(UPDATED_TYPE)
            .weight(UPDATED_WEIGHT)
            .width(UPDATED_WIDTH);
        TruckTypeDTO truckTypeDTO = truckTypeMapper.toDto(updatedTruckType);

        restTruckTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, truckTypeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(truckTypeDTO))
            )
            .andExpect(status().isOk());

        // Validate the TruckType in the database
        List<TruckType> truckTypeList = truckTypeRepository.findAll();
        assertThat(truckTypeList).hasSize(databaseSizeBeforeUpdate);
        TruckType testTruckType = truckTypeList.get(truckTypeList.size() - 1);
        assertThat(testTruckType.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testTruckType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTruckType.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testTruckType.getHeight()).isEqualTo(UPDATED_HEIGHT);
        assertThat(testTruckType.getLength()).isEqualTo(UPDATED_LENGTH);
        assertThat(testTruckType.getMaxSpeed()).isEqualTo(UPDATED_MAX_SPEED);
        assertThat(testTruckType.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testTruckType.getWeight()).isEqualTo(UPDATED_WEIGHT);
        assertThat(testTruckType.getWidth()).isEqualTo(UPDATED_WIDTH);
    }

    @Test
    void putNonExistingTruckType() throws Exception {
        int databaseSizeBeforeUpdate = truckTypeRepository.findAll().size();
        truckType.setId(longCount.incrementAndGet());

        // Create the TruckType
        TruckTypeDTO truckTypeDTO = truckTypeMapper.toDto(truckType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTruckTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, truckTypeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(truckTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TruckType in the database
        List<TruckType> truckTypeList = truckTypeRepository.findAll();
        assertThat(truckTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchTruckType() throws Exception {
        int databaseSizeBeforeUpdate = truckTypeRepository.findAll().size();
        truckType.setId(longCount.incrementAndGet());

        // Create the TruckType
        TruckTypeDTO truckTypeDTO = truckTypeMapper.toDto(truckType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTruckTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(truckTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TruckType in the database
        List<TruckType> truckTypeList = truckTypeRepository.findAll();
        assertThat(truckTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamTruckType() throws Exception {
        int databaseSizeBeforeUpdate = truckTypeRepository.findAll().size();
        truckType.setId(longCount.incrementAndGet());

        // Create the TruckType
        TruckTypeDTO truckTypeDTO = truckTypeMapper.toDto(truckType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTruckTypeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(truckTypeDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TruckType in the database
        List<TruckType> truckTypeList = truckTypeRepository.findAll();
        assertThat(truckTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateTruckTypeWithPatch() throws Exception {
        // Initialize the database
        truckTypeRepository.save(truckType);

        int databaseSizeBeforeUpdate = truckTypeRepository.findAll().size();

        // Update the truckType using partial update
        TruckType partialUpdatedTruckType = new TruckType();
        partialUpdatedTruckType.setId(truckType.getId());

        partialUpdatedTruckType
            .code(UPDATED_CODE)
            .category(UPDATED_CATEGORY)
            .height(UPDATED_HEIGHT)
            .type(UPDATED_TYPE)
            .weight(UPDATED_WEIGHT)
            .width(UPDATED_WIDTH);

        restTruckTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTruckType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTruckType))
            )
            .andExpect(status().isOk());

        // Validate the TruckType in the database
        List<TruckType> truckTypeList = truckTypeRepository.findAll();
        assertThat(truckTypeList).hasSize(databaseSizeBeforeUpdate);
        TruckType testTruckType = truckTypeList.get(truckTypeList.size() - 1);
        assertThat(testTruckType.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testTruckType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTruckType.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testTruckType.getHeight()).isEqualTo(UPDATED_HEIGHT);
        assertThat(testTruckType.getLength()).isEqualTo(DEFAULT_LENGTH);
        assertThat(testTruckType.getMaxSpeed()).isEqualTo(DEFAULT_MAX_SPEED);
        assertThat(testTruckType.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testTruckType.getWeight()).isEqualTo(UPDATED_WEIGHT);
        assertThat(testTruckType.getWidth()).isEqualTo(UPDATED_WIDTH);
    }

    @Test
    void fullUpdateTruckTypeWithPatch() throws Exception {
        // Initialize the database
        truckTypeRepository.save(truckType);

        int databaseSizeBeforeUpdate = truckTypeRepository.findAll().size();

        // Update the truckType using partial update
        TruckType partialUpdatedTruckType = new TruckType();
        partialUpdatedTruckType.setId(truckType.getId());

        partialUpdatedTruckType
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .category(UPDATED_CATEGORY)
            .height(UPDATED_HEIGHT)
            .length(UPDATED_LENGTH)
            .maxSpeed(UPDATED_MAX_SPEED)
            .type(UPDATED_TYPE)
            .weight(UPDATED_WEIGHT)
            .width(UPDATED_WIDTH);

        restTruckTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTruckType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTruckType))
            )
            .andExpect(status().isOk());

        // Validate the TruckType in the database
        List<TruckType> truckTypeList = truckTypeRepository.findAll();
        assertThat(truckTypeList).hasSize(databaseSizeBeforeUpdate);
        TruckType testTruckType = truckTypeList.get(truckTypeList.size() - 1);
        assertThat(testTruckType.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testTruckType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTruckType.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testTruckType.getHeight()).isEqualTo(UPDATED_HEIGHT);
        assertThat(testTruckType.getLength()).isEqualTo(UPDATED_LENGTH);
        assertThat(testTruckType.getMaxSpeed()).isEqualTo(UPDATED_MAX_SPEED);
        assertThat(testTruckType.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testTruckType.getWeight()).isEqualTo(UPDATED_WEIGHT);
        assertThat(testTruckType.getWidth()).isEqualTo(UPDATED_WIDTH);
    }

    @Test
    void patchNonExistingTruckType() throws Exception {
        int databaseSizeBeforeUpdate = truckTypeRepository.findAll().size();
        truckType.setId(longCount.incrementAndGet());

        // Create the TruckType
        TruckTypeDTO truckTypeDTO = truckTypeMapper.toDto(truckType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTruckTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, truckTypeDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(truckTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TruckType in the database
        List<TruckType> truckTypeList = truckTypeRepository.findAll();
        assertThat(truckTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchTruckType() throws Exception {
        int databaseSizeBeforeUpdate = truckTypeRepository.findAll().size();
        truckType.setId(longCount.incrementAndGet());

        // Create the TruckType
        TruckTypeDTO truckTypeDTO = truckTypeMapper.toDto(truckType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTruckTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(truckTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TruckType in the database
        List<TruckType> truckTypeList = truckTypeRepository.findAll();
        assertThat(truckTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamTruckType() throws Exception {
        int databaseSizeBeforeUpdate = truckTypeRepository.findAll().size();
        truckType.setId(longCount.incrementAndGet());

        // Create the TruckType
        TruckTypeDTO truckTypeDTO = truckTypeMapper.toDto(truckType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTruckTypeMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(truckTypeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TruckType in the database
        List<TruckType> truckTypeList = truckTypeRepository.findAll();
        assertThat(truckTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteTruckType() throws Exception {
        // Initialize the database
        truckTypeRepository.save(truckType);

        int databaseSizeBeforeDelete = truckTypeRepository.findAll().size();

        // Delete the truckType
        restTruckTypeMockMvc
            .perform(delete(ENTITY_API_URL_ID, truckType.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TruckType> truckTypeList = truckTypeRepository.findAll();
        assertThat(truckTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

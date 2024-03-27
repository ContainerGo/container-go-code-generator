package vn.containergo.service.mapper;

import static vn.containergo.domain.TruckTypeAsserts.*;
import static vn.containergo.domain.TruckTypeTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TruckTypeMapperTest {

    private TruckTypeMapper truckTypeMapper;

    @BeforeEach
    void setUp() {
        truckTypeMapper = new TruckTypeMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getTruckTypeSample1();
        var actual = truckTypeMapper.toEntity(truckTypeMapper.toDto(expected));
        assertTruckTypeAllPropertiesEquals(expected, actual);
    }
}

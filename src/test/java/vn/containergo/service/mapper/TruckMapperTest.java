package vn.containergo.service.mapper;

import static vn.containergo.domain.TruckAsserts.*;
import static vn.containergo.domain.TruckTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TruckMapperTest {

    private TruckMapper truckMapper;

    @BeforeEach
    void setUp() {
        truckMapper = new TruckMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getTruckSample1();
        var actual = truckMapper.toEntity(truckMapper.toDto(expected));
        assertTruckAllPropertiesEquals(expected, actual);
    }
}

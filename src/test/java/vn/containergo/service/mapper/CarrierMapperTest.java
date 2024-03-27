package vn.containergo.service.mapper;

import static vn.containergo.domain.CarrierAsserts.*;
import static vn.containergo.domain.CarrierTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CarrierMapperTest {

    private CarrierMapper carrierMapper;

    @BeforeEach
    void setUp() {
        carrierMapper = new CarrierMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getCarrierSample1();
        var actual = carrierMapper.toEntity(carrierMapper.toDto(expected));
        assertCarrierAllPropertiesEquals(expected, actual);
    }
}

package vn.containergo.service.mapper;

import static vn.containergo.domain.CarrierPersonAsserts.*;
import static vn.containergo.domain.CarrierPersonTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CarrierPersonMapperTest {

    private CarrierPersonMapper carrierPersonMapper;

    @BeforeEach
    void setUp() {
        carrierPersonMapper = new CarrierPersonMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getCarrierPersonSample1();
        var actual = carrierPersonMapper.toEntity(carrierPersonMapper.toDto(expected));
        assertCarrierPersonAllPropertiesEquals(expected, actual);
    }
}

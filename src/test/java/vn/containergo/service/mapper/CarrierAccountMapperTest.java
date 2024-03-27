package vn.containergo.service.mapper;

import static vn.containergo.domain.CarrierAccountAsserts.*;
import static vn.containergo.domain.CarrierAccountTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CarrierAccountMapperTest {

    private CarrierAccountMapper carrierAccountMapper;

    @BeforeEach
    void setUp() {
        carrierAccountMapper = new CarrierAccountMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getCarrierAccountSample1();
        var actual = carrierAccountMapper.toEntity(carrierAccountMapper.toDto(expected));
        assertCarrierAccountAllPropertiesEquals(expected, actual);
    }
}

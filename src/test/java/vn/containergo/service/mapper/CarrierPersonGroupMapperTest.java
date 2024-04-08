package vn.containergo.service.mapper;

import static vn.containergo.domain.CarrierPersonGroupAsserts.*;
import static vn.containergo.domain.CarrierPersonGroupTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CarrierPersonGroupMapperTest {

    private CarrierPersonGroupMapper carrierPersonGroupMapper;

    @BeforeEach
    void setUp() {
        carrierPersonGroupMapper = new CarrierPersonGroupMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getCarrierPersonGroupSample1();
        var actual = carrierPersonGroupMapper.toEntity(carrierPersonGroupMapper.toDto(expected));
        assertCarrierPersonGroupAllPropertiesEquals(expected, actual);
    }
}

package vn.containergo.service.mapper;

import static vn.containergo.domain.ShipperPersonGroupAsserts.*;
import static vn.containergo.domain.ShipperPersonGroupTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShipperPersonGroupMapperTest {

    private ShipperPersonGroupMapper shipperPersonGroupMapper;

    @BeforeEach
    void setUp() {
        shipperPersonGroupMapper = new ShipperPersonGroupMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getShipperPersonGroupSample1();
        var actual = shipperPersonGroupMapper.toEntity(shipperPersonGroupMapper.toDto(expected));
        assertShipperPersonGroupAllPropertiesEquals(expected, actual);
    }
}

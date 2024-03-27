package vn.containergo.service.mapper;

import static vn.containergo.domain.ShipperAsserts.*;
import static vn.containergo.domain.ShipperTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShipperMapperTest {

    private ShipperMapper shipperMapper;

    @BeforeEach
    void setUp() {
        shipperMapper = new ShipperMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getShipperSample1();
        var actual = shipperMapper.toEntity(shipperMapper.toDto(expected));
        assertShipperAllPropertiesEquals(expected, actual);
    }
}

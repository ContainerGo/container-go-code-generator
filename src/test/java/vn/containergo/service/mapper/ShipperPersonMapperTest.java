package vn.containergo.service.mapper;

import static vn.containergo.domain.ShipperPersonAsserts.*;
import static vn.containergo.domain.ShipperPersonTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShipperPersonMapperTest {

    private ShipperPersonMapper shipperPersonMapper;

    @BeforeEach
    void setUp() {
        shipperPersonMapper = new ShipperPersonMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getShipperPersonSample1();
        var actual = shipperPersonMapper.toEntity(shipperPersonMapper.toDto(expected));
        assertShipperPersonAllPropertiesEquals(expected, actual);
    }
}

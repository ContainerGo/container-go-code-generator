package vn.containergo.service.mapper;

import static vn.containergo.domain.ShipperAccountAsserts.*;
import static vn.containergo.domain.ShipperAccountTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShipperAccountMapperTest {

    private ShipperAccountMapper shipperAccountMapper;

    @BeforeEach
    void setUp() {
        shipperAccountMapper = new ShipperAccountMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getShipperAccountSample1();
        var actual = shipperAccountMapper.toEntity(shipperAccountMapper.toDto(expected));
        assertShipperAccountAllPropertiesEquals(expected, actual);
    }
}

package vn.containergo.service.mapper;

import static vn.containergo.domain.ShipperNotificationAsserts.*;
import static vn.containergo.domain.ShipperNotificationTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShipperNotificationMapperTest {

    private ShipperNotificationMapper shipperNotificationMapper;

    @BeforeEach
    void setUp() {
        shipperNotificationMapper = new ShipperNotificationMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getShipperNotificationSample1();
        var actual = shipperNotificationMapper.toEntity(shipperNotificationMapper.toDto(expected));
        assertShipperNotificationAllPropertiesEquals(expected, actual);
    }
}

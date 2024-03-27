package vn.containergo.service.mapper;

import static vn.containergo.domain.ShipmentHistoryAsserts.*;
import static vn.containergo.domain.ShipmentHistoryTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShipmentHistoryMapperTest {

    private ShipmentHistoryMapper shipmentHistoryMapper;

    @BeforeEach
    void setUp() {
        shipmentHistoryMapper = new ShipmentHistoryMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getShipmentHistorySample1();
        var actual = shipmentHistoryMapper.toEntity(shipmentHistoryMapper.toDto(expected));
        assertShipmentHistoryAllPropertiesEquals(expected, actual);
    }
}

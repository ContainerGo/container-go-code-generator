package vn.containergo.service.mapper;

import static vn.containergo.domain.ShipmentPlanAsserts.*;
import static vn.containergo.domain.ShipmentPlanTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShipmentPlanMapperTest {

    private ShipmentPlanMapper shipmentPlanMapper;

    @BeforeEach
    void setUp() {
        shipmentPlanMapper = new ShipmentPlanMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getShipmentPlanSample1();
        var actual = shipmentPlanMapper.toEntity(shipmentPlanMapper.toDto(expected));
        assertShipmentPlanAllPropertiesEquals(expected, actual);
    }
}

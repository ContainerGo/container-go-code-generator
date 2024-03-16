package vn.containergo.service.mapper;

import org.junit.jupiter.api.BeforeEach;

class ShipperPersonMapperTest {

    private ShipperPersonMapper shipperPersonMapper;

    @BeforeEach
    public void setUp() {
        shipperPersonMapper = new ShipperPersonMapperImpl();
    }
}

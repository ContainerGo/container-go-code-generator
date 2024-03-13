package vn.containergo.service.mapper;

import org.junit.jupiter.api.BeforeEach;

class ShipperAccountMapperTest {

    private ShipperAccountMapper shipperAccountMapper;

    @BeforeEach
    public void setUp() {
        shipperAccountMapper = new ShipperAccountMapperImpl();
    }
}

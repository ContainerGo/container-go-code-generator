package vn.containergo.service.mapper;

import org.junit.jupiter.api.BeforeEach;

class TruckMapperTest {

    private TruckMapper truckMapper;

    @BeforeEach
    public void setUp() {
        truckMapper = new TruckMapperImpl();
    }
}

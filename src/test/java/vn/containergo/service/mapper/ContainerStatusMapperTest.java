package vn.containergo.service.mapper;

import org.junit.jupiter.api.BeforeEach;

class ContainerStatusMapperTest {

    private ContainerStatusMapper containerStatusMapper;

    @BeforeEach
    public void setUp() {
        containerStatusMapper = new ContainerStatusMapperImpl();
    }
}

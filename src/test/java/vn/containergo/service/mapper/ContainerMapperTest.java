package vn.containergo.service.mapper;

import org.junit.jupiter.api.BeforeEach;

class ContainerMapperTest {

    private ContainerMapper containerMapper;

    @BeforeEach
    public void setUp() {
        containerMapper = new ContainerMapperImpl();
    }
}

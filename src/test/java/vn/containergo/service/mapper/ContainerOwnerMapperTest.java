package vn.containergo.service.mapper;

import static vn.containergo.domain.ContainerOwnerAsserts.*;
import static vn.containergo.domain.ContainerOwnerTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ContainerOwnerMapperTest {

    private ContainerOwnerMapper containerOwnerMapper;

    @BeforeEach
    void setUp() {
        containerOwnerMapper = new ContainerOwnerMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getContainerOwnerSample1();
        var actual = containerOwnerMapper.toEntity(containerOwnerMapper.toDto(expected));
        assertContainerOwnerAllPropertiesEquals(expected, actual);
    }
}

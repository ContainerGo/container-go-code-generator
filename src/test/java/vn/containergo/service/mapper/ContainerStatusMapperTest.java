package vn.containergo.service.mapper;

import static vn.containergo.domain.ContainerStatusAsserts.*;
import static vn.containergo.domain.ContainerStatusTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ContainerStatusMapperTest {

    private ContainerStatusMapper containerStatusMapper;

    @BeforeEach
    void setUp() {
        containerStatusMapper = new ContainerStatusMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getContainerStatusSample1();
        var actual = containerStatusMapper.toEntity(containerStatusMapper.toDto(expected));
        assertContainerStatusAllPropertiesEquals(expected, actual);
    }
}

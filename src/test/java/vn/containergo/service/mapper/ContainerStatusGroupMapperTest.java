package vn.containergo.service.mapper;

import static vn.containergo.domain.ContainerStatusGroupAsserts.*;
import static vn.containergo.domain.ContainerStatusGroupTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ContainerStatusGroupMapperTest {

    private ContainerStatusGroupMapper containerStatusGroupMapper;

    @BeforeEach
    void setUp() {
        containerStatusGroupMapper = new ContainerStatusGroupMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getContainerStatusGroupSample1();
        var actual = containerStatusGroupMapper.toEntity(containerStatusGroupMapper.toDto(expected));
        assertContainerStatusGroupAllPropertiesEquals(expected, actual);
    }
}

package vn.containergo.service.mapper;

import static vn.containergo.domain.ContainerTypeAsserts.*;
import static vn.containergo.domain.ContainerTypeTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ContainerTypeMapperTest {

    private ContainerTypeMapper containerTypeMapper;

    @BeforeEach
    void setUp() {
        containerTypeMapper = new ContainerTypeMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getContainerTypeSample1();
        var actual = containerTypeMapper.toEntity(containerTypeMapper.toDto(expected));
        assertContainerTypeAllPropertiesEquals(expected, actual);
    }
}

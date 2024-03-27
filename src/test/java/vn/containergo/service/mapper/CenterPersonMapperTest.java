package vn.containergo.service.mapper;

import static vn.containergo.domain.CenterPersonAsserts.*;
import static vn.containergo.domain.CenterPersonTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CenterPersonMapperTest {

    private CenterPersonMapper centerPersonMapper;

    @BeforeEach
    void setUp() {
        centerPersonMapper = new CenterPersonMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getCenterPersonSample1();
        var actual = centerPersonMapper.toEntity(centerPersonMapper.toDto(expected));
        assertCenterPersonAllPropertiesEquals(expected, actual);
    }
}

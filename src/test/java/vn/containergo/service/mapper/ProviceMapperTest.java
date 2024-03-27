package vn.containergo.service.mapper;

import static vn.containergo.domain.ProviceAsserts.*;
import static vn.containergo.domain.ProviceTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProviceMapperTest {

    private ProviceMapper proviceMapper;

    @BeforeEach
    void setUp() {
        proviceMapper = new ProviceMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getProviceSample1();
        var actual = proviceMapper.toEntity(proviceMapper.toDto(expected));
        assertProviceAllPropertiesEquals(expected, actual);
    }
}

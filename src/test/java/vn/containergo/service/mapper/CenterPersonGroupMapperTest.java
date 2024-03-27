package vn.containergo.service.mapper;

import static vn.containergo.domain.CenterPersonGroupAsserts.*;
import static vn.containergo.domain.CenterPersonGroupTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CenterPersonGroupMapperTest {

    private CenterPersonGroupMapper centerPersonGroupMapper;

    @BeforeEach
    void setUp() {
        centerPersonGroupMapper = new CenterPersonGroupMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getCenterPersonGroupSample1();
        var actual = centerPersonGroupMapper.toEntity(centerPersonGroupMapper.toDto(expected));
        assertCenterPersonGroupAllPropertiesEquals(expected, actual);
    }
}

package vn.containergo.service.mapper;

import static vn.containergo.domain.OfferAsserts.*;
import static vn.containergo.domain.OfferTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OfferMapperTest {

    private OfferMapper offerMapper;

    @BeforeEach
    void setUp() {
        offerMapper = new OfferMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getOfferSample1();
        var actual = offerMapper.toEntity(offerMapper.toDto(expected));
        assertOfferAllPropertiesEquals(expected, actual);
    }
}

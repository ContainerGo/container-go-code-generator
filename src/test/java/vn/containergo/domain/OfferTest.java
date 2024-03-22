package vn.containergo.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static vn.containergo.domain.ContainerTestSamples.*;
import static vn.containergo.domain.OfferTestSamples.*;

import org.junit.jupiter.api.Test;
import vn.containergo.web.rest.TestUtil;

class OfferTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Offer.class);
        Offer offer1 = getOfferSample1();
        Offer offer2 = new Offer();
        assertThat(offer1).isNotEqualTo(offer2);

        offer2.setId(offer1.getId());
        assertThat(offer1).isEqualTo(offer2);

        offer2 = getOfferSample2();
        assertThat(offer1).isNotEqualTo(offer2);
    }

    @Test
    void containerTest() throws Exception {
        Offer offer = getOfferRandomSampleGenerator();
        Container containerBack = getContainerRandomSampleGenerator();

        offer.setContainer(containerBack);
        assertThat(offer.getContainer()).isEqualTo(containerBack);

        offer.container(null);
        assertThat(offer.getContainer()).isNull();
    }
}

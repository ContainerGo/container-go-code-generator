package vn.containergo.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static vn.containergo.domain.CarrierTestSamples.*;

import org.junit.jupiter.api.Test;
import vn.containergo.web.rest.TestUtil;

class CarrierTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Carrier.class);
        Carrier carrier1 = getCarrierSample1();
        Carrier carrier2 = new Carrier();
        assertThat(carrier1).isNotEqualTo(carrier2);

        carrier2.setId(carrier1.getId());
        assertThat(carrier1).isEqualTo(carrier2);

        carrier2 = getCarrierSample2();
        assertThat(carrier1).isNotEqualTo(carrier2);
    }
}

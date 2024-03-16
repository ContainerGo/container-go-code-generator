package vn.containergo.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static vn.containergo.domain.CarrierPersonTestSamples.*;
import static vn.containergo.domain.CarrierTestSamples.*;

import org.junit.jupiter.api.Test;
import vn.containergo.web.rest.TestUtil;

class CarrierPersonTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CarrierPerson.class);
        CarrierPerson carrierPerson1 = getCarrierPersonSample1();
        CarrierPerson carrierPerson2 = new CarrierPerson();
        assertThat(carrierPerson1).isNotEqualTo(carrierPerson2);

        carrierPerson2.setId(carrierPerson1.getId());
        assertThat(carrierPerson1).isEqualTo(carrierPerson2);

        carrierPerson2 = getCarrierPersonSample2();
        assertThat(carrierPerson1).isNotEqualTo(carrierPerson2);
    }

    @Test
    void carrierTest() throws Exception {
        CarrierPerson carrierPerson = getCarrierPersonRandomSampleGenerator();
        Carrier carrierBack = getCarrierRandomSampleGenerator();

        carrierPerson.setCarrier(carrierBack);
        assertThat(carrierPerson.getCarrier()).isEqualTo(carrierBack);

        carrierPerson.carrier(null);
        assertThat(carrierPerson.getCarrier()).isNull();
    }
}

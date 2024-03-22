package vn.containergo.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static vn.containergo.domain.CarrierPersonTestSamples.*;
import static vn.containergo.domain.CarrierTestSamples.*;
import static vn.containergo.domain.TruckTestSamples.*;

import java.util.HashSet;
import java.util.Set;
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

    @Test
    void truckTest() throws Exception {
        Carrier carrier = getCarrierRandomSampleGenerator();
        Truck truckBack = getTruckRandomSampleGenerator();

        carrier.addTruck(truckBack);
        assertThat(carrier.getTrucks()).containsOnly(truckBack);
        assertThat(truckBack.getCarrier()).isEqualTo(carrier);

        carrier.removeTruck(truckBack);
        assertThat(carrier.getTrucks()).doesNotContain(truckBack);
        assertThat(truckBack.getCarrier()).isNull();

        carrier.trucks(new HashSet<>(Set.of(truckBack)));
        assertThat(carrier.getTrucks()).containsOnly(truckBack);
        assertThat(truckBack.getCarrier()).isEqualTo(carrier);

        carrier.setTrucks(new HashSet<>());
        assertThat(carrier.getTrucks()).doesNotContain(truckBack);
        assertThat(truckBack.getCarrier()).isNull();
    }

    @Test
    void carrierPersonTest() throws Exception {
        Carrier carrier = getCarrierRandomSampleGenerator();
        CarrierPerson carrierPersonBack = getCarrierPersonRandomSampleGenerator();

        carrier.addCarrierPerson(carrierPersonBack);
        assertThat(carrier.getCarrierPeople()).containsOnly(carrierPersonBack);
        assertThat(carrierPersonBack.getCarrier()).isEqualTo(carrier);

        carrier.removeCarrierPerson(carrierPersonBack);
        assertThat(carrier.getCarrierPeople()).doesNotContain(carrierPersonBack);
        assertThat(carrierPersonBack.getCarrier()).isNull();

        carrier.carrierPeople(new HashSet<>(Set.of(carrierPersonBack)));
        assertThat(carrier.getCarrierPeople()).containsOnly(carrierPersonBack);
        assertThat(carrierPersonBack.getCarrier()).isEqualTo(carrier);

        carrier.setCarrierPeople(new HashSet<>());
        assertThat(carrier.getCarrierPeople()).doesNotContain(carrierPersonBack);
        assertThat(carrierPersonBack.getCarrier()).isNull();
    }
}

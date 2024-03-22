package vn.containergo.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static vn.containergo.domain.CarrierTestSamples.*;
import static vn.containergo.domain.TruckTestSamples.*;
import static vn.containergo.domain.TruckTypeTestSamples.*;

import org.junit.jupiter.api.Test;
import vn.containergo.web.rest.TestUtil;

class TruckTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Truck.class);
        Truck truck1 = getTruckSample1();
        Truck truck2 = new Truck();
        assertThat(truck1).isNotEqualTo(truck2);

        truck2.setId(truck1.getId());
        assertThat(truck1).isEqualTo(truck2);

        truck2 = getTruckSample2();
        assertThat(truck1).isNotEqualTo(truck2);
    }

    @Test
    void typeTest() throws Exception {
        Truck truck = getTruckRandomSampleGenerator();
        TruckType truckTypeBack = getTruckTypeRandomSampleGenerator();

        truck.setType(truckTypeBack);
        assertThat(truck.getType()).isEqualTo(truckTypeBack);

        truck.type(null);
        assertThat(truck.getType()).isNull();
    }

    @Test
    void carrierTest() throws Exception {
        Truck truck = getTruckRandomSampleGenerator();
        Carrier carrierBack = getCarrierRandomSampleGenerator();

        truck.setCarrier(carrierBack);
        assertThat(truck.getCarrier()).isEqualTo(carrierBack);

        truck.carrier(null);
        assertThat(truck.getCarrier()).isNull();
    }
}

package vn.containergo.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static vn.containergo.domain.TruckTestSamples.*;

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
}

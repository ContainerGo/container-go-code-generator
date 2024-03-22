package vn.containergo.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static vn.containergo.domain.ShipperPersonTestSamples.*;
import static vn.containergo.domain.ShipperTestSamples.*;

import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;
import vn.containergo.web.rest.TestUtil;

class ShipperTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Shipper.class);
        Shipper shipper1 = getShipperSample1();
        Shipper shipper2 = new Shipper();
        assertThat(shipper1).isNotEqualTo(shipper2);

        shipper2.setId(shipper1.getId());
        assertThat(shipper1).isEqualTo(shipper2);

        shipper2 = getShipperSample2();
        assertThat(shipper1).isNotEqualTo(shipper2);
    }

    @Test
    void shipperPersonTest() throws Exception {
        Shipper shipper = getShipperRandomSampleGenerator();
        ShipperPerson shipperPersonBack = getShipperPersonRandomSampleGenerator();

        shipper.addShipperPerson(shipperPersonBack);
        assertThat(shipper.getShipperPeople()).containsOnly(shipperPersonBack);
        assertThat(shipperPersonBack.getShipper()).isEqualTo(shipper);

        shipper.removeShipperPerson(shipperPersonBack);
        assertThat(shipper.getShipperPeople()).doesNotContain(shipperPersonBack);
        assertThat(shipperPersonBack.getShipper()).isNull();

        shipper.shipperPeople(new HashSet<>(Set.of(shipperPersonBack)));
        assertThat(shipper.getShipperPeople()).containsOnly(shipperPersonBack);
        assertThat(shipperPersonBack.getShipper()).isEqualTo(shipper);

        shipper.setShipperPeople(new HashSet<>());
        assertThat(shipper.getShipperPeople()).doesNotContain(shipperPersonBack);
        assertThat(shipperPersonBack.getShipper()).isNull();
    }
}

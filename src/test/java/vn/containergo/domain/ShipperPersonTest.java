package vn.containergo.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static vn.containergo.domain.ShipperPersonTestSamples.*;
import static vn.containergo.domain.ShipperTestSamples.*;

import org.junit.jupiter.api.Test;
import vn.containergo.web.rest.TestUtil;

class ShipperPersonTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ShipperPerson.class);
        ShipperPerson shipperPerson1 = getShipperPersonSample1();
        ShipperPerson shipperPerson2 = new ShipperPerson();
        assertThat(shipperPerson1).isNotEqualTo(shipperPerson2);

        shipperPerson2.setId(shipperPerson1.getId());
        assertThat(shipperPerson1).isEqualTo(shipperPerson2);

        shipperPerson2 = getShipperPersonSample2();
        assertThat(shipperPerson1).isNotEqualTo(shipperPerson2);
    }

    @Test
    void shipperTest() throws Exception {
        ShipperPerson shipperPerson = getShipperPersonRandomSampleGenerator();
        Shipper shipperBack = getShipperRandomSampleGenerator();

        shipperPerson.setShipper(shipperBack);
        assertThat(shipperPerson.getShipper()).isEqualTo(shipperBack);

        shipperPerson.shipper(null);
        assertThat(shipperPerson.getShipper()).isNull();
    }
}

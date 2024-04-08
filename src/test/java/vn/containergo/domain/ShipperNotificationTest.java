package vn.containergo.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static vn.containergo.domain.ShipperNotificationTestSamples.*;
import static vn.containergo.domain.ShipperPersonTestSamples.*;

import org.junit.jupiter.api.Test;
import vn.containergo.web.rest.TestUtil;

class ShipperNotificationTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ShipperNotification.class);
        ShipperNotification shipperNotification1 = getShipperNotificationSample1();
        ShipperNotification shipperNotification2 = new ShipperNotification();
        assertThat(shipperNotification1).isNotEqualTo(shipperNotification2);

        shipperNotification2.setId(shipperNotification1.getId());
        assertThat(shipperNotification1).isEqualTo(shipperNotification2);

        shipperNotification2 = getShipperNotificationSample2();
        assertThat(shipperNotification1).isNotEqualTo(shipperNotification2);
    }

    @Test
    void personTest() throws Exception {
        ShipperNotification shipperNotification = getShipperNotificationRandomSampleGenerator();
        ShipperPerson shipperPersonBack = getShipperPersonRandomSampleGenerator();

        shipperNotification.setPerson(shipperPersonBack);
        assertThat(shipperNotification.getPerson()).isEqualTo(shipperPersonBack);

        shipperNotification.person(null);
        assertThat(shipperNotification.getPerson()).isNull();
    }
}

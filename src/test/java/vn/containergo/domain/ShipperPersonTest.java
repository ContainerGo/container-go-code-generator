package vn.containergo.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static vn.containergo.domain.ShipperNotificationTestSamples.*;
import static vn.containergo.domain.ShipperPersonGroupTestSamples.*;
import static vn.containergo.domain.ShipperPersonTestSamples.*;
import static vn.containergo.domain.ShipperTestSamples.*;

import java.util.HashSet;
import java.util.Set;
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
    void enabledNotificationsTest() throws Exception {
        ShipperPerson shipperPerson = getShipperPersonRandomSampleGenerator();
        ShipperNotification shipperNotificationBack = getShipperNotificationRandomSampleGenerator();

        shipperPerson.addEnabledNotifications(shipperNotificationBack);
        assertThat(shipperPerson.getEnabledNotifications()).containsOnly(shipperNotificationBack);
        assertThat(shipperNotificationBack.getPerson()).isEqualTo(shipperPerson);

        shipperPerson.removeEnabledNotifications(shipperNotificationBack);
        assertThat(shipperPerson.getEnabledNotifications()).doesNotContain(shipperNotificationBack);
        assertThat(shipperNotificationBack.getPerson()).isNull();

        shipperPerson.enabledNotifications(new HashSet<>(Set.of(shipperNotificationBack)));
        assertThat(shipperPerson.getEnabledNotifications()).containsOnly(shipperNotificationBack);
        assertThat(shipperNotificationBack.getPerson()).isEqualTo(shipperPerson);

        shipperPerson.setEnabledNotifications(new HashSet<>());
        assertThat(shipperPerson.getEnabledNotifications()).doesNotContain(shipperNotificationBack);
        assertThat(shipperNotificationBack.getPerson()).isNull();
    }

    @Test
    void groupTest() throws Exception {
        ShipperPerson shipperPerson = getShipperPersonRandomSampleGenerator();
        ShipperPersonGroup shipperPersonGroupBack = getShipperPersonGroupRandomSampleGenerator();

        shipperPerson.setGroup(shipperPersonGroupBack);
        assertThat(shipperPerson.getGroup()).isEqualTo(shipperPersonGroupBack);

        shipperPerson.group(null);
        assertThat(shipperPerson.getGroup()).isNull();
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

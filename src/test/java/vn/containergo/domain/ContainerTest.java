package vn.containergo.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static vn.containergo.domain.ContainerOwnerTestSamples.*;
import static vn.containergo.domain.ContainerStatusTestSamples.*;
import static vn.containergo.domain.ContainerTestSamples.*;
import static vn.containergo.domain.ContainerTypeTestSamples.*;
import static vn.containergo.domain.DistrictTestSamples.*;
import static vn.containergo.domain.ProviceTestSamples.*;
import static vn.containergo.domain.ShipmentPlanTestSamples.*;
import static vn.containergo.domain.TruckTestSamples.*;
import static vn.containergo.domain.TruckTypeTestSamples.*;
import static vn.containergo.domain.WardTestSamples.*;

import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;
import vn.containergo.web.rest.TestUtil;

class ContainerTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Container.class);
        Container container1 = getContainerSample1();
        Container container2 = new Container();
        assertThat(container1).isNotEqualTo(container2);

        container2.setId(container1.getId());
        assertThat(container1).isEqualTo(container2);

        container2 = getContainerSample2();
        assertThat(container1).isNotEqualTo(container2);
    }

    @Test
    void shipmentPlanTest() throws Exception {
        Container container = getContainerRandomSampleGenerator();
        ShipmentPlan shipmentPlanBack = getShipmentPlanRandomSampleGenerator();

        container.addShipmentPlan(shipmentPlanBack);
        assertThat(container.getShipmentPlans()).containsOnly(shipmentPlanBack);
        assertThat(shipmentPlanBack.getContainer()).isEqualTo(container);

        container.removeShipmentPlan(shipmentPlanBack);
        assertThat(container.getShipmentPlans()).doesNotContain(shipmentPlanBack);
        assertThat(shipmentPlanBack.getContainer()).isNull();

        container.shipmentPlans(new HashSet<>(Set.of(shipmentPlanBack)));
        assertThat(container.getShipmentPlans()).containsOnly(shipmentPlanBack);
        assertThat(shipmentPlanBack.getContainer()).isEqualTo(container);

        container.setShipmentPlans(new HashSet<>());
        assertThat(container.getShipmentPlans()).doesNotContain(shipmentPlanBack);
        assertThat(shipmentPlanBack.getContainer()).isNull();
    }

    @Test
    void pickupProviceTest() throws Exception {
        Container container = getContainerRandomSampleGenerator();
        Provice proviceBack = getProviceRandomSampleGenerator();

        container.setPickupProvice(proviceBack);
        assertThat(container.getPickupProvice()).isEqualTo(proviceBack);

        container.pickupProvice(null);
        assertThat(container.getPickupProvice()).isNull();
    }

    @Test
    void pickupDistrictTest() throws Exception {
        Container container = getContainerRandomSampleGenerator();
        District districtBack = getDistrictRandomSampleGenerator();

        container.setPickupDistrict(districtBack);
        assertThat(container.getPickupDistrict()).isEqualTo(districtBack);

        container.pickupDistrict(null);
        assertThat(container.getPickupDistrict()).isNull();
    }

    @Test
    void pickupWardTest() throws Exception {
        Container container = getContainerRandomSampleGenerator();
        Ward wardBack = getWardRandomSampleGenerator();

        container.setPickupWard(wardBack);
        assertThat(container.getPickupWard()).isEqualTo(wardBack);

        container.pickupWard(null);
        assertThat(container.getPickupWard()).isNull();
    }

    @Test
    void dropoffProviceTest() throws Exception {
        Container container = getContainerRandomSampleGenerator();
        Provice proviceBack = getProviceRandomSampleGenerator();

        container.setDropoffProvice(proviceBack);
        assertThat(container.getDropoffProvice()).isEqualTo(proviceBack);

        container.dropoffProvice(null);
        assertThat(container.getDropoffProvice()).isNull();
    }

    @Test
    void dropoffDistrictTest() throws Exception {
        Container container = getContainerRandomSampleGenerator();
        District districtBack = getDistrictRandomSampleGenerator();

        container.setDropoffDistrict(districtBack);
        assertThat(container.getDropoffDistrict()).isEqualTo(districtBack);

        container.dropoffDistrict(null);
        assertThat(container.getDropoffDistrict()).isNull();
    }

    @Test
    void dropoffWardTest() throws Exception {
        Container container = getContainerRandomSampleGenerator();
        Ward wardBack = getWardRandomSampleGenerator();

        container.setDropoffWard(wardBack);
        assertThat(container.getDropoffWard()).isEqualTo(wardBack);

        container.dropoffWard(null);
        assertThat(container.getDropoffWard()).isNull();
    }

    @Test
    void typeTest() throws Exception {
        Container container = getContainerRandomSampleGenerator();
        ContainerType containerTypeBack = getContainerTypeRandomSampleGenerator();

        container.setType(containerTypeBack);
        assertThat(container.getType()).isEqualTo(containerTypeBack);

        container.type(null);
        assertThat(container.getType()).isNull();
    }

    @Test
    void statusTest() throws Exception {
        Container container = getContainerRandomSampleGenerator();
        ContainerStatus containerStatusBack = getContainerStatusRandomSampleGenerator();

        container.setStatus(containerStatusBack);
        assertThat(container.getStatus()).isEqualTo(containerStatusBack);

        container.status(null);
        assertThat(container.getStatus()).isNull();
    }

    @Test
    void truckTypeTest() throws Exception {
        Container container = getContainerRandomSampleGenerator();
        TruckType truckTypeBack = getTruckTypeRandomSampleGenerator();

        container.setTruckType(truckTypeBack);
        assertThat(container.getTruckType()).isEqualTo(truckTypeBack);

        container.truckType(null);
        assertThat(container.getTruckType()).isNull();
    }

    @Test
    void truckTest() throws Exception {
        Container container = getContainerRandomSampleGenerator();
        Truck truckBack = getTruckRandomSampleGenerator();

        container.setTruck(truckBack);
        assertThat(container.getTruck()).isEqualTo(truckBack);

        container.truck(null);
        assertThat(container.getTruck()).isNull();
    }

    @Test
    void ownerTest() throws Exception {
        Container container = getContainerRandomSampleGenerator();
        ContainerOwner containerOwnerBack = getContainerOwnerRandomSampleGenerator();

        container.setOwner(containerOwnerBack);
        assertThat(container.getOwner()).isEqualTo(containerOwnerBack);

        container.owner(null);
        assertThat(container.getOwner()).isNull();
    }
}

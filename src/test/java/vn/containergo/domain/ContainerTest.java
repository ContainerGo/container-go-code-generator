package vn.containergo.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static vn.containergo.domain.ContainerStatusTestSamples.*;
import static vn.containergo.domain.ContainerTestSamples.*;
import static vn.containergo.domain.ContainerTypeTestSamples.*;
import static vn.containergo.domain.DistrictTestSamples.*;
import static vn.containergo.domain.ProviceTestSamples.*;
import static vn.containergo.domain.TruckTypeTestSamples.*;
import static vn.containergo.domain.WardTestSamples.*;

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
}
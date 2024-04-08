package vn.containergo.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static vn.containergo.domain.ContainerTestSamples.*;
import static vn.containergo.domain.ShipmentPlanTestSamples.*;

import org.junit.jupiter.api.Test;
import vn.containergo.web.rest.TestUtil;

class ShipmentPlanTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ShipmentPlan.class);
        ShipmentPlan shipmentPlan1 = getShipmentPlanSample1();
        ShipmentPlan shipmentPlan2 = new ShipmentPlan();
        assertThat(shipmentPlan1).isNotEqualTo(shipmentPlan2);

        shipmentPlan2.setId(shipmentPlan1.getId());
        assertThat(shipmentPlan1).isEqualTo(shipmentPlan2);

        shipmentPlan2 = getShipmentPlanSample2();
        assertThat(shipmentPlan1).isNotEqualTo(shipmentPlan2);
    }

    @Test
    void containerTest() throws Exception {
        ShipmentPlan shipmentPlan = getShipmentPlanRandomSampleGenerator();
        Container containerBack = getContainerRandomSampleGenerator();

        shipmentPlan.setContainer(containerBack);
        assertThat(shipmentPlan.getContainer()).isEqualTo(containerBack);

        shipmentPlan.container(null);
        assertThat(shipmentPlan.getContainer()).isNull();
    }
}

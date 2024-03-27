package vn.containergo.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static vn.containergo.domain.ContainerTestSamples.*;
import static vn.containergo.domain.ShipmentHistoryTestSamples.*;

import org.junit.jupiter.api.Test;
import vn.containergo.web.rest.TestUtil;

class ShipmentHistoryTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ShipmentHistory.class);
        ShipmentHistory shipmentHistory1 = getShipmentHistorySample1();
        ShipmentHistory shipmentHistory2 = new ShipmentHistory();
        assertThat(shipmentHistory1).isNotEqualTo(shipmentHistory2);

        shipmentHistory2.setId(shipmentHistory1.getId());
        assertThat(shipmentHistory1).isEqualTo(shipmentHistory2);

        shipmentHistory2 = getShipmentHistorySample2();
        assertThat(shipmentHistory1).isNotEqualTo(shipmentHistory2);
    }

    @Test
    void containerTest() throws Exception {
        ShipmentHistory shipmentHistory = getShipmentHistoryRandomSampleGenerator();
        Container containerBack = getContainerRandomSampleGenerator();

        shipmentHistory.setContainer(containerBack);
        assertThat(shipmentHistory.getContainer()).isEqualTo(containerBack);

        shipmentHistory.container(null);
        assertThat(shipmentHistory.getContainer()).isNull();
    }
}

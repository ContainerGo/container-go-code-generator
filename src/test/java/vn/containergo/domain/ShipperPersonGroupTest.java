package vn.containergo.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static vn.containergo.domain.ShipperPersonGroupTestSamples.*;

import org.junit.jupiter.api.Test;
import vn.containergo.web.rest.TestUtil;

class ShipperPersonGroupTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ShipperPersonGroup.class);
        ShipperPersonGroup shipperPersonGroup1 = getShipperPersonGroupSample1();
        ShipperPersonGroup shipperPersonGroup2 = new ShipperPersonGroup();
        assertThat(shipperPersonGroup1).isNotEqualTo(shipperPersonGroup2);

        shipperPersonGroup2.setId(shipperPersonGroup1.getId());
        assertThat(shipperPersonGroup1).isEqualTo(shipperPersonGroup2);

        shipperPersonGroup2 = getShipperPersonGroupSample2();
        assertThat(shipperPersonGroup1).isNotEqualTo(shipperPersonGroup2);
    }
}

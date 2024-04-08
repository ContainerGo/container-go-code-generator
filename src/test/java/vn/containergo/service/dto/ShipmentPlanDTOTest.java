package vn.containergo.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;
import org.junit.jupiter.api.Test;
import vn.containergo.web.rest.TestUtil;

class ShipmentPlanDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ShipmentPlanDTO.class);
        ShipmentPlanDTO shipmentPlanDTO1 = new ShipmentPlanDTO();
        shipmentPlanDTO1.setId(UUID.randomUUID());
        ShipmentPlanDTO shipmentPlanDTO2 = new ShipmentPlanDTO();
        assertThat(shipmentPlanDTO1).isNotEqualTo(shipmentPlanDTO2);
        shipmentPlanDTO2.setId(shipmentPlanDTO1.getId());
        assertThat(shipmentPlanDTO1).isEqualTo(shipmentPlanDTO2);
        shipmentPlanDTO2.setId(UUID.randomUUID());
        assertThat(shipmentPlanDTO1).isNotEqualTo(shipmentPlanDTO2);
        shipmentPlanDTO1.setId(null);
        assertThat(shipmentPlanDTO1).isNotEqualTo(shipmentPlanDTO2);
    }
}

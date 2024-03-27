package vn.containergo.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import vn.containergo.web.rest.TestUtil;

class ShipmentHistoryDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ShipmentHistoryDTO.class);
        ShipmentHistoryDTO shipmentHistoryDTO1 = new ShipmentHistoryDTO();
        shipmentHistoryDTO1.setId(1L);
        ShipmentHistoryDTO shipmentHistoryDTO2 = new ShipmentHistoryDTO();
        assertThat(shipmentHistoryDTO1).isNotEqualTo(shipmentHistoryDTO2);
        shipmentHistoryDTO2.setId(shipmentHistoryDTO1.getId());
        assertThat(shipmentHistoryDTO1).isEqualTo(shipmentHistoryDTO2);
        shipmentHistoryDTO2.setId(2L);
        assertThat(shipmentHistoryDTO1).isNotEqualTo(shipmentHistoryDTO2);
        shipmentHistoryDTO1.setId(null);
        assertThat(shipmentHistoryDTO1).isNotEqualTo(shipmentHistoryDTO2);
    }
}

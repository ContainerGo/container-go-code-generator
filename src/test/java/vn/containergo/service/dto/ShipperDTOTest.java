package vn.containergo.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import vn.containergo.web.rest.TestUtil;

class ShipperDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ShipperDTO.class);
        ShipperDTO shipperDTO1 = new ShipperDTO();
        shipperDTO1.setId(1L);
        ShipperDTO shipperDTO2 = new ShipperDTO();
        assertThat(shipperDTO1).isNotEqualTo(shipperDTO2);
        shipperDTO2.setId(shipperDTO1.getId());
        assertThat(shipperDTO1).isEqualTo(shipperDTO2);
        shipperDTO2.setId(2L);
        assertThat(shipperDTO1).isNotEqualTo(shipperDTO2);
        shipperDTO1.setId(null);
        assertThat(shipperDTO1).isNotEqualTo(shipperDTO2);
    }
}

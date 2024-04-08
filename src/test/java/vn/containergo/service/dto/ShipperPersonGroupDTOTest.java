package vn.containergo.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;
import org.junit.jupiter.api.Test;
import vn.containergo.web.rest.TestUtil;

class ShipperPersonGroupDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ShipperPersonGroupDTO.class);
        ShipperPersonGroupDTO shipperPersonGroupDTO1 = new ShipperPersonGroupDTO();
        shipperPersonGroupDTO1.setId(UUID.randomUUID());
        ShipperPersonGroupDTO shipperPersonGroupDTO2 = new ShipperPersonGroupDTO();
        assertThat(shipperPersonGroupDTO1).isNotEqualTo(shipperPersonGroupDTO2);
        shipperPersonGroupDTO2.setId(shipperPersonGroupDTO1.getId());
        assertThat(shipperPersonGroupDTO1).isEqualTo(shipperPersonGroupDTO2);
        shipperPersonGroupDTO2.setId(UUID.randomUUID());
        assertThat(shipperPersonGroupDTO1).isNotEqualTo(shipperPersonGroupDTO2);
        shipperPersonGroupDTO1.setId(null);
        assertThat(shipperPersonGroupDTO1).isNotEqualTo(shipperPersonGroupDTO2);
    }
}

package vn.containergo.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;
import org.junit.jupiter.api.Test;
import vn.containergo.web.rest.TestUtil;

class ShipperPersonDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ShipperPersonDTO.class);
        ShipperPersonDTO shipperPersonDTO1 = new ShipperPersonDTO();
        shipperPersonDTO1.setId(UUID.randomUUID());
        ShipperPersonDTO shipperPersonDTO2 = new ShipperPersonDTO();
        assertThat(shipperPersonDTO1).isNotEqualTo(shipperPersonDTO2);
        shipperPersonDTO2.setId(shipperPersonDTO1.getId());
        assertThat(shipperPersonDTO1).isEqualTo(shipperPersonDTO2);
        shipperPersonDTO2.setId(UUID.randomUUID());
        assertThat(shipperPersonDTO1).isNotEqualTo(shipperPersonDTO2);
        shipperPersonDTO1.setId(null);
        assertThat(shipperPersonDTO1).isNotEqualTo(shipperPersonDTO2);
    }
}

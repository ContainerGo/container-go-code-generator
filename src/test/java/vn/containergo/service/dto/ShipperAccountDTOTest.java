package vn.containergo.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;
import org.junit.jupiter.api.Test;
import vn.containergo.web.rest.TestUtil;

class ShipperAccountDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ShipperAccountDTO.class);
        ShipperAccountDTO shipperAccountDTO1 = new ShipperAccountDTO();
        shipperAccountDTO1.setId(UUID.randomUUID());
        ShipperAccountDTO shipperAccountDTO2 = new ShipperAccountDTO();
        assertThat(shipperAccountDTO1).isNotEqualTo(shipperAccountDTO2);
        shipperAccountDTO2.setId(shipperAccountDTO1.getId());
        assertThat(shipperAccountDTO1).isEqualTo(shipperAccountDTO2);
        shipperAccountDTO2.setId(UUID.randomUUID());
        assertThat(shipperAccountDTO1).isNotEqualTo(shipperAccountDTO2);
        shipperAccountDTO1.setId(null);
        assertThat(shipperAccountDTO1).isNotEqualTo(shipperAccountDTO2);
    }
}

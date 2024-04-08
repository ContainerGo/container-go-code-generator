package vn.containergo.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;
import org.junit.jupiter.api.Test;
import vn.containergo.web.rest.TestUtil;

class ShipperNotificationDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ShipperNotificationDTO.class);
        ShipperNotificationDTO shipperNotificationDTO1 = new ShipperNotificationDTO();
        shipperNotificationDTO1.setId(UUID.randomUUID());
        ShipperNotificationDTO shipperNotificationDTO2 = new ShipperNotificationDTO();
        assertThat(shipperNotificationDTO1).isNotEqualTo(shipperNotificationDTO2);
        shipperNotificationDTO2.setId(shipperNotificationDTO1.getId());
        assertThat(shipperNotificationDTO1).isEqualTo(shipperNotificationDTO2);
        shipperNotificationDTO2.setId(UUID.randomUUID());
        assertThat(shipperNotificationDTO1).isNotEqualTo(shipperNotificationDTO2);
        shipperNotificationDTO1.setId(null);
        assertThat(shipperNotificationDTO1).isNotEqualTo(shipperNotificationDTO2);
    }
}

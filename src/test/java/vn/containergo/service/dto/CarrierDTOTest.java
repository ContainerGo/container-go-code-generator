package vn.containergo.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;
import org.junit.jupiter.api.Test;
import vn.containergo.web.rest.TestUtil;

class CarrierDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CarrierDTO.class);
        CarrierDTO carrierDTO1 = new CarrierDTO();
        carrierDTO1.setId(UUID.randomUUID());
        CarrierDTO carrierDTO2 = new CarrierDTO();
        assertThat(carrierDTO1).isNotEqualTo(carrierDTO2);
        carrierDTO2.setId(carrierDTO1.getId());
        assertThat(carrierDTO1).isEqualTo(carrierDTO2);
        carrierDTO2.setId(UUID.randomUUID());
        assertThat(carrierDTO1).isNotEqualTo(carrierDTO2);
        carrierDTO1.setId(null);
        assertThat(carrierDTO1).isNotEqualTo(carrierDTO2);
    }
}

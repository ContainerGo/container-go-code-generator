package vn.containergo.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;
import org.junit.jupiter.api.Test;
import vn.containergo.web.rest.TestUtil;

class CarrierPersonDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CarrierPersonDTO.class);
        CarrierPersonDTO carrierPersonDTO1 = new CarrierPersonDTO();
        carrierPersonDTO1.setId(UUID.randomUUID());
        CarrierPersonDTO carrierPersonDTO2 = new CarrierPersonDTO();
        assertThat(carrierPersonDTO1).isNotEqualTo(carrierPersonDTO2);
        carrierPersonDTO2.setId(carrierPersonDTO1.getId());
        assertThat(carrierPersonDTO1).isEqualTo(carrierPersonDTO2);
        carrierPersonDTO2.setId(UUID.randomUUID());
        assertThat(carrierPersonDTO1).isNotEqualTo(carrierPersonDTO2);
        carrierPersonDTO1.setId(null);
        assertThat(carrierPersonDTO1).isNotEqualTo(carrierPersonDTO2);
    }
}

package vn.containergo.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;
import org.junit.jupiter.api.Test;
import vn.containergo.web.rest.TestUtil;

class CarrierPersonGroupDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CarrierPersonGroupDTO.class);
        CarrierPersonGroupDTO carrierPersonGroupDTO1 = new CarrierPersonGroupDTO();
        carrierPersonGroupDTO1.setId(UUID.randomUUID());
        CarrierPersonGroupDTO carrierPersonGroupDTO2 = new CarrierPersonGroupDTO();
        assertThat(carrierPersonGroupDTO1).isNotEqualTo(carrierPersonGroupDTO2);
        carrierPersonGroupDTO2.setId(carrierPersonGroupDTO1.getId());
        assertThat(carrierPersonGroupDTO1).isEqualTo(carrierPersonGroupDTO2);
        carrierPersonGroupDTO2.setId(UUID.randomUUID());
        assertThat(carrierPersonGroupDTO1).isNotEqualTo(carrierPersonGroupDTO2);
        carrierPersonGroupDTO1.setId(null);
        assertThat(carrierPersonGroupDTO1).isNotEqualTo(carrierPersonGroupDTO2);
    }
}

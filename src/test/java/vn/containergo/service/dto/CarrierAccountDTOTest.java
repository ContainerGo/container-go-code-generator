package vn.containergo.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import vn.containergo.web.rest.TestUtil;

class CarrierAccountDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CarrierAccountDTO.class);
        CarrierAccountDTO carrierAccountDTO1 = new CarrierAccountDTO();
        carrierAccountDTO1.setId(1L);
        CarrierAccountDTO carrierAccountDTO2 = new CarrierAccountDTO();
        assertThat(carrierAccountDTO1).isNotEqualTo(carrierAccountDTO2);
        carrierAccountDTO2.setId(carrierAccountDTO1.getId());
        assertThat(carrierAccountDTO1).isEqualTo(carrierAccountDTO2);
        carrierAccountDTO2.setId(2L);
        assertThat(carrierAccountDTO1).isNotEqualTo(carrierAccountDTO2);
        carrierAccountDTO1.setId(null);
        assertThat(carrierAccountDTO1).isNotEqualTo(carrierAccountDTO2);
    }
}

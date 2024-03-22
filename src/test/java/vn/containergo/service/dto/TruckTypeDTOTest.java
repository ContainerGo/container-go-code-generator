package vn.containergo.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import vn.containergo.web.rest.TestUtil;

class TruckTypeDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TruckTypeDTO.class);
        TruckTypeDTO truckTypeDTO1 = new TruckTypeDTO();
        truckTypeDTO1.setId(1L);
        TruckTypeDTO truckTypeDTO2 = new TruckTypeDTO();
        assertThat(truckTypeDTO1).isNotEqualTo(truckTypeDTO2);
        truckTypeDTO2.setId(truckTypeDTO1.getId());
        assertThat(truckTypeDTO1).isEqualTo(truckTypeDTO2);
        truckTypeDTO2.setId(2L);
        assertThat(truckTypeDTO1).isNotEqualTo(truckTypeDTO2);
        truckTypeDTO1.setId(null);
        assertThat(truckTypeDTO1).isNotEqualTo(truckTypeDTO2);
    }
}

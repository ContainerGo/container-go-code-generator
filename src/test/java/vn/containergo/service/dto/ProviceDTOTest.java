package vn.containergo.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import vn.containergo.web.rest.TestUtil;

class ProviceDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProviceDTO.class);
        ProviceDTO proviceDTO1 = new ProviceDTO();
        proviceDTO1.setId(1L);
        ProviceDTO proviceDTO2 = new ProviceDTO();
        assertThat(proviceDTO1).isNotEqualTo(proviceDTO2);
        proviceDTO2.setId(proviceDTO1.getId());
        assertThat(proviceDTO1).isEqualTo(proviceDTO2);
        proviceDTO2.setId(2L);
        assertThat(proviceDTO1).isNotEqualTo(proviceDTO2);
        proviceDTO1.setId(null);
        assertThat(proviceDTO1).isNotEqualTo(proviceDTO2);
    }
}

package vn.containergo.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;
import org.junit.jupiter.api.Test;
import vn.containergo.web.rest.TestUtil;

class CenterPersonDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CenterPersonDTO.class);
        CenterPersonDTO centerPersonDTO1 = new CenterPersonDTO();
        centerPersonDTO1.setId(UUID.randomUUID());
        CenterPersonDTO centerPersonDTO2 = new CenterPersonDTO();
        assertThat(centerPersonDTO1).isNotEqualTo(centerPersonDTO2);
        centerPersonDTO2.setId(centerPersonDTO1.getId());
        assertThat(centerPersonDTO1).isEqualTo(centerPersonDTO2);
        centerPersonDTO2.setId(UUID.randomUUID());
        assertThat(centerPersonDTO1).isNotEqualTo(centerPersonDTO2);
        centerPersonDTO1.setId(null);
        assertThat(centerPersonDTO1).isNotEqualTo(centerPersonDTO2);
    }
}

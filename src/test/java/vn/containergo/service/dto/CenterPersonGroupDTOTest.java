package vn.containergo.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;
import org.junit.jupiter.api.Test;
import vn.containergo.web.rest.TestUtil;

class CenterPersonGroupDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CenterPersonGroupDTO.class);
        CenterPersonGroupDTO centerPersonGroupDTO1 = new CenterPersonGroupDTO();
        centerPersonGroupDTO1.setId(UUID.randomUUID());
        CenterPersonGroupDTO centerPersonGroupDTO2 = new CenterPersonGroupDTO();
        assertThat(centerPersonGroupDTO1).isNotEqualTo(centerPersonGroupDTO2);
        centerPersonGroupDTO2.setId(centerPersonGroupDTO1.getId());
        assertThat(centerPersonGroupDTO1).isEqualTo(centerPersonGroupDTO2);
        centerPersonGroupDTO2.setId(UUID.randomUUID());
        assertThat(centerPersonGroupDTO1).isNotEqualTo(centerPersonGroupDTO2);
        centerPersonGroupDTO1.setId(null);
        assertThat(centerPersonGroupDTO1).isNotEqualTo(centerPersonGroupDTO2);
    }
}

package vn.containergo.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;
import org.junit.jupiter.api.Test;
import vn.containergo.web.rest.TestUtil;

class ContainerStatusDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContainerStatusDTO.class);
        ContainerStatusDTO containerStatusDTO1 = new ContainerStatusDTO();
        containerStatusDTO1.setId(UUID.randomUUID());
        ContainerStatusDTO containerStatusDTO2 = new ContainerStatusDTO();
        assertThat(containerStatusDTO1).isNotEqualTo(containerStatusDTO2);
        containerStatusDTO2.setId(containerStatusDTO1.getId());
        assertThat(containerStatusDTO1).isEqualTo(containerStatusDTO2);
        containerStatusDTO2.setId(UUID.randomUUID());
        assertThat(containerStatusDTO1).isNotEqualTo(containerStatusDTO2);
        containerStatusDTO1.setId(null);
        assertThat(containerStatusDTO1).isNotEqualTo(containerStatusDTO2);
    }
}

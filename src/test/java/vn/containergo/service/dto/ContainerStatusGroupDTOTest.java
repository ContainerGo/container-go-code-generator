package vn.containergo.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;
import org.junit.jupiter.api.Test;
import vn.containergo.web.rest.TestUtil;

class ContainerStatusGroupDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContainerStatusGroupDTO.class);
        ContainerStatusGroupDTO containerStatusGroupDTO1 = new ContainerStatusGroupDTO();
        containerStatusGroupDTO1.setId(UUID.randomUUID());
        ContainerStatusGroupDTO containerStatusGroupDTO2 = new ContainerStatusGroupDTO();
        assertThat(containerStatusGroupDTO1).isNotEqualTo(containerStatusGroupDTO2);
        containerStatusGroupDTO2.setId(containerStatusGroupDTO1.getId());
        assertThat(containerStatusGroupDTO1).isEqualTo(containerStatusGroupDTO2);
        containerStatusGroupDTO2.setId(UUID.randomUUID());
        assertThat(containerStatusGroupDTO1).isNotEqualTo(containerStatusGroupDTO2);
        containerStatusGroupDTO1.setId(null);
        assertThat(containerStatusGroupDTO1).isNotEqualTo(containerStatusGroupDTO2);
    }
}

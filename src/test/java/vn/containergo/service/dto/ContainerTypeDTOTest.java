package vn.containergo.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import vn.containergo.web.rest.TestUtil;

class ContainerTypeDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContainerTypeDTO.class);
        ContainerTypeDTO containerTypeDTO1 = new ContainerTypeDTO();
        containerTypeDTO1.setId(1L);
        ContainerTypeDTO containerTypeDTO2 = new ContainerTypeDTO();
        assertThat(containerTypeDTO1).isNotEqualTo(containerTypeDTO2);
        containerTypeDTO2.setId(containerTypeDTO1.getId());
        assertThat(containerTypeDTO1).isEqualTo(containerTypeDTO2);
        containerTypeDTO2.setId(2L);
        assertThat(containerTypeDTO1).isNotEqualTo(containerTypeDTO2);
        containerTypeDTO1.setId(null);
        assertThat(containerTypeDTO1).isNotEqualTo(containerTypeDTO2);
    }
}

package vn.containergo.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import vn.containergo.web.rest.TestUtil;

class ContainerDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContainerDTO.class);
        ContainerDTO containerDTO1 = new ContainerDTO();
        containerDTO1.setId(1L);
        ContainerDTO containerDTO2 = new ContainerDTO();
        assertThat(containerDTO1).isNotEqualTo(containerDTO2);
        containerDTO2.setId(containerDTO1.getId());
        assertThat(containerDTO1).isEqualTo(containerDTO2);
        containerDTO2.setId(2L);
        assertThat(containerDTO1).isNotEqualTo(containerDTO2);
        containerDTO1.setId(null);
        assertThat(containerDTO1).isNotEqualTo(containerDTO2);
    }
}

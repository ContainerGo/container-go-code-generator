package vn.containergo.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import vn.containergo.web.rest.TestUtil;

class ContainerOwnerDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContainerOwnerDTO.class);
        ContainerOwnerDTO containerOwnerDTO1 = new ContainerOwnerDTO();
        containerOwnerDTO1.setId(1L);
        ContainerOwnerDTO containerOwnerDTO2 = new ContainerOwnerDTO();
        assertThat(containerOwnerDTO1).isNotEqualTo(containerOwnerDTO2);
        containerOwnerDTO2.setId(containerOwnerDTO1.getId());
        assertThat(containerOwnerDTO1).isEqualTo(containerOwnerDTO2);
        containerOwnerDTO2.setId(2L);
        assertThat(containerOwnerDTO1).isNotEqualTo(containerOwnerDTO2);
        containerOwnerDTO1.setId(null);
        assertThat(containerOwnerDTO1).isNotEqualTo(containerOwnerDTO2);
    }
}

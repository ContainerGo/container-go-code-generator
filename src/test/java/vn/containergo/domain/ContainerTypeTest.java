package vn.containergo.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static vn.containergo.domain.ContainerTypeTestSamples.*;

import org.junit.jupiter.api.Test;
import vn.containergo.web.rest.TestUtil;

class ContainerTypeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContainerType.class);
        ContainerType containerType1 = getContainerTypeSample1();
        ContainerType containerType2 = new ContainerType();
        assertThat(containerType1).isNotEqualTo(containerType2);

        containerType2.setId(containerType1.getId());
        assertThat(containerType1).isEqualTo(containerType2);

        containerType2 = getContainerTypeSample2();
        assertThat(containerType1).isNotEqualTo(containerType2);
    }
}

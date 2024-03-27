package vn.containergo.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static vn.containergo.domain.ContainerOwnerTestSamples.*;

import org.junit.jupiter.api.Test;
import vn.containergo.web.rest.TestUtil;

class ContainerOwnerTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContainerOwner.class);
        ContainerOwner containerOwner1 = getContainerOwnerSample1();
        ContainerOwner containerOwner2 = new ContainerOwner();
        assertThat(containerOwner1).isNotEqualTo(containerOwner2);

        containerOwner2.setId(containerOwner1.getId());
        assertThat(containerOwner1).isEqualTo(containerOwner2);

        containerOwner2 = getContainerOwnerSample2();
        assertThat(containerOwner1).isNotEqualTo(containerOwner2);
    }
}

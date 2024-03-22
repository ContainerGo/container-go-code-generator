package vn.containergo.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static vn.containergo.domain.ContainerStatusGroupTestSamples.*;
import static vn.containergo.domain.ContainerStatusTestSamples.*;

import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;
import vn.containergo.web.rest.TestUtil;

class ContainerStatusGroupTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContainerStatusGroup.class);
        ContainerStatusGroup containerStatusGroup1 = getContainerStatusGroupSample1();
        ContainerStatusGroup containerStatusGroup2 = new ContainerStatusGroup();
        assertThat(containerStatusGroup1).isNotEqualTo(containerStatusGroup2);

        containerStatusGroup2.setId(containerStatusGroup1.getId());
        assertThat(containerStatusGroup1).isEqualTo(containerStatusGroup2);

        containerStatusGroup2 = getContainerStatusGroupSample2();
        assertThat(containerStatusGroup1).isNotEqualTo(containerStatusGroup2);
    }

    @Test
    void containerStatusTest() throws Exception {
        ContainerStatusGroup containerStatusGroup = getContainerStatusGroupRandomSampleGenerator();
        ContainerStatus containerStatusBack = getContainerStatusRandomSampleGenerator();

        containerStatusGroup.addContainerStatus(containerStatusBack);
        assertThat(containerStatusGroup.getContainerStatuses()).containsOnly(containerStatusBack);
        assertThat(containerStatusBack.getGroup()).isEqualTo(containerStatusGroup);

        containerStatusGroup.removeContainerStatus(containerStatusBack);
        assertThat(containerStatusGroup.getContainerStatuses()).doesNotContain(containerStatusBack);
        assertThat(containerStatusBack.getGroup()).isNull();

        containerStatusGroup.containerStatuses(new HashSet<>(Set.of(containerStatusBack)));
        assertThat(containerStatusGroup.getContainerStatuses()).containsOnly(containerStatusBack);
        assertThat(containerStatusBack.getGroup()).isEqualTo(containerStatusGroup);

        containerStatusGroup.setContainerStatuses(new HashSet<>());
        assertThat(containerStatusGroup.getContainerStatuses()).doesNotContain(containerStatusBack);
        assertThat(containerStatusBack.getGroup()).isNull();
    }
}

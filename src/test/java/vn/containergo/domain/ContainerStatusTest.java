package vn.containergo.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static vn.containergo.domain.ContainerStatusGroupTestSamples.*;
import static vn.containergo.domain.ContainerStatusTestSamples.*;

import org.junit.jupiter.api.Test;
import vn.containergo.web.rest.TestUtil;

class ContainerStatusTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContainerStatus.class);
        ContainerStatus containerStatus1 = getContainerStatusSample1();
        ContainerStatus containerStatus2 = new ContainerStatus();
        assertThat(containerStatus1).isNotEqualTo(containerStatus2);

        containerStatus2.setId(containerStatus1.getId());
        assertThat(containerStatus1).isEqualTo(containerStatus2);

        containerStatus2 = getContainerStatusSample2();
        assertThat(containerStatus1).isNotEqualTo(containerStatus2);
    }

    @Test
    void groupTest() throws Exception {
        ContainerStatus containerStatus = getContainerStatusRandomSampleGenerator();
        ContainerStatusGroup containerStatusGroupBack = getContainerStatusGroupRandomSampleGenerator();

        containerStatus.setGroup(containerStatusGroupBack);
        assertThat(containerStatus.getGroup()).isEqualTo(containerStatusGroupBack);

        containerStatus.group(null);
        assertThat(containerStatus.getGroup()).isNull();
    }
}

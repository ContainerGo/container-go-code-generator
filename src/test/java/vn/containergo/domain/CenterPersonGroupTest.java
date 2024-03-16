package vn.containergo.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static vn.containergo.domain.CenterPersonGroupTestSamples.*;
import static vn.containergo.domain.CenterPersonTestSamples.*;

import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;
import vn.containergo.web.rest.TestUtil;

class CenterPersonGroupTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CenterPersonGroup.class);
        CenterPersonGroup centerPersonGroup1 = getCenterPersonGroupSample1();
        CenterPersonGroup centerPersonGroup2 = new CenterPersonGroup();
        assertThat(centerPersonGroup1).isNotEqualTo(centerPersonGroup2);

        centerPersonGroup2.setId(centerPersonGroup1.getId());
        assertThat(centerPersonGroup1).isEqualTo(centerPersonGroup2);

        centerPersonGroup2 = getCenterPersonGroupSample2();
        assertThat(centerPersonGroup1).isNotEqualTo(centerPersonGroup2);
    }

    @Test
    void usersTest() throws Exception {
        CenterPersonGroup centerPersonGroup = getCenterPersonGroupRandomSampleGenerator();
        CenterPerson centerPersonBack = getCenterPersonRandomSampleGenerator();

        centerPersonGroup.addUsers(centerPersonBack);
        assertThat(centerPersonGroup.getUsers()).containsOnly(centerPersonBack);
        assertThat(centerPersonBack.getGroups()).containsOnly(centerPersonGroup);

        centerPersonGroup.removeUsers(centerPersonBack);
        assertThat(centerPersonGroup.getUsers()).doesNotContain(centerPersonBack);
        assertThat(centerPersonBack.getGroups()).doesNotContain(centerPersonGroup);

        centerPersonGroup.users(new HashSet<>(Set.of(centerPersonBack)));
        assertThat(centerPersonGroup.getUsers()).containsOnly(centerPersonBack);
        assertThat(centerPersonBack.getGroups()).containsOnly(centerPersonGroup);

        centerPersonGroup.setUsers(new HashSet<>());
        assertThat(centerPersonGroup.getUsers()).doesNotContain(centerPersonBack);
        assertThat(centerPersonBack.getGroups()).doesNotContain(centerPersonGroup);
    }
}

package vn.containergo.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static vn.containergo.domain.CenterPersonGroupTestSamples.*;
import static vn.containergo.domain.CenterPersonTestSamples.*;

import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;
import vn.containergo.web.rest.TestUtil;

class CenterPersonTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CenterPerson.class);
        CenterPerson centerPerson1 = getCenterPersonSample1();
        CenterPerson centerPerson2 = new CenterPerson();
        assertThat(centerPerson1).isNotEqualTo(centerPerson2);

        centerPerson2.setId(centerPerson1.getId());
        assertThat(centerPerson1).isEqualTo(centerPerson2);

        centerPerson2 = getCenterPersonSample2();
        assertThat(centerPerson1).isNotEqualTo(centerPerson2);
    }

    @Test
    void groupsTest() throws Exception {
        CenterPerson centerPerson = getCenterPersonRandomSampleGenerator();
        CenterPersonGroup centerPersonGroupBack = getCenterPersonGroupRandomSampleGenerator();

        centerPerson.addGroups(centerPersonGroupBack);
        assertThat(centerPerson.getGroups()).containsOnly(centerPersonGroupBack);

        centerPerson.removeGroups(centerPersonGroupBack);
        assertThat(centerPerson.getGroups()).doesNotContain(centerPersonGroupBack);

        centerPerson.groups(new HashSet<>(Set.of(centerPersonGroupBack)));
        assertThat(centerPerson.getGroups()).containsOnly(centerPersonGroupBack);

        centerPerson.setGroups(new HashSet<>());
        assertThat(centerPerson.getGroups()).doesNotContain(centerPersonGroupBack);
    }
}

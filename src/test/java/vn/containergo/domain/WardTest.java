package vn.containergo.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static vn.containergo.domain.WardTestSamples.*;

import org.junit.jupiter.api.Test;
import vn.containergo.web.rest.TestUtil;

class WardTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Ward.class);
        Ward ward1 = getWardSample1();
        Ward ward2 = new Ward();
        assertThat(ward1).isNotEqualTo(ward2);

        ward2.setId(ward1.getId());
        assertThat(ward1).isEqualTo(ward2);

        ward2 = getWardSample2();
        assertThat(ward1).isNotEqualTo(ward2);
    }
}

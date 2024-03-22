package vn.containergo.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static vn.containergo.domain.ProviceTestSamples.*;

import org.junit.jupiter.api.Test;
import vn.containergo.web.rest.TestUtil;

class ProviceTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Provice.class);
        Provice provice1 = getProviceSample1();
        Provice provice2 = new Provice();
        assertThat(provice1).isNotEqualTo(provice2);

        provice2.setId(provice1.getId());
        assertThat(provice1).isEqualTo(provice2);

        provice2 = getProviceSample2();
        assertThat(provice1).isNotEqualTo(provice2);
    }
}

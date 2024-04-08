package vn.containergo.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static vn.containergo.domain.CarrierPersonGroupTestSamples.*;

import org.junit.jupiter.api.Test;
import vn.containergo.web.rest.TestUtil;

class CarrierPersonGroupTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CarrierPersonGroup.class);
        CarrierPersonGroup carrierPersonGroup1 = getCarrierPersonGroupSample1();
        CarrierPersonGroup carrierPersonGroup2 = new CarrierPersonGroup();
        assertThat(carrierPersonGroup1).isNotEqualTo(carrierPersonGroup2);

        carrierPersonGroup2.setId(carrierPersonGroup1.getId());
        assertThat(carrierPersonGroup1).isEqualTo(carrierPersonGroup2);

        carrierPersonGroup2 = getCarrierPersonGroupSample2();
        assertThat(carrierPersonGroup1).isNotEqualTo(carrierPersonGroup2);
    }
}

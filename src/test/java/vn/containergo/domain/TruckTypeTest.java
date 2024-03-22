package vn.containergo.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static vn.containergo.domain.TruckTypeTestSamples.*;

import org.junit.jupiter.api.Test;
import vn.containergo.web.rest.TestUtil;

class TruckTypeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TruckType.class);
        TruckType truckType1 = getTruckTypeSample1();
        TruckType truckType2 = new TruckType();
        assertThat(truckType1).isNotEqualTo(truckType2);

        truckType2.setId(truckType1.getId());
        assertThat(truckType1).isEqualTo(truckType2);

        truckType2 = getTruckTypeSample2();
        assertThat(truckType1).isNotEqualTo(truckType2);
    }
}

package vn.containergo.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static vn.containergo.domain.ShipperTestSamples.*;

import org.junit.jupiter.api.Test;
import vn.containergo.web.rest.TestUtil;

class ShipperTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Shipper.class);
        Shipper shipper1 = getShipperSample1();
        Shipper shipper2 = new Shipper();
        assertThat(shipper1).isNotEqualTo(shipper2);

        shipper2.setId(shipper1.getId());
        assertThat(shipper1).isEqualTo(shipper2);

        shipper2 = getShipperSample2();
        assertThat(shipper1).isNotEqualTo(shipper2);
    }
}

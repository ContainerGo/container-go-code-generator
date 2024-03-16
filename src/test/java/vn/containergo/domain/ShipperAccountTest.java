package vn.containergo.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static vn.containergo.domain.ShipperAccountTestSamples.*;
import static vn.containergo.domain.ShipperTestSamples.*;

import org.junit.jupiter.api.Test;
import vn.containergo.web.rest.TestUtil;

class ShipperAccountTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ShipperAccount.class);
        ShipperAccount shipperAccount1 = getShipperAccountSample1();
        ShipperAccount shipperAccount2 = new ShipperAccount();
        assertThat(shipperAccount1).isNotEqualTo(shipperAccount2);

        shipperAccount2.setId(shipperAccount1.getId());
        assertThat(shipperAccount1).isEqualTo(shipperAccount2);

        shipperAccount2 = getShipperAccountSample2();
        assertThat(shipperAccount1).isNotEqualTo(shipperAccount2);
    }

    @Test
    void shipperTest() throws Exception {
        ShipperAccount shipperAccount = getShipperAccountRandomSampleGenerator();
        Shipper shipperBack = getShipperRandomSampleGenerator();

        shipperAccount.setShipper(shipperBack);
        assertThat(shipperAccount.getShipper()).isEqualTo(shipperBack);

        shipperAccount.shipper(null);
        assertThat(shipperAccount.getShipper()).isNull();
    }
}

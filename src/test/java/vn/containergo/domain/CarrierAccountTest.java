package vn.containergo.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static vn.containergo.domain.CarrierAccountTestSamples.*;
import static vn.containergo.domain.CarrierTestSamples.*;

import org.junit.jupiter.api.Test;
import vn.containergo.web.rest.TestUtil;

class CarrierAccountTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CarrierAccount.class);
        CarrierAccount carrierAccount1 = getCarrierAccountSample1();
        CarrierAccount carrierAccount2 = new CarrierAccount();
        assertThat(carrierAccount1).isNotEqualTo(carrierAccount2);

        carrierAccount2.setId(carrierAccount1.getId());
        assertThat(carrierAccount1).isEqualTo(carrierAccount2);

        carrierAccount2 = getCarrierAccountSample2();
        assertThat(carrierAccount1).isNotEqualTo(carrierAccount2);
    }

    @Test
    void carrierTest() throws Exception {
        CarrierAccount carrierAccount = getCarrierAccountRandomSampleGenerator();
        Carrier carrierBack = getCarrierRandomSampleGenerator();

        carrierAccount.setCarrier(carrierBack);
        assertThat(carrierAccount.getCarrier()).isEqualTo(carrierBack);

        carrierAccount.carrier(null);
        assertThat(carrierAccount.getCarrier()).isNull();
    }
}

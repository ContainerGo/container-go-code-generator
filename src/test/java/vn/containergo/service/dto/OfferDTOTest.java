package vn.containergo.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;
import org.junit.jupiter.api.Test;
import vn.containergo.web.rest.TestUtil;

class OfferDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OfferDTO.class);
        OfferDTO offerDTO1 = new OfferDTO();
        offerDTO1.setId(UUID.randomUUID());
        OfferDTO offerDTO2 = new OfferDTO();
        assertThat(offerDTO1).isNotEqualTo(offerDTO2);
        offerDTO2.setId(offerDTO1.getId());
        assertThat(offerDTO1).isEqualTo(offerDTO2);
        offerDTO2.setId(UUID.randomUUID());
        assertThat(offerDTO1).isNotEqualTo(offerDTO2);
        offerDTO1.setId(null);
        assertThat(offerDTO1).isNotEqualTo(offerDTO2);
    }
}

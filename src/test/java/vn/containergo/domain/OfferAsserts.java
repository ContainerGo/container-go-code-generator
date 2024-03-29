package vn.containergo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class OfferAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertOfferAllPropertiesEquals(Offer expected, Offer actual) {
        assertOfferAutoGeneratedPropertiesEquals(expected, actual);
        assertOfferAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertOfferAllUpdatablePropertiesEquals(Offer expected, Offer actual) {
        assertOfferUpdatableFieldsEquals(expected, actual);
        assertOfferUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertOfferAutoGeneratedPropertiesEquals(Offer expected, Offer actual) {
        assertThat(expected)
            .as("Verify Offer auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertOfferUpdatableFieldsEquals(Offer expected, Offer actual) {
        assertThat(expected)
            .as("Verify Offer relevant properties")
            .satisfies(e -> assertThat(e.getMessage()).as("check message").isEqualTo(actual.getMessage()))
            .satisfies(e -> assertThat(e.getPickupFromDate()).as("check pickupFromDate").isEqualTo(actual.getPickupFromDate()))
            .satisfies(e -> assertThat(e.getPickupUntilDate()).as("check pickupUntilDate").isEqualTo(actual.getPickupUntilDate()))
            .satisfies(e -> assertThat(e.getDropoffFromDate()).as("check dropoffFromDate").isEqualTo(actual.getDropoffFromDate()))
            .satisfies(e -> assertThat(e.getDropoffUntilDate()).as("check dropoffUntilDate").isEqualTo(actual.getDropoffUntilDate()))
            .satisfies(e -> assertThat(e.getState()).as("check state").isEqualTo(actual.getState()))
            .satisfies(e -> assertThat(e.getPrice()).as("check price").isEqualTo(actual.getPrice()))
            .satisfies(e -> assertThat(e.getCarrierId()).as("check carrierId").isEqualTo(actual.getCarrierId()))
            .satisfies(e -> assertThat(e.getCarrierPersonId()).as("check carrierPersonId").isEqualTo(actual.getCarrierPersonId()))
            .satisfies(e -> assertThat(e.getTruckId()).as("check truckId").isEqualTo(actual.getTruckId()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertOfferUpdatableRelationshipsEquals(Offer expected, Offer actual) {
        assertThat(expected)
            .as("Verify Offer relationships")
            .satisfies(e -> assertThat(e.getContainer()).as("check container").isEqualTo(actual.getContainer()));
    }
}

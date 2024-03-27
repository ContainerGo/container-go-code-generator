package vn.containergo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class ShipmentHistoryAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertShipmentHistoryAllPropertiesEquals(ShipmentHistory expected, ShipmentHistory actual) {
        assertShipmentHistoryAutoGeneratedPropertiesEquals(expected, actual);
        assertShipmentHistoryAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertShipmentHistoryAllUpdatablePropertiesEquals(ShipmentHistory expected, ShipmentHistory actual) {
        assertShipmentHistoryUpdatableFieldsEquals(expected, actual);
        assertShipmentHistoryUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertShipmentHistoryAutoGeneratedPropertiesEquals(ShipmentHistory expected, ShipmentHistory actual) {
        assertThat(expected)
            .as("Verify ShipmentHistory auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertShipmentHistoryUpdatableFieldsEquals(ShipmentHistory expected, ShipmentHistory actual) {
        assertThat(expected)
            .as("Verify ShipmentHistory relevant properties")
            .satisfies(e -> assertThat(e.getEvent()).as("check event").isEqualTo(actual.getEvent()))
            .satisfies(e -> assertThat(e.getTimestamp()).as("check timestamp").isEqualTo(actual.getTimestamp()))
            .satisfies(e -> assertThat(e.getExecutedBy()).as("check executedBy").isEqualTo(actual.getExecutedBy()))
            .satisfies(e -> assertThat(e.getLocation()).as("check location").isEqualTo(actual.getLocation()))
            .satisfies(e -> assertThat(e.getLat()).as("check lat").isEqualTo(actual.getLat()))
            .satisfies(e -> assertThat(e.getLng()).as("check lng").isEqualTo(actual.getLng()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertShipmentHistoryUpdatableRelationshipsEquals(ShipmentHistory expected, ShipmentHistory actual) {
        assertThat(expected)
            .as("Verify ShipmentHistory relationships")
            .satisfies(e -> assertThat(e.getContainer()).as("check container").isEqualTo(actual.getContainer()));
    }
}
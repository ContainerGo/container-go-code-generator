package vn.containergo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class ShipmentPlanAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertShipmentPlanAllPropertiesEquals(ShipmentPlan expected, ShipmentPlan actual) {
        assertShipmentPlanAutoGeneratedPropertiesEquals(expected, actual);
        assertShipmentPlanAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertShipmentPlanAllUpdatablePropertiesEquals(ShipmentPlan expected, ShipmentPlan actual) {
        assertShipmentPlanUpdatableFieldsEquals(expected, actual);
        assertShipmentPlanUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertShipmentPlanAutoGeneratedPropertiesEquals(ShipmentPlan expected, ShipmentPlan actual) {
        assertThat(expected)
            .as("Verify ShipmentPlan auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertShipmentPlanUpdatableFieldsEquals(ShipmentPlan expected, ShipmentPlan actual) {
        assertThat(expected)
            .as("Verify ShipmentPlan relevant properties")
            .satisfies(
                e ->
                    assertThat(e.getEstimatedPickupFromDate())
                        .as("check estimatedPickupFromDate")
                        .isEqualTo(actual.getEstimatedPickupFromDate())
            )
            .satisfies(
                e ->
                    assertThat(e.getEstimatedPickupUntilDate())
                        .as("check estimatedPickupUntilDate")
                        .isEqualTo(actual.getEstimatedPickupUntilDate())
            )
            .satisfies(
                e ->
                    assertThat(e.getEstimatedDropoffFromDate())
                        .as("check estimatedDropoffFromDate")
                        .isEqualTo(actual.getEstimatedDropoffFromDate())
            )
            .satisfies(
                e ->
                    assertThat(e.getEstimatedDropoffUntilDate())
                        .as("check estimatedDropoffUntilDate")
                        .isEqualTo(actual.getEstimatedDropoffUntilDate())
            )
            .satisfies(e -> assertThat(e.getDriverId()).as("check driverId").isEqualTo(actual.getDriverId()))
            .satisfies(e -> assertThat(e.getTruckId()).as("check truckId").isEqualTo(actual.getTruckId()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertShipmentPlanUpdatableRelationshipsEquals(ShipmentPlan expected, ShipmentPlan actual) {
        assertThat(expected)
            .as("Verify ShipmentPlan relationships")
            .satisfies(e -> assertThat(e.getContainer()).as("check container").isEqualTo(actual.getContainer()));
    }
}

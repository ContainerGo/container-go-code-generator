package vn.containergo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class CarrierPersonGroupAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertCarrierPersonGroupAllPropertiesEquals(CarrierPersonGroup expected, CarrierPersonGroup actual) {
        assertCarrierPersonGroupAutoGeneratedPropertiesEquals(expected, actual);
        assertCarrierPersonGroupAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertCarrierPersonGroupAllUpdatablePropertiesEquals(CarrierPersonGroup expected, CarrierPersonGroup actual) {
        assertCarrierPersonGroupUpdatableFieldsEquals(expected, actual);
        assertCarrierPersonGroupUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertCarrierPersonGroupAutoGeneratedPropertiesEquals(CarrierPersonGroup expected, CarrierPersonGroup actual) {
        assertThat(expected)
            .as("Verify CarrierPersonGroup auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertCarrierPersonGroupUpdatableFieldsEquals(CarrierPersonGroup expected, CarrierPersonGroup actual) {
        assertThat(expected)
            .as("Verify CarrierPersonGroup relevant properties")
            .satisfies(e -> assertThat(e.getName()).as("check name").isEqualTo(actual.getName()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertCarrierPersonGroupUpdatableRelationshipsEquals(CarrierPersonGroup expected, CarrierPersonGroup actual) {}
}

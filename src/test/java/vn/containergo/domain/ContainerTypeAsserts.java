package vn.containergo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class ContainerTypeAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertContainerTypeAllPropertiesEquals(ContainerType expected, ContainerType actual) {
        assertContainerTypeAutoGeneratedPropertiesEquals(expected, actual);
        assertContainerTypeAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertContainerTypeAllUpdatablePropertiesEquals(ContainerType expected, ContainerType actual) {
        assertContainerTypeUpdatableFieldsEquals(expected, actual);
        assertContainerTypeUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertContainerTypeAutoGeneratedPropertiesEquals(ContainerType expected, ContainerType actual) {
        assertThat(expected)
            .as("Verify ContainerType auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertContainerTypeUpdatableFieldsEquals(ContainerType expected, ContainerType actual) {
        assertThat(expected)
            .as("Verify ContainerType relevant properties")
            .satisfies(e -> assertThat(e.getCode()).as("check code").isEqualTo(actual.getCode()))
            .satisfies(e -> assertThat(e.getName()).as("check name").isEqualTo(actual.getName()))
            .satisfies(e -> assertThat(e.getDescription()).as("check description").isEqualTo(actual.getDescription()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertContainerTypeUpdatableRelationshipsEquals(ContainerType expected, ContainerType actual) {}
}

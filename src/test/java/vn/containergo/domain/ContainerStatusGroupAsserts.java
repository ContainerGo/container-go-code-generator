package vn.containergo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class ContainerStatusGroupAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertContainerStatusGroupAllPropertiesEquals(ContainerStatusGroup expected, ContainerStatusGroup actual) {
        assertContainerStatusGroupAutoGeneratedPropertiesEquals(expected, actual);
        assertContainerStatusGroupAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertContainerStatusGroupAllUpdatablePropertiesEquals(ContainerStatusGroup expected, ContainerStatusGroup actual) {
        assertContainerStatusGroupUpdatableFieldsEquals(expected, actual);
        assertContainerStatusGroupUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertContainerStatusGroupAutoGeneratedPropertiesEquals(ContainerStatusGroup expected, ContainerStatusGroup actual) {
        assertThat(expected)
            .as("Verify ContainerStatusGroup auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertContainerStatusGroupUpdatableFieldsEquals(ContainerStatusGroup expected, ContainerStatusGroup actual) {
        assertThat(expected)
            .as("Verify ContainerStatusGroup relevant properties")
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
    public static void assertContainerStatusGroupUpdatableRelationshipsEquals(ContainerStatusGroup expected, ContainerStatusGroup actual) {}
}
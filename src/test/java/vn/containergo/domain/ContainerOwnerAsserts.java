package vn.containergo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class ContainerOwnerAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertContainerOwnerAllPropertiesEquals(ContainerOwner expected, ContainerOwner actual) {
        assertContainerOwnerAutoGeneratedPropertiesEquals(expected, actual);
        assertContainerOwnerAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertContainerOwnerAllUpdatablePropertiesEquals(ContainerOwner expected, ContainerOwner actual) {
        assertContainerOwnerUpdatableFieldsEquals(expected, actual);
        assertContainerOwnerUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertContainerOwnerAutoGeneratedPropertiesEquals(ContainerOwner expected, ContainerOwner actual) {
        assertThat(expected)
            .as("Verify ContainerOwner auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertContainerOwnerUpdatableFieldsEquals(ContainerOwner expected, ContainerOwner actual) {
        assertThat(expected)
            .as("Verify ContainerOwner relevant properties")
            .satisfies(e -> assertThat(e.getName()).as("check name").isEqualTo(actual.getName()))
            .satisfies(e -> assertThat(e.getPhone()).as("check phone").isEqualTo(actual.getPhone()))
            .satisfies(e -> assertThat(e.getEmail()).as("check email").isEqualTo(actual.getEmail()))
            .satisfies(e -> assertThat(e.getAddress()).as("check address").isEqualTo(actual.getAddress()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertContainerOwnerUpdatableRelationshipsEquals(ContainerOwner expected, ContainerOwner actual) {}
}

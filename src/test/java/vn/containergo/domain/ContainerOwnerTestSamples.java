package vn.containergo.domain;

import java.util.UUID;

public class ContainerOwnerTestSamples {

    public static ContainerOwner getContainerOwnerSample1() {
        return new ContainerOwner()
            .id(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa"))
            .name("name1")
            .phone("phone1")
            .email("email1")
            .address("address1");
    }

    public static ContainerOwner getContainerOwnerSample2() {
        return new ContainerOwner()
            .id(UUID.fromString("ad79f240-3727-46c3-b89f-2cf6ebd74367"))
            .name("name2")
            .phone("phone2")
            .email("email2")
            .address("address2");
    }

    public static ContainerOwner getContainerOwnerRandomSampleGenerator() {
        return new ContainerOwner()
            .id(UUID.randomUUID())
            .name(UUID.randomUUID().toString())
            .phone(UUID.randomUUID().toString())
            .email(UUID.randomUUID().toString())
            .address(UUID.randomUUID().toString());
    }
}

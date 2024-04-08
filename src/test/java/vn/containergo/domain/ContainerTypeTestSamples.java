package vn.containergo.domain;

import java.util.UUID;

public class ContainerTypeTestSamples {

    public static ContainerType getContainerTypeSample1() {
        return new ContainerType()
            .id(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa"))
            .code("code1")
            .name("name1")
            .description("description1");
    }

    public static ContainerType getContainerTypeSample2() {
        return new ContainerType()
            .id(UUID.fromString("ad79f240-3727-46c3-b89f-2cf6ebd74367"))
            .code("code2")
            .name("name2")
            .description("description2");
    }

    public static ContainerType getContainerTypeRandomSampleGenerator() {
        return new ContainerType()
            .id(UUID.randomUUID())
            .code(UUID.randomUUID().toString())
            .name(UUID.randomUUID().toString())
            .description(UUID.randomUUID().toString());
    }
}

package vn.containergo.domain;

import java.util.UUID;

public class WardTestSamples {

    public static Ward getWardSample1() {
        return new Ward()
            .id(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa"))
            .code("code1")
            .name("name1")
            .description("description1");
    }

    public static Ward getWardSample2() {
        return new Ward()
            .id(UUID.fromString("ad79f240-3727-46c3-b89f-2cf6ebd74367"))
            .code("code2")
            .name("name2")
            .description("description2");
    }

    public static Ward getWardRandomSampleGenerator() {
        return new Ward()
            .id(UUID.randomUUID())
            .code(UUID.randomUUID().toString())
            .name(UUID.randomUUID().toString())
            .description(UUID.randomUUID().toString());
    }
}

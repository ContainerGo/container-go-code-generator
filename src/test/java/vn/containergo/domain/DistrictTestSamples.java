package vn.containergo.domain;

import java.util.UUID;

public class DistrictTestSamples {

    public static District getDistrictSample1() {
        return new District()
            .id(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa"))
            .code("code1")
            .name("name1")
            .description("description1");
    }

    public static District getDistrictSample2() {
        return new District()
            .id(UUID.fromString("ad79f240-3727-46c3-b89f-2cf6ebd74367"))
            .code("code2")
            .name("name2")
            .description("description2");
    }

    public static District getDistrictRandomSampleGenerator() {
        return new District()
            .id(UUID.randomUUID())
            .code(UUID.randomUUID().toString())
            .name(UUID.randomUUID().toString())
            .description(UUID.randomUUID().toString());
    }
}

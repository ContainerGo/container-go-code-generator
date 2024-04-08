package vn.containergo.domain;

import java.util.UUID;

public class CenterPersonTestSamples {

    public static CenterPerson getCenterPersonSample1() {
        return new CenterPerson()
            .id(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa"))
            .name("name1")
            .phone("phone1")
            .email("email1")
            .address("address1");
    }

    public static CenterPerson getCenterPersonSample2() {
        return new CenterPerson()
            .id(UUID.fromString("ad79f240-3727-46c3-b89f-2cf6ebd74367"))
            .name("name2")
            .phone("phone2")
            .email("email2")
            .address("address2");
    }

    public static CenterPerson getCenterPersonRandomSampleGenerator() {
        return new CenterPerson()
            .id(UUID.randomUUID())
            .name(UUID.randomUUID().toString())
            .phone(UUID.randomUUID().toString())
            .email(UUID.randomUUID().toString())
            .address(UUID.randomUUID().toString());
    }
}

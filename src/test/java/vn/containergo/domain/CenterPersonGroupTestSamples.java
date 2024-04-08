package vn.containergo.domain;

import java.util.UUID;

public class CenterPersonGroupTestSamples {

    public static CenterPersonGroup getCenterPersonGroupSample1() {
        return new CenterPersonGroup().id(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa")).name("name1");
    }

    public static CenterPersonGroup getCenterPersonGroupSample2() {
        return new CenterPersonGroup().id(UUID.fromString("ad79f240-3727-46c3-b89f-2cf6ebd74367")).name("name2");
    }

    public static CenterPersonGroup getCenterPersonGroupRandomSampleGenerator() {
        return new CenterPersonGroup().id(UUID.randomUUID()).name(UUID.randomUUID().toString());
    }
}

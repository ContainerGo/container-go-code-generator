package vn.containergo.domain;

import java.util.UUID;

public class ContainerTestSamples {

    public static Container getContainerSample1() {
        return new Container()
            .id(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa"))
            .contNo("contNo1")
            .additionalRequirements("additionalRequirements1")
            .pickupContact("pickupContact1")
            .pickupContactPhone("pickupContactPhone1")
            .pickupAddress("pickupAddress1")
            .dropoffContact("dropoffContact1")
            .dropoffContactPhone("dropoffContactPhone1")
            .dropoffAddress("dropoffAddress1")
            .points("points1")
            .shipperId(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa"))
            .carrierId(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa"));
    }

    public static Container getContainerSample2() {
        return new Container()
            .id(UUID.fromString("ad79f240-3727-46c3-b89f-2cf6ebd74367"))
            .contNo("contNo2")
            .additionalRequirements("additionalRequirements2")
            .pickupContact("pickupContact2")
            .pickupContactPhone("pickupContactPhone2")
            .pickupAddress("pickupAddress2")
            .dropoffContact("dropoffContact2")
            .dropoffContactPhone("dropoffContactPhone2")
            .dropoffAddress("dropoffAddress2")
            .points("points2")
            .shipperId(UUID.fromString("ad79f240-3727-46c3-b89f-2cf6ebd74367"))
            .carrierId(UUID.fromString("ad79f240-3727-46c3-b89f-2cf6ebd74367"));
    }

    public static Container getContainerRandomSampleGenerator() {
        return new Container()
            .id(UUID.randomUUID())
            .contNo(UUID.randomUUID().toString())
            .additionalRequirements(UUID.randomUUID().toString())
            .pickupContact(UUID.randomUUID().toString())
            .pickupContactPhone(UUID.randomUUID().toString())
            .pickupAddress(UUID.randomUUID().toString())
            .dropoffContact(UUID.randomUUID().toString())
            .dropoffContactPhone(UUID.randomUUID().toString())
            .dropoffAddress(UUID.randomUUID().toString())
            .points(UUID.randomUUID().toString())
            .shipperId(UUID.randomUUID())
            .carrierId(UUID.randomUUID());
    }
}

package vn.containergo.domain;

import java.util.UUID;

public class ShipperNotificationTestSamples {

    public static ShipperNotification getShipperNotificationSample1() {
        return new ShipperNotification().id(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa")).code("code1").name("name1");
    }

    public static ShipperNotification getShipperNotificationSample2() {
        return new ShipperNotification().id(UUID.fromString("ad79f240-3727-46c3-b89f-2cf6ebd74367")).code("code2").name("name2");
    }

    public static ShipperNotification getShipperNotificationRandomSampleGenerator() {
        return new ShipperNotification().id(UUID.randomUUID()).code(UUID.randomUUID().toString()).name(UUID.randomUUID().toString());
    }
}

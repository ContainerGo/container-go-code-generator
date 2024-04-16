package vn.containergo.domain;

import java.util.UUID;

public class ShipperAccountTestSamples {

    public static ShipperAccount getShipperAccountSample1() {
        return new ShipperAccount().id(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa"));
    }

    public static ShipperAccount getShipperAccountSample2() {
        return new ShipperAccount().id(UUID.fromString("ad79f240-3727-46c3-b89f-2cf6ebd74367"));
    }

    public static ShipperAccount getShipperAccountRandomSampleGenerator() {
        return new ShipperAccount().id(UUID.randomUUID());
    }
}

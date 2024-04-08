package vn.containergo.domain;

import java.util.UUID;

public class ShipperPersonGroupTestSamples {

    public static ShipperPersonGroup getShipperPersonGroupSample1() {
        return new ShipperPersonGroup().id(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa")).name("name1");
    }

    public static ShipperPersonGroup getShipperPersonGroupSample2() {
        return new ShipperPersonGroup().id(UUID.fromString("ad79f240-3727-46c3-b89f-2cf6ebd74367")).name("name2");
    }

    public static ShipperPersonGroup getShipperPersonGroupRandomSampleGenerator() {
        return new ShipperPersonGroup().id(UUID.randomUUID()).name(UUID.randomUUID().toString());
    }
}

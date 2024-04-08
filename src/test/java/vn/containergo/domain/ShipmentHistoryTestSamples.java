package vn.containergo.domain;

import java.util.UUID;

public class ShipmentHistoryTestSamples {

    public static ShipmentHistory getShipmentHistorySample1() {
        return new ShipmentHistory()
            .id(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa"))
            .event("event1")
            .executedBy("executedBy1")
            .location("location1");
    }

    public static ShipmentHistory getShipmentHistorySample2() {
        return new ShipmentHistory()
            .id(UUID.fromString("ad79f240-3727-46c3-b89f-2cf6ebd74367"))
            .event("event2")
            .executedBy("executedBy2")
            .location("location2");
    }

    public static ShipmentHistory getShipmentHistoryRandomSampleGenerator() {
        return new ShipmentHistory()
            .id(UUID.randomUUID())
            .event(UUID.randomUUID().toString())
            .executedBy(UUID.randomUUID().toString())
            .location(UUID.randomUUID().toString());
    }
}

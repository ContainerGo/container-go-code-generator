package vn.containergo.domain;

import java.util.UUID;

public class ShipmentPlanTestSamples {

    public static ShipmentPlan getShipmentPlanSample1() {
        return new ShipmentPlan()
            .id(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa"))
            .driverId(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa"))
            .truckId(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa"));
    }

    public static ShipmentPlan getShipmentPlanSample2() {
        return new ShipmentPlan()
            .id(UUID.fromString("ad79f240-3727-46c3-b89f-2cf6ebd74367"))
            .driverId(UUID.fromString("ad79f240-3727-46c3-b89f-2cf6ebd74367"))
            .truckId(UUID.fromString("ad79f240-3727-46c3-b89f-2cf6ebd74367"));
    }

    public static ShipmentPlan getShipmentPlanRandomSampleGenerator() {
        return new ShipmentPlan().id(UUID.randomUUID()).driverId(UUID.randomUUID()).truckId(UUID.randomUUID());
    }
}

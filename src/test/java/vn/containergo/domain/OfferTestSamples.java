package vn.containergo.domain;

import java.util.UUID;

public class OfferTestSamples {

    public static Offer getOfferSample1() {
        return new Offer()
            .id(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa"))
            .message("message1")
            .carrierId(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa"))
            .truckId(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa"));
    }

    public static Offer getOfferSample2() {
        return new Offer()
            .id(UUID.fromString("ad79f240-3727-46c3-b89f-2cf6ebd74367"))
            .message("message2")
            .carrierId(UUID.fromString("ad79f240-3727-46c3-b89f-2cf6ebd74367"))
            .truckId(UUID.fromString("ad79f240-3727-46c3-b89f-2cf6ebd74367"));
    }

    public static Offer getOfferRandomSampleGenerator() {
        return new Offer()
            .id(UUID.randomUUID())
            .message(UUID.randomUUID().toString())
            .carrierId(UUID.randomUUID())
            .truckId(UUID.randomUUID());
    }
}

package vn.containergo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ShipmentHistoryTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static ShipmentHistory getShipmentHistorySample1() {
        return new ShipmentHistory().id(1L).event("event1").executedBy("executedBy1").location("location1");
    }

    public static ShipmentHistory getShipmentHistorySample2() {
        return new ShipmentHistory().id(2L).event("event2").executedBy("executedBy2").location("location2");
    }

    public static ShipmentHistory getShipmentHistoryRandomSampleGenerator() {
        return new ShipmentHistory()
            .id(longCount.incrementAndGet())
            .event(UUID.randomUUID().toString())
            .executedBy(UUID.randomUUID().toString())
            .location(UUID.randomUUID().toString());
    }
}

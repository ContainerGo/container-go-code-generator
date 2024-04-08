package vn.containergo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class TruckTestSamples {

    private static final Random random = new Random();
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Truck getTruckSample1() {
        return new Truck()
            .id(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa"))
            .code("code1")
            .name("name1")
            .model("model1")
            .manufacturer("manufacturer1")
            .year(1)
            .numberPlate("numberPlate1");
    }

    public static Truck getTruckSample2() {
        return new Truck()
            .id(UUID.fromString("ad79f240-3727-46c3-b89f-2cf6ebd74367"))
            .code("code2")
            .name("name2")
            .model("model2")
            .manufacturer("manufacturer2")
            .year(2)
            .numberPlate("numberPlate2");
    }

    public static Truck getTruckRandomSampleGenerator() {
        return new Truck()
            .id(UUID.randomUUID())
            .code(UUID.randomUUID().toString())
            .name(UUID.randomUUID().toString())
            .model(UUID.randomUUID().toString())
            .manufacturer(UUID.randomUUID().toString())
            .year(intCount.incrementAndGet())
            .numberPlate(UUID.randomUUID().toString());
    }
}

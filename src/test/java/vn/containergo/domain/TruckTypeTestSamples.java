package vn.containergo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class TruckTypeTestSamples {

    private static final Random random = new Random();
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static TruckType getTruckTypeSample1() {
        return new TruckType()
            .id(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa"))
            .code("code1")
            .name("name1")
            .category("category1")
            .height(1)
            .length(1)
            .type("type1")
            .weight(1)
            .width(1);
    }

    public static TruckType getTruckTypeSample2() {
        return new TruckType()
            .id(UUID.fromString("ad79f240-3727-46c3-b89f-2cf6ebd74367"))
            .code("code2")
            .name("name2")
            .category("category2")
            .height(2)
            .length(2)
            .type("type2")
            .weight(2)
            .width(2);
    }

    public static TruckType getTruckTypeRandomSampleGenerator() {
        return new TruckType()
            .id(UUID.randomUUID())
            .code(UUID.randomUUID().toString())
            .name(UUID.randomUUID().toString())
            .category(UUID.randomUUID().toString())
            .height(intCount.incrementAndGet())
            .length(intCount.incrementAndGet())
            .type(UUID.randomUUID().toString())
            .weight(intCount.incrementAndGet())
            .width(intCount.incrementAndGet());
    }
}

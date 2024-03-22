package vn.containergo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class TruckTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Truck getTruckSample1() {
        return new Truck().id(1L).code("code1").name("name1");
    }

    public static Truck getTruckSample2() {
        return new Truck().id(2L).code("code2").name("name2");
    }

    public static Truck getTruckRandomSampleGenerator() {
        return new Truck().id(longCount.incrementAndGet()).code(UUID.randomUUID().toString()).name(UUID.randomUUID().toString());
    }
}

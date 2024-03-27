package vn.containergo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ContainerTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Container getContainerSample1() {
        return new Container()
            .id(1L)
            .contNo("contNo1")
            .additionalRequirements("additionalRequirements1")
            .pickupContact("pickupContact1")
            .pickupContactPhone("pickupContactPhone1")
            .pickupAddress("pickupAddress1")
            .dropoffContact("dropoffContact1")
            .dropoffContactPhone("dropoffContactPhone1")
            .dropoffAddress("dropoffAddress1")
            .points("points1")
            .shipperId(1L)
            .carrierId(1L);
    }

    public static Container getContainerSample2() {
        return new Container()
            .id(2L)
            .contNo("contNo2")
            .additionalRequirements("additionalRequirements2")
            .pickupContact("pickupContact2")
            .pickupContactPhone("pickupContactPhone2")
            .pickupAddress("pickupAddress2")
            .dropoffContact("dropoffContact2")
            .dropoffContactPhone("dropoffContactPhone2")
            .dropoffAddress("dropoffAddress2")
            .points("points2")
            .shipperId(2L)
            .carrierId(2L);
    }

    public static Container getContainerRandomSampleGenerator() {
        return new Container()
            .id(longCount.incrementAndGet())
            .contNo(UUID.randomUUID().toString())
            .additionalRequirements(UUID.randomUUID().toString())
            .pickupContact(UUID.randomUUID().toString())
            .pickupContactPhone(UUID.randomUUID().toString())
            .pickupAddress(UUID.randomUUID().toString())
            .dropoffContact(UUID.randomUUID().toString())
            .dropoffContactPhone(UUID.randomUUID().toString())
            .dropoffAddress(UUID.randomUUID().toString())
            .points(UUID.randomUUID().toString())
            .shipperId(longCount.incrementAndGet())
            .carrierId(longCount.incrementAndGet());
    }
}

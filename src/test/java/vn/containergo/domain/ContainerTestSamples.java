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
            .dropoffContact("dropoffContact1")
            .dropoffContactPhone("dropoffContactPhone1")
            .dropoffAddress("dropoffAddress1")
            .shipperId(1L)
            .carrierId(1L);
    }

    public static Container getContainerSample2() {
        return new Container()
            .id(2L)
            .contNo("contNo2")
            .additionalRequirements("additionalRequirements2")
            .dropoffContact("dropoffContact2")
            .dropoffContactPhone("dropoffContactPhone2")
            .dropoffAddress("dropoffAddress2")
            .shipperId(2L)
            .carrierId(2L);
    }

    public static Container getContainerRandomSampleGenerator() {
        return new Container()
            .id(longCount.incrementAndGet())
            .contNo(UUID.randomUUID().toString())
            .additionalRequirements(UUID.randomUUID().toString())
            .dropoffContact(UUID.randomUUID().toString())
            .dropoffContactPhone(UUID.randomUUID().toString())
            .dropoffAddress(UUID.randomUUID().toString())
            .shipperId(longCount.incrementAndGet())
            .carrierId(longCount.incrementAndGet());
    }
}

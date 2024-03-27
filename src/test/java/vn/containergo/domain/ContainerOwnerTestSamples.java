package vn.containergo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ContainerOwnerTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static ContainerOwner getContainerOwnerSample1() {
        return new ContainerOwner().id(1L).name("name1").phone("phone1").email("email1").address("address1");
    }

    public static ContainerOwner getContainerOwnerSample2() {
        return new ContainerOwner().id(2L).name("name2").phone("phone2").email("email2").address("address2");
    }

    public static ContainerOwner getContainerOwnerRandomSampleGenerator() {
        return new ContainerOwner()
            .id(longCount.incrementAndGet())
            .name(UUID.randomUUID().toString())
            .phone(UUID.randomUUID().toString())
            .email(UUID.randomUUID().toString())
            .address(UUID.randomUUID().toString());
    }
}

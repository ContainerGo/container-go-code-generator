package vn.containergo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ContainerStatusGroupTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static ContainerStatusGroup getContainerStatusGroupSample1() {
        return new ContainerStatusGroup().id(1L).code("code1").name("name1").description("description1");
    }

    public static ContainerStatusGroup getContainerStatusGroupSample2() {
        return new ContainerStatusGroup().id(2L).code("code2").name("name2").description("description2");
    }

    public static ContainerStatusGroup getContainerStatusGroupRandomSampleGenerator() {
        return new ContainerStatusGroup()
            .id(longCount.incrementAndGet())
            .code(UUID.randomUUID().toString())
            .name(UUID.randomUUID().toString())
            .description(UUID.randomUUID().toString());
    }
}

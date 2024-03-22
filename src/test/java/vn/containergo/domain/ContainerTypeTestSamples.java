package vn.containergo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ContainerTypeTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static ContainerType getContainerTypeSample1() {
        return new ContainerType().id(1L).code("code1").name("name1").description("description1");
    }

    public static ContainerType getContainerTypeSample2() {
        return new ContainerType().id(2L).code("code2").name("name2").description("description2");
    }

    public static ContainerType getContainerTypeRandomSampleGenerator() {
        return new ContainerType()
            .id(longCount.incrementAndGet())
            .code(UUID.randomUUID().toString())
            .name(UUID.randomUUID().toString())
            .description(UUID.randomUUID().toString());
    }
}

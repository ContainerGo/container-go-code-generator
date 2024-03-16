package vn.containergo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class CenterPersonGroupTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static CenterPersonGroup getCenterPersonGroupSample1() {
        return new CenterPersonGroup().id(1L).name("name1").description("description1");
    }

    public static CenterPersonGroup getCenterPersonGroupSample2() {
        return new CenterPersonGroup().id(2L).name("name2").description("description2");
    }

    public static CenterPersonGroup getCenterPersonGroupRandomSampleGenerator() {
        return new CenterPersonGroup()
            .id(longCount.incrementAndGet())
            .name(UUID.randomUUID().toString())
            .description(UUID.randomUUID().toString());
    }
}

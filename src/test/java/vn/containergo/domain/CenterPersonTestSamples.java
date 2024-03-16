package vn.containergo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class CenterPersonTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static CenterPerson getCenterPersonSample1() {
        return new CenterPerson().id(1L).name("name1").phone("phone1").email("email1").address("address1");
    }

    public static CenterPerson getCenterPersonSample2() {
        return new CenterPerson().id(2L).name("name2").phone("phone2").email("email2").address("address2");
    }

    public static CenterPerson getCenterPersonRandomSampleGenerator() {
        return new CenterPerson()
            .id(longCount.incrementAndGet())
            .name(UUID.randomUUID().toString())
            .phone(UUID.randomUUID().toString())
            .email(UUID.randomUUID().toString())
            .address(UUID.randomUUID().toString());
    }
}

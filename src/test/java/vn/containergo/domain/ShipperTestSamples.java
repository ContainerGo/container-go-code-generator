package vn.containergo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class ShipperTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Shipper getShipperSample1() {
        return new Shipper()
            .id(1L)
            .code("code1")
            .name("name1")
            .address("address1")
            .taxCode("taxCode1")
            .companySize(1)
            .paymentType("paymentType1");
    }

    public static Shipper getShipperSample2() {
        return new Shipper()
            .id(2L)
            .code("code2")
            .name("name2")
            .address("address2")
            .taxCode("taxCode2")
            .companySize(2)
            .paymentType("paymentType2");
    }

    public static Shipper getShipperRandomSampleGenerator() {
        return new Shipper()
            .id(longCount.incrementAndGet())
            .code(UUID.randomUUID().toString())
            .name(UUID.randomUUID().toString())
            .address(UUID.randomUUID().toString())
            .taxCode(UUID.randomUUID().toString())
            .companySize(intCount.incrementAndGet())
            .paymentType(UUID.randomUUID().toString());
    }
}

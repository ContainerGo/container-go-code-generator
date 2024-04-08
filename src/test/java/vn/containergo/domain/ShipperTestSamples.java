package vn.containergo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class ShipperTestSamples {

    private static final Random random = new Random();
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Shipper getShipperSample1() {
        return new Shipper()
            .id(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa"))
            .code("code1")
            .name("name1")
            .address("address1")
            .taxCode("taxCode1")
            .companySize(1);
    }

    public static Shipper getShipperSample2() {
        return new Shipper()
            .id(UUID.fromString("ad79f240-3727-46c3-b89f-2cf6ebd74367"))
            .code("code2")
            .name("name2")
            .address("address2")
            .taxCode("taxCode2")
            .companySize(2);
    }

    public static Shipper getShipperRandomSampleGenerator() {
        return new Shipper()
            .id(UUID.randomUUID())
            .code(UUID.randomUUID().toString())
            .name(UUID.randomUUID().toString())
            .address(UUID.randomUUID().toString())
            .taxCode(UUID.randomUUID().toString())
            .companySize(intCount.incrementAndGet());
    }
}

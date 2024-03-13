package vn.containergo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ShipperTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Shipper getShipperSample1() {
        return new Shipper()
            .id(1L)
            .code("code1")
            .name("name1")
            .address("address1")
            .taxCode("taxCode1")
            .bankAccount("bankAccount1")
            .bankName("bankName1")
            .accountName("accountName1")
            .branchName("branchName1");
    }

    public static Shipper getShipperSample2() {
        return new Shipper()
            .id(2L)
            .code("code2")
            .name("name2")
            .address("address2")
            .taxCode("taxCode2")
            .bankAccount("bankAccount2")
            .bankName("bankName2")
            .accountName("accountName2")
            .branchName("branchName2");
    }

    public static Shipper getShipperRandomSampleGenerator() {
        return new Shipper()
            .id(longCount.incrementAndGet())
            .code(UUID.randomUUID().toString())
            .name(UUID.randomUUID().toString())
            .address(UUID.randomUUID().toString())
            .taxCode(UUID.randomUUID().toString())
            .bankAccount(UUID.randomUUID().toString())
            .bankName(UUID.randomUUID().toString())
            .accountName(UUID.randomUUID().toString())
            .branchName(UUID.randomUUID().toString());
    }
}

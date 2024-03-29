package vn.containergo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class CarrierTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Carrier getCarrierSample1() {
        return new Carrier()
            .id(1L)
            .code("code1")
            .name("name1")
            .address("address1")
            .taxCode("taxCode1")
            .bankAccount("bankAccount1")
            .bankName("bankName1")
            .accountName("accountName1")
            .branchName("branchName1")
            .companySize(1)
            .vehicles(1)
            .shipmentsLeftForDay(1);
    }

    public static Carrier getCarrierSample2() {
        return new Carrier()
            .id(2L)
            .code("code2")
            .name("name2")
            .address("address2")
            .taxCode("taxCode2")
            .bankAccount("bankAccount2")
            .bankName("bankName2")
            .accountName("accountName2")
            .branchName("branchName2")
            .companySize(2)
            .vehicles(2)
            .shipmentsLeftForDay(2);
    }

    public static Carrier getCarrierRandomSampleGenerator() {
        return new Carrier()
            .id(longCount.incrementAndGet())
            .code(UUID.randomUUID().toString())
            .name(UUID.randomUUID().toString())
            .address(UUID.randomUUID().toString())
            .taxCode(UUID.randomUUID().toString())
            .bankAccount(UUID.randomUUID().toString())
            .bankName(UUID.randomUUID().toString())
            .accountName(UUID.randomUUID().toString())
            .branchName(UUID.randomUUID().toString())
            .companySize(intCount.incrementAndGet())
            .vehicles(intCount.incrementAndGet())
            .shipmentsLeftForDay(intCount.incrementAndGet());
    }
}

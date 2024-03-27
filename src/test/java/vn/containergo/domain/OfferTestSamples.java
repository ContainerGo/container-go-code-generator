package vn.containergo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class OfferTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Offer getOfferSample1() {
        return new Offer().id(1L).message("message1").carrierId(1L).carrierPersonId(1L).truckId(1L);
    }

    public static Offer getOfferSample2() {
        return new Offer().id(2L).message("message2").carrierId(2L).carrierPersonId(2L).truckId(2L);
    }

    public static Offer getOfferRandomSampleGenerator() {
        return new Offer()
            .id(longCount.incrementAndGet())
            .message(UUID.randomUUID().toString())
            .carrierId(longCount.incrementAndGet())
            .carrierPersonId(longCount.incrementAndGet())
            .truckId(longCount.incrementAndGet());
    }
}

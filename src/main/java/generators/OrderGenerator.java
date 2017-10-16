package generators;

import entities.Order;
import org.apache.commons.text.RandomStringGenerator;

import java.time.LocalDate;
import java.util.Random;

public class OrderGenerator {
    private static Random randomNumberGenerator = new Random();
    private static RandomStringGenerator randomStringGenerator =
            new RandomStringGenerator.Builder().withinRange('a', 'z').build();
    private static LocalDate currentDate = LocalDate.now();

    public static Order generate() {
        String salt = randomStringGenerator.generate(20);
        Order order = new Order();

        order.setCost(randomNumberGenerator.nextInt(100000));
        order.setDeliveryAddress("Delivery address " + salt);
        order.setOutpostAddress("Outpost address " + salt);
        order.setOrderDate(currentDate);
        order.setCustomerId(randomNumberGenerator.nextInt(1000000));
        order.setOrderPaid(randomNumberGenerator.nextBoolean());
        order.setOrderSent(randomNumberGenerator.nextBoolean());
        order.setOrderDelivered(randomNumberGenerator.nextBoolean());
        order.setGroup(randomNumberGenerator.nextInt(10));

        return order;
    }
}

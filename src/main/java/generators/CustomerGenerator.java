package generators;

import entities.Customer;
import org.apache.commons.text.RandomStringGenerator;

import java.util.Random;

public class CustomerGenerator {
    private static Random randomNumberGenerator = new Random();
    private  static RandomStringGenerator randomStringGenerator =
            new RandomStringGenerator.Builder().withinRange('a', 'z').build();

    public static Customer generate() {
        String salt = randomStringGenerator.generate(20);
        Customer customer = new Customer();

        customer.setName(String.format("Customer name %s", salt));
        customer.setAge(randomNumberGenerator.nextInt(100));
        customer.setCity(String.format("City %s", salt));
        customer.setBalance(randomNumberGenerator.nextInt(1000000));
        customer.setOrdersCount(randomNumberGenerator.nextInt(10000));
        customer.setGroup(randomNumberGenerator.nextInt(10));

        return customer;
    }
}

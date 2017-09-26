package processors;

import entities.Customer;

import java.util.List;
import java.util.stream.Collectors;

public class CustomerCSVProcessor {

    public List<String> process (List<Customer> customers) {
        return customers.stream().map(this::process).collect(Collectors.toList());
    }

    public String process (Customer customer) {
        return String.format("%d, %s, %d, %s, %d, %d",
                customer.getId(),
                customer.getName(),
                customer.getAge(),
                customer.getCity(),
                customer.getBalance(),
                customer.getOrdersCount());
    }
}

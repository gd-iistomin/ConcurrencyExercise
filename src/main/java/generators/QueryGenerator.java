package generators;

import entities.Customer;

public class QueryGenerator {
    private final static String SELECT_ALL_CUSTOMERS = "SELECT * FROM customers";

    public static String insertCustomer(Customer customer) {
        String template = "INSERT INTO customers (name, age, city, balance, orders_count) VALUES ('%s', %d, '%s', %d, %d);";
        return String.format(template,
                customer.getName(),
                customer.getAge(),
                customer.getCity(),
                customer.getBalance(),
                customer.getOrdersCount());
    }

    public static String selectAllCustomers() {
        return SELECT_ALL_CUSTOMERS;
    }
}

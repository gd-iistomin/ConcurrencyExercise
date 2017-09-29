package generators;

import entities.Customer;

public class QueryGenerator {
    private final static String SELECT_ALL_CUSTOMERS = "SELECT * FROM customers";
    private final static String INSERT_CUSTOMER_TEMPLATE = "INSERT INTO customers (name, age, city, balance, orders_count) VALUES ('%s', %d, '%s', %d, %d);";
    private final static String SELECT_CUSTOMERS_CHUNK_TEMPLATE = "SELECT * FROM customers OFFSET %d LIMIT %d";
    private final static String SELECT_CUSTOMERS_COUNT = "SELECT COUNT(id) FROM customers;";

    public static String insertCustomer(Customer customer) {
        return String.format(INSERT_CUSTOMER_TEMPLATE,
                customer.getName(),
                customer.getAge(),
                customer.getCity(),
                customer.getBalance(),
                customer.getOrdersCount());
    }

    public static String selectAllCustomers() {
        return SELECT_ALL_CUSTOMERS;
    }

    public static String selectChunkOfCustomers(long offset, int limit) {
        return String.format(SELECT_CUSTOMERS_CHUNK_TEMPLATE, offset, limit);
    }

    public static String selectCustomersCount() {
        return SELECT_CUSTOMERS_COUNT;
    }
}

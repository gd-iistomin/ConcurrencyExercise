package generators;

import entities.Customer;
import entities.Order;

public class QueryGenerator {
    private final static String SELECT_ALL_CUSTOMERS = "SELECT * FROM customers";
    private final static String INSERT_CUSTOMER_TEMPLATE = "INSERT INTO customers (name, age, city, balance, orders_count) VALUES ('%s', %d, '%s', %d, %d);";
    private final static String INSERT_ORDER_TEMPLATE = "INSERT INTO orders (cost, delivery_address, outpost_address, order_date, customer_id, order_paid, order_sent, order_delivered) VALUES (%d, '%s', '%s', '%s', %d, %b, %b, %b);";
    private final static String SELECT_CHUNK_TEMPLATE = "SELECT * FROM %s OFFSET %d LIMIT %d";
    private final static String SELECT_CUSTOMERS_COUNT = "SELECT COUNT(id) FROM %s;";

    public static String insertCustomer(Customer customer) {
        return String.format(INSERT_CUSTOMER_TEMPLATE,
                customer.getName(),
                customer.getAge(),
                customer.getCity(),
                customer.getBalance(),
                customer.getOrdersCount());
    }

    public static String insertOrder(Order order) {
        return String.format(INSERT_ORDER_TEMPLATE,
                order.getCost(),
                order.getDeliveryAddress(),
                order.getOutpostAddress(),
                order.getOrderDate(),
                order.getCustomerId(),
                order.isOrderPaid(),
                order.isOrderSent(),
                order.isOrderDelivered());
    }

    public static String selectAllCustomers() {
        return SELECT_ALL_CUSTOMERS;
    }

    public static String selectChunkOfCustomers(long offset, int limit) {
        return String.format(SELECT_CHUNK_TEMPLATE, "customers", offset, limit);
    }

    public static String selectChunkOfOrders(long offset, int limit) {
        return String.format(SELECT_CHUNK_TEMPLATE, "orders", offset, limit);
    }

    public static String selectCustomersCount() {
        return String.format(SELECT_CUSTOMERS_COUNT, "customers");
    }

    public static String selectOrdersCount() {
        return String.format(SELECT_CUSTOMERS_COUNT, "orders");
    }
}

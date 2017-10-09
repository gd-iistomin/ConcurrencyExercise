package generators;

public class QueryGenerator {
    private final static String INSERT_CUSTOMER_TEMPLATE = "INSERT INTO customers (name, age, city, balance, orders_count) VALUES (?, ?, ?, ?, ?);";
    private final static String INSERT_ORDER_TEMPLATE = "INSERT INTO orders (cost, delivery_address, outpost_address, order_date, customer_id, order_paid, order_sent, order_delivered) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
    private final static String SELECT_CHUNK_TEMPLATE = "SELECT * FROM %s OFFSET ? LIMIT ?";
    private final static String SELECT_CUSTOMERS_COUNT = "SELECT COUNT(id) FROM %s;";

    public static String insertCustomer() {
        return INSERT_CUSTOMER_TEMPLATE;
    }

    public static String insertOrder() {
        return INSERT_ORDER_TEMPLATE;
    }

    public static String selectChunkOfCustomers() {
        return String.format(SELECT_CHUNK_TEMPLATE, "customers");
    }

    public static String selectChunkOfOrders() {
        return String.format(SELECT_CHUNK_TEMPLATE, "orders");
    }

    public static String selectCustomersCount() {
        return String.format(SELECT_CUSTOMERS_COUNT, "customers");
    }

    public static String selectOrdersCount() {
        return String.format(SELECT_CUSTOMERS_COUNT, "orders");
    }
}

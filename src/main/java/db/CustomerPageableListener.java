package db;

import entities.Customer;
import generators.QueryGenerator;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class CustomerPageableListener implements Runnable {
    private final int limit;
    private int offset;
    private boolean hasNext = true;

    private int objectCount = 0;
    private Queue<Customer> queue;

    public CustomerPageableListener(int limit, Queue queue) {
        this.limit = limit;
        this.queue = queue;
    }

    @Override
    public void run() {
        while (hasNext) {
            try {
                getNextChunk().forEach(customer -> {
                    queue.add(customer);
                    objectCount++;
                });
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        System.out.println(String.format("%d customers fetched from db", objectCount));
    }

    public List<Customer> getNextChunk() throws SQLException {
        if (!hasNext) {
            throw new RuntimeException("No more customers!");
        }
        try (Connection conn = DataSource.getConnection();
             Statement stmt = conn.createStatement()) {

            ResultSet rs = stmt.executeQuery(QueryGenerator.selectChunkOfCustomers(offset, limit));

            List<Customer> result = new ArrayList<>();
            while (rs.next()) {
                result.add(parseCustomer(rs));
            }

            offset += limit;
            rs = stmt.executeQuery(QueryGenerator.selectCustomersCount());

            long count = 0;
            if (rs.next()) {
                count = rs.getLong(1);
            }

            if (offset >= count) {
                hasNext = false;
            }

            return result;
        }
    }

    public boolean hasNext() {
        return hasNext;
    }

    private Customer parseCustomer(ResultSet rs) throws SQLException {
        Customer customer = new Customer();
        customer.setId(rs.getLong(1));
        customer.setName(rs.getString(2));
        customer.setAge(rs.getInt(3));
        customer.setCity(rs.getString(4));
        customer.setBalance(rs.getInt(5));
        customer.setOrdersCount(rs.getInt(6));
        return customer;
    }
}

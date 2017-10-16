package db;

import db.config.DataSource;
import entities.Customer;
import generators.CustomerGenerator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CustomerFiller implements Runnable {

    private int amount;

    public CustomerFiller(int amount) {
        this.amount = amount;
    }

    @Override
    public void run() {
        try (Connection conn = DataSource.getConnection()) {
            PreparedStatement insertCustomerPS = conn.prepareStatement(QueryGenerator.insertCustomer());

            for (int i = 0; i < amount; i++) {
                Customer customer = CustomerGenerator.generate();

                insertCustomerPS.setString(1, customer.getName());
                insertCustomerPS.setInt(2, customer.getAge());
                insertCustomerPS.setString(3, customer.getCity());
                insertCustomerPS.setInt(4, customer.getBalance());
                insertCustomerPS.setInt(5, customer.getOrdersCount());
                insertCustomerPS.setInt(6, customer.getGroup());

                insertCustomerPS.addBatch();
            }
            insertCustomerPS.executeBatch();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}

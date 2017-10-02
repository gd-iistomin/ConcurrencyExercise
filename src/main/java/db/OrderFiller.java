package db;

import db.config.DataSource;
import generators.OrderGenerator;
import generators.QueryGenerator;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class OrderFiller implements Runnable {

    private int amount;

    public OrderFiller(int amount) {
        this.amount = amount;
    }

    @Override
    public void run() {
        try (Connection conn = DataSource.getConnection();
             Statement stmt = conn.createStatement()) {

            for (int i = 0; i < amount; i++) {
                stmt.addBatch(QueryGenerator.insertOrder(OrderGenerator.generate()));
            }
            stmt.executeBatch();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}

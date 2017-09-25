package db;

import generators.CustomerGenerator;
import generators.QueryGenerator;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DBUtils {

    public static void insertRandomCustomers(long amount) {

        try (Connection conn = DataSource.getConnection();
             Statement stmt = conn.createStatement()) {

            for (int i = 0; i < amount; i++) {
                stmt.addBatch(QueryGenerator.insertCustomer(CustomerGenerator.generate()));
            }
            stmt.executeBatch();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}

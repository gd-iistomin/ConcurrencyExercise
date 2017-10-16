package db;

import db.config.DataSource;
import entities.Order;
import generators.OrderGenerator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Date;

public class OrderFiller implements Runnable {

    private int amount;

    public OrderFiller(int amount) {
        this.amount = amount;
    }

    @Override
    public void run() {
        try (Connection conn = DataSource.getConnection()) {
            PreparedStatement insertOrderPS = conn.prepareStatement(QueryGenerator.insertOrder());

            for (int i = 0; i < amount; i++) {
                Order order = OrderGenerator.generate();

                insertOrderPS.setInt(1, order.getCost());
                insertOrderPS.setString(2, order.getDeliveryAddress());
                insertOrderPS.setString(3, order.getOutpostAddress());
                insertOrderPS.setDate(4, Date.valueOf(order.getOrderDate()));
                insertOrderPS.setLong(5, order.getCustomerId());
                insertOrderPS.setBoolean(6, order.isOrderPaid());
                insertOrderPS.setBoolean(7, order.isOrderSent());
                insertOrderPS.setBoolean(8, order.isOrderDelivered());
                insertOrderPS.setInt(9, order.getGroup());

                insertOrderPS.addBatch();
            }
            insertOrderPS.executeBatch();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}

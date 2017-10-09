package db.readers;

import entities.Order;
import generators.QueryGenerator;
import processors.CSVProcessTask;
import processors.OrderCSVProcessTask;
import utils.Utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class OrderPageableReader extends PageableReader<Order> {

    public OrderPageableReader(int limit,
                               ExecutorService processorService,
                               ExecutorService writerService) {
        super(limit, processorService, writerService);
    }

    @Override
    protected CSVProcessTask<Order> createProcessTask(List<Order> entities, ExecutorService writerService) {
        Utils.log("Create new 'process' task for 'order' entities");
        return new OrderCSVProcessTask(entities, writerService);
    }

    @Override
    protected String selectChunkSQL() {
        return QueryGenerator.selectChunkOfOrders();
    }

    @Override
    protected String selectCountSQL() {
        return QueryGenerator.selectOrdersCount();
    }

    @Override
    protected Order parse(ResultSet rs) throws SQLException {
        Order order = new Order();
        order.setId(rs.getLong(1));
        order.setCost(rs.getInt(2));
        order.setDeliveryAddress(rs.getString(3));
        order.setOutpostAddress(rs.getString(4));
        order.setOrderDate(rs.getDate(5).toLocalDate());
        order.setCustomerId(rs.getLong(6));
        order.setOrderPaid(rs.getBoolean(7));
        order.setOrderSent(rs.getBoolean(8));
        order.setOrderDelivered(rs.getBoolean(9));
        return order;
    }
}

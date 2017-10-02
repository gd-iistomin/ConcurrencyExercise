package processors;

import entities.Order;
import fileWriters.WriteTask;

import java.util.List;
import java.util.concurrent.ExecutorService;

public class OrderCSVProcessTask extends CSVProcessTask<Order> {

    public OrderCSVProcessTask(List<Order> inputList, ExecutorService writerService) {
        super(inputList, writerService);
    }

    @Override
    protected WriteTask createWriteTask(List<String> strings) {
        return new WriteTask("orders.csv", strings);
    }

    @Override
    protected String process(Order entity) {
        return String.format("%d, %d, %s, %s, %s, %d, %b, %b, %b",
                entity.getId(),
                entity.getCost(),
                entity.getDeliveryAddress(),
                entity.getOutpostAddress(),
                entity.getOrderDate(),
                entity.getCustomerId(),
                entity.isOrderPaid(),
                entity.isOrderSent(),
                entity.isOrderDelivered());
    }
}

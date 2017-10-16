package processors;

import entities.Order;
import fileWriters.WriteTask;
import utils.Utils;

import java.util.List;
import java.util.concurrent.ExecutorService;

public class OrderCSVProcessTask extends CSVProcessTask<Order> {

    public OrderCSVProcessTask(List<Order> inputList, ExecutorService writerService) {
        super(inputList, writerService);
    }

    @Override
    protected WriteTask createWriteTask(List<String> strings) {
        Utils.log("Create new 'write' task for 'order' entities");
        return new WriteTask("orders.csv", strings);
    }

    @Override
    protected String process(Order entity) {
        return String.format("%d, %d, %s, %s, %s, %d, %b, %b, %b, %d",
                entity.getId(),
                entity.getCost(),
                entity.getDeliveryAddress(),
                entity.getOutpostAddress(),
                entity.getOrderDate(),
                entity.getCustomerId(),
                entity.isOrderPaid(),
                entity.isOrderSent(),
                entity.isOrderDelivered(),
                entity.getGroup());
    }
}

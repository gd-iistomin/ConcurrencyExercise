package processors;

import com.google.common.collect.ListMultimap;
import entities.Order;
import fileWriters.WriteTask;
import processors.data.ProcessedEntry;
import utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class OrderCSVProcessTask extends CSVProcessTask<Order> {

    public OrderCSVProcessTask(List<Order> inputList, ExecutorService writerService) {
        super(inputList, writerService);
    }

    @Override
    protected List<WriteTask> createWriteTask(List<ProcessedEntry> data) {
        Utils.log("Create new 'write' task for 'order' entities");
        List<WriteTask> tasks = new ArrayList<>();

        ListMultimap<String, String> multimap = groupByParam(data);
        multimap.keySet().forEach(key ->
                tasks.add(new WriteTask(String.format("output/orders/group_%s.csv", key), multimap.get(key))));

        return tasks;
    }

    @Override
    protected ProcessedEntry process(Order entity) {
        String param = String.valueOf(entity.getGroup());
        String string = String.format("%d, %d, %s, %s, %s, %d, %b, %b, %b, %d",
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

        return new ProcessedEntry(string, param);
    }
}

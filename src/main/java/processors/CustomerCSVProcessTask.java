package processors;

import com.google.common.collect.ListMultimap;
import entities.Customer;
import fileWriters.WriteTask;
import processors.data.ProcessedEntry;
import utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class CustomerCSVProcessTask extends CSVProcessTask<Customer> {

    public CustomerCSVProcessTask(List<Customer> inputList, ExecutorService writerService) {
        super(inputList, writerService);
    }

    @Override
    protected List<WriteTask> createWriteTask(List<ProcessedEntry> data) {
        Utils.log("Create new 'write' task for 'customer' entities");
        List<WriteTask> tasks = new ArrayList<>();

        ListMultimap<String, String> multimap = groupByParam(data);
        multimap.keySet().forEach(key ->
                tasks.add(new WriteTask(String.format("output/customers/group_%s.csv", key), multimap.get(key))));

        return tasks;
    }

    @Override
    protected ProcessedEntry process(Customer entity) {
        String param = String.valueOf(entity.getGroup());
        String string = String.format("%d, %s, %d, %s, %d, %d, %d",
                entity.getId(),
                entity.getName(),
                entity.getAge(),
                entity.getCity(),
                entity.getBalance(),
                entity.getOrdersCount(),
                entity.getGroup());

        return new ProcessedEntry(string, param);
    }
}

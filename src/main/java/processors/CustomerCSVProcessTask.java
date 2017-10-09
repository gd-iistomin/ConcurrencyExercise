package processors;

import entities.Customer;
import fileWriters.WriteTask;
import utils.Utils;

import java.util.List;
import java.util.concurrent.ExecutorService;

public class CustomerCSVProcessTask extends CSVProcessTask<Customer> {

    public CustomerCSVProcessTask(List<Customer> inputList, ExecutorService writerService) {
        super(inputList, writerService);
    }

    @Override
    protected WriteTask createWriteTask(List<String> strings) {
        Utils.log("Create new 'write' task for 'customer' entities");
        return new WriteTask("customers.csv", strings);
    }

    @Override
    protected String process(Customer entity) {
        return String.format("%d, %s, %d, %s, %d, %d",
                entity.getId(),
                entity.getName(),
                entity.getAge(),
                entity.getCity(),
                entity.getBalance(),
                entity.getOrdersCount());
    }
}

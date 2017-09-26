import db.CustomerPageableListener;
import entities.Customer;
import fileWriters.FileWriter;
import processors.CustomerCSVProcessor;

import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    public static void main(String[] args) throws SQLException, IOException {
        ConcurrentLinkedQueue<String> stringQueue = new ConcurrentLinkedQueue<>();
        ConcurrentLinkedQueue<Customer> customerQueue = new ConcurrentLinkedQueue<>();

        AtomicInteger activeCustomerReaders = new AtomicInteger(0);
        AtomicInteger activeCustomerProcessors = new AtomicInteger(0);

        new Thread(new CustomerPageableListener(10000, customerQueue, activeCustomerReaders)).start();
        activeCustomerReaders.incrementAndGet();
        for (int i = 0; i < Runtime.getRuntime().availableProcessors(); i++) {
            new Thread(new CustomerCSVProcessor(customerQueue, stringQueue, activeCustomerReaders, activeCustomerProcessors)).start();
            activeCustomerProcessors.incrementAndGet();
        }
        new Thread(new FileWriter(stringQueue, "customers.csv", activeCustomerProcessors)).start();
    }
}

import db.CustomerPageableListener;
import entities.Customer;
import fileWriters.FileWriter;
import processors.CustomerCSVProcessor;

import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Main {

    public static void main(String[] args) throws SQLException, IOException {
        ConcurrentLinkedQueue<String> stringQueue = new ConcurrentLinkedQueue<>();
        ConcurrentLinkedQueue<Customer> customerQueue = new ConcurrentLinkedQueue<>();

        new Thread(new CustomerPageableListener(20, customerQueue)).start();
        for (int i = 0; i < Runtime.getRuntime().availableProcessors(); i++) {
            new Thread(new CustomerCSVProcessor(customerQueue, stringQueue)).start();
        }
        new Thread(new FileWriter(stringQueue, "customers.csv")).start();
    }
}

import db.readers.CustomerPageableReader;
import db.readers.OrderPageableReader;
import utils.Utils;

import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.*;

public class Main {

    private static final int THREADS = Runtime.getRuntime().availableProcessors();
    private static final int READERS = 2;

    public static void main(String[] args) throws SQLException, IOException {
        ExecutorService readerService = Executors.newFixedThreadPool(READERS);
        ExecutorService processorService = Executors.newFixedThreadPool(THREADS);
        ExecutorService writerService = Executors.newSingleThreadExecutor();

        long startTime = System.currentTimeMillis();
        readerService.execute(new CustomerPageableReader(20000, processorService, writerService));
        readerService.execute(new OrderPageableReader(20000, processorService, writerService));
        readerService.shutdown();

        try {
            readerService.awaitTermination(1, TimeUnit.HOURS);
            Utils.log("Readers done");
            processorService.shutdown();
            processorService.awaitTermination(1, TimeUnit.HOURS);
            Utils.log("Processors done");
            writerService.shutdown();
            writerService.awaitTermination(1, TimeUnit.HOURS);
            Utils.log("Writers done");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long endTime   = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        Utils.printExecutionTime(totalTime);
    }
}

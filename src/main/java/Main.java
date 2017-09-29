import db.readers.CustomerPageableReader;

import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.*;

public class Main {

    private static final int THREADS = Runtime.getRuntime().availableProcessors();

    public static void main(String[] args) throws SQLException, IOException {
        CountDownLatch activeReaders = new CountDownLatch(1);
        ExecutorService processorService = Executors.newFixedThreadPool(THREADS);
        ExecutorService writerService = Executors.newSingleThreadExecutor();

        long startTime = System.currentTimeMillis();
        new Thread(new CustomerPageableReader(1000, processorService, writerService, activeReaders)).start();

        try {
            activeReaders.await();
            processorService.shutdown();
            processorService.awaitTermination(1, TimeUnit.MINUTES);
            writerService.shutdown();
            writerService.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long endTime   = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        long hours = totalTime / 3600000;
        totalTime -= hours * 3600000;
        long mins = totalTime / 60000;
        totalTime -= mins * 60000;
        long secs = totalTime / 1000;
        long ms = totalTime - secs * 1000;
        System.out.println(String.format("Execution time: %d:%d:%d:%d", hours, mins, secs, ms));
    }
}

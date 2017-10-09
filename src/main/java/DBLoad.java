
import db.CustomerFiller;
import db.OrderFiller;
import utils.Utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class DBLoad {

    public static void main(String[] args) throws InterruptedException {
        long startTime = System.currentTimeMillis();

        ExecutorService service = Executors.newCachedThreadPool();
        service.execute(new CustomerFiller(10000));
        service.execute(new OrderFiller(10000));
        service.shutdown();
        service.awaitTermination(5, TimeUnit.MINUTES);

        long endTime   = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        Utils.printExecutionTime(totalTime);
    }
}


import db.CustomerFiller;

public class DBLoad {

    public static void main(String[] args) {
        for (int i = 0; i < Runtime.getRuntime().availableProcessors(); i++) {
            new Thread(new CustomerFiller(250000)).start();
        }
    }
}

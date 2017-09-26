
import db.DBUtils;

public class DBLoad {

    public static void main(String[] args) {
        DBUtils.insertRandomCustomers(10000);
    }
}

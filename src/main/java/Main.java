import db.CustomerPageableListener;
import entities.Customer;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws SQLException {
        List<Customer> customers = new ArrayList<>();
        CustomerPageableListener pageable = new CustomerPageableListener(10);
        while (pageable.hasNext()) {
            customers.addAll(pageable.getNextChunk());
            System.out.println(customers.size());
        }
    }
}

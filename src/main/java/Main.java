import db.CustomerPageableListener;
import fileWriters.CustomerCSVWriter;
import processors.CustomerCSVProcessor;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class Main {

    public static void main(String[] args) throws SQLException, IOException {
        CustomerCSVProcessor customerCSVProcessor = new CustomerCSVProcessor();
        CustomerCSVWriter customerCSVWriter = new CustomerCSVWriter();
        CustomerPageableListener pageable = new CustomerPageableListener(10);

        while (pageable.hasNext()) {
            List<String> strings = customerCSVProcessor.process(pageable.getNextChunk());
            strings.forEach(customerCSVWriter::pushToQueue);
        }

        customerCSVWriter.write();
    }
}

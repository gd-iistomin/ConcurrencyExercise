package db.readers;

import entities.Customer;
import generators.QueryGenerator;
import processors.CSVProcessTask;
import processors.CustomerCSVProcessTask;
import utils.Utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class CustomerPageableReader extends PageableReader<Customer> {

    public CustomerPageableReader(int limit, ExecutorService processorService, ExecutorService writerService) {
        super(limit, processorService, writerService);
    }

    @Override
    protected CSVProcessTask<Customer> createProcessTask(List<Customer> entities, ExecutorService writerService) {
        Utils.log("Create new 'process' task for 'customer' entities");
        return new CustomerCSVProcessTask(entities, writerService);
    }

    @Override
    protected String selectChunkSQL() {
        return QueryGenerator.selectChunkOfCustomers();
    }

    @Override
    protected String selectCountSQL() {
        return QueryGenerator.selectCustomersCount();
    }

    @Override
    protected Customer parse(ResultSet rs) throws SQLException {
        Customer customer = new Customer();
        customer.setId(rs.getLong(1));
        customer.setName(rs.getString(2));
        customer.setAge(rs.getInt(3));
        customer.setCity(rs.getString(4));
        customer.setBalance(rs.getInt(5));
        customer.setOrdersCount(rs.getInt(6));
        return customer;
    }
}

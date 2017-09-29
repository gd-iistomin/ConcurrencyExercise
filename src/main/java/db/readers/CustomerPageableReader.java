package db.readers;

import entities.Customer;
import generators.QueryGenerator;
import processors.CSVProcessTask;
import processors.CustomerCSVProcessTask;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;

public class CustomerPageableReader extends PageableReader<Customer> {

    public CustomerPageableReader(int limit, ExecutorService processorService, ExecutorService writerService, CountDownLatch activeReaders) {
        super(limit, processorService, writerService, activeReaders);
    }

    @Override
    protected CSVProcessTask<Customer> createProcessTask(List<Customer> entities, ExecutorService writerService) {
        return new CustomerCSVProcessTask(entities, writerService);
    }

    @Override
    protected String selectChunkSQL(long offset, int limit) {
        return QueryGenerator.selectChunkOfCustomers(offset, limit);
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

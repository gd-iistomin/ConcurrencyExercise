package db.readers;

import db.config.DataSource;
import processors.CSVProcessTask;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;

public abstract class PageableReader<T> implements Runnable {
    private final int limit;
    private long offset;
    private boolean hasNext = true;

    private int objectCount = 0;
    private ExecutorService processorService;
    private ExecutorService writerService;
    private CountDownLatch activeReaders;

    public PageableReader(int limit,
                          ExecutorService processorService,
                          ExecutorService writerService,
                          CountDownLatch activeReaders) {
        this.limit = limit;
        this.processorService = processorService;
        this.writerService = writerService;
        this.activeReaders = activeReaders;
    }

    @Override
    public void run() {
        while (hasNext) {
            try {
                List<T> entities = getNextChunk();
                processorService.execute(createProcessTask(entities, writerService));
                objectCount += entities.size();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        System.out.println(String.format("%d entities fetched from db", objectCount));
        activeReaders.countDown();
    }

    protected List<T> getNextChunk() throws SQLException {
        if (!hasNext) {
            throw new RuntimeException("No more customers!");
        }
        try (Connection conn = DataSource.getConnection();
             Statement stmt = conn.createStatement()) {

            ResultSet rs = stmt.executeQuery(selectChunkSQL(offset, limit));

            List<T> result = new ArrayList<>();
            while (rs.next()) {
                result.add(parse(rs));
            }

            offset += limit;
            rs = stmt.executeQuery(selectCountSQL());

            long count = 0;
            if (rs.next()) {
                count = rs.getLong(1);
            }

            if (offset >= count) {
                hasNext = false;
            }

            return result;
        }
    }

    protected abstract CSVProcessTask<T> createProcessTask(List<T> entities, ExecutorService writerService);

    protected abstract String selectChunkSQL(long offset, int limit);

    protected abstract String selectCountSQL();

    protected abstract T parse(ResultSet rs) throws SQLException;

    public boolean hasNext() {
        return hasNext;
    }
}

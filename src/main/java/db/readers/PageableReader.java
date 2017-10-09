package db.readers;

import db.config.DataSource;
import processors.CSVProcessTask;
import utils.Utils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;

public abstract class PageableReader<T> implements Callable<Boolean> {
    private final int limit;
    private long offset;
    private boolean hasNext = true;

    private int objectCount = 0;
    private ExecutorService processorService;
    private ExecutorService writerService;

    private PreparedStatement selectChunkPS;
    private PreparedStatement selectCountPS;

    public PageableReader(int limit,
                          ExecutorService processorService,
                          ExecutorService writerService) {
        this.limit = limit;
        this.processorService = processorService;
        this.writerService = writerService;
    }

    @Override
    public Boolean call() {
        try (Connection conn = DataSource.getConnection()) {
            selectChunkPS = conn.prepareStatement(selectChunkSQL());
            selectCountPS = conn.prepareStatement(selectCountSQL());
            while (hasNext) {
                List<T> entities = getNextChunk();
                processorService.execute(createProcessTask(entities, writerService));
                objectCount += entities.size();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        Utils.log(String.format("%d entities fetched from db", objectCount));
        return true;
    }

    private List<T> getNextChunk() throws SQLException {
        if (!hasNext) {
            throw new RuntimeException("No more customers!");
        }

        selectChunkPS.setLong(1, offset);
        selectChunkPS.setInt(2, limit);
        ResultSet rs = selectChunkPS.executeQuery();

        List<T> result = new ArrayList<>();
        while (rs.next()) {
            result.add(parse(rs));
        }

        offset += limit;
        rs = selectCountPS.executeQuery();

        long count = 0;
        if (rs.next()) {
            count = rs.getLong(1);
        }

        if (offset >= count) {
            hasNext = false;
        }

        return result;
    }

    protected abstract CSVProcessTask<T> createProcessTask(List<T> entities, ExecutorService writerService);

    protected abstract String selectChunkSQL();

    protected abstract String selectCountSQL();

    protected abstract T parse(ResultSet rs) throws SQLException;
}

package processors;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import fileWriters.WriteTask;
import processors.data.ProcessedEntry;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

public abstract class CSVProcessTask<T> implements Runnable {
    private List<T> inputList;
    private ExecutorService writerService;

    protected CSVProcessTask(List<T> inputList, ExecutorService writerService) {
        this.inputList = inputList;
        this.writerService = writerService;
    }

    @Override
    public void run() {
        List<ProcessedEntry> outputList = inputList.stream()
                .map(this::process)
                .collect(Collectors.toList());
        createWriteTask(outputList).forEach(writerService::execute);
    }

    protected abstract List<WriteTask> createWriteTask(List<ProcessedEntry> strings);

    protected abstract ProcessedEntry process(T entity);

    protected static ListMultimap<String, String> groupByParam(List<ProcessedEntry> entries) {
        ListMultimap<String, String> multimap = ArrayListMultimap.create();
        entries.forEach(entry -> multimap.put(entry.getParameter(), entry.getData()));
        return multimap;
    }
}

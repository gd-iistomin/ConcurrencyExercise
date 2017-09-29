package processors;

import fileWriters.WriteTask;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

public abstract class CSVProcessTask<T> implements Runnable {
    private List<T> inputList;
    private ExecutorService writerService;

    public CSVProcessTask(List<T> inputList, ExecutorService writerService) {
        this.inputList = inputList;
        this.writerService = writerService;
    }

    @Override
    public void run() {
        List<String> outputList = inputList.stream()
                .map(this::process)
                .collect(Collectors.toList());
        writerService.execute(createWriteTask(outputList));
//        System.out.println(String.format("%d entities processed", outputList.size()));
    }

    protected abstract WriteTask createWriteTask(List<String> strings);

    protected abstract String process(T entity);
}

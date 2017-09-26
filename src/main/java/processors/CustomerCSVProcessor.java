package processors;

import entities.Customer;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class CustomerCSVProcessor implements Runnable {
    private static int count = 0;
    private int objectCount = 0;

    private int num;
    private Queue<Customer> inputQueue;
    private Queue<String> outputQueue;
    private AtomicInteger activeReaders;
    private AtomicInteger activeProcessors;

    public CustomerCSVProcessor(Queue<Customer> inputQueue, Queue<String> outputQueue, AtomicInteger activeReaders, AtomicInteger activeProcessors) {
        num = ++count;
        this.inputQueue = inputQueue;
        this.outputQueue = outputQueue;
        this.activeReaders = activeReaders;
        this.activeProcessors = activeProcessors;
    }

    @Override
    public void run() {
        for (int i = 0; i < 50; i++) {
            Customer cust;
            while (activeReaders.get() > 0 || !inputQueue.isEmpty()) {
                while ((cust = inputQueue.poll()) != null) {
                    outputQueue.add(process(cust));
                    objectCount++;
                }
                try {
                    Thread.currentThread().sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        activeProcessors.decrementAndGet();
        System.out.println(String.format("Processor %d processed %d objects", num, objectCount));
    }

    public List<String> process (List<Customer> customers) {
        return customers.stream().map(this::process).collect(Collectors.toList());
    }

    public String process (Customer customer) {
        return String.format("%d, %s, %d, %s, %d, %d",
                customer.getId(),
                customer.getName(),
                customer.getAge(),
                customer.getCity(),
                customer.getBalance(),
                customer.getOrdersCount());
    }
}

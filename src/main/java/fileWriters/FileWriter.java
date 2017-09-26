package fileWriters;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileLock;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;

public class FileWriter implements Runnable {
    private Queue<String> queue;
    private String fileName;

    private int objectCount = 0;
    AtomicInteger activeProcessors;

    public FileWriter(Queue<String> queue, String fileName, AtomicInteger activeProcessors) {
        this.queue = queue;
        this.fileName = fileName;
        this.activeProcessors = activeProcessors;
    }

    @Override
    public void run() {
        try (FileOutputStream fos = new FileOutputStream(fileName)) {
            try (FileLock lock = fos.getChannel().lock()) {
                DataOutputStream outStream = new DataOutputStream(new BufferedOutputStream(fos));
                String str;
                while (activeProcessors.get() > 0 || !queue.isEmpty()) {
                    while ((str = queue.poll()) != null) {
                        outStream.write((str + "\n").getBytes("UTF-8"));
                        objectCount++;
                    }
                    outStream.flush();
                    Thread.currentThread().sleep(100);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        System.out.println(String.format("%d lines written to file", objectCount));
    }
}

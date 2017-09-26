package fileWriters;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileLock;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class CustomerCSVWriter {
    private Queue<String> queue = new ConcurrentLinkedQueue<>();
    private final String FILE_NAME = "customers.csv";

    public boolean pushToQueue(String string) {
        return queue.add(string);
    }

    public void write() throws IOException {
        try (FileOutputStream fos = new FileOutputStream(FILE_NAME)) {
            try (FileLock lock = fos.getChannel().lock()) {
                DataOutputStream outStream = new DataOutputStream(new BufferedOutputStream(fos));
                while (!queue.isEmpty()) {
                    outStream.write(queue.remove().getBytes("UTF-8"));
                    outStream.write("\n".getBytes("UTF-8"));
                }
                outStream.flush();
            }
        }
    }
}

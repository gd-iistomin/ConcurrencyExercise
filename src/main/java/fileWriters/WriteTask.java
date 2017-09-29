package fileWriters;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileLock;
import java.util.List;

public class WriteTask implements Runnable {
    private String fileName;
    private List<String> inputList;

    public WriteTask(String fileName, List<String> inputList) {
        this.fileName = fileName;
        this.inputList = inputList;
    }

    @Override
    public void run() {
        try (FileOutputStream fos = new FileOutputStream(fileName, true)) {
            try (FileLock lock = fos.getChannel().lock()) {
                DataOutputStream outStream = new DataOutputStream(new BufferedOutputStream(fos));
                for (String str : inputList) {
                    outStream.write((str + "\n").getBytes("UTF-8"));
                }
                outStream.flush();
//                System.out.println(String.format("%d strings written", inputList.size()));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

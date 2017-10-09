package utils;

public class Utils {

    private static final boolean LOGGING_ENABLED = true;

    public static void log(String string) {
        if (LOGGING_ENABLED) {
            System.out.println(string);
        }
    }

    public static void printExecutionTime(long milis) {
        long hours = milis / 3600000;
        milis -= hours * 3600000;
        long mins = milis / 60000;
        milis -= mins * 60000;
        long secs = milis / 1000;
        milis -= secs * 1000;
        System.out.println(String.format("Execution time: %d:%d:%d:%d", hours, mins, secs, milis));
    }
}

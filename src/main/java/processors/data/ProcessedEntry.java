package processors.data;

public class ProcessedEntry {
    private final String data;
    private final String parameter;

    public ProcessedEntry(String data, String parameter) {
        this.data = data;
        this.parameter = parameter;
    }

    public String getData() {
        return data;
    }

    public String getParameter() {
        return parameter;
    }
}

package FileRead;

public class WordAndCountModel {
    private int count;
    private String line;

    public WordAndCountModel(int count, String line) {
        this.count = count;
        this.line = line;
    }

    public int getCount() {
        return count;
    }

    public String getLine() {
        return line;
    }
}

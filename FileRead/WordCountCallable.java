package FileRead;

import java.util.concurrent.Callable;

public class WordCountCallable implements Callable<WordAndCountModel> {
    String line;
    public WordCountCallable(String pLine) {
        line = pLine;
    }

    @Override
    public WordAndCountModel call() throws Exception {
        return Utils.readLineWordCount(line);
    }
}

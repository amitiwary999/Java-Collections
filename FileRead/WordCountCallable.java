package FileRead;

import java.util.concurrent.Callable;

public class WordCountCallable implements Callable<WordAndCountModel> {
    String line;
    int cpuIntensive = 0;
    public WordCountCallable(String pLine, int cpuIntensive) {
        line = pLine;
        this.cpuIntensive = cpuIntensive;
    }

    @Override
    public WordAndCountModel call() throws Exception {
        if(this.cpuIntensive == 1){
            return Utils.readLineWordCountWithWait(line);
        } else {
            return Utils.readLineWordCount(line);
        }
    }
}

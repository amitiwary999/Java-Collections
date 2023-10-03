package FileRead;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

public class MultiThreadFileOperation {
    /** read file using input stream
     *   // Efficient use of memory
     *   try (InputStream is = new FileInputStream(largeFileName);
     *        BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
     *       String line;
     *       while ((line = br.readLine()) != null) {
     *           // process one line
     *       }
     *   }
     *   read using multi thread. so that can read very fast and calculate number of words in each line.
     * **/
    public WordAndCountModel readLineWordCount(String line){
        StringBuilder tempStr = new StringBuilder(line);
        String[] strSplits = tempStr.toString().split("\n");
        int count = 0;
        int charVal = 0;
        for(String strSplit : strSplits){
            String tempSplitStr = strSplit;
            char[] charArr = tempSplitStr.toCharArray();
            for(char charItem : charArr){
                int charItemIntVal = charItem;
                charVal += charItemIntVal;
            }
            int wordLength = strSplit.length();
            count += wordLength;

        }
        return new WordAndCountModel(charVal, tempStr.toString());
    }

    public void readFile() {
        try (InputStream is = new FileInputStream("/Users/amitt/Desktop/longLineText.txt");
             BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            String line;
            System.out.println("start time non thread " + new Date().getTime());
            List<WordAndCountModel> li = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                li.add(readLineWordCount(line));
               // System.out.println("count on non thread " + line.length());
            }
            System.out.println("end time non thread " + new Date().getTime());
        }catch(IOException e){
            throw new RuntimeException(e);
        }
    }

    public void readFileThreadCallable(){
        try (InputStream is = new FileInputStream("/Users/amitt/Desktop/longLineText.txt");
             BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            String line;
            ExecutorService es = Executors.newFixedThreadPool(4);
            ExecutorService es1 = Executors.newFixedThreadPool(2);
            List<CompletableFuture<WordAndCountModel>> futureList = new ArrayList<>();
            System.out.println("start time thread " + new Date().getTime());
            Collection<Callable<WordAndCountModel>> callables = new ArrayList<>();

            while ((line = br.readLine()) != null) {
                String finalLine = line;
                WordCountCallable wordCountCallable = new WordCountCallable(finalLine);
                callables.add(wordCountCallable);
            }

            es1.submit(() -> {
                List<Future<WordAndCountModel>> wordCountFutureList;
                try{
                    wordCountFutureList = new ArrayList<>(es.invokeAll(callables));
                } catch (InterruptedException interruptedException){
                    throw new RuntimeException(interruptedException);
                }
                System.out.println("end time thread function before " + new Date().getTime());
                for(int i=0; i<wordCountFutureList.size(); i++){
                    try {
                        wordCountFutureList.get(i).get();
                    } catch (ExecutionException | InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                System.out.println("end time thread " + new Date().getTime());

            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("end time thread function " + new Date().getTime());
    }

    public void readFileThread(){
        try (InputStream is = new FileInputStream("/Users/amitt/Desktop/longLineText.txt");
             BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            String line;
            ExecutorService es = Executors.newFixedThreadPool(4);
            ExecutorService es1 = Executors.newFixedThreadPool(2);
            List<CompletableFuture<WordAndCountModel>> futureList = new ArrayList<>();
            System.out.println("start time thread " + new Date().getTime());

            while ((line = br.readLine()) != null) {
                String finalLine = line;
               futureList.add(CompletableFuture.supplyAsync(() -> readLineWordCount(finalLine), es));
            }
            es1.submit(() -> {
                CompletableFuture.allOf(futureList.toArray(new CompletableFuture[]{})).join();
                System.out.println("end time thread function before " + new Date().getTime());
                int futureListLen = futureList.size();
                for(int i=0; i<futureListLen; i++){
                    if(futureList.get(i) != null){
                        WordAndCountModel wordAndCountModel = futureList.get(i).join();
                       // System.out.println("thread " + Thread.currentThread().getName());
                       // System.out.println("line " + wordAndCountModel.getLine());
                       // System.out.println(" count " +wordAndCountModel.getCount());
                    }
                }
                System.out.println("end time thread " + new Date().getTime());
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("end time thread function " + new Date().getTime());
    }
}

package FileRead;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
        return new WordAndCountModel(tempStr.length(), tempStr.toString());
    }

    public void readFile() {
        try (InputStream is = new FileInputStream("/Users/amitt/Desktop/longLineText.txt");
             BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            String line;
            System.out.println("start time non thread " + new Date().getTime());
            while ((line = br.readLine()) != null) {
                System.out.println("count on non thread " + line.length());
            }
            System.out.println("end time non thread " + new Date().getTime());
        }catch(IOException e){
            throw new RuntimeException(e);
        }
    }

    public void readFileThread(){
        try (InputStream is = new FileInputStream("/Users/amitt/Desktop/longLineText.txt");
             BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            String line;
            System.out.println("start time thread " + new Date().getTime());
            ExecutorService es = Executors.newFixedThreadPool(2);
            List<CompletableFuture<WordAndCountModel>> futureList = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                String finalLine = line;
                futureList.add(CompletableFuture.supplyAsync(() -> readLineWordCount(finalLine), es));
            }
            es.submit(() -> {
                CompletableFuture.allOf(futureList.toArray(new CompletableFuture[]{})).join();
                int futureListLen = futureList.size();
                for(int i=0; i<futureListLen; i++){
                    if(futureList.get(i) != null){
                        WordAndCountModel wordAndCountModel = futureList.get(i).join();
                        System.out.println("thread " + Thread.currentThread().getName());
                        System.out.println("line " + wordAndCountModel.getLine());
                        System.out.println(" count " +wordAndCountModel.getCount());
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

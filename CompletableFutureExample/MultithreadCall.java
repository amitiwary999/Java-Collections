package CompletableFutureExample;

import Constants.Constant;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultithreadCall {
    private HttpRequest getRequest(String inData){
        String strUrl = "https://weatherapi-com.p.rapidapi.com/current.json?q=";
        strUrl += inData;
        HttpRequest request = HttpRequest.newBuilder(URI.create(strUrl)).header("X-RapidAPI-Key", Constant.RAPID_API_KEY)
                .header("X-RapidAPI-Host", "weatherapi-com.p.rapidapi.com")
                .GET().build();
        return request;
    }

    private String getWeatherData(String inData){
        System.out.println("thread " + Thread.currentThread().getId());
        try{
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = getRequest(inData);
            return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    private CompletableFuture<String> getWeatherDataFuture(String inData){
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = getRequest(inData);
            return client.sendAsync(request, HttpResponse.BodyHandlers.ofString()).thenApply(stringHttpResponse -> {
                System.out.println("Thread in res future " + Thread.currentThread().getName()+" id is "+Thread.currentThread().getId());
                return stringHttpResponse.body();
            });
        }catch (Exception e){
            e.printStackTrace();
            return  CompletableFuture.completedFuture("Failed to get the data");
        }
    }

    public void triggerCallFuture(){
        CompletableFuture<String> futureRes1 = getWeatherDataFuture("26.49,80.65");
        CompletableFuture<String> futureRes2 = getWeatherDataFuture("23.65,82.30");
        CompletableFuture<String> futureRes3 = getWeatherDataFuture("28.50,100.95");

        CompletableFuture.allOf(futureRes1, futureRes2, futureRes3);
        System.out.println("first resp " + futureRes1.join());
        System.out.println("second resp " + futureRes2.join());
        System.out.println("third resp " + futureRes3.join());
    }

    public void triggerCall(){
        String[] latLong = new String[]{"26.49,80.65", "23.65,82.30",  "28.50,100.95",  "13.95,-56.25",  "-0.70,-71.22", "10.23,56.92",
                "15.25,45.24", "13.00,23.00", "15.24,25.35", "55.67,65.45", "66.57,46.97", "98.21,23.44",
        };
        ExecutorService es = Executors.newFixedThreadPool(4);
        List<CompletableFuture<?>> futureList = new ArrayList<>();
        for(int i=0; i< latLong.length; i++){
            int finalI = i;
            futureList.add(CompletableFuture.supplyAsync(() -> getWeatherData(latLong[finalI]), es));
        }
        CompletableFuture.allOf(futureList.toArray(new CompletableFuture[]{})).join();

        for(int i=0; i< latLong.length; i++){
            if(futureList.get(i) != null)
                System.out.println("resp for " + i +" " + futureList.get(i).join());
        }
    }
}

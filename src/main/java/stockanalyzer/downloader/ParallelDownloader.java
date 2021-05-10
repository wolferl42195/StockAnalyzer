package stockanalyzer.downloader;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ParallelDownloader extends Downloader{
    @Override
    public int process(List<String> tickers) {

        ExecutorService executor = Executors.newCachedThreadPool();
        List<Future> futures = new ArrayList<>();

        for(String ticker : tickers){
            futures.add(executor.submit(new Task(ticker)));
        }

        for(int i = 0; i < tickers.size(); i++) {
            Future<String> future = futures.get(i);
            try{
                System.out.println("downloaded: " + tickers.get(i) + " to " + future.get());
            } catch (InterruptedException e){
                e.printStackTrace();
            } catch (ExecutionException e){
                e.printStackTrace();
            }
        }
        return futures.size();
    }

    static class Task implements Callable<String>{
        private String ticker;

        public Task(String ticker){
            this.ticker = ticker;
        }

        @Override
        public String call() throws Exception {
            SequentialDownloader sd = new SequentialDownloader();
            return sd.saveJson2File(ticker);
        }
    }
}

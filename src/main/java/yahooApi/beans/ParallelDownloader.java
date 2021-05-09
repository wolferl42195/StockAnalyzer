package yahooApi.beans;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ParallelDownloader extends Downloader{
    @Override
    public int process(List<String> tickers) {

        int count = 0;

        ArrayList<Runnable> runnables = new ArrayList<>();
        ExecutorService executor = Executors.newCachedThreadPool();

        for(int i = 0; i <= tickers.size(); i+=8) {
            runnables.add(new Runnable() {
                @Override
                public void run() {
                    Integer count = 0;
                    for (String ticker : tickers) {
                        String fileName = saveJson2File(ticker);
                        if(fileName != null)
                            count++;
                    }
                    Future<Integer> countint = executor.submit(count);
                }
            });
        }

        for(Runnable runnable:runnables){
            executor.execute(runnable);
        }

        return count;
    }
}

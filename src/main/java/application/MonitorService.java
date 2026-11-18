package application;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MonitorService{
    private final MonitorDispatcher dispatcher;
    
    public MonitorService(MonitorDispatcher dispatcher){
        this.dispatcher = dispatcher;
    }

    public List<MonitorResult> collectAll(){
        if(dispatcher.getMonitors().isEmpty())
            return null;
        ExecutorService executor = Executors.newFixedThreadPool(dispatcher.getMonitors().size());
        try {
            List<Future<MonitorResult>> futures = dispatcher.getMonitors()
                .stream()
                .map(m->executor.submit(()->{
                    try {
                        return MonitorResult.success(m.collect(), m.getName());
                    } catch (Exception e) {
                        return MonitorResult.failure(e, m.getName());
                    }
                }))
                .toList();
            List<MonitorResult> results = new ArrayList<>();
            for(Future<MonitorResult> future : futures){
                results.add(future.get());
            }
            return results;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Сбор данных прерван", e);
        } catch(ExecutionException e){
            throw new RuntimeException("Сбор данных прерван", e);
        } finally{
            executor.shutdown();
        }
    }
    public List<MonitorResult> collectAll_CompletbleFuture(){
        if(dispatcher.getMonitors().isEmpty())
            return null;
        ExecutorService executor = Executors.newFixedThreadPool(dispatcher.getMonitors().size());
        try {
            List<CompletableFuture<MonitorResult>> futures = dispatcher.getMonitors()
                .stream()
                .map(m->CompletableFuture.supplyAsync(m::collect, executor)
                    .handle((metrics, error)->{
                        if(error == null)
                            return MonitorResult.success(metrics, m.getName());
                        return MonitorResult.failure(error, m.getName());
                    }))
                .toList();
            CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

            return futures.stream().map(f->f.join()).toList();
        } catch(Exception e){
            throw new RuntimeException("Сбор данных прерван", e);
        } finally{
            executor.shutdown();
        }
    }
    public void printReport(List<MonitorResult> results){
        System.out.println(" >>> Report");
        if(results != null)
            for(MonitorResult result : results){
                System.out.println("\n [" + result.getMonitorName() + "]");
                if(result.isSuccess())
                    for(String key: result.getMetrics().keys())
                        System.out.println(" > " + key + ":\t" + result.getMetrics().get(key));
                else
                    System.out.println(" ! Error occured while collecting metrics: "+ result.getError().getMessage());
            }
        System.out.println(" <<<");
    }
}
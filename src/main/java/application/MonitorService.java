package application;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import domain.MapMetrics;
import domain.Metrics;
import domain.MonitorDispatcher;

public class MonitorService{
    private final MonitorDispatcher dispatcher;
    
    public MonitorService(MonitorDispatcher dispatcher){
        this.dispatcher = dispatcher;
    }

    public Metrics collectAll(){
        if(dispatcher.getMonitors().isEmpty())
            return null;
        ExecutorService executor = Executors.newFixedThreadPool(dispatcher.getMonitors().size());
        try {
            List<Future<Metrics>> futures = dispatcher.getMonitors()
                .stream()
                .map(m->executor.submit(m::collect))
                .toList();
            Map<String, Object> allValues = new HashMap<>();
            for(Future<Metrics> future : futures){
                Metrics metrics = future.get();
                allValues.putAll(metrics.getValues());
            }
            return new MapMetrics(new HashMap<>(allValues));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Сбор данных прерван", e);
        } catch(ExecutionException e){
            throw new RuntimeException("Сбор данных прерван", e);
        } finally{
            executor.shutdown();
        }
    }
    public Metrics collectAll_CompletbleFuture(){
        if(dispatcher.getMonitors().isEmpty())
            return null;
        ExecutorService executor = Executors.newFixedThreadPool(dispatcher.getMonitors().size());
        try {
            List<CompletableFuture<Metrics>> futures = dispatcher.getMonitors()
                .stream()
                .map(m->CompletableFuture.supplyAsync(m::collect, executor))
                .toList();
            CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
            HashMap<String, Object> allValues = new HashMap<>();
            for(CompletableFuture<Metrics> future : futures){
                allValues.putAll(future.join().getValues());
            }
            return new MapMetrics(allValues);
        } catch(Exception e){
            throw new RuntimeException("Сбор данных прерван", e);
        } finally{
            executor.shutdown();
        }
    }
    //public void printReport(Metrics metrics);
}
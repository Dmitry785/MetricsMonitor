package application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import domain.MapMetrics;
import domain.Metrics;
import domain.Monitor;
import domain.MonitorDispatcher;

public class MonitorService{
    private final MonitorDispatcher dispatcher;
    
    public MonitorService(MonitorDispatcher dispatcher){
        this.dispatcher = dispatcher;
    }

    public Metrics collectAll(){
        ExecutorService executor = Executors.newFixedThreadPool(3);
        try {
            List<Monitor> monitors = dispatcher.getMonitors();
            List<Future<Metrics>> futures = new ArrayList<>();
            for(Monitor monitor: monitors){
                Future<Metrics> future = executor.submit(monitor::collect);
                futures.add(future);
            }

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
        ExecutorService executor = Executors.newFixedThreadPool(3);
        try {
            
        } catch (Exception e) {
        }
    }
}
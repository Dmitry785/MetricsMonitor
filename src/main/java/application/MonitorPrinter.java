package application;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MonitorPrinter{
    private MonitorService service;
    private ScheduledExecutorService executor;
    private int drawDelay = 1;

    public MonitorPrinter(MonitorService service){
        this.service = service;
    }
    public void setDrawDelay(int delay){
        this.drawDelay = delay;
    }
    public void start(){
        if(executor != null && !executor.isShutdown())
            return;
        executor = Executors.newSingleThreadScheduledExecutor();

        executor.scheduleWithFixedDelay(()->{
            clearScreen();
            service.printReport(service.collectAll());
        }, 0, drawDelay, TimeUnit.SECONDS);
    }
    public void stop(){
        if(executor == null || executor.isShutdown())
            return;
        executor.shutdown();
    }
    private void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
package application;

import domain.Metrics;

public class MonitorResult{
    private final Metrics metrics;
    private final Throwable error;
    private final String monitorName;

    private MonitorResult(Metrics metrics, Throwable error, String monitorName){
        this.metrics = metrics;
        this.error = error;
        this.monitorName = monitorName;
    }

    public String getMonitorName(){
        return monitorName;
    }
    public Throwable getError(){
        return error;
    }
    public Metrics getMetrics(){
        return metrics;
    }
    public boolean isSuccess(){
        return error == null;
    } 
    public static MonitorResult success(Metrics metrics, String monitorName){
        return new MonitorResult(metrics, null, monitorName);
    }
    public static MonitorResult failure(Throwable error, String monitorName){
        return new MonitorResult(null, error, monitorName);
    }
}
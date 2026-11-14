package domain;

import java.util.ArrayList;
import java.util.List;

public class MonitorDispatcher{
    private final List<Monitor> monitors = new ArrayList<>();
    public void register(Monitor monitor){
        if(monitors.contains(monitor))
            return;
        monitors.add(monitor);
    }
    public List<Monitor> getMonitors(){
        return monitors;
    }
}
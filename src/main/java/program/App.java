package program;

import application.*;
import infrastructure.*;

public class App {
	public static void main(String[] args) {
		System.out.println("Program started");
		MonitorDispatcher dispatcher = new MonitorDispatcher();
		dispatcher.register(new CpuMonitor());
		dispatcher.register(new MemoryMonitor());
		dispatcher.register(new DiskMonitor());
		MonitorService monitorService = new MonitorService(dispatcher);

		monitorService.printReport(monitorService.collectAll());
	}
}

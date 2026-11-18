package program;

import java.io.IOException;

import application.*;
import infrastructure.*;

public class App {
	public static void main(String[] args) throws IOException {
		System.out.println("Program started");
		MonitorDispatcher dispatcher = new MonitorDispatcher();
		dispatcher.register(new CpuMonitor());
		dispatcher.register(new MemoryMonitor());
		dispatcher.register(new GpuMonitor());
		dispatcher.register(new DiskMonitor());
		MonitorService monitorService = new MonitorService(dispatcher);
		MonitorPrinter printer = new MonitorPrinter(monitorService);
		printer.setDrawDelay(2);
		printer.start();
		System.in.read();
		printer.stop();
	}
}

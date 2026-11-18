package program;

import application.MonitorService;
import domain.MonitorDispatcher;

public class App {
	public static void main(String[] args) {
		System.out.println("Program started");
		MonitorDispatcher dispatcher = new MonitorDispatcher();

		MonitorService monitorService = new MonitorService(dispatcher);

		monitorService.collectAll();
	}
}

package app;

import java.util.HashMap;

import domain.MapMetrics;

public class App {
	public static void main(String[] args) {
		System.out.println("Program started");
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("key1", 5);
		MapMetrics metrics = new MapMetrics(map);
		System.out.println(metrics.get("key1"));
	}
}

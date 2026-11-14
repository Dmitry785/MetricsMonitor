package program;

import java.util.HashMap;

import domain.MapMetrics;

public class App {
	public static void main(String[] args) {
		System.out.println("Program started");
		HashMap<String, Object> map = new HashMap<>();
		map.put("key1", 5);
		MapMetrics metrics = new MapMetrics(map);
		System.out.println(metrics.get("key1"));
		System.out.println(metrics.get("key1", String.class));
	}
}

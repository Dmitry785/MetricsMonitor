package infrastructure;

import java.util.HashMap;
import java.util.Map;

import oshi.SystemInfo;
import oshi.hardware.GlobalMemory;

import domain.*;

public class MemoryMonitor implements Monitor {
	@Override
	public String getName() {
		return "Memory monitor";
	}

	@Override
	public Metrics collect() {
		SystemInfo systemInfo = new SystemInfo();
		GlobalMemory memory = systemInfo.getHardware().getMemory();
		double totalMemory = memory.getTotal();
		double availableMemory = memory.getAvailable();
		double usedMemory = totalMemory - availableMemory;
		HashMap<String, Object> map = new HashMap<>();
		map.putAll(Map.of("memory.used", Math.round(usedMemory/Math.pow(1024, 3)*100)/100.0 + "Gb",
				"memory.total", Math.round(totalMemory/Math.pow(1024, 3)*100)/100.0 + "Gb",
				"memory.available", Math.round(availableMemory/Math.pow(1024, 3)*100)/100.0 + "Gb"));
		Metrics metrics = new MapMetrics(map);
		return metrics;
	}

}

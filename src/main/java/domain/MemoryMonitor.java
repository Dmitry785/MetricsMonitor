package domain;

import java.util.HashMap;
import java.util.Map;

import oshi.SystemInfo;
import oshi.hardware.GlobalMemory;

public class MemoryMonitor implements Monitor {
	@Override
	public String getName() {
		return "Memory monitor";
	}

	@Override
	public Metrics collect() {
		SystemInfo systemInfo = new SystemInfo();
		GlobalMemory memory = systemInfo.getHardware().getMemory();
		long totalMemory = memory.getTotal();
		long availableMemory = memory.getAvailable();
		long usedMemory = totalMemory - availableMemory;
		Map<String, Object> map = Map.of("memory.used", usedMemory,
				"memory.total", totalMemory,
				"memory.available", availableMemory);
		Metrics metrics = new MapMetrics(new HashMap<String, Object>(map));
		return metrics;
	}

}

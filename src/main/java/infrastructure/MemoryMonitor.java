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
		long totalMemory = memory.getTotal();
		long availableMemory = memory.getAvailable();
		long usedMemory = totalMemory - availableMemory;
		HashMap<String, Object> map = new HashMap<>();
		map.putAll(Map.of("memory.used", BytesConverter.SmartConvert(usedMemory),
				"memory.total", BytesConverter.SmartConvert(totalMemory),
				"memory.available", BytesConverter.SmartConvert(availableMemory)));
		Metrics metrics = new MapMetrics(map);
		return metrics;
	}

}

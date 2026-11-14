package domain;

import java.util.HashMap;
import java.util.Map;

import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.util.Util;

public class CpuMonitor implements Monitor{
	@Override
	public String getName() {
		return "Cpu monitor";
	}

	@Override
	public Metrics collect() {
		SystemInfo info = new SystemInfo();
		CentralProcessor cpu = info.getHardware().getProcessor();
		long[] oldTicks = cpu.getSystemCpuLoadTicks();
		Util.sleep(1000);
		double cpuLoad = cpu.getSystemCpuLoadBetweenTicks(oldTicks);
		Map<String, Object> map = Map.of("cpu.usage", cpuLoad);
		Metrics metrics = new MapMetrics(new HashMap<>(map));
		return metrics;
		
	}

}

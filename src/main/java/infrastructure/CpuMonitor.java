package infrastructure;

import java.util.HashMap;
import java.util.Map;

import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.util.Util;

import domain.*;

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
		HashMap<String, Object> map = new HashMap<>();
		map.put("cpu.usage", Math.round(cpuLoad*10000)/100.0 + "%");
		Metrics metrics = new MapMetrics(map);
		return metrics;
		
	}

}

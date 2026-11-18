package infrastructure;

import java.util.HashMap;
import java.util.Map;

import oshi.SystemInfo;
import oshi.hardware.GraphicsCard;

import domain.*;

public class GpuMonitor implements Monitor{
	@Override
	public String getName() {
		return "Gpu monitor";
	}

	@Override
	public Metrics collect() {
		SystemInfo info = new SystemInfo();
		
		HashMap<String, Object> map = new HashMap<>();
		map.put("gpu.count", info.getHardware().getGraphicsCards().size());
		for(GraphicsCard card : info.getHardware().getGraphicsCards()){
            String prefix = "gpu."+card.getName();
		    map.putAll(Map.of(prefix+".vendor", card.getVendor(),
		        prefix+".vram", BytesConverter.SmartConvert(card.getVRam()),
		        prefix+".id", card.getDeviceId()));
        }
		Metrics metrics = new MapMetrics(map);
		return metrics;
		
	}

}

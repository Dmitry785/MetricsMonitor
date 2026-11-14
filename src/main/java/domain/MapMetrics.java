package domain;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MapMetrics implements Metrics{
	private final Map<String, Object> metricsMap;
	public MapMetrics(HashMap<String, Object> map) {
		this.metricsMap = map;
	}
	@Override
	public Object get(String key) {
		return this.metricsMap.get(key);
	}

	@Override
	public <T> T get(String key, Class<T> type) {
		try {
			return type.cast(this.metricsMap.get(key));
		} catch(Exception e){
			return null;
		}
	}

	@Override
	public boolean contains(String key) {
		return this.metricsMap.containsKey(key);
	}

	@Override
	public Set<String> keys() {
		return this.metricsMap.keySet();
	}

	@Override
	public Map<String, Object> getValues() {
		return this.metricsMap;
	}
	
}

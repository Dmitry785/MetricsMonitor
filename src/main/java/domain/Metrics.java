package domain;

import java.util.Map;
import java.util.Set;

public interface Metrics {
	Object get(String key);
	<T> T get (String key, Class<T> type);
	boolean contains(String key);
	Set<String> keys();
	Map<String, Object> getValues();
}

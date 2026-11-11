package domain;

public interface Monitor {
	String getName();
	Metrics collect();
}

package domain;

import java.util.HashMap;
import java.util.List;

import oshi.SystemInfo;
import oshi.software.os.FileSystem;
import oshi.software.os.OSFileStore;

public class DiskMonitor implements Monitor{
	@Override
	public String getName() {
		return "Disk monitor";
	}

	@Override
	public Metrics collect() {
		SystemInfo info = new SystemInfo();
		FileSystem fs = info.getOperatingSystem().getFileSystem();
		List<OSFileStore> fileStores = fs.getFileStores();

		HashMap<String, Object> map = new HashMap<>();

        for(OSFileStore fileStore : fileStores){
            if(fileStore.getTotalSpace() == 0)
                continue;
            long total = fileStore.getTotalSpace();
            long free = fileStore.getFreeSpace();
            long usable = fileStore.getUsableSpace();
            map.put(fileStore.getName()+".total", total);
            map.put(fileStore.getName()+".free", free);
            map.put(fileStore.getName()+".usable", usable);
        }

		Metrics metrics = new MapMetrics(map);
		return metrics;
		
	}

}

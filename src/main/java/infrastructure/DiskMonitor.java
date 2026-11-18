package infrastructure;

import java.util.HashMap;
import java.util.List;

import oshi.SystemInfo;
import oshi.software.os.FileSystem;
import oshi.software.os.OSFileStore;

import domain.*;

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
			map.put("disk."+fileStore.getName()+".total", BytesConverter.SmartConvert(total));
			map.put("disk."+fileStore.getName()+".free", BytesConverter.SmartConvert(free));
			map.put("disk."+fileStore.getName()+".usable", BytesConverter.SmartConvert(usable));
        }

		Metrics metrics = new MapMetrics(map);
		return metrics;
		
	}

}

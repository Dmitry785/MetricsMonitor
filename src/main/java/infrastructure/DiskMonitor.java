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
			if(total/1024 >= 1)
            	map.put(fileStore.getName()+".total", Math.round(total/Math.pow(1024, 3)*100)/100.0 + "Gb");
			else
            	map.put(fileStore.getName()+".total", Math.round(total/Math.pow(1024, 2)*100)/100.0 + "Mb");
			if(free/1024 >= 1)
            	map.put(fileStore.getName()+".free", Math.round(free/Math.pow(1024, 3)*100)/100.0 + "Gb");
			else
            	map.put(fileStore.getName()+".free", Math.round(free/Math.pow(1024, 2)*100)/100.0 + "Mb");
			if(usable/1024 >= 1)
            	map.put(fileStore.getName()+".usable", Math.round(usable/Math.pow(1024, 3)*100)/100.0 + "Gb");
			else
            	map.put(fileStore.getName()+".usable", Math.round(usable/Math.pow(1024, 2)*100)/100.0 + "Mb");
			System.out.println(fileStore.getName() + total);
        }

		Metrics metrics = new MapMetrics(map);
		return metrics;
		
	}

}

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;

public class Main {

    public static void main(String[] args) {
        ConfigManager configReader = new ConfigManager();
        try {
            configReader.readConfigFile("src/config.cfg");
            configReader.outAllKeyVal();
        } catch (IOException e) {
            e.printStackTrace();
        }

        SystemMonitor systemMonitor = new SystemMonitor();
        systemMonitor.track();
    }
}
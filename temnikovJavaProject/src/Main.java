import java.io.IOException;


public class Main {

    public static void main(String[] args) {
        ConfigManager configReader = new ConfigManager();
        try {
            configReader.readConfigFile("src/config.cfg");
        } catch (IOException e) {
            e.printStackTrace();
        }

        SystemMonitor systemMonitor = new SystemMonitor();
        systemMonitor.track();

    }
}

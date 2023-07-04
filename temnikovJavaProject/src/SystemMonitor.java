import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class SystemMonitor {
    private int trackSecondsInterval = 0;

    SystemMonitor() {
        trackSecondsInterval = ConfigManager.getConfigValue("trackSecondsInterval");
    }

    public double trackCpuUsage() {
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("linux")) {
            return trackCpuUsageLinux();
        } else if (os.contains("mac")) {
            return trackCpuUsageMac();
        } else {
            System.out.println("Unsupported operating system: " + os);
            return 0;
        }
    }

    private double trackCpuUsageLinux() {
        // Мониторинг использования CPU
        double useCpu = 0;
        try {
            Process process = Runtime.getRuntime().exec("top -n 1 -b");
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("%Cpu(s)")) {
                    String[] parts = line.split(":");
                    if (parts.length > 1) {
                        String cpuUsageString = parts[1].trim().split(",")[3];
                        double cpuUsage = Double.parseDouble(cpuUsageString.replace("id", "").trim());
                        useCpu = 100 - cpuUsage;
                    }
                    break;
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return useCpu;
    }

    private double trackCpuUsageMac() {
        double useCpu = 0;
        try {
            Process process = Runtime.getRuntime().exec("top -l 1 -n 0 -stats cpu");
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("CPU usage:")) {
                    String[] parts = line.split(":");
                    if (parts.length > 1) {
                        String cpuUsageString = parts[1].trim().split(",")[2];
                        String cpuUsage = cpuUsageString.substring(0, cpuUsageString.indexOf("%"));
                        double cpuUsageDouble = Double.parseDouble(cpuUsage);
                        useCpu = 100 - cpuUsageDouble;
                    }
                    break;
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return useCpu;
    }


    public void trackDiskUsage() {
        // Мониторинг использования памяти

    }

    public void trackNetUsage() {
        // Мониторинг использования сети
    }

    public void trackRamUsage() {
        // Мониторинг использования оперативной памяти
    }
}

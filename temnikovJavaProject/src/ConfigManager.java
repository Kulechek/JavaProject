import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.io.BufferedReader;


public class ConfigManager {
    private static Map<String, Integer> configValues;
    ConfigManager() {
        configValues = new HashMap<>();
    }

    public void readConfigFile(String filePath) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split("=");
            if (parts.length == 2) {
                String key = parts[0].trim();
                String value = parts[1].trim();
                configValues.put(key, Integer.valueOf(value));
            }
        }

        reader.close();
    }

    public static Integer getConfigValue(String key) {
        return configValues.get(key);
    }

    public void outAllKeyVal() {
        configValues.forEach((key, value) -> System.out.println(key + " = " + value));
    }
}

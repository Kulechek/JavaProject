package com.example.temnikovJavaProject.services;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;
import java.sql.Timestamp;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.example.temnikovJavaProject.ConfigManager;
import com.example.temnikovJavaProject.DNS;
import com.example.temnikovJavaProject.models.TestPost;
import com.sun.management.OperatingSystemMXBean;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SystemMonitorService {
    private String os = "";
    private int trackCpu;
    private int trackRam;
    private int trackDisk;
    private int trackNet;
    private int trackSecondsInterval = 10;
    private int trackTimeMinute = 1;
    private String trackNetAddress;

    private TestPostService testPostService;

    @Autowired
    public SystemMonitorService(TestPostService testPostService) {
        this();
        this.testPostService = testPostService;
    }

    public SystemMonitorService() {
        whatIsOs();
        getConfigVar();
    }

    private void getConfigVar() {
        trackCpu = Integer.valueOf(ConfigManager.getConfigValue("trackCpu"));
        trackRam = Integer.valueOf(ConfigManager.getConfigValue("trackRam"));
        trackDisk = Integer.valueOf(ConfigManager.getConfigValue("trackDisk"));
        trackNet = Integer.valueOf(ConfigManager.getConfigValue("trackNet"));
        trackSecondsInterval = Integer.valueOf(ConfigManager.getConfigValue("trackSecondsInterval"));
        trackTimeMinute = Integer.valueOf(ConfigManager.getConfigValue("trackTimeMinute"));
        trackNetAddress = ConfigManager.getConfigValue("trackNetAddress");
    }

    private void whatIsOs() {
        os = System.getProperty("os.name").toLowerCase();
    }

    @PostConstruct
    public void startMonitoring() {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(this::track, 0, trackSecondsInterval, TimeUnit.SECONDS);
    }

    public void track() {
                double rezTrackCpuUsage = 0;
                if(trackCpu == 1) {
                    rezTrackCpuUsage = trackCpuUsage();
                    System.out.println("CPU usage: " + rezTrackCpuUsage + " %");
                }

                double rezTrackRamUsage = 0;
                if(trackRam == 1) {
                    rezTrackRamUsage = trackRamUsage();
                    System.out.println("Ram usage: "  +  rezTrackRamUsage + " %");
                }

                double rezTrackDiskUsage = 0;
                if(trackDisk == 1) {
                    rezTrackDiskUsage = trackDiskUsage();
                    System.out.println("Disk usage: " + rezTrackDiskUsage + "%");
                }

                double rezTrackNetUsage = 0;
                if(trackNet == 1) {
                    rezTrackNetUsage = trackNetUsage(trackNetAddress);
                    System.out.println("Ping: " + rezTrackNetUsage + " ms");
                }
                System.out.println();

                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                // Создание объекта TestPost (замените поля на соответствующие поля сущности)
                TestPost testPost = new TestPost(timestamp, rezTrackCpuUsage, rezTrackRamUsage, rezTrackDiskUsage, rezTrackNetUsage);

                // Сохранение в базе данных через сервис
                testPostService.saveTestPost(testPost);


    }

    private double trackCpuUsage() {
        if (os.contains("linux")) {
            return trackCpuUsageLinux();
        } else if (os.contains("mac")) {
            return trackCpuUsageMac();
        } else {
            System.out.println("Unsupported operating system: " + os);
            return 0;
        }
    }

    private double trackRamUsage() {
        if (os.contains("linux")) {
            return trackRamUsageLinux();
        } else if (os.contains("mac")) {
            return trackRamUsageMac();
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
                        useCpu = Math.round(useCpu * 100.0) / 100.0;
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
                        useCpu = Math.round(useCpu * 100.0) / 100.0;
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


    private double trackDiskUsage() {
        // Мониторинг использования памяти
        File file = new File("/");
        long totalSpace = file.getTotalSpace();
        long freeSpace = file.getFreeSpace();
        long usedSpace = totalSpace - freeSpace;
        double diskUsagePercentage = (double) usedSpace / totalSpace * 100.0;;
        diskUsagePercentage = Math.round(diskUsagePercentage * 100.0) / 100.0;

        return diskUsagePercentage;
    }

    private double trackNetUsage(String dnsServer) {
        long retPingTime = 0;
        if (DNS.isValidDNSServer(dnsServer)) {
            long pingTime = DNS.pingDNS(dnsServer);
            if(pingTime != -1) {
                retPingTime = pingTime;
            } else {
                trackNet = 0;
            }
        } else {
            System.out.println("Некорректный DNS сервер.");
            trackNet = 0;
        }

        return retPingTime;
    }

    private double trackRamUsageLinux() {
        double usedMemory = 0;
        try {
            Process process = Runtime.getRuntime().exec("free -m");
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Mem:")) {
                    String[] parts = line.split("\\s+");
                    if (parts.length >= 3) {
                        double totalMemory = Double.parseDouble(parts[1]);
                        double freeMemory = Double.parseDouble(parts[3]);
                        usedMemory = (totalMemory - freeMemory) / totalMemory * 100.0;
                        usedMemory = Math.round(usedMemory * 100.0) / 100.0;
                    }
                    break;
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return usedMemory;
    }

    private static double trackRamUsageMac() {
        OperatingSystemMXBean osBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        long totalMemory = osBean.getTotalPhysicalMemorySize();
        long freeMemory = osBean.getFreePhysicalMemorySize();
        long usedMemory = totalMemory - freeMemory;
        double ramUsagePercentage = (double) usedMemory / totalMemory * 100;
        double roundedPercentage = Math.round(ramUsagePercentage * 100.0) / 100.0;

        return roundedPercentage;
    }
}

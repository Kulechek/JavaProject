import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.time.Duration;

public class DNS implements Comparable<DNS>{
    private final String dnsIP;
    private final Duration time;

    public DNS(String newDnsIP, Duration newTime)
    {
        dnsIP = newDnsIP;
        time = newTime;
    }

    public Duration getTime()
    {
        return this.time;
    }

    public String getIP()
    {
        return this.dnsIP;
    }

    @Override
    public int compareTo(DNS o)
    {
        return Long.compare(this.time.toMillis(),o.time.toMillis());
    }

    public static boolean isValidDNSServer(String dnsServer) {
        String[] parts = dnsServer.split("\\.");
        if (parts.length != 4) {
            return false;
        }

        for (String part : parts) {
            try {
                int number = Integer.parseInt(part);
                if (number < 0 || number > 255) {
                    return false;
                }
            } catch (NumberFormatException e) {
                return false;
            }
        }

        return true; // DNS сервер корректен
    }

    public static long pingDNS(String dnsServer) {
        try {
            Socket socket = new Socket();
            long startTime = System.currentTimeMillis();
            socket.connect(new InetSocketAddress(dnsServer, 80));
            long endTime = System.currentTimeMillis();
            socket.close();
            return endTime - startTime;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1; // В случае ошибки возвращаем -1
    }

}

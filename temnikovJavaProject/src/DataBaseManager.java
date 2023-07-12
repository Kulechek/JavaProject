import java.sql.*;

public class DataBaseManager {
    private Connection connection;
    private String url;
    private String username;
    private String password;

    DataBaseManager(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public void connect() throws SQLException {
        connection = DriverManager.getConnection(url, username, password);
        System.out.println("\nУспешное подключение к базе данных.\n");
    }

    public void disconnect() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
            System.out.println("Отключение от базы данных выполнено.");
        }
    }

    public void insertRecord(double trackCpu, double trackRam, double trackDisk, double trackNet) throws SQLException {
        String sql = "INSERT INTO system_monitor (test_time, track_cpu, track_ram, track_disk, track_net) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        statement.setTimestamp(1, timestamp);
        statement.setDouble(2, trackCpu);
        statement.setDouble(3, trackRam);
        statement.setDouble(4, trackDisk);
        statement.setDouble(5, trackNet);

        int rowsInserted = statement.executeUpdate();
        if (rowsInserted > 0) {
            System.out.println("Запись успешно добавлена в таблицу.");
        }
    }
}

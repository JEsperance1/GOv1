import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 * This class represents the connection to the database and is built upon by the Exercise DAO. Database manager
 * establishes the connection and handles errors. Exercise DAO then uses that connection to make the specified changes
 * create, read, write, etc.
 */
public class DatabaseManager {
    public static String URL = "jdbc:sqlite:" + MainMenuSwing.mainFilePath;


    public static Connection connect() {
        URL = "jdbc:sqlite:" + MainMenuSwing.mainFilePath;
        System.out.println("Attempting to connect to the database...");
        System.out.println("JDBC URL: " + URL);

        try {
            // Explicitly load the JDBC driver
            Class.forName("org.sqlite.JDBC");
            System.out.println("JDBC Driver loaded successfully.");

            // Attempt to establish the connection
            Connection conn = DriverManager.getConnection(URL);
            if (conn != null) {
                System.out.println("Connection successful!");
            } else {
                System.out.println("Failed to establish connection.");
            }
            return conn;

        } catch (ClassNotFoundException e) {
            System.out.println("SQLite JDBC driver not found: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Database connection failed: " + e.getMessage());
            e.printStackTrace(); // Print the stack trace for more details
        }
        return null;
    }
}
import java.sql.Connection;
import java.sql.Statement;

/**
 * This class creates a table in the event that the exercise table does not already exist. Once the table is
 * created.The ExerciseDAO class writes to it and performs the action specified by the user. This is because the
 * program no longer reads from a text file to pull its data. The program now establishes a connection to a database
 * via the database manager and reads via the read
 */
public class TableCreator {

    /**
     * Creates the "exercises" table in the database if it doesn't already exist.
     * The table includes columns for ID, name, and category.
     **/
    public static void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS exercises ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "name TEXT NOT NULL, "
                + "category TEXT NOT NULL"
                + ");";

        try (Connection conn = DatabaseManager.connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Table created successfully!");
        } catch (Exception e) {
            System.out.println("Error creating table: " + e.getMessage());
        }
    }
}
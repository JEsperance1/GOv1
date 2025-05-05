import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/**
 * The ExerciseDAO class serves as the interface between the held user input and the database write action
 * it does the same for the other functions of the app such as data reading and updating.
 */
public class ExerciseDAO {

    // INSERT an exercise into the database
    /**
     * Inserts a new exercise record into the database.
     *
     * @param exercise the name of the exercise to insert
     * @param weight the weight used during the exercise
     * @param reps the number of repetitions performed
     * @param sets the number of sets performed
     * @param rpe the rate of perceived exertion (e.g. 1–10 scale)
     * @throws SQLException if a database access error occurs
     */
    public static void insertExercise(String exercise, double weight, int reps, int sets, double rpe) {
        String sql = "INSERT INTO exercises (name, weight, reps, sets, rpe) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, exercise);
            pstmt.setDouble(2, weight);
            pstmt.setInt(3, reps);
            pstmt.setInt(4, sets);
            pstmt.setDouble(5, rpe);

            pstmt.executeUpdate();
            System.out.println("Exercise added!");
        } catch (Exception e) {
            System.out.println("Insert failed: " + e.getMessage());
            System.out.print(DatabaseManager.URL);
        }
    }

    // Method to fetch all exercise records from the database

    /**
     * Retrieves all exercise records from the database and returns them as a list of formatted strings.
     *
     * @return a list of strings representing each exercise record in the database;
     *         returns a list with an error message if the retrieval fails
     * @throws SQLException if a database access error occurs
     */
    public static List<String> getAllExerciseRecords() {
        List<String> records = new ArrayList<>();
        String sql = "SELECT * FROM exercises";

        try (Connection conn = DatabaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            if (conn == null) {
                throw new SQLException("Failed to establish a connection.");
            }

            System.out.println("Database connection successful!");

            while (rs.next()) {
                int exerciseId = rs.getInt("id");
                String name = rs.getString("name");
                double weight = rs.getDouble("weight");
                int reps = rs.getInt("reps");
                int sets = rs.getInt("sets");
                double rpe = rs.getDouble("rpe");

                // Format the record as a string
                String record = String.format("ID: %d | Name: %s | Weight: %.2f | Reps: %d | Sets: %d | RPE: %.2f |",
                        exerciseId, name, weight, reps, sets, rpe);

                // Print each record as it is parsed
                //System.out.println(record);

                // Add to the records list for later display in the JTextArea
                records.add(record);
                System.out.print(records);
            }

            // ✅ Add this right here
            if (records.isEmpty()) {
                System.out.println("No records found in the database.");
                records.add("No exercise records to display.");
                System.out.println("These are your records:" + records);
            }

        } catch (SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage());
            e.printStackTrace();
            records.add("Failed to load data from the database.");
        }

        return records;
    }


    public static void createExercisesTableIfNotExists() {
        String sql = """
        CREATE TABLE IF NOT EXISTS exercises (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            name TEXT NOT NULL,
            weight REAL,
            reps INTEGER,
            sets INTEGER,
            rpe REAL
        );
        """;

        try (Connection conn = DatabaseManager.connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("✅ Table 'exercises' verified or created.");
        } catch (SQLException e) {
            System.err.println("❌ Failed to create or verify table: " + e.getMessage());
        }
    }
    /**
     * Reads a SQL file and validates/executes only valid INSERT INTO exercises statements.
     *
     * @param filePath the path to the SQL file containing insert statements
     * @return true if all INSERT statements are valid and executed successfully; false otherwise
     * @throws IOException if an error occurs while reading the file
     * @throws SQLException if a SQL statement is invalid or fails during execution
     */
    public static boolean validateAndReadSQLFile(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath));
             Connection conn = DatabaseManager.connect()) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();

                // Check for a valid INSERT statement
                if (line.toUpperCase().startsWith("INSERT INTO exercises")) {
                    try (PreparedStatement pstmt = conn.prepareStatement(line)) {
                        pstmt.executeUpdate();
                    } catch (SQLException ex) {
                        System.out.println("SQL Error: " + ex.getMessage());
                        return false;  // Return false if there are errors in execution
                    }
                } else {
                    return false;  // Invalid SQL format
                }
            }
            return true;  // All records processed successfully
        } catch (IOException | SQLException ex) {
            System.out.println("File/SQL Processing Error: " + ex.getMessage());
            return false;
        }
    }


    // Method to fetch exercise data, calculate total weight lifted, and update it in the database
    /**
     * Fetches weight, reps, and sets for a specific exercise by ID and calculates total weight lifted.
     *
     * @param exerciseId the ID of the exercise to retrieve
     * @return the total weight lifted (weight × reps × sets), or 0 if the exercise does not exist
     * @throws SQLException if a database access error occurs
     */
    public static double getExerciseDataAndCalculateTWL(int exerciseId) {
        String sql = "SELECT weight, reps, sets FROM exercises WHERE id = ?";

        try (Connection conn = DatabaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Set the exercise ID in the query
            pstmt.setInt(1, exerciseId);

            // Execute the query and get the result
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    // Retrieve the weight, reps, and sets for the exercise
                    double weight = rs.getDouble("weight");
                    int reps = rs.getInt("reps");
                    int sets = rs.getInt("sets");

                    // Calculate the total weight lifted
                    double totalWeightLifted = weight * reps * sets;
                    return totalWeightLifted;
                } else {
                    System.out.println("No exercise found with ID: " + exerciseId);
                }
            }

        } catch (Exception e) {
            System.out.println("Error fetching exercise data: " + e.getMessage());
        }
        return 0;
    }



    /**
     * Checks if an exercise with the specified ID exists in the database.
     *
     * @param exerciseId the ID of the exercise to check
     * @return true if the exercise exists; false otherwise
     * @throws SQLException if a database access error occurs
     */
    public static boolean doesExerciseExist(int exerciseId) {
        String sql = "SELECT COUNT(*) FROM exercises WHERE id = ?";
        try (Connection conn = DatabaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, exerciseId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;  // Returns true if exercise exists
                }
            }
        } catch (Exception e) {
            System.out.println("Error checking exercise existence: " + e.getMessage());
        }
        return false;  // Returns false if the exercise does not exist
    }



    // UPDATE an exercise in the database by ID
    /**
     * Updates an existing exercise record in the database with new values.
     *
     * @param id the ID of the exercise to update
     * @param exercise the new name of the exercise
     * @param weight the new weight value
     * @param reps the new number of repetitions
     * @param sets the new number of sets
     * @param rpe the new rate of perceived exertion
     * @throws SQLException if a database access error occurs or if the update fails
     */
    public static void updateExercise(int id, String exercise, double weight, int reps, int sets, double rpe) {
        String sql = "UPDATE exercises SET name = ?, weight = ?, reps = ?, sets = ?, rpe = ? WHERE id = ?";
        try (Connection conn = DatabaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, exercise);
            pstmt.setDouble(2, weight);
            pstmt.setInt(3, reps);
            pstmt.setInt(4, sets);
            pstmt.setDouble(5, rpe);
            pstmt.setInt(6, id);

            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Exercise updated!");
            } else {
                System.out.println("No exercise found with that ID.");
            }
        } catch (Exception e) {
            System.out.println("Update failed: " + e.getMessage());
        }
    }


    // DELETE an exercise from the database based on exerciseId
    /**
     * Deletes an exercise from the database by its ID.
     *
     * @param exerciseId the ID of the exercise to delete
     * @throws SQLException if a database access error occurs or the deletion fails
     */
    public static void deleteExercise(int exerciseId) {
        String sql = "DELETE FROM exercises WHERE id = ?";

        try (Connection conn = DatabaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Set the exercise ID to delete
            pstmt.setInt(1, exerciseId);

            // Execute the delete query
            int rowsAffected = pstmt.executeUpdate();

            // Check if any row was deleted
            if (rowsAffected > 0) {
                System.out.println("Exercise deleted successfully!");
            } else {
                System.out.println("No exercise found with ID: " + exerciseId);
            }
        } catch (Exception e) {
            System.out.println("Delete failed: " + e.getMessage());
        }
    }

}
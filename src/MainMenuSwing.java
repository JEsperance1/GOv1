import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * This Class is sets up the main menu and all of its component UI assets. Buttons, text fields,
 * action listeners.
 **/
public class MainMenuSwing {
    public static String mainFilePath;
    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainMenuSwing::new);

        // STARTUP SCREEN SETUP
        startUp1.setLayout(new GridLayout(4, 1, 10, 10));
        startUp1.setBackground(Color.decode("#2C3E50"));

        JLabel filePathLabel = new JLabel(
                "<html>Input a filepath to start. This is where your data will be stored and read from.<br>" +
                        "If you are copying the filepath from somewhere, ensure it is not in quotations.</html>"
        );
        filePathLabel.setForeground(Color.WHITE);
        filePathLabel.setHorizontalAlignment(SwingConstants.CENTER);
        startUp1.add(filePathLabel);

        JTextField filePathInputField = new JTextField(20);
        startUp1.add(filePathInputField);

        JButton addFilePath = new JButton("Add Filepath");
        startUp1.add(addFilePath);

// Action listener to handle filepath validation and segue to main menu
        addFilePath.addActionListener(e -> {
            String filePath = filePathInputField.getText().trim();
            if (filePath.isEmpty()) {
                JOptionPane.showMessageDialog(startUp1, "File path cannot be empty.");
                return;
            }
            if (!Files.exists(Paths.get(filePath)) || !Files.isRegularFile(Paths.get(filePath))) {
                JOptionPane.showMessageDialog(startUp1, "Invalid file path. Please enter a valid file.");
                return;
            }
            // File path is valid
            JOptionPane.showMessageDialog(startUp1, "File path added successfully!");
            MainMenuSwing.mainFilePath = filePath;
            // Move to main menu
            moveToCard("Main Menu");
            DatabaseManager.connect();
        });
    }



    private JFrame frame;
    private static JPanel cardPanel;
    private static CardLayout mainMenuCardLayout;
    private JPanel mainMenuPanel;


    private static JPanel startUp1 = new JPanel();

    private JPanel logExercise1 = new JPanel();
    private JPanel logExercise2 = new JPanel();
    private JPanel logExercise3 = new JPanel();
    private JPanel logExercise4 = new JPanel();
    private JPanel logExercise5 = new JPanel();

    private JPanel readFromFile1 = new JPanel();
    private JPanel readFromFile2 = new JPanel();
    private JPanel readFromFile3 = new JPanel();
    private JPanel readFromFile4 = new JPanel();

    private JPanel updateFile1 = new JPanel();
    private JPanel updateFile2 = new JPanel();
    private JPanel updateFile3 = new JPanel();
    private JPanel updateFile4 = new JPanel();
    private JPanel updateFile5 = new JPanel();
    private JPanel updateFile6 = new JPanel();

    private JPanel deleteRecord1 = new JPanel();
    private JPanel deleteRecord2 = new JPanel();
    private JPanel deleteRecord3 = new JPanel();
    private JPanel deleteRecord4 = new JPanel();

    private JPanel displayRecords1 = new JPanel();
    private JPanel displayRecords2 = new JPanel();
    private JPanel displayRecords3 = new JPanel();
    private JPanel displayRecords4 = new JPanel();

    private JPanel TWL1 = new JPanel();
    private JPanel TWL2 = new JPanel();
    private JPanel TWL3 = new JPanel();
    private JPanel TWL4 = new JPanel();






    private String updateRecordId;

    private void returnToMainMenu() {
        mainMenuCardLayout.show(cardPanel, "Main Menu");
    }
    private static void moveToCard(String cardName) {
        mainMenuCardLayout.show(cardPanel, cardName);
    }


//creates new screen that preceeds the main menu

    public MainMenuSwing() {
        frame = new JFrame("Main Menu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(626, 450);       frame.setLocationRelativeTo(null);

        mainMenuCardLayout = new CardLayout();
        cardPanel = new JPanel(mainMenuCardLayout);

        mainMenuPanel = new JPanel(new GridLayout(7, 1, 10, 10));

        JButton createRecord = new JButton("Log Exercise");
        JButton readFromFile = new JButton("Read from File");
        JButton updateRecords = new JButton("Update Record");
        JButton deleteRecord = new JButton("Delete Record");
        JButton displayRecords = new JButton("Display Records");
        JButton TWL = new JButton("TWL");
        JButton exit = new JButton("Exit");



        mainMenuPanel.add(createRecord);
        //  mainMenuPanel.add(readFromFile);
        mainMenuPanel.add(updateRecords);
        mainMenuPanel.add(deleteRecord);
        mainMenuPanel.add(displayRecords);
        mainMenuPanel.add(TWL);
        mainMenuPanel.add(exit);


        createRecord.addActionListener(e -> {moveToCard("Log 1");
            DatabaseManager.connect();
        });
        readFromFile.addActionListener(e -> {moveToCard("Start 1");
            DatabaseManager.connect();
        });
        updateRecords.addActionListener(e -> {moveToCard("Update 1");
            DatabaseManager.connect();
            System.out.print("This should refresh connection to the database");
        });
        deleteRecord.addActionListener(e -> {moveToCard("Delete 1");
            DatabaseManager.connect();
        });

        TWL.addActionListener(e -> {moveToCard("TWL 1");
            DatabaseManager.connect();
        });
        exit.addActionListener(e -> System.exit(0));








        //This section defines behavior of the create record pathway
        logExercise1.add(new JLabel("What Exercise will you be doing?"));
        logExercise1.setBackground(Color.decode("#464D77"));
        JTextField exerciseInputField = new JTextField(20);
        logExercise1.add(exerciseInputField);
        JButton toLogWorkout2 = new JButton("Submit");
        toLogWorkout2.addActionListener(e -> {
            String exerciseText = exerciseInputField.getText();
            if (exerciseText.trim().isEmpty()) {
                JOptionPane.showMessageDialog(logExercise2, "Please enter an exercise name.");
            } else {
                moveToCard("Log 2");
            }
        });

        JButton returnToHomeButtonFromLogWorkout1 = new JButton("Return To Home Screen");
        returnToHomeButtonFromLogWorkout1.addActionListener(e -> {
            moveToCard("Main Menu");
        });
        logExercise1.add(returnToHomeButtonFromLogWorkout1);
        logExercise1.add(toLogWorkout2);

        logExercise2.add(new JLabel("How much Weight?"));
        logExercise2.setBackground(Color.decode("#464D77"));
        JTextField weightInputField = new JTextField(20);
        logExercise2.add(weightInputField);
        JButton toLogWorkout3 = new JButton("Submit");
        toLogWorkout3.addActionListener(e -> {
            String weightText = weightInputField.getText();
            if (isDouble(weightText)) {
                moveToCard("Log 3");
            } else {
                JOptionPane.showMessageDialog(logExercise2, "Please enter a valid number for weight.");
            }
        });
        logExercise2.add(toLogWorkout3);

        logExercise3.add(new JLabel("How many Reps?"));
        logExercise3.setBackground(Color.decode("#464D77"));
        JTextField repsInputField = new JTextField(20);
        logExercise3.add(repsInputField);
        JButton toLogWorkout4 = new JButton("Submit");
        toLogWorkout4.addActionListener(e -> {
            String repsText = repsInputField.getText();
            while (repsText.trim().isEmpty() || !isInteger(repsText) || Integer.parseInt(repsText) < 1 ) {
                JOptionPane.showMessageDialog(logExercise3, "Please enter a valid integer for reps.");
                return;
            }
            moveToCard("Log 4");
        });
        logExercise3.add(toLogWorkout4);

        logExercise4.add(new JLabel("How many Sets?"));
        logExercise4.setBackground(Color.decode("#464D77"));
        JTextField setsInputField = new JTextField(20);
        logExercise4.add(setsInputField);
        JButton toLogWorkout5 = new JButton("Submit");
        toLogWorkout5.addActionListener(e -> {
            String setsText = setsInputField.getText();
            while (!isInteger(setsText)) {
                JOptionPane.showMessageDialog(logExercise4, "Please enter a valid integer for sets.");
                return;
            }
            moveToCard("Log 5");
        });
        logExercise4.add(toLogWorkout5);

        logExercise5.add(new JLabel("Enter RPE:"));
        logExercise5.setBackground(Color.decode("#464D77"));
        JTextField RPEInputField = new JTextField(20);
        logExercise5.add(RPEInputField);
        JButton returnToHomeButton = new JButton("Save and Return To Home Screen");
        returnToHomeButton.addActionListener(e -> {
            String repsText = repsInputField.getText().trim();
            String RPEText = RPEInputField.getText().trim();

            if (repsText.isEmpty() || !isInteger(repsText) || Integer.parseInt(repsText) < 1) {
                JOptionPane.showMessageDialog(logExercise3, "Please enter valid reps.");
                return;
            }

            if (RPEText.isEmpty() || !isInteger(RPEText) || Integer.parseInt(RPEText) < 1 || Integer.parseInt(RPEText) > 10) {
                JOptionPane.showMessageDialog(logExercise3, "Please enter a valid RPE.");
                return;
            }

           /* Day newDay = new Day(
                    exerciseInputField.getText(),
                    Double.parseDouble(weightInputField.getText()),
                    Integer.parseInt(repsText),
                    Integer.parseInt(setsInputField.getText()),
                    Integer.parseInt(RPEText)
            );


            saveToFile(newDay);  // Save the data to a text file*/
            ExerciseDAO.insertExercise(exerciseInputField.getText(),
                    Double.parseDouble(weightInputField.getText()),
                    Integer.parseInt(repsText),
                    Integer.parseInt(setsInputField.getText()),
                    Integer.parseInt(RPEText));


            returnToMainMenu();
        });

        // Read from File Panel
        JPanel readFromFilePanel = new JPanel();
        readFromFilePanel.setLayout(new BorderLayout());
        JTextArea fileContentArea = new JTextArea();
        fileContentArea.setEditable(false); // Make it read-only
        JScrollPane scrollPane = new JScrollPane(fileContentArea);
        readFromFilePanel.add(scrollPane, BorderLayout.CENTER);

        JButton backButton = new JButton("Back to Main Menu");
        backButton.addActionListener(e -> returnToMainMenu());
        readFromFilePanel.add(backButton, BorderLayout.SOUTH);

        // Read content from the file and display it
        File file = new File("workout_log.txt");
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                StringBuilder fileContent = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    fileContent.append(line).append("\n");
                }
                fileContentArea.setText(fileContent.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            fileContentArea.setText("No exercise records found.");
        }
        logExercise5.add(returnToHomeButton);




















        //This section defines behavior of the read record pathway
        readFromFile1.add(new JLabel("Input the filepath:"));
        readFromFile1.setBackground(Color.decode("#44CC00"));
        JTextField filepathInputField = new JTextField(20);
        JButton toReadFromFile2 = new JButton("Submit");
        readFromFile1.add(filepathInputField);

        JButton returnToHomeButtonFromRead1 = new JButton("Return To Home Screen");
        readFromFile1.add(returnToHomeButtonFromRead1);
        returnToHomeButtonFromRead1.addActionListener(e10 -> {
            moveToCard("Main Menu");

        });

        readFromFile1.add(toReadFromFile2);
        toReadFromFile2.addActionListener(e -> {
            String filePath = filepathInputField.getText();

            if (filePath.trim().isEmpty() || !Files.exists(Paths.get(filePath)) || !Files.isRegularFile(Paths.get(filePath))) {
                JOptionPane.showMessageDialog(readFromFile1, "Please enter a valid filepath.");
            } else {
                ExerciseDAO.validateAndReadSQLFile(filePath);

                System.out.print(DatabaseManager.URL);
                moveToCard("Read 2");
            }
        });
        readFromFile2.add(new JLabel("File was found! If valid data is found in it, \n"));
        readFromFile2.add(new JLabel("it will be added to the database."));






        JButton backButtonFromReadFile2 = new JButton("Back to Main Menu");
        readFromFile2.setBackground(Color.decode("#44CC00"));
        readFromFile2.add(backButtonFromReadFile2);
        backButtonFromReadFile2.addActionListener(e -> {
            /*performs the logic  that stores the data


             */
            moveToCard("Main Menu");
        });





















// This section defines behavior of the update record pathway
        updateFile1.add(new JLabel("Input the exercise ID you want to update:"));
        updateFile1.setBackground(Color.decode("#FF9F1C"));
        JTextField updateRecordInputField = new JTextField(20);
        updateFile1.add(updateRecordInputField);
        JButton toMainMenuFromUpdate1 = new JButton("Return to Main Menu");
        JButton toUpdateFile2 = new JButton("Submit");
        updateFile1.add(toUpdateFile2);
        updateFile1.add(toMainMenuFromUpdate1);

        toMainMenuFromUpdate1.addActionListener(e -> moveToCard("Main Menu"));

        toUpdateFile2.addActionListener(e1 -> {
            updateRecordId = updateRecordInputField.getText().trim();

            if (updateRecordId.isEmpty() || !isInteger(updateRecordId) || !ExerciseDAO.doesExerciseExist(Integer.parseInt(updateRecordId))) {
                JOptionPane.showMessageDialog(updateFile1, "No Exercise was found with that ID.");
            } else {
                moveToCard("Update 2");
            }
        });

        updateFile2.add(new JLabel("What Exercise will you be doing?"));
        updateFile2.setBackground(Color.decode("#FF9F1C"));
        JTextField updateExerciseInputField = new JTextField(20);
        updateFile2.add(updateExerciseInputField);
        JButton toUpdateFile3 = new JButton("Submit");
        toUpdateFile3.addActionListener(e2 -> {
            if (updateExerciseInputField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(updateFile2, "Please enter an exercise name.");
            } else {
                moveToCard("Update 3");
            }
        });
        updateFile2.add(toUpdateFile3);

        updateFile3.add(new JLabel("How much Weight?"));
        updateFile3.setBackground(Color.decode("#FF9F1C"));
        JTextField updateWeightInputField = new JTextField(20);
        updateFile3.add(updateWeightInputField);
        JButton toUpdateFile4 = new JButton("Submit");
        toUpdateFile4.addActionListener(e3 -> {
            if (isDouble(updateWeightInputField.getText())) {
                moveToCard("Update 4");
            } else {
                JOptionPane.showMessageDialog(updateFile3, "Please enter a valid number for weight.");
            }
        });
        updateFile3.add(toUpdateFile4);

        updateFile4.add(new JLabel("How many Reps?"));
        updateFile4.setBackground(Color.decode("#FF9F1C"));
        JTextField updateRepsInputField = new JTextField(20);
        updateFile4.add(updateRepsInputField);
        JButton toUpdateFile5 = new JButton("Submit");
        updateFile4.add(toUpdateFile5);

        toUpdateFile5.addActionListener(e4 -> {
            String updateRepsText = updateRepsInputField.getText().trim();

            if (updateRepsText.isEmpty() || !isInteger(updateRepsText)) {
                JOptionPane.showMessageDialog(updateFile4, "Please enter a valid integer for reps.");
                return;
            }

            int updateRepsValue = Integer.parseInt(updateRepsText);
            if (updateRepsValue < 0) {
                JOptionPane.showMessageDialog(updateFile4, "Reps must be a non-negative integer.");
                return;
            }

            moveToCard("Update 5");
        });

        updateFile5.add(new JLabel("How many Sets?"));
        updateFile5.setBackground(Color.decode("#FF9F1C"));
        JTextField updateSetsInputField = new JTextField(20);
        updateFile5.add(updateSetsInputField);
        JButton toUpdateFile6 = new JButton("Submit");
        updateFile5.add(toUpdateFile6);

        toUpdateFile6.addActionListener(e5 -> {
            String updateSetsText = updateSetsInputField.getText().trim();

            if (updateSetsText.isEmpty() || !isInteger(updateSetsText)) {
                JOptionPane.showMessageDialog(updateFile5, "Please enter a valid integer for sets.");
                return;
            }

            int setsValue = Integer.parseInt(updateSetsText);
            if (setsValue < 1) {
                JOptionPane.showMessageDialog(updateFile5, "Sets must be at least 1.");
                return;
            }

            moveToCard("Update 6");
        });

        updateFile6.add(new JLabel("Enter RPE:"));
        updateFile6.setBackground(Color.decode("#FF9F1C"));
        JTextField updateRPEInputField = new JTextField(20);
        updateFile6.add(updateRPEInputField);
        JButton returnToHomeButtonFromUpdate6 = new JButton("Save and Return To Home Screen");
        updateFile6.add(returnToHomeButtonFromUpdate6);

        returnToHomeButtonFromUpdate6.addActionListener(e5 -> {
            String updateRPEText = updateRPEInputField.getText().trim();

            if (updateRPEText.isEmpty() || !isInteger(updateRPEText)) {
                JOptionPane.showMessageDialog(updateFile6, "Please enter a valid integer for RPE.");
                return;
            }

            int updateRPEValue = Integer.parseInt(updateRPEText);
            if (updateRPEValue < 1 || updateRPEValue > 10) {
                JOptionPane.showMessageDialog(updateFile6, "Please enter a valid RPE between 1 and 10.");
                return;
            }

            ExerciseDAO.updateExercise(
                    Integer.parseInt(updateRecordId),
                    updateExerciseInputField.getText(),
                    Double.parseDouble(updateWeightInputField.getText()),
                    Integer.parseInt(updateRepsInputField.getText()),
                    Integer.parseInt(updateSetsInputField.getText()),
                    Double.parseDouble(updateRPEInputField.getText())
            );

            returnToMainMenu();
        });












        //This section defines behavior of the delete record pathway
        deleteRecord1.add(new JLabel("Input the Exercise ID you want to delete:"));
        deleteRecord1.setBackground(Color.decode("#F71735"));
        JTextField exerciseIdInputField = new JTextField(20);
        deleteRecord1.add(exerciseIdInputField);
        JButton toDeleteFile2 = new JButton("Submit");

        JButton toMainMenuFromDelete1 = new JButton("Return to Main Menu");
        deleteRecord1.add(toDeleteFile2);
        deleteRecord1.add(toMainMenuFromDelete1);

        toMainMenuFromDelete1.addActionListener(e -> {
            moveToCard("Main Menu");
        });

        toDeleteFile2.addActionListener(e -> {
            String exerciseId = exerciseIdInputField.getText();

            if (exerciseId.trim().isEmpty() || !isInteger(exerciseId) || !ExerciseDAO.doesExerciseExist(Integer.parseInt(exerciseId.trim()))) {
                JOptionPane.showMessageDialog(deleteRecord1, "No Exercise was found with that ID.");
            } else {
                ExerciseDAO.deleteExercise(Integer.parseInt(exerciseId));
                moveToCard("Delete 2");
            }
        });
        deleteRecord2.add(new JLabel("Record has been deleted successfully."));
        deleteRecord2.setBackground(Color.decode("#F71735"));
        JButton toMainMenuFromDelete2 = new JButton("Return to Main Menu");
        deleteRecord2.add(toMainMenuFromDelete2);

        toMainMenuFromDelete2.addActionListener(e -> {
            moveToCard("Main Menu");
        });











// Display Records Panel
        displayRecords1 = new JPanel();
        displayRecords1.setLayout(new BoxLayout(displayRecords1, BoxLayout.Y_AXIS)); // Using BoxLayout to center content
        displayRecords1.setBackground(Color.decode("#B700E0"));

// Set the layout of displayRecords1 to FlowLayout with CENTER alignment
        displayRecords1.setLayout(new FlowLayout(FlowLayout.CENTER));

// Create a JLabel to display a header
        JLabel headerLabel = new JLabel("Workout History:", JLabel.CENTER);
        headerLabel.setFont(new Font("Arial", Font.PLAIN, 16));  // Optionally adjust font size or style
        displayRecords1.add(headerLabel);

// Create a JTextArea to display the records (20 rows, 50 columns)
        JTextArea textArea = new JTextArea(20, 50);
        textArea.setBackground(Color.decode("#B700E0"));
        textArea.setEditable(false);  // Make it non-editable
        textArea.setFont(new Font("Arial", Font.PLAIN, 12));  // Set a smaller font for the text
        textArea.setMargin(new Insets(10, 10, 10, 10));  // Optional padding

// Set alignment to center
        textArea.setAlignmentX(JTextArea.CENTER_ALIGNMENT);

        displayRecords.addActionListener(e -> {moveToCard("Display 1");
            DatabaseManager.connect();
            // Fetch records from the database
            List<String> records = ExerciseDAO.getAllExerciseRecords();

            // Build the display text
            StringBuilder recordsText = new StringBuilder();
            for (String record : records) {
                recordsText.append(record).append("\n");
            }

            // Update the JTextArea with formatted records
            textArea.setText(recordsText.toString());

            // Add the panel containing the textArea (if not already added)
            cardPanel.add(displayRecords1, "DisplayRecords");
            CardLayout layout = (CardLayout) cardPanel.getLayout();
            layout.show(cardPanel, "DisplayRecords");

            frame.add(cardPanel);
            frame.setVisible(true);

        });
// Create a JScrollPane to enable scrolling
        JScrollPane scrollPane1 = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(500, 300)); // Optional: Adjust the scrollpane size
        displayRecords1.add(scrollPane1); // Add the scrollable text area

// Add a return button
        JButton returnToHomeButtonFromDisplay1 = new JButton("Return To Home Screen");
        displayRecords1.add(returnToHomeButtonFromDisplay1);
        returnToHomeButtonFromDisplay1.addActionListener(e8 -> {
            moveToCard("Main Menu");
        });

// Fetch all exercise records from the database via ExerciseDAO
        List<String> records = ExerciseDAO.getAllExerciseRecords();

// Prepare the records to be displayed in the JTextArea
        StringBuilder recordsText = new StringBuilder();
        for (String record : records) {
            recordsText.append(record).append("\n");
        }

// Set the formatted records text in the JTextArea
        textArea.setText(recordsText.toString());


// Add the displayRecords1 panel to your card panel
        cardPanel.add(displayRecords1, "DisplayRecords");
        frame.add(cardPanel);

        frame.setVisible(true);







        //This section defines behavior of the TWL record pathway
        TWL1.add(new JLabel("Input the Exercise ID you want to calculate TWL for:"));
        TWL1.setBackground(Color.decode("#749D1B"));
        JTextField TWLExerciseIdInputField = new JTextField(20);
        TWL1.add(TWLExerciseIdInputField);

        JButton returnToHomeButtonFromTWL1 = new JButton("Return To Home Screen");
        TWL1.add(returnToHomeButtonFromTWL1);
        returnToHomeButtonFromTWL1.addActionListener(e9 -> {
            moveToCard("Main Menu");
        });



        JButton toTWL2 = new JButton("Submit");

        TWL1.add(toTWL2);

        toTWL2.addActionListener(e -> {
            String exerciseIdStr = TWLExerciseIdInputField.getText().trim(); // Get the Exercise ID input

            try {
                if (exerciseIdStr.isEmpty() || !isInteger(exerciseIdStr) || !ExerciseDAO.doesExerciseExist(Integer.parseInt(exerciseIdStr))) {
                    JOptionPane.showMessageDialog(TWL1, "Please enter a valid id.");
                } else {
                    int exerciseId = Integer.parseInt(exerciseIdStr); // Parse the exercise ID
                    // Call the method to get the exercise data and calculate total weight lifted
                    double TWLValue = ExerciseDAO.getExerciseDataAndCalculateTWL(exerciseId);
                    TWL2.setBackground(Color.decode("#749D1B"));


                    JLabel TWLOutputLabel = new JLabel("TWL for ExerciseID-" + exerciseId + " is: " + TWLValue + "lbs");
                    TWL2.add(TWLOutputLabel);
                    moveToCard("TWL 2");
                    JButton returnToHomeButtonFromTWL2 = new JButton("Return To Home Screen");
                    TWL2.add(returnToHomeButtonFromTWL2);
                    returnToHomeButtonFromTWL2.addActionListener(e7 -> {
                        moveToCard("Main Menu");
                        TWL2.remove(returnToHomeButtonFromTWL2);
                        TWL2.remove(TWLOutputLabel);

                    });
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(TWL1, "No Exercise was found with that ID.");
            }
        });






































        cardPanel.add(mainMenuPanel, "Main Menu");

        cardPanel.add(startUp1, "Start 1");

        cardPanel.add(logExercise1, "Log 1");
        cardPanel.add(logExercise2, "Log 2");
        cardPanel.add(logExercise3, "Log 3");
        cardPanel.add(logExercise4, "Log 4");
        cardPanel.add(logExercise5, "Log 5");

        cardPanel.add(readFromFile1, "Read 1");
        cardPanel.add(readFromFile2, "Read 2");
        cardPanel.add(readFromFile3, "Read 3");
        cardPanel.add(readFromFile4, "Read 4");

        cardPanel.add(updateFile1, "Update 1");
        cardPanel.add(updateFile2, "Update 2");
        cardPanel.add(updateFile3, "Update 3");
        cardPanel.add(updateFile4, "Update 4");
        cardPanel.add(updateFile5, "Update 5");
        cardPanel.add(updateFile6, "Update 6");


        cardPanel.add(deleteRecord1, "Delete 1");
        cardPanel.add(deleteRecord2, "Delete 2");
        cardPanel.add(deleteRecord3, "Delete 3");
        cardPanel.add(deleteRecord4, "Delete 4");

        cardPanel.add(displayRecords1, "Display 1");
        cardPanel.add(displayRecords2, "Display 2");
        cardPanel.add(displayRecords3, "Display 3");
        cardPanel.add(displayRecords4, "Display 4");

        cardPanel.add(TWL1, "TWL 1");
        cardPanel.add(TWL2, "TWL 2");
        cardPanel.add(TWL3, "TWL 3");
        cardPanel.add(TWL4, "TWL 4");

        cardPanel.add(startUp1, "Start 1");
        cardPanel.add(mainMenuPanel, "Main Menu");
        frame.add(cardPanel);
        frame.setVisible(true);

// Start on startup screen
        mainMenuCardLayout.show(cardPanel, "Start 1");

        frame.add(cardPanel);
        frame.setVisible(true);
    }

    private boolean isInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isDouble(String input) {
        try {
            Double.parseDouble(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void saveToFile(Day day) {
        try {
            File file = new File("workout_log.txt");
            BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
            writer.write(day.toString());
            writer.newLine();
            writer.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error saving data to file.");
        }
    }

    /**
     * This Class represents the deprecated storage unit for exercises. Using Day no longer makes
     * sense because it only stores the EXERCISE not the day's group of exercises.
     */
    public static class Day {
        private String exercise;
        private double weight;
        private int reps;
        private int sets;
        private int RPE;

        public Day(String exercise, double weight, int reps, int sets, int RPE) {
            this.exercise = exercise;
            this.weight = weight;
            this.reps = reps;
            this.sets = sets;
            this.RPE = RPE;
        }

        @Override
        public String toString() {
            return "Exercise: " + exercise + ", Weight: " + weight + ", Reps: " + reps + ", Sets: " + sets + ", RPE: " + RPE;
        }
    }
}

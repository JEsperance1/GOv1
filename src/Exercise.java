

/**
 * This class replaces the previously deprecated day class. It stores them the same way, but is easier to access
 * and is named more appropriately. Exercises are then added to the record by making each successfully created instance
 * a member of an ArrayList with the datatype of exercise.
 * Exercise instances are complete sets of work, they MUST include weight, reps, sets, exercise name, and RPE
 **/
public class Exercise {
    private String name;
    private double weight;
    private int reps;
    private int sets;
    private double rpe;


    /**
     * Constructs an {@code Exercise} object with the specified details.
     * Initializes the exercise name, weight, reps, sets, and RPE value.
     *
     * @param name the name of the exercise (e.g., "Deadlift")
     * @param weight the weight used for the exercise
     * @param reps the number of repetitions per set
     * @param sets the number of sets performed
     * @param rpe the Rate of Perceived Exertion (e.g., 7.5 for moderate effort)
     */
    public Exercise(String name, double weight, int reps, int sets, double rpe) {
        this.name = name;
        this.weight = weight;
        this.reps = reps;
        this.sets = sets;
        this.rpe = rpe;
    }

    @Override
    public String toString() {
        return "Exercise: " + name + ", Weight: " + weight + "kg, Reps: " + reps + ", Sets: " + sets + ", RPE: " + rpe;
    }
}
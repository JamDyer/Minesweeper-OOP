package org.example;
import java.util.Hashtable;
import java.util.Scanner;

public class Player {

    static Scanner reader = new Scanner(System.in); // Create a scanner object for user input
    int numMoves = 0;

    public static double[] choice() {

        boolean success = false;
        int row = 0;
        int col;
        Board board = null;

        do {
            System.out.println("Which column would you like to pick?: "); // Prompt user for the number of surfaces
            try {
                row = reader.nextInt(); // Read user input
            } catch (Exception e) {
                System.out.println("Invalid input for number of " + row + "s. Please enter a valid input.");
                return new double[]{0, 0}; // Exit method if input is invalid
            }


            System.out.println("Which column would you like to pick?: "); // Prompt user for the number of surfaces
            col = 0;
            try {
                col = reader.nextInt(); // Read user input
            } catch (Exception e) {
                System.out.println("Invalid input for number of " + col + "s. Please enter a valid integer.");
                return new double[]{0, 0}; // Exit method if input is invalid
            }

            if (row < 0 || row >= board.numRows || col < 0 || col >= board.numCols) {
                System.out.println("Invalid input. Try again.");
                continue;
            }
            success = true;
        } while (!success);

        // need to make sure that the row is transferred to a number using a dictionary
        return new double[] {row, col}; // Return the total dimension cost

    }
}

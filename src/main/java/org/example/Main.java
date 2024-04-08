package org.example;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);

        Game game = new Game(); // Create a new game instance

        boolean play = true;
        while (play) {

            System.out.println("\n Would you like to play again? : ");
            String input = reader.next().toLowerCase(); // Read user input for replay option

            if (input.equals("yes") || input.equals("y")) {
                Game newGame = new Game(); // Create a new game instance if user wants to play again
            } else {
                System.out.println("Thank you for playing! Come back soon.");
                play = false; // Exit the loop if user does not want to play again
            }
        }
        reader.close(); // Close the scanner to release resources
    }
}

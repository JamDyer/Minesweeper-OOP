package org.example;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);

        Game game = new Game();

        boolean play = true;
        while (play) {

            System.out.println("\n Would you like to play again? : ");
            String input = reader.next().toLowerCase();

            if (input.equals("yes") || input.equals("y")) {
                Game newGame = new Game();
            } else {
                System.out.println("Thank you for playing! Come back soon.");
                play = false;
            }
        }
        reader.close();
    }
}


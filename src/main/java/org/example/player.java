package org.example;
import java.util.Hashtable;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Player {

    static Scanner reader = new Scanner(System.in); // Create a scanner object for user input
    int numMoves = 0;

    Board board;

    int row;
    int col;
    int flag;
    String move;

    public int[] choice() {

        boolean success = false;

        do {
            System.out.println("Which location would you like to pick?: ");
            move = reader.nextLine().trim().toUpperCase(); // Read user input, trim and make upper case.

            if (move.isEmpty()) {
                System.out.println("Empty input. Please enter your move.");
            } else if (!isValid(move)) {
                System.out.println("Invalid input format. Please enter your coordinates followed by an optional 'f' for flagging.");
                System.out.println("Example: A3 or B7f");
            } else {
                try {
                    row = fromBase36(move.charAt(1));
                    col = fromBase36(move.charAt(0));
                    flag = move.length() == 3 ? 1 : 0;

                    if (row < 0 || row >= board.numRows || col < 0 || col >= board.numCols) {
                        System.out.println("Coordinates out of bounds. Please try again.");
                        continue;
                    }
                    success = true;
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter valid integers for rows and columns.");
                    reader.nextLine(); // Clear the scanner's buffer
                }
            }
        } while (!success);

        // need to make sure that the row is transferred to a number using a dictionary
        return new int[] {row - 1, col - 1, flag}; // Return the total dimension cost

    }

    public static boolean isValid(String move){
        if (move.length() < 2 || move.length() > 3){
            return false;
        } else if (!isBase36(move.charAt(0)) || !isBase36(move.charAt(1))) {
            return false;
        }
        return move.length() == 2 || move.charAt(2) == 'F';
    }

    public static boolean isBase36(char test){
        return (test >= '0' && test <= '9') || (test >= 'A' && test <= 'Z');
    }

    public static int fromBase36(char num){
        return num <= '9' ? (num - '0') : (num - 'A') + 10;
    }

    public static char toBase36(int num){
        return (char)(num < 10 ? ('0' + num) : ('A' + (num - 10)));
    }
}

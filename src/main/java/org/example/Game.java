package org.example;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Game {

    boolean gameWon = false;
    boolean gameLost = false;

    Board board; // reference the game board
    Player player;

    public Game() { // Runs the actual game
        board = new Board(1, 1);
        Difficulty(); // Gets the dimensions of the board
        StartGame();
    }


    private void StartGame() {

        Scanner reader = new Scanner(System.in); // Create a scanner object for user input
        System.out.println("\n\n ======================================NEW GAME====================================== \n");

        Player player = new Player();
        player.board = board;
        boolean FirstMove = true;
        board.initialise(); // Builds the board so that it can be visualised for the user
        int flags = board.numMines;

        while (!gameWon && !gameLost) { // Runs loop so user can make continuous moves

            DisplayBoard(); // Shows the board to the user

            System.out.printf("\nNumber of turns: %d, Number of flags: %d\n", player.numMoves, flags);
            System.out.println(" ");


            int[] choice;
            if (FirstMove) { // If it is the first move the players first move is chosen and then the
                // mines are placed around this. The values of each square are then also created

                System.out.println("Please enter your coordinates followed by an optional 'f' for flagging.");
                System.out.println("Example: A3 or B7f \n");

                choice = player.choice();
                board.MinePlacer(choice[0], choice[1]);
                board.neighbouringMines();
                FirstMove = false;
            } else {
                choice = player.choice();
            }
            player.numMoves++;

            int row = choice[0];
            int col = choice[1];
            int flag = choice[2];

            Tile Selected = board.grid[row][col];
            Selected.reveal();

            if (flag == 1) {
                Selected.flagToggle(); // Toggle flag on selected tile
                if (Selected.isFlagged){
                    flags--;
                } else {
                    flags++;
                }
            } else {

                if (Selected.isFlagged){
                    System.out.println("You cannot choose a space you've already flagged!!!");
                    continue;
                }

                if (Selected.isMine) {
                    gameLost = true;
                    DisplayBoard(); // Display all tiles
                    displayGameOverScreen();
                    System.out.println("\n You have hit a mine and so have lost"); // Tell the user if they lost
                } else {
                    if (Selected.neighbouringMines == 0) {
                        RevealNeighbours(row, col);
                    }
                }
            }
            if (IsGameWon()) { // Tell the user if they won
                gameWon = true;
                WinnerArt();
                System.out.println("YOU HAVE CLEARED ALL THE TILES! \n WELL DONE");
            }
        }

    }

    private void Difficulty() { // Choose the difficulty aka size and amount of mines
        Scanner reader = new Scanner(System.in);
        boolean success = false;
        while (!success) {
            System.out.println("\n Which difficulty would you like to choose: \n" +
                    "1. Beginner (8 x 8 with 10 mines) \n" +
                    "2. Intermediate (16 x 16 with 40 mines) \n" +
                    "3. Expert (30 x 16 with 99 mines) \n" +
                    "4. Custom");

            String choice = reader.nextLine().toLowerCase();
            switch (choice) {
                case "beginner":
                case "1":
                    board.numRows = 8;
                    board.numCols = 8;
                    board.numMines = 10;
                    success = true;
                    break;
                case "intermediate":
                case "2":
                    board.numRows = 16;
                    board.numCols = 16;
                    board.numMines = 40;
                    success = true;
                    break;
                case "expert":
                case "3":
                    board.numRows = 30;
                    board.numCols = 16;
                    board.numMines = 99;
                    success = true;
                    break;
                case "custom":
                case "4":
                    try {
                        System.out.println("Enter number of rows: ");
                        board.numRows = reader.nextInt();
                        System.out.println("Enter number of columns: ");
                        board.numCols = reader.nextInt();
                        boolean mining = false;
                        while (!mining){
                            System.out.println("Enter number of mines: ");
                            board.numMines = reader.nextInt();
                            int maxMines = board.numCols * board.numCols - 2;
                            if (board.numMines > maxMines){
                                System.out.println("Too many mines! That's suicide, please enter less mines");
                            } else {
                                mining = true;
                            }
                        }
                        if ( board.numRows < 1 || board.numCols < 1 || board.numMines < 1 ){
                            System.out.println("Invalid input. Please enter valid positive integers");
                            reader.nextLine();
                        } else if ( board.numRows > 36 || board.numCols < 36 ){
                            System.out.println("Your grid is too big! Don't get ahead of yourself, make it smaller.");
                            continue;
                        } else{
                            System.out.println("Thank you for you board selections");
                            success = true;
                        }
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input. Please enter valid integers for rows, columns, and " +
                                "mines if this is your choice.");
                        reader.nextLine();
                    }
                    break;
                default:
                    System.out.println("Invalid choice. Please choose again.");
                    break;
            }
        }
    }


    private void DisplayBoard() { //Displays the current state of the board

        Tile[][] grid = board.getGrid();

        System.out.print("   | ");
        for (int i = 1; i <= board.numCols; i++) {
            System.out.printf("%c ", Player.toBase36(i));
        }

        System.out.print("\n --+");
        for (int i = 1; i <= board.numCols; i++) {
            System.out.print("--");
        }

        for (int i = 0; i < board.numRows; i++) {

            System.out.printf("\n %c | ", Player.toBase36(i + 1));

            for (int j = 0; j < board.numCols; j++) {

                Tile tile = grid[i][j];

                if (tile.isFlagged) {
                    System.out.print("F ");
                    continue;
                }

                if (tile.isRevealed) {
                    if (tile.isMine) {
                        System.out.print("* ");
                    } else {
                        System.out.printf("%d ", tile.neighbouringMines);
                    }
                } else {
                    System.out.print("? ");
                }
            }
        }
        System.out.println(" ");
    }

    private boolean IsGameWon() { // Checks if the game is won
        for (int i = 0; i < board.numRows; i++) {
            for (int j = 0; j < board.numCols; j++) {

                Tile pick = board.grid[i][j];
                if (!pick.isRevealed && !pick.isMine) {
                    return false;
                }

            }
        }
        return true;
    }

    private void RevealNeighbours(int row, int col) { // Puts the value of on each tile of No. neighbouring bombs

        Tile[][] grid = board.getGrid();

        for (int k = -1; k <= 1; k++) {
            for (int l = -1; l <= 1; ++l) {
                int newRow = row + k;
                int newCol = col + l;
                if (newRow >= 0 && newRow < board.numRows && newCol >= 0 && newCol < board.numCols) {
                    Tile CurrentTile = grid[newRow][newCol];
                    if (CurrentTile.neighbouringMines == 0 && !CurrentTile.isRevealed) {
                        CurrentTile.reveal();
                        RevealNeighbours(newRow, newCol);
                    } else {
                        CurrentTile.reveal();
                    }
                }
            }
        }
    }

    private void displayGameOverScreen() {
        System.out.println("      _ ._  _ , _ ._");
        System.out.println("    (_ ' ( `  )_  .__)");
        System.out.println("  ( (  (    )   `)  ) _)");
        System.out.println(" (__ (_   (_ . _) _) ,__)");
        System.out.println("      `~~`\\ ' . /`~~`");
        System.out.println("           ;   ;");
        System.out.println("           /   \\");
        System.out.println("___________|___|_________");
        System.out.println("  _____          __  __ ______    ______      ________ _____  ");
        System.out.println(" / ____|   /\\   |  \\/  |  ____|  / __ \\ \\    / /  ____|  __ \\ ");
        System.out.println("| |  __   /  \\  | \\  / | |__    | |  | \\ \\  / /| |__  | |__) |");
        System.out.println("| | |_ | / /\\ \\ | |\\/| |  __|   | |  | |\\ \\/ / |  __| |  _  / ");
        System.out.println("| |__| |/ ____ \\| |  | | |____  | |__| | \\  /  | |____| | \\ \\ ");
        System.out.println(" \\_____/_/    \\_\\_|  |_|______|  \\____/   \\/   |______|_|  \\_\\");
        System.out.println("                                                                ");

    }

    public static void WinnerArt() {
        // ASCII art for "Winner" message
        String winnerArt =
                "__        _____ _   _ _   _ _____ ____  _ \n" +
                        "\\ \\      / /_ _| \\ | | \\ | | ____|  _ \\| |\n" +
                        " \\ \\ /\\ / / | ||  \\| |  \\| |  _| | |_) | |\n" +
                        "  \\ V  V /  | || |\\  | |\\  | |___|  _ <|_|\n" +
                        "   \\_/\\_/  |___|_| \\_|_| \\_|_____|_| \\_(_)";

        System.out.println(winnerArt);
    }
}


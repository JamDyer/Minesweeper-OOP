package org.example;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Game {

    boolean gameWon = false;
    boolean gameLost = false;

    Board board;

    public Game() {
        board = new Board(1, 1);
        Difficulty();
        StartGame();
    }


    private void StartGame() {

        Scanner reader = new Scanner(System.in); // Create a scanner object for user input
        System.out.println("\n\n ======================================NEW GAME====================================== \n");

        Player player = new Player();
        player.board = board;
        boolean FirstMove = true;
        board.initialise();

        while (!gameWon && !gameLost) {

            DisplayBoard();

            int[] choice;
            if (FirstMove) {

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
                Selected.flagToggle();
            } else {

                if (Selected.isMine) {
                    gameLost = true;
                    DisplayBoard();
                    System.out.println("\n\n BOOOOOOOOOOOM!!! \n GAME OVER YOU HIT A MINE! \n\n");
                } else {
                    if (Selected.neighbouringMines == 0) {
                        RevealNeighbours(row, col);
                    }
                }
            }
            if (IsGameWon()) {
                gameWon = true;
                System.out.println("You've only gone and done it \n Bish bash bosh \n" +
                        "Game Won Congratulations");
            }
        }
    }

    private void Difficulty() {
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
                            reader.nextLine(); // Clear the scanner's buffer
                        } else{
                            System.out.println("Thank you for you board selections");
                            success = true;
                        }
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input. Please enter valid integers for rows, columns, and " +
                                "mines if this is your choice.");
                        reader.nextLine(); // Clear the scanner's buffer
                    }
                    break;
                default:
                    System.out.println("Invalid choice. Please choose again.");
                    break;
            }
        }
    }


    private void DisplayBoard() {

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

    private boolean IsGameWon() {
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

    private void RevealNeighbours(int row, int col) {

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
}


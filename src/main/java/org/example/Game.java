package org.example;
import java.util.Scanner;

public class Game {

    boolean gameWon = false;
    boolean gameLost = false;

    Board board;

    public Game(){
        board = new Board();
        StartGame();
    }


    private void StartGame(){
        System.out.println("start of StartGame");
        Scanner reader = new Scanner(System.in); // Create a scanner object for user input
        System.out.println("\n\n ======================================NEW GAME====================================== \n");

        while(!gameWon && !gameLost){

            DisplayBoard();

            double[] Choice = Player.choice();
            Player player = null;
            player.numMoves++;

            int row = (int) Choice[0];
            int col = (int) Choice[1];

            Tile Selected = board.grid[row][col];

            if (Selected.isMine){
                gameLost = true;
                System.out.println("\n\n BOOOOOOOOOOOM!!! \n\n GAME OVER YOU HIT A MINE!");
            } else {
                Selected.reveal();

                if (IsGameWon()){
                    gameWon = true;
                    System.out.println("You've only gone and done it \n Bish bash bosh \n" +
                            "Game Won Congratulations");
                }
            }
        }
    }


    private void DisplayBoard(){

        Tile[][] grid = board.getGrid();

        System.out.println("  | ");
        for (int i = 1; i < board.numCols; i++) {
            System.out.printf("%d, ", i);
        }

        System.out.print("\n --+");
        for (int i = 1; i < board.numCols; i++) {
            System.out.print("--");
        }

        for (int i = 0; i < board.numRows; i++) {

            System.out.printf("\n %d, | ", i);

            for (int j = 0; j < board.numCols; j++) {

                Tile tile = grid[i][j];

                if (tile.isRevealed){
                    if (tile.isMine){
                        System.out.print("* ");
                    }
                    else{
                        System.out.printf("%d ", tile.neighbouringMines);
                    }
                }
                else{
                    System.out.print("?");
                }

            }
        }
        System.out.println(" ");


        }

    private boolean IsGameWon(){
        for (int i = 0; i < board.numRows; i++){
            for (int j = 0; j < board.numCols; j++){

                Tile pick = board.grid[i][j];
                if (!pick.isRevealed && !pick.isMine){
                    return false;
                }

            }
        }
        return true;
    }

}


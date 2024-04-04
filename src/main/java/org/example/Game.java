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
        Scanner reader = new Scanner(System.in); // Create a scanner object for user input
        System.out.println("\n\n ======================================NEW GAME====================================== \n");

        while(!gameWon && !gameLost){

            DisplayBoard();

            double[] Choice = player.choice();
            player player = null;
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


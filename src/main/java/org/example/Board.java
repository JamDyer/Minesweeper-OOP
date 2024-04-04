package org.example;
import java.util.Random;

public class Board {

    int numRows = 6;
    int numCols = 6;
    int numMines = 6;

    Tile[][] grid;


    public Board(){
        initialise();
        MinePlacer();
        neighbouringMines();
    }


    private void initialise(){
        grid = new Tile[numRows][numCols];

        for (int i = 0; i <= numRows; i++){
            for (int j = 0; j <= numCols; ++j){
                grid[i][j] = new Tile();
            }
        }
    }
    private void MinePlacer(){
        int Placed = 0;

        while (Placed < numMines){
            Random r = new Random();
            int row = r.nextInt(numRows);
            int col = r.nextInt(numCols);
            if (grid[row][col].isMine() == false){
                grid[row][col].isMine = true;
            }
        }
    }

    private void neighbouringMines(){
        for (int i = 0; i <= numRows; i++) {
            for (int j = 0; j <= numCols; ++j) {

                int BOMBS = 0;

                for (int k = -1; k <= 1; k++) {
                    for (int l = -1; l <= 1; ++l) {

                        if (grid[i+k][j+l].isMine = true){
                            BOMBS++;
                        }

                    }

                }

                grid[i][j].neighbouringMines = BOMBS;

            }
        }
    }

    public Tile[][] getGrid() {
        return grid;
    }
}

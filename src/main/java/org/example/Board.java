package org.example;
import java.util.Random;

public class Board {
    int numRows = 16;
    int numCols = 16;
    int numMines = 40;

    Tile[][] grid;


    public Board(int FirstMoveRow, int FirstMoveCol){
        //initialise();
        //MinePlacer(FirstMoveRow, FirstMoveCol);
        //neighbouringMines();
    }

    public void initialise(){ // Builds the grid using the row and column sizes
        grid = new Tile[numRows][numCols];

        for (int i = 0; i < numRows; i++){
            for (int j = 0; j < numCols; ++j){
                grid[i][j] = new Tile();
            }
        }
    }
    public void MinePlacer(int initialRow, int initialCol){ // Populates grid with mines
        int Placed = 0;

        while (Placed < numMines){
            Random r = new Random();
            int row = r.nextInt(numRows);
            int col = r.nextInt(numCols);

            if (row != initialRow || col != initialCol) {
                if (!grid[row][col].isMine()) {
                    grid[row][col].isMine = true;
                    Placed++;
                }
            }
        }
    }

    public void neighbouringMines(){ // Calculates surrounding mines
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; ++j) {

                int BOMBS = 0;

                for (int k = -1; k <= 1; k++) {
                    for (int l = -1; l <= 1; ++l) {

                        int newRow = i + k;
                        int newCol = j + l;
                        if (newRow >= 0 && newRow < numRows && newCol >= 0 && newCol < numCols) {
                            if (grid[i+k][j+l].isMine) {
                                BOMBS++;
                            }
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

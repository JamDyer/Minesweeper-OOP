package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestBoard {

    private Board board;

    @Test
    public void testInitialization() {
        Board board = new Board(1, 1);
        board.initialise();
        Assertions.assertNotNull(board.getGrid(), "Board: getGrid should not return null");
        Assertions.assertEquals(board.getGrid().length, board.numRows, "Board: numRows should match grid length");
        Assertions.assertEquals(board.getGrid()[0].length, board.numCols, "Board: numCols should match grid width");
    }

    @Test
    public void testNeighboringMines() {
        Board board = new Board(3, 3);
        board.initialise();
        board.MinePlacer(1, 1);
        board.neighbouringMines();
        Assertions.assertEquals(board.getGrid()[0][0].neighbouringMines, 1, "Board: Neighboring mines should be calculated correctly");
    }

    @Test
    public void testGetGrid() {
        Board board = new Board(1, 1);
        board.initialise();
        Tile[][] grid = board.getGrid();
        Assertions.assertNotNull(grid, "Board: getGrid should not return null");
        Assertions.assertEquals(grid.length, board.numRows, "Board: numRows should match grid length");
        Assertions.assertEquals(grid[0].length, board.numCols, "Board: numCols should match grid width");
    }
}
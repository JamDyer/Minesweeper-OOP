package org.example;

public class Tile { // Represents object for each individual tile
    boolean isMine = false; // Attribute to indicate whether the tile contains a mine
    boolean isRevealed = false; // Attribute to indicate whether the tile is revealed
    boolean isFlagged = false; // Attribute to indicate whether the tile is flagged
    int neighbouringMines = 0; // Attribute for number of neighboring tiles containing mines

    public void reveal(){ // Reveals tile
        isRevealed = true;
    }

    public void flagToggle(){ // Toggles flag on tile
        isFlagged = !isFlagged;
    }

    public boolean isMine() { // Checks if the tile has a mine on it
        return isMine;
    }


}

package org.example;

public class Tile {
    boolean isMine = false;
    boolean isRevealed = false;
    boolean isFlagged = false;
    int neighbouringMines = 0;
    private boolean mine;

    public void reveal(){
        isRevealed = true;
    }

    public void flagToggle(){
        isFlagged = !isFlagged;
    }


    public boolean isMine() {
        return isMine;
    }

    public void setMine(boolean mine) {
        this.mine = mine;
    }
}

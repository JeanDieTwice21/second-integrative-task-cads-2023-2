package com.labirynth.model;

public class Cell {
    private int row;
    private int col;
    private CellType type;

    public Cell(int row, int col, CellType type) {
        this.row = row;
        this.col = col;
        this.type = type;
    }


    public int getRow(){

        return row;
    }

    public int getCol(){

        return col;
    }

    public void setRow(int row){

        this.row = row;
    }

    public void setCol(int col){

        this.col = col;
    }

    public CellType getType(){

        return type;
    }

    public void setType(CellType type){

        this.type =type;
    }
}

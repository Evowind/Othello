package cell;

import java.io.Serializable;

/**
 * GridCell class:
 * Singular grid cells will be stored alongside methods that will edit the content of those cells.
 */
public class Cell implements Serializable {
    /**
     * The current state held in a cell
     */
    private CellState state;

    /**
     * Col axis value
     */
    public int x;
    
    /**
     * Row axis value
     */
    public int y;

    /**
     * Print in the console the symbol that represent the cell state.
     */
    public void draw() {
        switch (state) {
            case BLACK -> System.out.print(" B ");
            case WHITE -> System.out.print(" W ");
            case EMPTY -> System.out.print(" - ");
            case PLAYABLE -> System.out.print(" x ");
        }
    }

    /**
     * Print in the console the emoji that represent the cell state.
     */
    public void drawEmoji() {
        switch (state) {
            case BLACK -> System.out.print("⚫ ");
            case WHITE -> System.out.print("⚪ ");
            case EMPTY -> System.out.print("\uD83D\uDFE9 ");
            case PLAYABLE -> System.out.print("❌ ");
        }
    }

    /**
     * GridCell constructor:
     * default state empty
     *
     * @param x = horizontal axis (left 0... right n)
     * @param y = vertical axis (top 0... bottom n) maybe change that order and display from bottom to top (through reverse recursion)
     */
    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
        this.state = CellState.EMPTY;
    }

    /**
     * Deep copy constructor for cell.Cell class
     * @param copy the object to copy
     */
    public Cell(Cell copy) {
        state = copy.getState();
        x = copy.getX();
        y = copy.getY();
    }

    /**
     * Getter for cell state:
     *
     * @return returns the state of a cell (0 = empty, 1 = black, 2 = white)
     */
    public CellState getState() {return state;}

    /**
     * Getter for cell X coordinate
     * @return returns the X coordinate of the cell
     */
    public int getX() {return x;}

    /**
     * Getter for cell Y coordinate
     * @return returns the Y coordinate of the cell
     */
    public int getY() {return y;}

    /**
     * Modifies the state of a cell:
     * @param state 0 = empty, 1 = black, 2 = white
     */
    public void setState(CellState state) {this.state = state;}
}
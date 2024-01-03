package cell;

/**
 * cell.CellState class:
 * Where all the possible state of a cell are stored.
 */
public enum CellState {
    /**
     * The state of a cell when the black player has claimed it
     */
    BLACK,
    /**
     * The state of a cell when the white player has claimed it
     */
    WHITE,
    /**
     * The state of a cell when no player has claimed it
     */
    EMPTY,
    /**
     * The state of a cell that the current player can claim
     */
    PLAYABLE
}

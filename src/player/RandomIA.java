package player;

import cell.Cell;
import othello.Grid;

import java.util.Collections;
import java.util.Random;

public class RandomIA {
    /**
     * Reference to the game grid to choose moves.
     */
    private final Grid board;


    /**
     * Sets up the AI ready to play random moves.
     *
     * @param board Reference to the game grid to choose moves.
     */
    public RandomIA(Grid board) {
        this.board = board;
    }

    /**
     * Chooses a move at random for all the valid moves.
     *
     * @return The position selected by the AI to play.
     */
    public int getRandomPos() {
        Random random = new Random();
        return random.nextInt(board.validMoves.size());
    }

    public Cell chooseMove2() {
        return board.validMoves.get(getRandomPos());
    }
}

package player;

import othello.GameState;
import othello.Grid;

/**
 * Heuristic class:
 * Evaluation of a given board.
 */
public class Heuristic {
    /**
     * Determination of the player that we calculate the score.
     */
    private final GameState turn;

    public Heuristic(Grid board) {
        this.turn = board.getTurn();
    }

    /**
     * Call the other evaluating function.
     *
     * @param board that we want to evaluate.
     * @return the final evaluation of the given board.
     */
    public int eval(Grid board) {
        int mob = evalMobility(board);
        int sc = evalCellDiff(board);
        return 2 * mob + sc + 1000 * evalCorner(board);
    }

    /**
     * Calculate the number of move each player can do (the more, the better).
     *
     * @param board that we want to evaluate.
     * @return evaluation of the number of move the current player against the opposing player.
     */
    public int evalMobility(Grid board) {
        int myMoveCount = board.getValidMoves().size();
        int opMoveCount = board.getValidMovesOpp().size();

        return 100 * (myMoveCount - opMoveCount) / (myMoveCount + opMoveCount + 1);//+1 avoid dividing by zero.
    }

    /**
     * Calculate the number of cell each player own (the more, the better).
     *
     * @param board that we want to evaluate.
     * @return evaluation of the number of cell the current player own against the opposing player.
     */
    public int evalCellDiff(Grid board) {
        int mySC;
        int opSC;
        if (turn == GameState.BLACK_TURN) {
            mySC = board.getNumBlack();
            opSC = board.getNumWhite();
        } else {
            mySC = board.getNumWhite();
            opSC = board.getNumBlack();
        }
        return 100 * (mySC - opSC) / (mySC + opSC);
    }

    /**
     * Calculate the number of important cell each player own.
     * The important cell are the corner one (since they are unflippable) and the one in the centre (they give you control of the game).
     *
     * @param board that we want to evaluate.
     * @return evaluation of the important cell each player own.
     */
    public int evalCorner(Grid board) {
        int s = Grid.size - 1;
        int myCorners = 0;
        int opCorners = 0;

        if (board.getGridList().get(0).get(0).getState() == board.playerColour(turn)) myCorners++;
        if (board.getGridList().get(s).get(0).getState() == board.playerColour(turn)) myCorners++;
        if (board.getGridList().get(0).get(s).getState() == board.playerColour(turn)) myCorners++;
        if (board.getGridList().get(s).get(s).getState() == board.playerColour(turn)) myCorners++;

        if (board.getGridList().get(0).get(0).getState() == board.opponentColour(turn)) opCorners++;
        if (board.getGridList().get(s).get(0).getState() == board.opponentColour(turn)) opCorners++;
        if (board.getGridList().get(0).get(s).getState() == board.opponentColour(turn)) opCorners++;
        if (board.getGridList().get(s).get(s).getState() == board.opponentColour(turn)) opCorners++;

        return 100 * (myCorners - opCorners) / (myCorners + opCorners + 1);

    }
}

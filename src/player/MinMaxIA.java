package player;

import cell.Cell;
import othello.GameState;
import othello.Grid;

import java.util.ArrayList;


public class MinMaxIA {

    private final int maxDepth;

    private final Heuristic heuristic;


    /**
     * initialisation of the heuristic with a default value.
     *
     * @param board Board that we want to make a move on.
     */
    public MinMaxIA(Grid board, int maxDepth) {
        this.heuristic = new Heuristic(board);
        this.maxDepth = maxDepth;
    }

    public Cell minMax(Grid origin) {
        /* Deep copy of the move list, so we don't erase this list by accident. */
        ArrayList<Cell> moves = origin.validMovesCopy(origin.getValidMoves());
        /* Default max value. */
        int max = Integer.MIN_VALUE;
        /* Default bestMove. */
        Cell bestMove = null;
        /* Iteration through all the list of move that the player can do. */
        for (Cell move : moves) {
            //int maxDepth = 5;
            /* Apply the move in a deep copy of the board. */
            origin = min(origin.applyMoveCloning(move, origin, origin.turn), maxDepth);
            /* Display the value of each possible move. */
            System.out.println("The move: " + (move.x + 1) + "x, " + (move.y + 1) + "y has a score of :" + heuristic.eval(origin));
            /* Do a comparison to determine the best move. */
            if (heuristic.eval(origin) > max) {
                max = heuristic.eval(origin);
                bestMove = move;
            }
        }
        return bestMove;
    }

    private Grid min(Grid analyse, int depth) {
        GameState colour;
        Grid bestState = null;
        /* Update the list of move the player can do. */
        analyse.updateValidMoves();
        /* Invert the player type for the max call. */
        if (analyse.turn == GameState.BLACK_TURN) {
            colour = GameState.WHITE_TURN;
        } else colour = GameState.BLACK_TURN;
        /* Deep copy of the move list, so we don't erase this list by accident. */
        ArrayList<Cell> moves = analyse.validMovesCopy(analyse.getValidMovesOpp());

        /* End the min-max analysis when we reach the depth or when no move are doable for the opponent.  */
        if (moves.size() == 0 || depth == 0) {
            return analyse;
        }

        /* Default val for comparison */
        int val = Integer.MAX_VALUE;
        Grid tempState;
        /* Iteration through all the list of move that the player can do. */
        for (Cell move : moves) {
            /* Apply the move in a deep copy of the board. */
            tempState = max(analyse.applyMoveCloning(move, analyse, colour), depth - 1);
            /* Do a comparison to determine the best move. */
            if (heuristic.eval(tempState) < val) {
                val = heuristic.eval(tempState);
                bestState = analyse;
            }
        }
        return bestState;
    }

    private Grid max(Grid analyse, int depth) {
        Grid bestState = null;
        /* Update the list of move the player can do. */
        analyse.updateValidMoves();
        /* Deep copy of the move list, so we don't erase this list by accident. */
        ArrayList<Cell> moves = analyse.validMovesCopy(analyse.getValidMoves());

        /* End the min-max analysis when we reach the depth or when no move are doable for the opponent.  */
        if (moves.size() == 0 || depth == 0) {
            return analyse;
        }

        /* Default val for comparison */
        int val = Integer.MIN_VALUE;

        Grid tempState;
        /* Iteration through all the list of move that the player can do. */
        for (Cell move : moves) {
            /* Apply the move in a deep copy of the board. */
            tempState = min(analyse.applyMoveCloning(move, analyse, analyse.turn), depth - 1);
            /* Do a comparison to determine the best move. */
            if (heuristic.eval(tempState) > val) {
                val = heuristic.eval(tempState);
                bestState = tempState;
            }
        }
        return bestState;
    }
}



package othello;

/**
 * Class othello.GameState:
 * Check the turn and the winner.
 */
public enum GameState {
    /**
     * Black player to play
     */
    BLACK_TURN,
    /**
     * White player to play
     */
    WHITE_TURN,
    /**
     * Game is over, draw
     */
    DRAW,
    /**
     * Game is over, black player won
     */
    BLACK_WIN,
    /**
     * Game is over, white player won
     */
    WHITE_WIN
}

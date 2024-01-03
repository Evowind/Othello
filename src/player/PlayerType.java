package player;

/**
 * player.PlayerType class:
 * Where all the possible type of player are stored (Human or AI).
 */
public enum PlayerType {
    HUMAN, AI_RANDOM, AI_MINMAX, AI_ALPHA;

    /**
     * Convert an int into a type of player.
     *
     * @param x the int used.
     * @return the player type.
     */
    public static PlayerType getPlayerType(int x) {
        return switch (x) {
            case 1 -> HUMAN;
            case 2 -> AI_RANDOM;
            case 3 -> AI_MINMAX;
            case 4 -> AI_ALPHA;
            default ->//Should never happen
                    null;
        };
    }
}



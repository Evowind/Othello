package othello;


import player.PlayerType;

import java.io.*;
import java.util.Scanner;

/**
 * othello.Game class:
 * Initialisation of everything related to the game.
 */
public class Game implements Serializable {
    /**
     * The game board.
     */
    private final Grid grid;

    /**
     * The type of the black player (AI or Human).
     */
    private PlayerType blackPlayer;

    /**
     * Black player depth (only used if black player is an AI)
     */
    private int blackDepth;

    /**
     * The type of the  white player (AI or Human).
     */
    private PlayerType whitePlayer;

    /**
     * White player depth (only used if white player is an AI)
     */
    private int whiteDepth;

    /**
     * Current state of the game.
     */
    public GameState state;

    /**
     * Parameter that dictates if the game will ask player's moves with coordinates or keyboard (z; q; s; d; e)
     * false = keyboard; true = coordinates
     */
    public boolean input;

    /**
     * Parameter that dictates if the game will display emojis or letters
     * false = letters; true = emojis
     */
    public boolean emoji;

    /**
     * Scanner used for multiple methods in othello.Game class
     */
    private static final Scanner in = new Scanner(System.in);

    /**
     * Select the type of both players between 4 available options
     */
    public void setPlayerType() {
        System.out.println("1: Human; 2: AI random; 3: AI min-max; 4: AI alpha");
        int p1 = -1;
        System.out.println("Select black player: ");
        while (p1 < 1 || p1 > 4) {
            p1 = in.nextInt();
            if (p1 > 0 && p1 < 5) blackPlayer = PlayerType.getPlayerType(p1);
            else System.out.println("Incorrect value");
            if (p1 == 3 || p1 == 4) blackDepth = setDepth();
        }
        int p2 = -1;
        System.out.println("Select white player: ");
        while (p2 < 1 || p2 > 4) {
            p2 = in.nextInt();
            if (p2 > 0 && p2 < 5) whitePlayer = PlayerType.getPlayerType(p2);
            else System.out.println("Incorrect value");
            if (p2 == 3 || p2 == 4) whiteDepth = setDepth();
        }
    }

    /**
     * Selects display type between letters and emojis
     * @return true (emojis) if 1; false (letters) if 0
     */
    public boolean setDisplaySettings() {
        System.out.println("Select display type: 0 = letters; 1 = emojis");
        int display = -1;
        while (display != 0 && display != 1) {
            display = in.nextInt();
        }
        return display == 1;
    }

    /**
     * Selects input settings between keyboard and coordinates
     * @return true (coordinates) if 1; false (keyboard) if 0
     */
    public boolean setInputSettings() {
        System.out.println("Select input method: 0 = keyboard 1 = coordinates");
        int input = -1;
        while (input != 0 && input != 1) {
            input = in.nextInt();
        }
        return input == 1;
    }

    /**
     * Select depth of AI
     * @return returns integer containing depth of AI
     */
    public int setDepth() {
        int depth = -1;
        System.out.println("Select depth of IA (must be between 1 and 5)");
        while (depth < 1 || depth > 5) {
            depth = in.nextInt();
            if (depth < 1 || depth > 5) System.out.println(" Incorrect value");
        }
        return depth;
    }

    /**
     * Select size of board
     * Must be an even integer due to the first four cells at the center.
     */
    public int setSize() {
        boolean valid = false;
        System.out.print("Size of the board: ");
        int size = 8;
        while (!valid) {
            size = in.nextInt();
            if (size >= 6 && size % 2 == 0) {
                valid = true;
            } else {
                System.out.println("Size must be an even integer and >= 6");
            }
        }
        return size;
    }

    /**
     * Method to get opponent's turn
     * @param player current player
     * @return returns opponent's turn
     */
    public GameState opponentTurn(GameState player) {
        if (player == GameState.WHITE_TURN) return GameState.BLACK_TURN;
        else return GameState.WHITE_TURN;
    }

    /**
     * Method to change turns:
     * If opponent can play: change turn
     * If opponent can't play: don't change turn
     * If you and opponent can't play: end game
     */
    public void changeTurn() {
        // if opponent cant play
        if (grid.validMovesOpp.size() != 0) state = opponentTurn(state);
            // if you can play
        else if (grid.validMoves.size() != 0) System.out.println("\n\tOpponent has no playable move, your turn again");
            // if you cant play
        else {
            System.out.print("\n\tGame is over, final score:");
            state = winner();
        }
        grid.turn = state;
        switch (state) {
            case BLACK_TURN -> grid.currentPlayer = blackPlayer;
            case WHITE_TURN -> grid.currentPlayer = whitePlayer;
        }
    }

    /**
     * Returns GameState enum containing player who won the game
     * @return BLACK_WIN if black won, WHITE_WIN if white won, DRAW if draw
     */
    public GameState winner() {
        if (grid.getNumBlack() > grid.getNumWhite()) return GameState.BLACK_WIN;
        if (grid.getNumBlack() < grid.getNumWhite()) return GameState.WHITE_WIN;
        else return GameState.DRAW;
    }

    /**
     * Prints winner of the current game
     */
    public void printWinner() {
        if(emoji) grid.draw_emoji();
        else grid.draw();
        switch (state) {
            case DRAW -> System.out.println("Draw");
            case BLACK_WIN -> System.out.println("Black wins");
            case WHITE_WIN -> System.out.println("White wins");
        }
    }

    /**
     * Start of application to initiate the game:
     * Maybe change so that it can load game with deserializer
     */
    /*
    public static void main1(String[] args) throws Exception {
        int restart = 1;
        while (restart == 1) {
            playGame();

            System.out.println("0 to quit; 1 to restart");
            restart = in.nextInt();

            if (restart == 1) for(int i = 0; i<20; i++) System.out.println();
        }
    }*/

    public static void main(String[] args) throws Exception {
        int select = -1;
        while (select != 0) {
            System.out.println("0 to quit; 1 to play; 2 to load game");
            select = in.nextInt();
            while (select < 0 || select > 2) {
                System.out.println("Wrong input, please pick 0, 1 or 2.");
                select = in.nextInt();
            }

            if (select == 1) {
                for(int i = 0; i<20; i++) System.out.println();
                playGame();
            }

            if (select == 2) {
                for(int i = 0; i<20; i++) System.out.println();
                loadGame();
            }
        }
    }

    /**
     * Starts a new game
     * Maybe add something to be able to save game with serializer
     */
    public static void playGame() throws Exception {
        Game game = new Game();
        while (game.state == GameState.WHITE_TURN || game.state == GameState.BLACK_TURN) {
            game.grid.updateValidMoves();

            if(game.emoji) game.grid.draw_emoji();
            else game.grid.draw();

            game.grid.selectMove();
            game.grid.updateValidMoves();
            game.grid.updateCount();
            game.changeTurn();
            saveGame(game);
        }
        game.printWinner();
    }

    /**
     * Serializer to save game into a file
     * @param game current game that we want to save
     */
    public static void saveGame(Game game) throws Exception {
        FileOutputStream fileOut = new FileOutputStream("last_save.ser");
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(game);
        out.close();
        fileOut.close();
    }

    /**
     * Deserializer to load game file and play it
     */
    public static void loadGame() throws Exception {
        Game game;
        FileInputStream fileIn = new FileInputStream("last_save.ser"); // not sure if correct relative file path
        ObjectInputStream in = new ObjectInputStream(fileIn);
        game = (Game) in.readObject();
        in.close();
        fileIn.close();

        while (game.state == GameState.WHITE_TURN || game.state == GameState.BLACK_TURN) {
            game.grid.updateValidMoves();

            if(game.emoji) game.grid.draw_emoji();
            else game.grid.draw();

            game.grid.selectMove();
            game.grid.updateValidMoves();
            game.grid.updateCount();
            game.changeTurn();
            //saveGame(game);
        }
        game.printWinner();
    }

    /**
     * Constructor for othello.Game class:
     * Create grid of desired size
     * Ask player types
     * Set first turn to black
     */
    public Game() {
        this.grid = new Grid(setSize());
        setPlayerType();
        if( blackPlayer == PlayerType.HUMAN || whitePlayer == PlayerType.HUMAN ) this.input = setInputSettings();
        this.emoji = setDisplaySettings();
        grid.emoji = this.emoji;
        grid.input = this.input;
        grid.blackDepth = blackDepth;
        grid.whiteDepth = whiteDepth;
        this.state = GameState.BLACK_TURN;
        this.grid.currentPlayer = blackPlayer;
    }
}
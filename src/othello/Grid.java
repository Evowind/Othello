package othello;


import player.MinMaxAlphaBeta;
import player.MinMaxIA;
import player.PlayerType;
import player.RandomIA;
import cell.Cell;
import cell.CellState;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * othello.Grid class:
 * This is where the grid will be stored alongside all the methods that will modify or access all the grid's cells.
 */
public class Grid implements Serializable {
    /**
     * Size of board
     */
    public static int size;

    /**
     * 2D array containing the cells that make up the board
     */
    private final ArrayList<ArrayList<Cell>> board;

    /**
     * Total number of black cells on the board
     */
    private int numBlack;

    /**
     * Total number of white cells on the board
     */
    private int numWhite;

    /**
     * List containing all of current player's valid moves
     */
    public ArrayList<Cell> validMoves;

    /**
     * List containing all of opponent player's valid moves
     */
    public ArrayList<Cell> validMovesOpp;

    /**
     * Current state of the game (indicates which player needs to play)
     */
    public GameState turn;

    /**
     * Current player type (indicates how the move is selected)
     */
    public PlayerType currentPlayer;

    /**
     * Black player depth (only used if black player is an AI)
     */
    public int blackDepth;

    /**
     * White player depth (only used if white player is an AI)
     */
    public int whiteDepth;

    /**
     * Indicates the cell where the cursor is currently placed
     */
    public Cell cursor;

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
     * Basic scanner input for the game.
     */
    private static final Scanner in = new Scanner(System.in);


    /**
     * Constructor for class othello.Grid
     * maybe also place cells inside the arraylist here
     *
     * @param size size of the grid (number of cases on both x-axis and y-axis)
     */
    public Grid(int size) {
        Grid.size = size;
        this.turn = GameState.BLACK_TURN;
        this.validMoves = new ArrayList<>();
        this.validMovesOpp = new ArrayList<>();
        this.cursor = new Cell(0, 0);
        this.input = true;
        this.emoji = true;

        board = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            board.add(new ArrayList<>(size));
            for (int j = 0; j < size; j++) {
                board.get(i).add(new Cell(i, j));
            }
        }
        board.get((size / 2) - 1).get((size / 2) - 1).setState(CellState.WHITE);
        board.get((size / 2)).get((size / 2)).setState(CellState.WHITE);
        board.get((size / 2) - 1).get((size / 2)).setState(CellState.BLACK);
        board.get((size / 2)).get((size / 2) - 1).setState(CellState.BLACK);
    }

    /**
     * Deep copy constructor for class othello.Grid
     *
     * @param copy the object to copy
     */
    public Grid(Grid copy) {
        size = copy.getSize();
        board = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            board.add(new ArrayList<>(size));
            for (int j = 0; j < size; j++) {
                board.get(i).add(new Cell(copy.board.get(i).get(j)));
            }
        }

        cursor = copy.getCursor();
        numBlack = copy.getNumBlack();
        numWhite = copy.getNumWhite();
        validMoves = copy.getValidMoves();
        validMovesOpp = copy.getValidMovesOpp();
        turn = copy.getTurn();
        currentPlayer = copy.getCurrentPlayer();
        blackDepth = copy.getBlackDepth();
        whiteDepth = copy.getWhiteDepth();
        input = copy.getInput();
        emoji = copy.getEmoji();
    }

    /**
     * Select which method to run by looking at current player type
     */
    public void selectMove() {
        switch (currentPlayer) {
            case HUMAN -> {
                if (input) selectMove1();
                else selectMove1_keyboard();
            }
            case AI_RANDOM -> selectMove2();
            case AI_MINMAX -> selectMove3();
            case AI_ALPHA -> selectMove4();
        }
    }

    /**
     * Coordinate based select move for human player
     */
    public void selectMove1() {
        boolean isPlayable = false;
        int x = 0;
        int y = 0;
        while (!isPlayable) {
            boolean valid = false;
            while (!valid) {
                System.out.println("Select x coordinate: ");
                x = in.nextInt();
                if (x >= 1 && x <= size) valid = true;
                else System.out.println("x must be an integer between 1 and "+size);
            }
            valid = false;
            while (!valid) {
                System.out.println("Select y coordinate: ");
                y = in.nextInt();
                if (y >= 1 && y <= size) valid = true;
                else System.out.println("y must be an integer between 1 and "+size);
            }
            if (board.get(y - 1).get(x - 1).getState() == CellState.PLAYABLE) isPlayable = true;
            else System.out.println("Move is not correct, please pick again: ");
        }
        makeMove(turn, y - 1, x - 1);
    }

    /**
     * Keyboard input based select move for human player
     */
    public void selectMove1_keyboard() {
        char direction;

        boolean isPlayed = false;
        while (!isPlayed) {
            direction = in.next().charAt(0);
            switch (direction) {
                case 'z':
                    if (cursor.y > 0) cursor.y = cursor.y - 1;
                    break;
                case 's':
                    if (cursor.y < size - 1) cursor.y = cursor.y + 1;
                    break;
                case 'q':
                    if (cursor.x > 0) cursor.x = cursor.x - 1;
                    break;
                case 'd':
                    if (cursor.x < size - 1) cursor.x = cursor.x + 1;
                    break;
                case 'e':
                    if (board.get(cursor.x).get(cursor.y).getState() == CellState.PLAYABLE) {
                        makeMove(turn, cursor.getY(), cursor.getX());
                        isPlayed = true;
                    }
            }
            if (emoji) draw_emoji();
            else draw();
        }
    }

    // keyboard input using KeyListener in case we decide to implement GUI (don't forget class needs to implement KeyListener)
    /*
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch(e.getKeyCode()) {
            case 90: if(cursor.y > 0) cursor.y = cursor.y - 1;
                break;
            case 83: if(cursor.y < size-1) cursor.y = cursor.y + 1;
                break;
            case 81: if(cursor.x > 0) cursor.x = cursor.x - 1;
                break;
            case 68: if(cursor.x < size-1) cursor.x = cursor.x +1;
                break;
            case 10: if(cursor.getState() == CellState.PLAYABLE) {
                    makeMove(turn, cursor.getY(), cursor.getX());

                }
                break;
            default:
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }*/

    /**
     * Select move for Random AI
     */
    public void selectMove2() {
        RandomIA AI = new RandomIA(this);
        Cell selected = AI.chooseMove2();
        makeMove(turn, selected.x, selected.y);
    }

    /**
     * Select move for Min-Max AI
     */

    public void selectMove3() {
        if (turn == GameState.BLACK_TURN) {
            MinMaxIA AI = new MinMaxIA(this, blackDepth);
            Cell selected = AI.minMax(this);
            makeMove(turn, selected.x, selected.y);
        } else {
            MinMaxIA AI = new MinMaxIA(this, whiteDepth);
            Cell selected = AI.minMax(this);
            makeMove(turn, selected.x, selected.y);
        }
    }


    /**
     * Select move for Alpha-Beta AI
     */
    public void selectMove4() {
        if (turn == GameState.BLACK_TURN) {
            MinMaxAlphaBeta AI = new MinMaxAlphaBeta(this, blackDepth);
            Cell selected = AI.minMaxAB(this);
            makeMove(turn, selected.x, selected.y);
        } else {
            MinMaxAlphaBeta AI = new MinMaxAlphaBeta(this, whiteDepth);
            Cell selected = AI.minMaxAB(this);
            makeMove(turn, selected.x, selected.y);
        }
    }


    /**
     * Resets the cells marked as playable back to empty
     *
     * @param grid board
     * @param size size of board
     */
    public void resetPlayable(ArrayList<ArrayList<Cell>> grid, int size) {
        validMoves.clear();
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                if (grid.get(i).get(j).getState() == CellState.PLAYABLE) grid.get(i).get(j).setState(CellState.EMPTY);
    }

    /**
     * Tests all the cells to see which ones are playable for current player
     * Playable moves will be stored in validMoves
     *
     * @param turn current player
     */
    public void testPlayable(GameState turn) {
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                if (board.get(i).get(j).getState() == CellState.EMPTY) for (int k = 0; k < 8; k++)
                    if (wouldFlip(turn, i, j, k)) {
                        board.get(i).get(j).setState(CellState.PLAYABLE);
                        if (!validMoves.contains(board.get(i).get(j)))
                            validMoves.add(board.get(i).get(j));
                    }
    }

    /**
     * Tests all the moves to see which ones are playable for current player's opponent
     * Playable moves will be stored in validMovesOpp
     *
     * @param turn current player
     */
    public void testPlayableOpp(GameState turn) {
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                if (board.get(i).get(j).getState() == CellState.EMPTY) for (int k = 0; k < 8; k++)
                    if (wouldFlip(turn, i, j, k))
                        if (!validMovesOpp.contains(board.get(i).get(j)))
                            validMovesOpp.add(board.get(i).get(j));
    }

    public ArrayList<Cell> validMovesCopy(ArrayList<Cell> validMoves) {
        ArrayList<Cell> newList = new ArrayList<>();
        for (Cell validMove : validMoves) {
            Cell newNode = new Cell(validMove);
            newList.add(newNode);
        }
        return newList;
    }


    /**
     * Used to update the list of valid moves of both current player and opponent
     */
    public void updateValidMoves() {
        resetPlayable(board, size);
        testPlayable(turn);

        validMovesOpp.clear();
        switch (turn) {
            case WHITE_TURN -> testPlayableOpp(GameState.BLACK_TURN);
            case BLACK_TURN -> testPlayableOpp(GameState.WHITE_TURN);
        }
    }

    /**
     * Update numBlack and numWhite variables
     */
    public void updateCount() {
        numBlack = countCell(CellState.BLACK);
        numWhite = countCell(CellState.WHITE);
    }

    /**
     * Print string that indicates which player has to play
     */
    public void printTurn() {
        switch (turn) {
            case BLACK_TURN -> System.out.println("Black to play:");
            case WHITE_TURN -> System.out.println("White to play:");
        }
    }

    /**
     * Updates the count of black and white cells and prints it
     * Then prints which player has to play and the board
     */
    public void draw() {
        updateCount();
        System.out.println("\nBlack: " + numBlack + " - White: " + numWhite);
        printTurn();

        System.out.print("    ");
        for (int c = 0; c < size; c++)
            System.out.print((c + 1) + "  ");
        System.out.println();

        for (int y = 0; y < size; y++) {
            if (y < 9) System.out.print(y + 1 + "  ");
            else System.out.print(y + 1 + " ");
            for (int x = 0; x < size; x++) {
                if (cursor.getX() == x && cursor.getY() == y && !input && (currentPlayer == PlayerType.HUMAN)) {
                    if (board.get(y).get(x).getState() == CellState.PLAYABLE)
                        System.out.print(" G "); // prints ok emoji (good)
                    else System.out.print(" N "); // prints ng emoji (no good)
                } else if (x < 9) board.get(y).get(x).draw();
                else {
                    System.out.print(" ");
                    board.get(y).get(x).draw();
                }
            }
            System.out.println();
        }
    }

    /**
     * Updates the count of black and white cells and prints it
     * Then prints which player has to play and the board
     */
    public void draw_emoji() {
        updateCount();
        System.out.println("\nBlack: " + numBlack + " - White: " + numWhite);
        printTurn();

        System.out.print("    ");
        for (int c = 0; c < size; c++)
            System.out.print((c + 1) + "  ");
        System.out.println();

        for (int y = 0; y < size; y++) {
            if (y < 9) System.out.print(y + 1 + "  ");
            else System.out.print(y + 1 + " ");
            for (int x = 0; x < size; x++) {
                if (cursor.getX() == x && cursor.getY() == y && !input && (currentPlayer == PlayerType.HUMAN)) {
                    if (board.get(y).get(x).getState() == CellState.PLAYABLE)
                        System.out.print("\uD83C\uDD97 "); // prints ok emoji (good)
                    else System.out.print("\uD83C\uDD96 "); // prints ng emoji (no good)
                } else if (x < 9) board.get(y).get(x).drawEmoji();
                else {
                    System.out.print(" ");
                    board.get(y).get(x).drawEmoji();
                }
            }
            System.out.println();
        }
    }

    /**
     * Detects if a set of given coordinates is inside the grid
     *
     * @param y first coordinate
     * @param x second coordinate
     * @return true if inside, false if outside
     */
    private boolean inbound(int y, int x) {
        return (y >= 0 && y < size) && (x >= 0 && x < size);
    }

    /**
     * Counts the number of times the specified symbol occurs.
     * Can be used as a score counter
     *
     * @param state A char that specifies the symbol to search for
     * @return An int with the number of occurrences of the symbol
     */
    public int countCell(CellState state) {
        int count = 0;
        for (int x = 0; x < size; x++)
            for (int y = 0; y < size; y++)
                if (board.get(x).get(y).getState() == state) count++;
        return count;
    }

    /**
     * Check whether this move would result in any flips in this direction.
     *
     * @param player the current player who is making this move
     * @param y      the row number of the move square
     * @param x      the column of the move square
     * @param dir    the direction that we are checking
     * @return returns true if it would flip, false if it wouldn't.
     */
    public boolean wouldFlip(GameState player, int y, int x, int dir) {
        int[] dirI = {-1, 1, 0, 0, 1, -1, 1, -1};
        int[] dirJ = {0, 0, 1, -1, 1, -1, -1, 1};
        boolean flag = false;
        for (int i = 0; i <= size; i++) {
            y += dirI[dir];
            x += dirJ[dir];
            if (inbound(y, x)) {
                if (board.get(y).get(x).getState() == opponentColour(player)) flag = true;
                else if (board.get(y).get(x).getState() == playerColour(player)) {
                    return flag;
                } else return false;
            }
        }
        return false;
    }


    /**
     * Make any flips in the given direction (0 to 8)
     *
     * @param player the current player who is making this move
     * @param y      the row number of the move square
     * @param x      the column of the move square
     * @param dir    the direction that we are checking
     */
    public void makeFlip(GameState player, int y, int x, int dir) {
        int[] dirI = {-1, 1, 0, 0, 1, -1, 1, -1};
        int[] dirJ = {0, 0, 1, -1, 1, -1, -1, 1};
        if (wouldFlip(player, y, x, dir)) {
            y += dirI[dir];
            x += dirJ[dir];
            while (board.get(y).get(x).getState() != playerColour(player)) {
                board.get(y).get(x).setState(playerColour(player));
                y += dirI[dir];
                x += dirJ[dir];
            }
        }
    }

    /**
     * Tests if the given coordinates are playable, if yes:
     * Plays the move
     * Looks in all 9 directions for cells that need to be flipped
     */
    public void makeMove(GameState player, int y, int x) {
        if (board.get(y).get(x).getState() == CellState.PLAYABLE) {
            board.get(y).get(x).setState(playerColour(player));
            for (int i = 0; i < 8; i++) {
                makeFlip(player, y, x, i);
            }
        }
    }


    /**
     * Same as above, used for MinMaxIA.java
     */
    public void setMove(GameState player, int y, int x) {
        board.get(y).get(x).setState(playerColour(player));
        for (int i = 0; i < 8; i++) {
            makeFlip(player, y, x, i);
        }
    }


    /**
     * Getter for grid:
     *
     * @return returns the 2D array containing all the cells.
     */
    public ArrayList<ArrayList<Cell>> getGridList() {
        return this.board;
    }

    /**
     * Getter for number of black pieces on the board:
     *
     * @return returns the amount of black pieces on the board.
     */
    public int getNumBlack() {
        return numBlack;
    }

    /**
     * Getter for number of white pieces on the board:
     *
     * @return returns the amount of white pieces on the board.
     */
    public int getNumWhite() {
        return numWhite;
    }

    /**
     * Getter for size of the board:
     *
     * @return returns the board's size.
     */
    public int getSize() {
        return size;
    }

    /**
     * Getter for current turn:
     *
     * @return returns the current turn.
     */
    public GameState getTurn() {
        return turn;
    }

    /**
     * Getter for cursor position:
     *
     * @return returns the cell containing the cursor
     */
    public Cell getCursor() {
        return cursor;
    }

    /**
     * Getter for list of valid current player's valid moves
     *
     * @return returns the list containing the current player's valid moves
     */
    public ArrayList<Cell> getValidMoves() {
        return validMoves;
    }

    /**
     * Getter for list of current player's opponent's valid moves
     *
     * @return returns the list containing the opponent's valid moves
     */
    public ArrayList<Cell> getValidMovesOpp() {
        return validMovesOpp;
    }

    /**
     * Getter for current player type:
     *
     * @return returns the current player type.
     */
    public PlayerType getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Getter for boolean input:
     *
     * @return returns the input boolean.
     */
    public boolean getInput() {
        return input;
    }

    /**
     * Getter for boolean emoji:
     *
     * @return returns the emoji boolean.
     */
    public boolean getEmoji() {
        return emoji;
    }

    /**
     * Getter for white player AI depth:
     *
     * @return returns depth of white AI.
     */
    public int getWhiteDepth() {
        return whiteDepth;
    }

    /**
     * Getter for black player AI depth:
     *
     * @return returns depth of black AI.
     */
    public int getBlackDepth() {
        return blackDepth;
    }

    /**
     * Getter for current player color:
     *
     * @param player current player
     * @return returns current player color
     */
    public CellState playerColour(GameState player) {
        if (player == GameState.WHITE_TURN) return CellState.WHITE;
        else return CellState.BLACK;
    }

    /**
     * Getter for opponent color:
     *
     * @param player current player
     * @return returns current player's opponent's color
     */
    public CellState opponentColour(GameState player) {
        if (player == GameState.WHITE_TURN) return CellState.BLACK;
        else return CellState.WHITE;
    }

    /**
     * Apply the move in a copied board.
     *
     * @param move   the cell we want to play
     * @param temp   the copied board
     * @param colour player color
     * @return a new grid with the move done.
     */
    public Grid applyMoveCloning(Cell move, Grid temp, GameState colour) {
        Grid newState = new Grid(temp);
        newState.setMove(colour, move.getY(), move.getX());

        return newState;
    }

}

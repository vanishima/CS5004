package ChessGame;

import java.util.List;

/**
 * An interface that represents a ChessPiece
 */
public interface ChessPiece {

    /**
     * Return the row index of this piece
     * @return the row index
     */
    Integer getRow();

    /**
     * Return the column index of this piece
     * @return the column index
     */
    Integer getColumn();

    /**
     * Return the color enum type of this piece
     * @return the color enum type
     */
    Color getColor();

    /**
     * Checks whether the two pieces are in the same color
     * @param other the other piece to compare with
     * @return true if same color, false otherwise
     */
    boolean sameColor(ChessPiece other);

    /**
     * Checks whether the piece can move to a particular position
     * @param row the row index
     * @param col the column index
     * @return true if it can move to that position, false otherwise
     */
    boolean canMove(int row, int col);

    /**
     * Checks whether the piece can kill a piece on a particular position
     * @param row the row index
     * @param col the column index
     * @return true if it can kill a piece on that position, false otherwise
     */
    boolean canKill(int row, int col);

    /**
     * Checks whether this piece is alive
     * @return true if alive, false otherwise
     */
    boolean isAlive();

    /**
     * Moves this piece to a particular position
     * @param row the row index
     * @param col the column index
     */
    void move(int row, int col);

    /**
     * Kill this piece
     */
    void killed();

    /**
     * Return a list of pairs of row and column indexes that
     * this piece must visit in order to move to the target position
     * @param row the target row index
     * @param col the target column index
     * @return a list of pairs of row and column indexes between this
     * piece and the target position
     */
    List<List<Integer>> positionInBetween(int row, int col);
}

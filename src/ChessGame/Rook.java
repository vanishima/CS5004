package ChessGame;

/**
 * A class that represents a Rook chess piece, as a subclass of
 * AbstractChessPiece.
 */
public class Rook extends AbstractChessPiece{

    /**
     * Create a Knight object using the constructor defined
     * in the parent class
     * @param row the initial row index position
     * @param col the initial column index position
     * @param color the color enum type
     */
    public Rook(int row, int col, Color color) {
        super(row, col, color);
    }

    /**
     * Checks whether this piece can move to the target position
     * @param row row of the target position
     * @param col column of the target position
     * @return true if it can move to that position, false otherwise
     */
    @Override
    public boolean canMove(int row, int col) {
        return AbstractChessPiece.withinRange(row, col)
                && moveHorizontalOrVertical(row, col);
    }
}

package ChessGame;

/**
 * A class that represents a Pawn chess piece, as a subclass of
 * AbstractChessPiece.
 */
public class Pawn extends AbstractChessPiece{

    /**
     * Create a Pawn object using the constructor defined
     * in the parent class
     * @param row the initial row index position
     * @param col the initial column index position
     * @param color the color enum type
     */
    public Pawn(int row, int col, Color color) {
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
        return moveAhead(row, col);
    }

    /**
     * Return true if the target piece is one place forward diagonally for Pawn
     * @param row row index of the victim
     * @param col col index of the victim
     * @return either this piece can kill the other piece
     */
    @Override
    public boolean canKill(int row, int col){
        int canKillTemp = (this.getColor() == Color.WHITE) ? 1 : - 1;
        return (row - this.getRow() == canKillTemp)
                && Math.abs(col - this.getColumn()) == 1;
    }
}

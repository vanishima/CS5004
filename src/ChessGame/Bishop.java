package ChessGame;

public class Bishop extends AbstractChessPiece{

    public Bishop(int row, int col, Color color) {
        super(row, col, color);
    }

    @Override
    public boolean canMove(int row, int col) {
        return AbstractChessPiece.withinRange(row, col)
                && moveDiagonal(row, col);
    }

    @Override
    public String toString() {
        return String.format("%sBishop", getColor());
    }
}

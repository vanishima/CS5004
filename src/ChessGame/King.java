package ChessGame;

import java.util.List;

public class King extends AbstractChessPiece {

    public King(int row, int col, Color color) {
        super(row, col, color);
    }

    @Override
    public boolean canMove(int row, int col) {
        return AbstractChessPiece.withinRange(row, col) &&
                moveAnyDirection(row, col);
    }

    @Override
    public String toString() {
        return String.format("%sKing", getColor());
    }
}

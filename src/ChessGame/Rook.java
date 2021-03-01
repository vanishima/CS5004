package ChessGame;

import java.util.ArrayList;
import java.util.List;

public class Rook extends AbstractChessPiece{
    public Rook(int row, int col, Color color) {
        super(row, col, color);
    }

    @Override
    public boolean canMove(int row, int col) {
        return AbstractChessPiece.withinRange(row, col)
                && moveHorizontalOrVertical(row, col);
    }

//    @Override
//    public String toString() {
//        return String.format("%sRook", getColor());
//    }
}

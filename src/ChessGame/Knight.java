package ChessGame;

public class Knight extends AbstractChessPiece{

    public Knight(int row, int col, Color color) {
        super(row, col, color);
    }

    @Override
    public boolean canMove(int row, int col) {
        return AbstractChessPiece.withinRange(row, col)
                && moveLPattern(row, col);
    }

//    @Override
//    public String toString() {
//        return String.format("%sKnight", getColor());
//    }
}

package ChessGame;

public class Queen extends AbstractChessPiece{

    public Queen(int row, int col, Color color){
        super(row, col, color);
    }

    @Override
    public boolean canMove(int row, int col) {
//        System.out.println("checking range for queen");
        return withinRange(row, col)
                && (moveHorizontalOrVertical(row, col)
                || moveDiagonal(row, col));
    }

//    @Override
//    public String toString() {
//        return String.format("%sQueen", getColor());
//    }

}

package ChessGame;

public class Pawn extends AbstractChessPiece{
    public Pawn(int row, int col, Color color) {
        super(row, col, color);
    }

    @Override
    public boolean canMove(int row, int col) {
        return AbstractChessPiece.withinRange(row, col)
                && moveAhead(row, col);
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

//    @Override
//    public String toString() {
//        return String.format("%sPawn", getColor());
//    }
}

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
     * @param other the target piece
     * @return either this piece can kill the other piece
     */
    @Override
    public boolean canKill(ChessPiece other){
        int canKillTemp = (this.getColor() == Color.WHITE) ? 1 : - 1;
        return ((other.getRow() - this.getRow()) == canKillTemp)
                && Math.abs(other.getColumn() - this.getColumn()) == 1
                && other.getColor() != this.getColor();
    }

    @Override
    public String toString() {
        return String.format("%sPawn", getColor());
    }
}

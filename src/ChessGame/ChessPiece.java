package ChessGame;

import java.util.List;

public interface ChessPiece {

    Integer getRow();

    Integer getColumn();

    Color getColor();

    boolean canMove(int row, int col);

    boolean canKill(ChessPiece other);

    void move(int row, int col);

    void killed();

    public List<List<Integer>> positionInBetween(int row, int col);
}

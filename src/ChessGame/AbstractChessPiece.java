package ChessGame;

import java.util.ArrayList;
import java.util.List;

/**
 * An abstract class of ChessPiece that has row, column,
 * color, and a boolean called alive
 */
public abstract class AbstractChessPiece implements ChessPiece{
    protected Integer row = 0;
    protected Integer column = 0;
    protected Color color;
    protected boolean alive = true; // default is alive

    /**
     * Constructs an AbstractChessPiece object with given row index,
     * column index, and color type
     * @param row the row index
     * @param col the column index
     * @param color the color enum type
     */
    public AbstractChessPiece(int row, int col, Color color){
        this.row = row;
        this.column = col;
        this.color = color;
    }

    /**
     * Return the row index of this piece
     * @return the row index
     */
    @Override
    public Integer getRow(){
        return this.row;
    }

    /**
     * Return the column index of this piece
     * @return the column index
     */
    @Override
    public Integer getColumn(){
        return this.column;
    }

    /**
     * Return the color enum type of this piece
     * @return the color enum type
     */
    @Override
    public Color getColor(){
        return this.color;
    }

    /**
     * Checks whether the two pieces are in the same color
     * @param other the other piece to compare with
     * @return true if same color, false otherwise
     */
    @Override
    public boolean sameColor(ChessPiece other) {
        return getColor() == other.getColor();
    }

    /**
     * Returns the color initial and the type of this piece in String
     * @return a String that gives the information about color and type
     * of chess
     */
    @Override
    public String toString(){
        return String.format("%s%s", this.getColor().initial(),
                    this.getClass().getSimpleName());
    }

    /**
     * Kill this piece by setting alive to false
     */
    public void killed(){
        this.alive = false;
    }

    /**
     * Checks whether this piece is alive
     * @return true if alive, false otherwise
     */
    public boolean isAlive(){
        return this.alive;
    }

    /**
     * Moves this piece to a particular position, no further
     * checks needed
     * @param row the row index
     * @param col the column index
     */
    public void move(int row, int col){
        this.row = row;
        this.column = col;
    }

    /**
     * Checks if the target position is on the same diagonal of
     * current position
     * @param row the target row index
     * @param col the target column index
     * @return true if the target position is on the same diagonal,
     * false otherwise
     */
    public boolean moveDiagonal(int row, int col){
        return Math.abs(this.getRow() - row) == Math.abs(this.getColumn() - col);
    }

    /**
     * Checks if the target position is on the same row or column
     * @param row the target row index
     * @param col the target column index
     * @return true if the target position is on the same row or column
     * false otherwise
     */
    public boolean moveHorizontalOrVertical(int row, int col){
        return this.getRow() == row || this.getColumn() == col;
    }

    /**
     * Checks if the target position is two rows and one column apart
     * or one row and two columns apart
     * @param row the target row index
     * @param col the target column index
     * @return true if an L pattern links the current position and
     * the target position
     */
    public boolean moveLPattern(int row, int col){
        int verDistance = Math.abs(this.getRow() - row);
        int LongDistance = Math.abs(this.getColumn() - col);
        return (verDistance == 2 && LongDistance == 1)
                || (verDistance == 1 && LongDistance == 2);
    }

    /**
     * A method that checks whether the given position is ahead
     * of the current position of this piece
     * @param row the row index
     * @param col the column index
     * @return true if the given position is one unit ahead, false otherwise
     */
    public boolean moveAhead(int row, int col){
        // 'ahead' means differently for each team
        int moveAheadTemp = (this.getColor() == Color.WHITE) ? 1 : - 1;
        return (row - this.getRow() == moveAheadTemp)
                && (col == this.getColumn());
    }

    /**
     * A method that checks whether the given position is one square away from
     * the current position of this piece in any direction
     * @param row the row index
     * @param col the column index
     * @return true if the given position is one square away in any direction,
     * false otherwise
     */
    public boolean moveAnyDirection(int row, int col) {
        int rowDiff = Math.abs(this.getRow() - row);
        int columnDiff = Math.abs(this.getColumn() - col);
        return (rowDiff <= 1 && columnDiff <= 1);
    }

    /**
     * An abstract method which will be defined in each subclass
     * It determines if a particular piece can move to the target
     * position
     * @param row row of the target position
     * @param col column of the target position
     * @return either the piece can move to that position or not
     */
    public abstract boolean canMove(int row, int col);

    /**
     * Return a list of positions (row, column) that the chess needs to pass
     * if it wants to kill the other piece
     * @param row the row index of target piece
     * @param col the column index of target piece
     * @return the list of positions this piece needs to pass
     */
    public List<List<Integer>> positionInBetween(int row, int col){
        // get the horizontal and vertical increment in order
        // to move from this position to the position given
        int rowIncrement = Integer.compare(row, this.getRow());
        int colIncrement = Integer.compare(col, this.getColumn());

        List<List<Integer>> path = new ArrayList<>();
        while ((Math.abs(row - this.getRow()) > 1
                || Math.abs(col - this.getColumn()) > 1)
                && withinRange(row, col)){

            row += rowIncrement;
            col += colIncrement;

            List<Integer> pieces = new ArrayList<>();
            pieces.add(row);
            pieces.add(col);
            path.add(pieces);
        }
        return path;
    }

    /**
     * A static method that checks whether the given row and column
     * are within the boundaries of a chess board
     * @param row the row index
     * @param col the column index
     * @return true if the row and column indices are valid, false otherwise
     */
    public static boolean withinRange(int row, int col){
        return 0 <= row && row <= 7 && 0 <= col && col <= 7;
    }

    /**
     * Return true if this piece can move to the target position
     * and a piece of different color is currently on that position
     * ALL CHESS PIECES EXCEPT PAWN SHARE THIS METHOD
     * @param row the row index of the victim
     * @param col the col index of the victim
     * @return either this piece can kill the other piece
     */
    public boolean canKill(int row, int col){
        return canMove(row, col);
    }

}

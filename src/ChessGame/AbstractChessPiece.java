package ChessGame;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractChessPiece implements ChessPiece{
    protected Integer row;
    protected Integer column;
    protected Color color;
    protected boolean alive = true; // default value is true

    /**
     * Constructs an AbstractChessPiece object with given row index,
     * column index, and color type
     * @param row the row index
     * @param col the column index
     * @param color the color type
     */
    public AbstractChessPiece(int row, int col, Color color){
        this.row = row;
        this.column = col;
        this.color = color;
    }

    @Override
    public Integer getRow(){
        return this.row;
    }

    @Override
    public Integer getColumn(){
        return this.column;
    };

    @Override
    public Color getColor(){
        return this.color;
    };

    public void killed(){
        this.alive = false;
    }

    public boolean isAlive(){
        return this.alive;
    }

    public void move(int row, int col){
        if (this.canMove(row, col)){
            this.row = row;
            this.column = col;
        } else {
            System.out.println("It cannot move to " + row + ", " + col);
        }
    }


    public boolean moveDiagonal(int row, int col){
//        System.out.println("checking moveDiagonal");
        if (this.getColumn() == col){
            return false;
        }
        return Math.abs((this.getRow() - row) / (this.getColumn() - col)) == 1;
    }

    public static int getIncrement(int a, int b){
        if (a < b) {
            return 1;
        } else if (a == b){
            return 0;
        } else {
            return -1;
        }
    }

    /**
     * Return a list of positions (row, column) that the chess needs to pass
     * if it wants to kill the other piece
     * @param row the row index of target piece
     * @param col the column index of target piece
     * @return the list of positions this piece needs to pass
     */
    public List<List<Integer>> positionInBetween(int row, int col){
        List<List<Integer>> path = new ArrayList<>();
        int rowIncrement = getIncrement(row, this.getRow());
        int colIncrement = getIncrement(col, this.getColumn());
        while (Math.abs(row - this.getRow()) > 1
                && Math.abs(col - this.getColumn()) > 1
                && withinRange(row, col)){
            List<Integer> pieces = new ArrayList<>();
            row += rowIncrement;
            col += colIncrement;
            pieces.add(row);
            pieces.add(col);
            path.add(pieces);
            System.out.println("added " + row + ", " + col);
        }
        return path;
    }

    public boolean moveHorizontalOrVertical(int row, int col){
//        System.out.println("checking moveHorizontalOrVertical");
        return this.getRow() == row || this.getColumn() == col;
    }

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
     * @param row row of the target position
     * @param col column of the target position
     * @return either the piece can move to that position or not
     */
    public abstract boolean canMove(int row, int col);

    /**
     * A static method that checks whether the given row and column
     * are within the boundaries
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
     * @param other the target piece
     * @return either this piece can kill the other piece
     */
    public boolean canKill(ChessPiece other){
        return canMove(other.getRow(), other.getColumn())
                && other.getColor() != this.getColor();
    }

}

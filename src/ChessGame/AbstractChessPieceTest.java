package ChessGame;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * A JUnit test for AbstractChessPiece
 */
public class AbstractChessPieceTest {
    private Bishop bishop;
    private King king;
    private Queen queen;
    private Rook rook;
    private Pawn pawn;
    private Knight knight;

    @Before
    public void setUp(){
        bishop = new Bishop(3, 3, Color.WHITE);
        king = new King(3, 3, Color.WHITE);
        queen = new Queen(3, 3, Color.WHITE);
        rook = new Rook(3, 3, Color.BLACK);
        pawn = new Pawn(3, 3, Color.BLACK);
        knight = new Knight(3, 3, Color.BLACK);
    }

    @Test
    public void getRow() {
        assertEquals(bishop.getRow(), 3, 0.1);
        assertNotEquals(king.getRow(), 0, 0.1);
    }

    @Test
    public void getColumn() {
        assertEquals(queen.getColumn(), 3, 0.1);
        assertNotEquals(pawn.getColumn(), 0, 0.1);
    }

    @Test
    public void getColor() {
        assertEquals(knight.getColor(), pawn.getColor());
        assertNotEquals(knight.getColor(), queen.getColor());
    }

    @Test
    public void sameColor() {
        assertTrue(bishop.sameColor(king));
        assertFalse(bishop.sameColor(rook));
    }

    @Test
    public void testToString() {
        String str1 = "W.Bishop";
        String str2 = "B.Knight";
        assertEquals(bishop.toString(), str1);
        assertEquals(knight.toString(), str2);
        assertNotEquals(knight.toString(), str1);
    }

    /**
     * The test for killed and isAlive method is combined
     */
    @Test
    public void isAlive() {
        assertTrue(king.isAlive());
        king.killed();
        assertFalse(king.isAlive());
    }

    @Test
    public void move() {
        assertNotEquals(4, bishop.getColumn(), 0.1);
        bishop.move(4,4);
        assertEquals(4, bishop.getColumn(), 0.1);
    }

    @Test
    public void canMove() {
        assertTrue(bishop.canMove(4,4)); // can move diagonal
        assertTrue(king.canMove(4,4)); // can move diagonal one step
        assertTrue(king.canMove(3,2)); // can move in straight line one step
        assertTrue(queen.canMove(7,7)); // can move diagonal
        assertTrue(queen.canMove(3,0)); // can move in straight line
        assertTrue(rook.canMove(3,0)); // can move in straight line
        assertTrue(pawn.canMove(2,3)); // can move ahead
        assertTrue(knight.canMove(1,2)); // can move in L pattern

        assertFalse(bishop.canMove(3,4)); // cannot move in straight line
        assertFalse(king.canMove(3,5)); // cannot move more than one unit
        assertFalse(queen.canMove(1,2)); // cannot go L pattern
        assertFalse(rook.canMove(4,4)); // cannot go diagonal
        assertFalse(pawn.canMove(4,3)); // cannot go backwards
        assertFalse(knight.canMove(3,4)); // cannot move in straight line
    }

    @Test
    public void positionsInBetween(){
        List<List<Integer>> straightPath = Arrays.asList(Arrays.asList(3,5), Arrays.asList(3,4));
        assertEquals(straightPath, rook.positionInBetween(3,6));

        List<List<Integer>> diagonalPath = Arrays.asList(Arrays.asList(5,1), Arrays.asList(4,2));
        assertEquals(diagonalPath, bishop.positionInBetween(6,0));
    }


    @Test
    public void withinRange() {
        assertTrue(AbstractChessPiece.withinRange(3,4));
        assertFalse(AbstractChessPiece.withinRange(0,8));
    }

    /**
     * The logic for canKill is the same as can Move for most pieces,
     * so we only test for Pawn.
     * The color condition is checked in ChessGame class.
     */
    @Test
    public void canKill() {
        assertFalse(pawn.canMove(2,2)); // cannot move diagonally
        assertTrue(pawn.canKill(2,2)); // but can kill diagonally
        assertFalse(pawn.canKill(1,1)); // cannot kill more than one step away
    }
}
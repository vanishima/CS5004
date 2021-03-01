package ChessGame;

import java.util.ArrayList;
import java.util.List;

public class ChessGame {
    private Player white;
    private Player black;
    // A 2D Array list to store pieces
    private ChessPiece[][] board = new ChessPiece[8][8];

    /**
     * Initialize two players and fill in the chess board
     * with 32 ChessPiece
     */
    public ChessGame(){
        this.white = new Player(Color.WHITE);
        this.black = new Player(Color.BLACK);
        // Place all chess pieces on the board
        this.initializePieces(Color.WHITE);
        this.initializePieces(Color.BLACK);

    }

    /**
     * Fill in the board list with 16 pieces of the same color
     * and link King object with the Player
     * @param color the color of the player
     */
    public void initializePieces(Color color){
        int firstRow = (color == Color.WHITE) ? 0 : 7;

        board[firstRow][0] = new Rook(firstRow, 0, color); // 车
        board[firstRow][7] = new Rook(firstRow, 7, color);
        board[firstRow][1] = new Knight(firstRow, 1, color); // 马
        board[firstRow][6] = new Knight(firstRow, 6, color);
        board[firstRow][2] = new Bishop(firstRow, 2, color); // 象
        board[firstRow][5] = new Bishop(firstRow, 5, color);
        board[firstRow][4] = new Queen(firstRow, 4, color);

        // store King object also in player's instance variable
        King king = new King(firstRow, 3, color);
        board[firstRow][3] = king;
        if (color == Color.WHITE){
            this.white.setKing(king);
        } else {
            this.black.setKing(king);
        }

        // 8 pawns
        int secondRow = (firstRow == 0) ? 1 : 6;
        for (int i = 0; i < 8; i++){
            board[secondRow][i] = new Pawn(secondRow, i, color);
        }
    }

    public boolean noPiecesInBetween(List<List<Integer>> position){
        if (position.size() == 0 || position.get(0).size() == 0){
            return true;
        }
        for (List<Integer> piece : position){
            System.out.println(piece.get(0) + "," + piece.get(1));
            if (this.board[piece.get(0)][piece.get(1)] != null){
                return false;
            }
        }
        return true;
    }

    public void moveOnBoard(ChessPiece target, int row, int col){
        System.out.println("inside moveOnBoard");
        if (!canMoveOnBoard(target, row, col)){
            // (!target.getClass().equals(Knight.class)) &&
            System.out.println("You cannot move " + target + " to " + row + ", " + col);
            return;
        }
        board[target.getRow()][target.getColumn()] = null;
        board[target.getRow()][target.getColumn()] = null;
        target.move(row, col);
        board[row][col] = target;
    }

    public boolean canMoveOnBoard(ChessPiece target, int row, int col){
        // the target should be able to move in that direction
        if (!target.canMove(row, col)) { return false; }
        // a piece of same team cannot be on that position
        ChessPiece mayPiece = board[row][col];
        if (mayPiece != null && mayPiece.getColor() == target.getColor()){
            return false;
        }
        // if the target is Knight, further check is not needed
        if (target.getClass() == Knight.class){
            return true;
        }
        System.out.println("inside canMoveOnBoard");
        // there should be no pieces in between
        List<List<Integer>> piecesInBetween = target.positionInBetween(row, col);
        return noPiecesInBetween(piecesInBetween);
    }

    /**
     * Check whether the killer piece can kill the victim
     * @param killer the killer piece
     * @param victim the piece to be killed
     * @return true if killer can kill the victim, false otherwise
     */
    public boolean canKill(ChessPiece killer, ChessPiece victim){
        // the knight can move on board as long as it's L pattern
        if (killer.getClass().equals(Knight.class)){
            return killer.canKill(victim);
        }
        // for other pieces, there should be no pieces in between
        return killer.canKill(victim)
                && canMoveOnBoard(killer, victim.getRow(), victim.getColumn());
    }

    /**
     * Moves the killer to victim's position and remove victim from the board
     * @param killer the killer ChessPiece
     * @param victim the ChessPiece being killed
     */
    public void toKill(ChessPiece killer, ChessPiece victim){
        // Simply replaces the victim with killer and
        // clears the killer's position on board
        victim.killed();
        moveOnBoard(killer, victim.getRow(), victim.getColumn());
    }

    /**
     * Return the chess piece on the position given by the user
     * @param row the row index
     * @param col the column index
     * @return the chess piece on that position
     */
    public ChessPiece getPiece(int row, int col){
        if (this.board[row][col] != null){
            return this.board[row][col];
        } else {
            System.out.println("There is no chess piece on " + row + ", " + col);
            return null;
        }
    }

    public void play(){

    }

    /**
     * Returns true if either white or black loses the game,
     * which means their king is killed
     * @return true if either white or black loses
     */
    public boolean hasWinner(){
        return white.lose() || black.lose();
    }

    /**
     * Displays the current board.
     */
    public void printBoard() {
//        System.out.println("\n");
        for (int i = 7; i >= 0; i--) {
            System.out.printf("%d   ", i);
            for (int j = 0; j <= 7; j++) {
                if (board[i][j] == null) {
                    System.out.printf("%-15s ", "[      ]");
                } else {
                    System.out.printf("%-15s ", board[i][j]);
                }
            }
            System.out.println("\n");
        }

        System.out.printf("%-4s", " ");
        for (int i = 0; i <= 7; i++) {
            System.out.printf("%-16d", i);
        }
        System.out.println("\n");
    }

    public static void main(String[] args){
        ChessGame game = new ChessGame();
        game.printBoard();
        ChessPiece whiteQueen = game.getPiece(0,4); // row and col is opposite in board
        ChessPiece blackPawn = game.getPiece(6, 4);


//        while (!game.hasWinner()){
//            game.play();
//        }

        game.moveOnBoard(blackPawn, 5, 4);
        game.moveOnBoard(blackPawn, 4, 4);
        game.moveOnBoard(whiteQueen, 0, 3);

        ChessPiece whiteKnight = game.getPiece(0,1);
        game.moveOnBoard(whiteKnight, 2, 0);
        ChessPiece blackBishop = game.getPiece(7,5);
        game.toKill(blackBishop, whiteKnight);
        game.printBoard();
    }



}

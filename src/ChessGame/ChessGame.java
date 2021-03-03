package ChessGame;

import java.util.List;
import java.util.Scanner;
import java.util.Random;

/**
 * A class that represents a Chess game with two ChessPiece as white king
 * and black king, an integer to indicate the current player, and a 2D array
 * that stores all pieces currently on the board (ie. currently alive)
 */
public class ChessGame {
    private ChessPiece whiteKing;
    private ChessPiece blackKing;
    private Integer currentPlayer; // 1 is white, -1 is black
    // A 2D Array list to store pieces
    private ChessPiece[][] board = new ChessPiece[8][8];

    /**
     * Initialize two players and fill in the chess board
     * with 32 ChessPiece
     */
    public ChessGame(){
        // Place all chess pieces on the board
        this.initializePieces(Color.WHITE);
        this.initializePieces(Color.BLACK);
        // randomly assign a player
        Random rand = new Random(System.nanoTime());
        this.currentPlayer = (rand.nextInt(2) == 0) ? -1:1;
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
            this.whiteKing = king;
        } else {
            this.blackKing = king;
        }

        // 8 pawns
        int secondRow = (firstRow == 0) ? 1 : 6;
        for (int i = 0; i < 8; i++){
            board[secondRow][i] = new Pawn(secondRow, i, color);
        }
    }

    /**
     * Allows the current user to choose a piece and move it
     * to a valid destination. The piece will kill an opponent's piece
     * if that piece is on it's destination.
     */
    public void play(){
        System.out.println("====== It's " + getCurrentPlayerColor() + " player's turn =====");

        ChessPiece target = askForChessPiece();  // get a valid chess piece
        int[] position = askForDestination(target);  // get a valid destination position
        moveOnBoard(target, position[0], position[1]);  // move the chess piece to that position

        System.out.println("\n\n\n\n\n\n");
        printBoard();
        changePlayer();  // change the turn to the opponent
    }

    /**
     * Return the color enum type of the current player
     * @return the color enum type of the current player
     */
    public Color getCurrentPlayerColor(){
        return (this.currentPlayer == -1) ? Color.BLACK : Color.WHITE;
    }

    /**
     * Change current player to another player by multiplying -1
     */
    public void changePlayer(){
        this.currentPlayer *= -1;
    }

    /**
     * Asks the user to choose a valid ChessPiece that is on the board
     * and belongs to the current player
     * @return the chosen ChessPiece
     */
    public ChessPiece askForChessPiece(){
        System.out.println("Choose a piece to move");
        Scanner keyboard = new Scanner(System.in);
        int row = keyboard.nextInt();
        int col = keyboard.nextInt();
        ChessPiece target = getPiece(row, col);

        // Keep asking until the player chooses his/her own piece
        while (target == null || target.getColor() != getCurrentPlayerColor()){
            System.out.println("Please choose a valid " + getCurrentPlayerColor() + " piece (eg. 1, 7)");
            row = keyboard.nextInt();
            col = keyboard.nextInt();
            target = getPiece(row, col);
        }
        System.out.println("You chose a " + target);
        return target;
    }

    /**
     * Return the chess piece on the position given by the user
     * @param row the row index
     * @param col the column index
     * @return the chess piece on that position
     */
    public ChessPiece getPiece(int row, int col){
        if (!AbstractChessPiece.withinRange(row, col)){
            System.out.println("The index is out of range.");
            return null;
        } else if (this.board[row][col] != null){
            return this.board[row][col];
        } else {
            System.out.println("There is no chess piece on " + row + ", " + col);
            return null;
        }
    }

    /**
     * Asks the user to choose a valid pair of row and column destination
     * to move the target piece
     * @param target the piece to be moved
     * @return the pair of row and column destination as a list
     */
    public int[] askForDestination(ChessPiece target){
        System.out.println("Choose a position to move to");
        Scanner keyboard = new Scanner(System.in);
        int row = keyboard.nextInt();
        int col = keyboard.nextInt();

        // Keep asking until the piece can move to destination
        while(!canMoveOnBoard(target, row, col)){
            System.out.println("    - Please enter a valid destination (eg. 2, 7)");
            row = keyboard.nextInt();
            col = keyboard.nextInt();
        }
        return new int[] {row, col};
    }

    /**
     * Checks whether a piece can move to the target position
     * @param target the piece to be moved
     * @param row the target row index
     * @param col the target column index
     * @return true if the piece can move to the target position, false otherwise
     */
    public boolean canMoveOnBoard(ChessPiece target, int row, int col){
        // a different position
        if (target.getRow().equals(row) && target.getColumn().equals(col)){
            System.out.println("You need to move to a different position");
            return false;
        }

        // can move or kill in that direction
        if (!target.canMove(row, col) || !canKill(target, row, col)) {
            System.out.println("This piece cannot move to that position!");
            return false;
        }

        if (target.getClass() == Knight.class){ return true; } // no need to check if the target is Knight
        return noPiecesInBetween(target, row, col);  // no pieces in between on the path
    }

    /**
     * Check whether the killer piece can kill the victim
     * @param killer the killer piece
     * @param row the row index of the victim
     * @param col the col index of the victim
     * @return true if killer can kill the victim, false otherwise
     */
    public boolean canKill(ChessPiece killer, int row, int col){
        ChessPiece victim = getPiece(row, col);
        if (victim == null){ return true; } // if no piece on that position, return true

        if (killer.sameColor(victim)){ // the piece should be of different color
            System.out.println("You cannot kill the same color piece");
            return false;
        }

        return killer.canKill(row, col); // if the piece can kill in that direction
    }

    /**
     * Checks whether there are pieces on the given list of position indexes
     * @param target the piece to be moved
     * @param row the target row index
     * @param col the target column index
     * @return true if there are no pieces on the given positions, false otherwise
     */
    public boolean noPiecesInBetween(ChessPiece target, int row, int col){
        List<List<Integer>> piecesInBetween = target.positionInBetween(row, col);
        if (piecesInBetween.size() == 0 || piecesInBetween.get(0).size() == 0){
            return true;
        }
        for (List<Integer> piece : piecesInBetween){
            if (getPiece(piece.get(0), piece.get(1)) != null){ // if there is a piece on any between position
                System.out.println("Piece on " + piece.get(0) + ", " +
                        piece.get(1) + " is in between!");
                return false;
            }
        }
        return true;
    }

    /**
     * Move the piece and kill the opponent's piece if it exists.
     * @param target the ChessPiece to be moved
     * @param row the target row index
     * @param col the target column index
     */
    public void moveOnBoard(ChessPiece target, int row, int col){
        System.out.println("move " + target + " to " + row + ", " + col);
        kill(target, row, col);
        board[row][col] = target;
        board[target.getRow()][target.getColumn()] = null;
        target.move(row, col);
    }

    /**
     * Change the alive status of the victim to false if there is a piece
     * @param killer the killer ChessPiece
     * @param row the row index of victim
     * @param col the col index of victim
     */
    public void kill(ChessPiece killer, int row, int col){
        ChessPiece victim = getPiece(row, col);
        if (victim != null){
            System.out.println(victim + " is killed");
            victim.killed();
        }
    }

    /**
     * Returns true if either white or black loses the game,
     * which means their king is killed
     * @return true if either white or black loses
     */
    public boolean hasWinner(){
        return whiteKing.isAlive() != blackKing.isAlive();
    }

    /**
     * Displays the current board.
     */
    public void printBoard() {
        System.out.println("========================================\n");
        for (int i = 7; i >= 0; i--) {
            System.out.printf("%d   ", i);
            for (int j = 0; j <= 7; j++) {
                if (board[i][j] == null) {
                    System.out.printf("%-12s", "[      ]");
                } else {
                    System.out.printf("%-12s", board[i][j]);
                }
            }
            System.out.println("\n");
        }

        System.out.printf("%-6s", " ");
        for (int i = 0; i <= 7; i++) {
            System.out.printf("%-12d", i);
        }
        System.out.println("\n");
    }

    public static void main(String[] args){
        ChessGame game = new ChessGame();
        game.printBoard();
        ChessPiece whiteQueen = game.getPiece(0,4); // row and col is opposite in board
        ChessPiece blackPawn = game.getPiece(6, 4);


        while (!game.hasWinner()){
            game.play();
        }

//        game.moveOnBoard(blackPawn, 5, 4);
//        game.moveOnBoard(blackPawn, 4, 4);
//        game.moveOnBoard(whiteQueen, 0, 3);
//
//        ChessPiece whiteKnight = game.getPiece(0,1);
//        game.moveOnBoard(whiteKnight, 2, 0);
//        ChessPiece blackBishop = game.getPiece(7,5);
//        game.toKill(blackBishop, whiteKnight);
        game.printBoard();
    }



}

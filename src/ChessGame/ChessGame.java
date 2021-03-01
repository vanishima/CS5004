package ChessGame;

//import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Random;

public class ChessGame {
    private ChessPiece whiteKing;
    private ChessPiece blackKing;
    private Integer currentPlayer;
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
        Random rand = new Random();
        this.currentPlayer = rand.nextInt(2);
        this.currentPlayer = (this.currentPlayer == 0) ? -1:1;
    }

    public Color getCurrentPlayer(){
        if (this.currentPlayer == -1){
            return Color.BLACK;
        } else if (this.currentPlayer == 1){
            return Color.WHITE;
        } else {
            System.out.println("There is something wrong with currentPlayer");
            return null;
        }
    }

    public void changePlayer(){
        this.currentPlayer *= -1;
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

    public boolean noPiecesInBetween(List<List<Integer>> position){
        if (position.size() == 0 || position.get(0).size() == 0){
            return true;
        }
        for (List<Integer> piece : position){
//            System.out.println(piece.get(0) + "," + piece.get(1));
            if (this.board[piece.get(0)][piece.get(1)] != null){
                System.out.println("Piece on " + piece.get(0) + ", " +
                                    piece.get(1) + " is in between!");
                return false;
            }
        }
        return true;
    }

    public void moveOnBoard(ChessPiece target, int row, int col){
        if (!canMoveOnBoard(target, row, col)){
            System.out.println("You cannot move " + target + " to " + row + ", " + col);
            return;
        }
        System.out.println("move " + target + " to " + row + ", " + col);
        kill(target, row, col);
        board[row][col] = target;
        board[target.getRow()][target.getColumn()] = null;
        target.move(row, col);
    }

    public boolean canMoveOnBoard(ChessPiece target, int row, int col){
        // 1) It has to be a different position than the current one
        if (target.getRow().equals(row) && target.getColumn().equals(col)){
            System.out.println("You need to move to a different position");
            return false;
        }
        // 2) the target should be able to move or kill in that direction
        if (!target.canMove(row, col) || ! canKill(target, row, col)) {
            System.out.println("This piece cannot move to that position!");
            return false;
        }
        // if the target is Knight, we don't need to check if there are
        // pieces in between
        if (target.getClass() == Knight.class){
            return true;
        }
        // 3) for other pieces, there should be no pieces in between
        List<List<Integer>> piecesInBetween = target.positionInBetween(row, col);
        return noPiecesInBetween(piecesInBetween);
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
        // if there is no piece on that position, return true
        if (victim == null){ return true; }
        // the knight can move on board as long as it's L pattern
        if (killer.getColor() == victim.getColor()){
            System.out.println("You cannot kill the same color piece");
            return false;
        }
        // for other pieces, there should be no pieces in between
        return killer.canKill(row, col);
    }

    /**
     * Moves the killer to victim's position and remove victim from the board
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
     * Return the chess piece on the position given by the user
     * @param row the row index
     * @param col the column index
     * @return the chess piece on that position
     */
    public ChessPiece getPiece(int row, int col) throws IllegalArgumentException{
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


    public void play(){
        System.out.println("====== It's " + this.getCurrentPlayer() +
                        " player's turn =====");
        System.out.println("Choose a piece to move");

        Scanner keyboard = new Scanner(System.in);
        int row = keyboard.nextInt();
        int col = keyboard.nextInt();
        ChessPiece target = getPiece(row, col);

        // Keep asking until the player chooses his/her own piece
        while (target == null || target.getColor() != this.getCurrentPlayer()){
//            System.out.println("target: " + target.getColor());
//            System.out.println("current: " + this.getCurrentPlayer());
            System.out.println("Please choose a valid " +
                                this.getCurrentPlayer() + " piece (eg. 1, 7)");
            row = keyboard.nextInt();
            col = keyboard.nextInt();
            target = getPiece(row, col);
        }
        System.out.println("You chose a " + target +
                        ". Choose a position to move to.");

        // Keep asking until the piece can move to destination
        while(!canMoveOnBoard(target, row, col)){
            System.out.println("    - Please enter a valid destination (eg. 2, 7)");
            row = keyboard.nextInt();
            col = keyboard.nextInt();
        }
        moveOnBoard(target, row, col);
        printBoard();
        changePlayer();
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
        System.out.println("========================================");
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

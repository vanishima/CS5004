package ChessGame;

import java.util.List;
import java.util.ArrayList;

public class Player {
    private Color color;
    private King king;

    /**
     * Construct 16 chess pieces based on the color of the player
     * @param color the Color type
     */
    public Player(Color color){
        this.color = color;
    }

    /**
     * Sets the king for this player to the given ChessPiece object.
     * @param piece a ChessPiece object that represents the king for this player
     */
    public void setKing(King piece) {
        this.king = piece;
    }

    /**
     * Check if the player's king is still alive
     * @return true if the king is alive, false otherwise
     */
    public boolean lose(){
        return !this.king.isAlive();
    }
}

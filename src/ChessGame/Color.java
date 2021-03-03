package ChessGame;

/**
 * An enum type that represents the color of pieces and the player
 */
public enum Color {
    BLACK ("B."),
    WHITE ("W.");

    /**
     * The initial to represent the color when printing the board
     */
    private final String initial;

    /**
     * Initialize the initial variable to the given String
     * @param initial the given String to represent initial
     */
    Color(String initial){
        this.initial= initial;
    }

    /**
     * Returns the initial of this color
     * @return the initial of this color as a String
     */
    public String initial(){
        return this.initial;
    }

}

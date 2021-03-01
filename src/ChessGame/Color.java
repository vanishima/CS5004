package ChessGame;

public enum Color {
    BLACK ("B."),
    WHITE ("W.");

    private final String initial;

    Color(String initial){
        this.initial= initial;
    }

    public String initial(){
        return this.initial;
    }

}

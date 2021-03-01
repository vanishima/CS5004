package ChessGame;

public class boardPosition {
    private Integer row;
    private Integer column;

    public boardPosition(Integer row, Integer column){
        this.row = row;
        this.column = column;
    }

    public Integer getRow(){
        return this.row;
    }

    public Integer getColumn(){
        return this.column;
    }

    public boolean withinRange(){
        return 0 <= row && row <= 7 && 0 <= column && column <= 7;
    }
}

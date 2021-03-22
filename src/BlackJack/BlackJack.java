package BlackJack;

public class BlackJack {
    private Deck deck;
    private Player player;
    private Player dealer;
    private Player winner;

    public BlackJack(){
        this.deck = new Deck();
        this.deck.shuffle();
        this.player = new Player(deck,false);
        this.dealer = new Player(deck,true);
    }

    /**
     * Check whether the game is over. There could be three situations.
     * 1) at least a player has blackjack
     * 2) at least a player busts
     * 3) both player stands
     * @return true if game is over, false if game is still going on
     */
    public boolean gameIsOver(){
        // to print out the dealer's hidden card
        if (winner != null){
            return true;
        }
        if (hasBlackJack()){
            System.out.println("\n**" + winner.getName() + " GOT A BLACKJACK!**\n");
            return true;
        } else if (hasBust()){
            System.out.println("\n**" + winner.getName() + " BUST!**\n");
            return true;
        } else {
            return player.isStand() && dealer.isStand();
        }
    }

    /**
     * Returns the winner of the game
     * @return the winner of the game
     */
    public Player getWinner(){
        if (this.winner == null){
            checkPoints();
        }
        return this.winner;
    }

    /**
     * Checks whether at least one player has blackjack
     * @return 1 if at at least one player has blackjack
     */
    public boolean hasBlackJack(){
        if (player.blackjack() && !dealer.blackjack()){
            winner = player;
        } else if (!player.blackjack() && dealer.blackjack()){
            winner = dealer;
        } else {
            return false;
        }
        return true;
    }

    public boolean hasBust(){
        if (!player.bust() && dealer.bust()){
            winner = player;
        } else if (player.bust() && !dealer.bust()){
            winner = dealer;
        } else {
            return false;
        }
        return true;
    }

    public void checkPoints(){
        System.out.println("\nChecking points...");
        System.out.println("Your maximum points: " + player.getMaxValue());
        System.out.println("Dealer's maximum points: " + dealer.getMaxValue());
        if (player.getMaxValue() > dealer.getMaxValue()){
            winner = player;
        } else if (player.getMaxValue() < dealer.getMaxValue()) {
            winner = dealer;
        }
    }

    public void showStatus(){
        System.out.println("Your total value is: " + player.totalValue());
        player.showStatus();
        if (gameIsOver()){
            System.out.println("Dealer's total value is: " + dealer.totalValue());
        }
        dealer.showStatus();
    }

    public void play(Player player){
        if (player.toHit()){
            player.hit(this.deck);
        } else {
            player.stand();
        }
    }

    public static void main(String[] args){
        System.out.println("============ Welcome to Blackjack ===========");
        BlackJack game = new BlackJack();
        game.showStatus();

        while (!game.gameIsOver()){
            System.out.println("\n=============== A new turn ===============");
            if (!game.player.isStand()){
                System.out.println(">> Your turn");
                game.play(game.player);
            }
            if (!game.dealer.isStand()){
                System.out.println("\n>> The dealer's turn");
                game.play(game.dealer);
            }
            System.out.println("\n=============== Current cards ===============");
            game.showStatus();
        }

        System.out.println("\n===============  Game is over! ===============");
//        game.showStatus();
        if (game.getWinner() != null){
            System.out.println("\nThe final winner is " + game.getWinner().getName());
        } else {
            System.out.println("\nThere is no winner in this game! A tie!");
        }
    }
}

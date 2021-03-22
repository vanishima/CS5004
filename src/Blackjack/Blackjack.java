package Blackjack;

import java.util.Scanner;

/**
 * A class that represents a Blackjack game
 */
public class Blackjack {
    private Deck deck;
    private Player player;
    private Player dealer;
    private Player winner;

    /**
     * Create a Blackjack game with a deck of 52 cards and shuffle the deck.
     * Create a player and a dealer and distribute 2 cards each.
     */
    public Blackjack(){
        this.deck = new Deck();
        this.deck.shuffle();
        this.player = new Player(deck,false);
        this.dealer = new Player(deck,true);
    }

    /**
     * Ask the player and the dealer to hit or stand until game is over
     */
    public void gamePlay(){
        while (!gameIsOver()){
            System.out.println("\n=============== Current cards ===============");
            showStatus();
            System.out.println("\n================ A new turn ================");
            playerPlay(player);
            playerPlay(dealer);
        }
    }

    /**
     * If the player did not choose to stand in the last round,
     * ask her to decide to hit or stand in this round
     * @param player the current player
     */
    public void playerPlay(Player player){
        if (player.isStand()){ return; }
        System.out.println(">>" + player.getName() +"'s turn");
        if (player.chooseToHit()){
            player.hit(this.deck);
        } else {
            player.stand();
        }
    }

    /**
     * Check whether the game is over. There could be three situations.
     * 1) at least a player has blackjack
     * 2) at least a player busts
     * 3) both player stands
     * @return true if game is over, false if game is still going on
     */
    public boolean gameIsOver(){
        if (winner != null){
            return true;
        }
        if (!hasBlackJack() && !hasBust()){   // if at least one player has blackjack or bust
            return player.isStand() && dealer.isStand();
        }
        return true;
    }

    /**
     * Checks whether the game has a winner
     * @return true if the game has a winner
     */
    public boolean hasWinner(){
        // only one busts or has blackjack  -> has winner
        if (winner != null){ return true; }
        // both busts or both has blackjack -> no winner
        return !hasBust() && !hasBlackJack() && hasHigherPoints();
    }

    /**
     * Checks whether at least one player has blackjack
     * @return true if at at least one player has blackjack, false if none has blackjack
     */
    public boolean hasBlackJack(){
        boolean playerBlackjack = player.blackjack();
        boolean dealerBlackjack = dealer.blackjack();

        if (playerBlackjack){ System.out.println("\n** Player GOT A BLACKJACK!**\n"); }
        if (dealerBlackjack){ System.out.println("\n** Dealer GOT A BLACKJACK!**\n"); }

        if (playerBlackjack != dealerBlackjack){            // only one has blackjack
            winner = (playerBlackjack) ? player : dealer;
            return true;
        }
        return playerBlackjack;                             // both has blackjack or none has blackjack

    }

    /**
     * Checks whether at least one player bust
     * @return true if at least one player bust, false if none or both bust
     */
    public boolean hasBust(){
        boolean playerBust = player.bust();
        boolean dealerBust = dealer.bust();

        if (playerBust){ System.out.println("\n** Player BUST!**\n"); }
        if (dealerBust){ System.out.println("\n** Dealer BUST!**\n"); }

        if (playerBust != dealerBust){                      // only one bust
            winner = (playerBust) ? dealer : player;
            return true;
        }
        return playerBust;                                  // both bust or no one bust
    }

    /**
     * Assume that no one has blackjack and no one bust, check the maximum points
     * of each player under 21
     */
    public boolean hasHigherPoints(){
        System.out.println("\nChecking points...");
        System.out.println("Your maximum points: " + player.getMaxValue());
        System.out.println("Dealer's maximum points: " + dealer.getMaxValue());
        if (player.getMaxValue() > dealer.getMaxValue()){
            winner = player;
        } else if (player.getMaxValue() < dealer.getMaxValue()) {
            winner = dealer;
        } else {
            return false;
        }
        return true;
    }

    /**
     * Print out the cards and total value of both player and dealer (one card is hidden).
     * Hide the hidden card of the dealer until game is over.
     */
    public void showStatus(){
        System.out.println("Your total points is: " + player.totalValue());
        player.showStatus();
        System.out.println("");
        if (gameIsOver()){
            System.out.println("Dealer's total points is: " + dealer.totalValue());
        }
        dealer.showStatus();
    }

    /**
     * Print out all the cards of both player and show if there is a winner
     * of the game is a tie.
     */
    private void showEnding(){
        this.dealer.showHiddenCard();
        System.out.println("=============== Game is over! ===============");
        showStatus();
        if (hasWinner()){
            System.out.println("\n>>> The final winner is " + winner.getName() + "! <<<");
        } else {
            System.out.println("\nThere is no winner in this game! A tie!");
        }
    }

    /**
     * Asks the player if to start a new game
     * @return true if to start a new game, false if the player wants to end game
     */
    public boolean endGame(){
        Scanner keyboard = new Scanner(System.in);
        System.out.println("\n.\n.\n.\n\nDo you want to play again? y/n");
        String ans = keyboard.nextLine();
        return ans.equals("n");
    }

    /**
     * A demo method to test methods in the Blackjack class
     * @param args
     */
    public static void main(String[] args){
        while (true){
            System.out.println("============ Welcome to Blackjack ===========");
            Blackjack game = new Blackjack();

            game.gamePlay();
            game.showEnding();

            if (game.endGame()){ break; }
        }
        System.out.println("Thanks for playing Blackjack.");
    }
}

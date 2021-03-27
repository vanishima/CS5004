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
        System.out.println("\n=============== Current cards ===============");
        showStatus();
        player.turn(deck);
        dealer.turn(deck);
    }

    /**
     * Checks whether the game has a winner
     * @return true if the game has a winner
     */
    public Player getWinner(){
        // if only one player has blackjack, only one player bust, or a player has higher score
        if (!hasBlackJack()){
            if (!hasBust()){
                if (!hasHigherPoints()){
                    return null;
                }
            }
        }
        return winner;
    }

    /**
     * Checks whether at least one player has blackjack
     * @return true if at at least one player has blackjack, false if none has blackjack
     */
    public boolean hasBlackJack(){
        if (player.blackjack() != dealer.blackjack()){
            winner = (player.blackjack()) ? player : dealer;
            System.out.println("\n>>> " + winner.getName() + " HAS A BLACKJACK! <<<");
        }
        return player.blackjack() || dealer.blackjack();
    }

    /**
     * Checks whether at least one player bust
     * @return true if at least one player bust, false if none or both bust
     */
    public boolean hasBust(){
        if (player.bust() != dealer.bust()){
            Player loser = (player.bust()) ? player : dealer;
            winner = (loser.equals(player)) ? dealer : player;
            System.out.println("\n>>> " + loser.getName() + " BUST with a total of " + loser.totalValue() + "! <<<");
        }
        return player.bust() || dealer.bust();
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
        player.showStatus();
        System.out.println("");
        dealer.showStatus();
    }

    /**
     * Print out all the cards of both player and show if there is a winner
     * of the game is a tie.
     */
    private void showEnding(){
        System.out.println("\n\n=============== Game is over! ===============");
        showStatus();
        if (getWinner() != null){
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

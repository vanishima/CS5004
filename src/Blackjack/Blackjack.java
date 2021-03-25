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
    public boolean hasWinner(){
        // if only one player has blackjack, only one player bust, or a player has higher score
        return hasBlackJackWinner() || hasBustWinner() || hasHigherPointsWinner();
    }

    /**
     * Checks whether at least one player has blackjack
     * @return true if at at least one player has blackjack, false if none has blackjack
     */
    public boolean hasBlackJackWinner(){
        boolean playerBlackjack = player.blackjack();
        boolean dealerBlackjack = dealer.blackjack();

        if (playerBlackjack){ System.out.println("\n** Player GOT A BLACKJACK!**\n"); }
        if (dealerBlackjack){ System.out.println("\n** Dealer GOT A BLACKJACK!**\n"); }

        if (playerBlackjack != dealerBlackjack){            // only one has blackjack
            winner = (playerBlackjack) ? player : dealer;
            return true;
        }
        return false;                             // both has blackjack or none has blackjack

    }

    /**
     * Checks whether at least one player bust
     * @return true if at least one player bust, false if none or both bust
     */
    public boolean hasBustWinner(){
        boolean playerBust = player.bust();
        boolean dealerBust = dealer.bust();

        if (playerBust){ System.out.println("\n** Player BUST!**\n"); }
        if (dealerBust){ System.out.println("\n** Dealer BUST!**\n"); }

        if (playerBust != dealerBust){                      // only one bust
            winner = (playerBust) ? dealer : player;
            return true;
        }
        return false;                                  // both bust or no one bust
    }

    /**
     * Assume that no one has blackjack and no one bust, check the maximum points
     * of each player under 21
     */
    public boolean hasHigherPointsWinner(){
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

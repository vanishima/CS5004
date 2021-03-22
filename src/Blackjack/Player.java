package Blackjack;

import java.util.Scanner;

/**
 * A class to represent a BlackJack player
 */
public class Player {
    private Hand cards;
    private String name = "Player";
    private boolean stand = false;

    /**
     * Create a player with 2 cards taken from a source deck
     * @param sourceDeck the deck where cards are taken from
     * @param isDealer true if the player is dealer, false if not
     */
    public Player(Deck sourceDeck, boolean isDealer){
        this.cards = new Hand(2, sourceDeck);
        if (isDealer){
            this.name = "Dealer";
            this.cards.setHiddenCard();
        }
    }

    /**
     * Checks whether this player choose to stand
     * @return true if stand, false, if not stand
     */
    public boolean isStand(){
        return this.stand;
    }

    /**
     * Return the name of the player as string
     * @return the name of the player
     */
    public String getName(){
        return this.name;
    }

    /**
     * Take one card from the sourceDeck
     * @param sourceDeck the deck where cards are taken from
     */
    public void hit(Deck sourceDeck){
        System.out.println(this.getName() + " HIT!");
        cards.getCardFromDeck(1, sourceDeck);
    }

    /**
     * Change the stand variable to true.
     */
    public void stand(){
        System.out.println(this.getName() + " STAND!");
        this.stand = true;
    }

    /**
     * Remove the hidden card restriction of the dealer player
     */
    public void showHiddenCard(){
        this.cards.removeHiddenRestriction();
    }

    /**
     * Check whether the player's cards are over 21
     * @return true if the cards are over 21, false if less than or equal to 21
     */
    public boolean bust(){
        if (totalValue() >= 21){
            this.stand = true;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Check whether the hand has exactly one Ace and a 10
     * @return true if there is a blackjack, false if not
     */
    public boolean blackjack(){
         // if total value is 11 and there is an Ace
        if (cards.getTotalValue() == 11 &&
                (cards.getCard(0).isAce() || cards.getCard(1).isAce())){
            this.stand();
            return true;
        } else {
            return false;
        }
    }

    /**
     * Returns the total value of the hand
     * @return the total value of the hand
     */
    public Integer totalValue(){
        return this.cards.getTotalValue();
    }

    /**
     * Returns the maximum value of the hand this player has
     * @return
     */
    public Integer getMaxValue(){
        return this.cards.getMaxValue();
    }

    /**
     * Determines if the player will hit
     * @return true if the player decides to hit, false if not
     */
    public boolean chooseToHit(){
        if (this.name.equals("Dealer")){
            return totalValue() < 17;
        } else {
            Scanner keyboard = new Scanner(System.in);
            System.out.println("Do you want to hit or stand? 1 for yes, 0 for no.");
            int answer;
            while (true){
                // keep asking for integer input
                while (!keyboard.hasNextInt()){
                    System.out.println("Please enter 0 to stand, 1 to hit.");
                    keyboard.next();
                }
                answer = keyboard.nextInt();
                // keep asking for valid integer (0 or 1)
                while (answer > 1 || answer < 0){
                    System.out.println("Please enter 0 to stand, 1 to hit.");
                    answer = keyboard.nextInt();
                }
                break;  // only break when the input is either 0 or 1
            }
            return answer == 1;
        }
    }

    /**
     * Print out all cards the player has, one of the dealer's cards
     * is hidden until the end
     */
    public void showStatus(){
        System.out.println(">> " + this.name + "'s hand");
        this.cards.printDeck();
    }
}

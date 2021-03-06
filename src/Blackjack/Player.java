package Blackjack;

import java.util.Scanner;

/**
 * A class to represent a BlackJack player
 */
public class Player {
    private Hand cards;
    private String name = "Player";

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
     * If the player did not choose to stand in the last round,
     * ask her to decide to hit or stand in this round
     * @param sourceDeck the deck from where new cards come from
     */
    public void turn(Deck sourceDeck){
        System.out.println("========" + getName() + "'s Turn ========");
        while (!blackjack() && !bust()) {
            if (chooseToHit()) {
                hit(sourceDeck);
            } else {
                break;
            }
        }
        cards.removeHiddenRestriction(); // Remove the hidden card restriction of the dealer player
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
        System.out.println(this.getName() + " HIT!\n");
        cards.getCardFromDeck(1, sourceDeck);
        showStatus();
    }

    /**
     * Check whether the player's cards are over 21
     * @return true if the cards are over 21, false if less than or equal to 21
     */
    public boolean bust(){
        return cards.bust();
    }

    /**
     * Check whether the hand has exactly one Ace and a 10
     * @return true if there is a blackjack, false if not
     */
    public boolean blackjack(){
        return cards.blackjack();
    }

    /**
     * Returns the total value of the hand
     * @return the total value of the hand
     */
    public Integer totalValue(){
        return cards.getTotalValue();
    }

    /**
     * Returns the maximum value of the hand this player has
     * @return the maximum value of the hand
     */
    public Integer getMaxValue(){
        return cards.getMaxValue();
    }

    /**
     * Determines if the player will hit
     * @return true if the player decides to hit, false if not
     */
    public boolean chooseToHit(){
        if (this.name.equals("Dealer")){
            return totalValue() < 17;
        } else {
            return getValidHitAnswer() == 1;
        }
    }

    /**
     * Keep asking for user input until 1 or 0 is entered
     * @return 1 or 0 entered by the user
     */
    public int getValidHitAnswer(){
        int answer = -1;
        Scanner keyboard = new Scanner(System.in);
        while (answer < 0 || answer > 1){
            System.out.println("Please enter 0 to stand, 1 to hit.");
            if (keyboard.hasNextInt()){
                answer = keyboard.nextInt();
            } else {
                keyboard.next();
            }
        }
        return answer;
    }

    /**
     * Print out all cards the player has, one of the dealer's cards
     * is hidden until the end
     */
    public void showStatus(){
        System.out.println(">> " + this.name + "'s hand");
        if (!getName().equals("Dealer")){
            System.out.println(getName() + "'s total points: " + totalValue());
        }
        System.out.println(cards);
    }
}

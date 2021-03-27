package Blackjack;

import java.util.Random;

/**
 * A class that represents a hand derived from Deck
 */
public class Hand extends Deck{
    Integer hiddenCard = -1;

    /**
     * Create a Hand object with zero cards
     */
    public Hand(){
        super(0, null);
    }

    /**
     * Create a hand with given number of cards, all removed from a bigger deck
     * @param numOfCards the number of cards will be added to the hand
     * @param sourceDeck the deck where the hand gets the card
     */
    public Hand(Integer numOfCards, Deck sourceDeck){
        super(numOfCards, sourceDeck);
    }

    /**
     * Checks if the hand has a blackjack.
     * When there is only two cards in the deck with a total value of 11,
     * and there is an Ace
     * @return true if the hand has a blackjack, false if not
     */
    public boolean blackjack(){
        return getTotalValue() == 11 && getDeckSize() == 2 &&
                (getCard(0).isAce() || getCard(1).isAce());
    }

    /**
     * Check whether the total value of cards in this hand is over 21
     * @return true if the cards are over 21, false if less than or equal to 21
     */
    public boolean bust(){
        return getTotalValue() > 21;
    }

    /**
     * Randomly select a card and set it as the hidden card
     */
    public void setHiddenCard(){
        Random rand = new Random();
        this.hiddenCard = rand.nextInt(this.getDeckSize());
    }

    /**
     * Set the index of the hidden card to -1 so that all cards will be printed
     */
    public void removeHiddenRestriction(){
        this.hiddenCard = -1;
    }

    /**
     * Print all cards in the deck except the hidden card
     */
    @Override
    public String toString(){
        System.out.print(getDeckSize() + " cards in this hand:\n");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < getDeckSize(); i++){
            if (i != this.hiddenCard){
                sb.append(String.format("%-10s",getCard(i)));
            }
        }
        sb.append("\n");
        return sb.toString();
    }

    /**
     * A demo method to test methods in the Hand class
     * @param args
     */
    public static void main(String[] args){
        Deck deck = new Deck();
        deck.sort();
        System.out.println(deck);

        // create two hands each with 5 cards
        Hand hand1 = new Hand(5, deck);
        Hand hand2 = new Hand(5, deck);

        System.out.println(hand1);
        System.out.println(hand2);

        System.out.println(deck);  // now we only have 42 cards in this deck

        deck.getCardFromDeck(5, hand1);
        System.out.println(deck);  // we should have 47 cards now

        deck.getCardFromDeck(5, hand2);
        System.out.println(deck);  // we should have all 52 cards now
    }
}

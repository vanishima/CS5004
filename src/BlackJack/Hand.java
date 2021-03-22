package BlackJack;

import java.util.Random;

/**
 * A class that represents a hand derived from Deck
 */
public class Hand extends Deck{

    /**
     * Create a Hand object with zero cards
     */
    public Hand(){
        super(0, null);
    }

    /**
     * Create a hand with given number of cards, all removed from a bigger deck
     * @param numOfCards the number of cards will be added to the hand
     * @param biggerDeck the deck where the hand gets the card
     */
    public Hand(Integer numOfCards, Deck biggerDeck){
        super(numOfCards, biggerDeck);
    }

    /**
     * A demo method to test methods in the Hand class
     * @param args
     */
    public static void main(String[] args){
        Deck deck = new Deck();
        deck.sort();
        deck.printDeck();

        // create two hands each with 5 cards
        Hand hand1 = new Hand(5, deck);
        Hand hand2 = new Hand(5, deck);

        hand1.printDeck();
        hand2.printDeck();

        deck.printDeck();  // now we only have 42 cards in this deck

        deck.getCardFromDeck(5, hand1);
        deck.printDeck();  // we should have 47 cards now

        deck.getCardFromDeck(5, hand2);
        deck.printDeck();  // we should have all 52 cards back

    }
}

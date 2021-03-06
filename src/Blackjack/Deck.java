package Blackjack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * A class to represent a deck of cards
 */
public class Deck {
    private List<Card> cards = new ArrayList<Card>();
    private Integer totalValue = 0;

    /**
     * Create a Deck object and add 52 new cards
     * into the arraylist
     */
    public Deck(){
        // add 13 cards for each suit
        for (Suit suit : Suit.values()){
            for (CardName name: CardName.values()){
                this.cards.add(new Card(suit, name));
                this.totalValue += name.getValue();
            }
        }
    }

    /**
     * Create a Deck object with the given number of cards
     * @param numOfCards number of cards in the deck
     * @param sourceDeck the deck where cards are removed and added to this deck
     */
    public Deck(Integer numOfCards, Deck sourceDeck){
        this.getCardFromDeck(numOfCards, sourceDeck);
    }

    /**
     * Remove the given number of cards from sourceDeck and add them to
     * this deck
     * @param numOfCards the number of cards to be removed and added
     * @param sourceDeck the deck where cards are removed and added to this deck
     */
    public void getCardFromDeck(Integer numOfCards, Deck sourceDeck){
        if (numOfCards > 0){
            for (int i = 0; i < numOfCards; i++){
                // remove numOfCards from the sourceDeck and add to this deck
                addCard(sourceDeck.removeCard());
            }
        }
    }

    /**
     * Add a Card object to the arraylist deck and update the total value
     * @param theCard the Card object to be added
     */
    public void addCard(Card theCard){
        cards.add(theCard);
        totalValue += theCard.getValue();
    }

    /**
     * Removes the first card stored in the Arraylist, deduct its value from total value
     * and return the card
     * @return the first card stored in the Arraylist
     */
    public Card removeCard() throws IllegalStateException{
        if (cards.size() == 0){
            throw new IllegalStateException("** EMPTY DECK **");
        } else {
            Card card = cards.get(0);
            this.totalValue -= card.getValue();
            cards.remove(0);
            return card;
        }
    }

    /**
     * Get the card from the given index
     * @param index the index of the card
     * @return the card on that index
     * @throws IndexOutOfBoundsException if index is invalid
     */
    public Card getCard(Integer index) throws IndexOutOfBoundsException{
        if (index < 0 || index >= cards.size()){
            throw new IndexOutOfBoundsException();
        }
        return cards.get(index);
    }

    /**
     * Randomly shuffle all cards in the deck
     */
    public void shuffle(){
        Random rand = new Random();
        for (int i = 0; i < cards.size(); i++){
            // generate two numbers from 0 to cards.size()-1
            int card1 = rand.nextInt(cards.size());
            int card2 = rand.nextInt(cards.size());
            // swap these two cards
            Card temp = getCard(card1);
            cards.set(card1, getCard(card2));
            cards.set(card2, temp);
        }
    }

    /**
     * Returns the number of cards in the deck
     * @return the number of cards in the deck
     */
    public Integer getDeckSize(){
        return this.cards.size();
    }

    /**
     * Sort the cards in the deck ordered by name
     */
    public void sort(){
        Collections.sort(cards);
    }

    /**
     * Print all cards in the deck and display the number of cards
     */
    @Override
    public String toString(){
        System.out.print(getDeckSize() + " cards in this deck:\n");
        StringBuilder string = new StringBuilder();
        for (int i = 0; i < getDeckSize() / 6; i++){
            for (int j = 0; j < 6; j++){
                string.append(String.format("%-10s", getCard(i * 6 + j)));
            }
            string.append("\n");
        }
        return string.toString();
    }

    /**
     * Returns the total value of this deck. Ace is treated as 1.
     * @return the total value of this deck.
     */
    public Integer getTotalValue(){
        return this.totalValue;
    }

    /**
     * Returns the maximum value of this deck by counting Ace as 10
     * without going over 21
     * @return the maximum value of this deck below 21
     */
    public Integer getMaxValue(){
        int tempTotal = getTotalValue();
        for (Card card: cards){
            // if there is an Ace and the total value is less than 12
            // add additional 9 to the total value
            if (card.isAce() && tempTotal < 12){
                tempTotal += 9;
                break;
            }
        }
        return tempTotal;
    }

    /**
     * A demo method to test methods in the Deck class
     * @param args
     */
    public static void main(String[] args){
        Deck deck = new Deck();
        deck.shuffle();
        deck.sort();
        System.out.println("deck size=" + deck.cards.size());
        System.out.println(deck);
    }
}

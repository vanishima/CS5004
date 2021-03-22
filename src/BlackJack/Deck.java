package BlackJack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * A class to represent a deck of cards
 */
public class Deck {
    private final List<Card> cardList = new ArrayList<Card>();
    private Integer totalValue = 0;
    private Integer hiddenCard = -1;

    /**
     * Create a Deck object and add 52 new cards
     * into the arraylist
     */
    public Deck(){
        // add 13 cards for each suit
        for (Suit suit : Suit.values()){
            for (CardName name: CardName.values()){
                this.cardList.add(new Card(suit, name));
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
        cardList.add(theCard);
        totalValue += theCard.getValue();
    }

    /**
     * Get the card from the given index
     * @param index the index of the card
     * @return the card on that index
     * @throws IndexOutOfBoundsException if index is invalid
     */
    public Card getCard(Integer index) throws IndexOutOfBoundsException{
        if (index < 0 || index >= cardList.size()){
            throw new IndexOutOfBoundsException();
        }
        return cardList.get(index);
    }

    /**
     * Randomly select a card and set it as the hidden card
     */
    public void setHiddenCard(){
        Random rand = new Random();
        int hiddenIndex = rand.nextInt(this.cardList.size());
        this.hiddenCard = hiddenIndex;
    }

    public void removeHiddenRestriction(){
        this.hiddenCard = -1;
    }

    /**
     * Randomly shuffle all cards in the deck
     */
    public void shuffle(){
        Random rand = new Random();
        for (int i = 0; i < cardList.size(); i++){
            // generate two numbers from 0 to cardList.size()-1
            int card1 = rand.nextInt(cardList.size());
            int card2 = rand.nextInt(cardList.size());
            // swap these two cards
            Card temp = getCard(card1);
            cardList.set(card1, getCard(card2));
            cardList.set(card2, temp);
        }
    }

    /**
     * Removes the first card stored in the Arraylist, deduct its value from total value
     * and return the card
     * @return the first card stored in the Arraylist
     */
    public Card removeCard(){
        if (cardList.size() == 0){
            System.out.println("** EMPTY DECK **");
            return null;
        } else {
            Card card = cardList.get(0);
            this.totalValue -= card.getValue();
            cardList.remove(0);
            return card;
        }
    }

    public Integer getTotalValue(){
        return this.totalValue;
    }


    public Integer getMaxValue(){
        int tempTotal = getTotalValue();
        for (Card card: cardList){
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
     * Sort the cards in the deck ordered by name
     */
    public void sort(){
        Collections.sort(cardList);
    }

    /**
     * Print all cards in the deck except the hidden one
     */
    public void printDeck(){
        if (cardList.size() == 0){
            System.out.println("** EMPTY DECK **");
        } else {
            System.out.print(cardList.size() + " cards in this deck: ");
            if (cardList.size() >= 6){
                for (int i = 0; i < cardList.size() / 6; i++){
                    for (int j = 0; j < 6; j++){
                        if (i != this.hiddenCard){
                            System.out.print(String.format("%-18s",getCard(i * 6 + j)));
                        }
                    }
                    System.out.println("");
                }
            } else {
                for (int i = 0; i <cardList.size(); i++){
                    if (i != this.hiddenCard){
                        System.out.print(String.format("%-18s",getCard(i)));
                    }
                }
                System.out.println("");
            }
        }
//        System.out.println("");
    }

    /**
     * A demo method to test methods in the Deck class
     * @param args
     */
    public static void main(String[] args){
        Deck deck = new Deck();
        deck.shuffle();
        deck.sort();
        System.out.println("deck size=" + deck.cardList.size());
        deck.printDeck();
    }
}

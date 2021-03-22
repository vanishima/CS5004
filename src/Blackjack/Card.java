package Blackjack;

/**
 * An enum type that represents suits in a deck of cards
 */
enum Suit{
    CLUBS("\u2663"), DIAMONDS("\u2666"),
    HEARTS("\u2665"), SPADES("\u2660");

    private String symbol;

    Suit(String theSymbol){
        this.symbol = theSymbol;
    }

    @Override
    public String toString(){
        return this.symbol;
    }

}

/**
 * An enum type that represents the names of cards
 * and the corresponding integer value
 */
enum CardName{
    ACE(1), TWO(2), THREE(3), FOUR(4), FIVE(5),
    SIX(6), SEVEN(7), EIGHT(8), NINE(9), TEN(10),
    JACK(10), QUEEN(10), KING(10);  // face value all 10

    private Integer value;

    /**
     * Create an enum type with the given value as its value
     * @param theValue the value of this card
     */
    CardName(Integer theValue){
        this.value = theValue;
    }

    /**
     * Returns the numerical value of this card
     * @return the numerical value of this card
     */
    public Integer getValue(){
        return this.value;
    }

    /**
     * Returns the name of card for face cards, and return digits for
     * other cards.
     * @return the string that represents this card
     */
    @Override
    public String toString(){
        if ((this.getValue() > 1 && this.getValue() < 10) ||
                this == CardName.TEN){
            return String.valueOf(this.getValue());
        } else {
            return this.name();
        }
    }

}

/**
 * A class that represents a card in a standard 52-card deck,
 * with suit, name, and integer value
 */
public class Card implements Comparable<Card>{
    private Suit suit;
    private CardName name;
    private Integer value;

    /**
     * Create a card with the given suit and card name
     * @param suit the suit of the card
     * @param name the name of the card
     */
    public Card(Suit suit, CardName name){
        setSuit(suit);
        setName(name);
        this.value = name.getValue();  // get the value from the enum type
    }

    /**
     * Returns the suit of the card
     * @return the suit of the card
     */
    public Suit getSuit() {
        return this.suit; // Clubs
    }

    /**
     * Returns the CardName type of the card by converting enum to string
     * @return the CardName of the card
     */
    public CardName getCardName(){
        return this.name;
    }

    /**
     * Returns the integer value of the card
     * @return the integer value of the card
     */
    public Integer getValue(){
        return this.value;
    }

    /**
     * Set the suit of the card
     * @param suit the suit of the card
     */
    private void setSuit(Suit suit) {
        this.suit = suit;
    }

    /**
     * Set the name of the card
     * @param name the name of the card
     */
    private void setName(CardName name) {
        this.name = name;
    }

    // We don't put a getter for value because it will be legally set
    // in the constructor

    /**
     * Returns the suit and name of the card as a string
     * @return the suit and name of the card as a string
     */
    public String toString(){
        return this.getSuit() + " " + this.getCardName();
    }

    /**
     * Compare the value of this card with another card
     */
    @Override
    public int compareTo(Card other) {
        return Integer.compare(this.getValue(), other.getValue());
    }

    /**
     * Checks whether this card is ace
     * @return true if it is ace, false if it is not ace
     */
    public boolean isAce(){
        return this.getCardName() == CardName.ACE;
    }

    /**
     * A demo method to test methods in the Card class
     * @param args
     */
    public static void main(String[] args){
        Card c1 = new Card(Suit.CLUBS, CardName.TWO);
        System.out.println(c1);
        Card c2 = new Card(Suit.SPADES, CardName.QUEEN);
        System.out.println(c2);
        System.out.println("\u2665 This should be a Hearts suit symbol.");
        System.out.println("\u2666 This should be a Diamonds suit symbol.");
        System.out.println("\u2663 This should be a Clubs suit symbol.");
        System.out.println("\u2660 This should be a Spades suit symbol.");
    }
}

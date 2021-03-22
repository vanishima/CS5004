package BlackJack;

/**
 * An enum type that represents suits in a deck of cards
 */
enum Suit{
    CLUBS, DIAMONDS, HEARTS, SPADES
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

    CardName(Integer theValue){
        this.value = theValue;
    }

    public Integer getValue(){
        return this.value;
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
     * Returns the name of the card by converting enum to string
     * @return the name of the card
     */
    public String getCardName(){
        // return digits for non-face cards
        if ((this.getValue() > 1 && this.getValue() < 10) ||
                this.name == CardName.TEN){
            return String.valueOf(name.getValue());
        } else {
            return this.name.toString();
        }
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
        return this.getCardName() + "[" + this.getSuit() + "]";
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
        return this.getCardName().equals("ACE");
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
    }
}

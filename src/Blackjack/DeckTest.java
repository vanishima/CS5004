package Blackjack;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DeckTest {
    private Deck deck;
    private Deck deck1;

    @Before
    public void setUp(){
        deck1 = new Deck();
        deck = new Deck(0, deck1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testOutOfIndexGetCard(){
        deck.getCard(1);
    }

    @Test(expected = IllegalStateException.class)
    public void testRemoveCardFromEmptyDeck(){
        deck.removeCard();
    }

    @Test
    public void testGetDeckSize(){
        assertEquals(0, deck.getDeckSize(), 0.1);
    }

    @Test
    public void testAddCard(){
        Card card = new Card(Suit.HEARTS, CardName.SIX);
        deck.addCard(card);
        assertEquals(card, deck.getCard(0));
    }

    @Test
    public void testToString(){
        assertEquals("", deck.toString());
    }

}
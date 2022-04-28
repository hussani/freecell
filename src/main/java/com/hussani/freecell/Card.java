package com.hussani.freecell;

public class Card {
    final private int value;
    final private Suit suit;

    public Card(int value, Suit suit) {
        this.value = value;
        this.suit = suit;
    }

    public int getValue() {
        return value;
    }

    public Suit getSuit() {
        return suit;
    }

    @Override
    public String toString() {
        return "Card{" +
                "value=" + value +
                ", suit=" + suit +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Card card = (Card) o;

        if (value != card.value) return false;
        return suit == card.suit;
    }

    @Override
    public int hashCode() {
        int result = value;
        result = 31 * result + suit.hashCode();
        return result;
    }
}

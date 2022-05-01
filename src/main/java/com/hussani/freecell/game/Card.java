package com.hussani.freecell.game;

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
        return "{" + value + "-" + suit + '}';
    }
}

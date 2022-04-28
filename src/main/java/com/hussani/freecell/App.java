package com.hussani.freecell;

import java.util.Collections;
import java.util.Vector;

public class App {
    public static final Integer MAX_CARD_NUMBERS = 13;
    public static final int MAX_CARDS = MAX_CARD_NUMBERS * Suit.values().length; // 52 cards

    public static void main(String[] args) {
        boolean exit = false;
        String input;

        Vector<Card> deck = populateDeck();
        Collections.shuffle(deck);
        while (!exit) {
            System.out.println("Welcome to Freecell!");
            System.out.println("Vector: " + deck);
            System.out.println("Please select an option:");
            input = System.console().readLine();

            System.out.println("You choose " + input);
            if (input.equals("exit")) {
                exit = true;
            }
        }
    }

    private static Vector<Card> populateDeck() {
        Vector<Card> deck = new Vector<>(MAX_CARDS, 1);
        for (Suit suit : Suit.values()) {
            for (int i = 1; i <= MAX_CARD_NUMBERS; i++) {
                deck.add(new Card(i, suit));
            }
        }
        return deck;
    }
}

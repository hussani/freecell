package com.hussani.freecell;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class App {
    public static final Integer MAX_CARD_NUMBERS = 13;
    public static final int MAX_CARDS = MAX_CARD_NUMBERS * Suit.values().length; // 52 cards

    public static void main(String[] args) {
        boolean exit = false;
        String input;

        MultipleStackInArray multipleStack = new MultipleStackInArray(populateDeck());
        while (!exit) {
            System.out.println("Welcome to Freecell!");
            multipleStack.print();
            System.out.println("Please select an option:");
            input = System.console().readLine();

            System.out.println("You choose " + input);

            multipleStack.pop(MultipleStackInArray.Stacks.GAME_1);

            if (input.equals("exit")) {
                exit = true;
            }
        }
    }

    private static Card[] populateDeck() {
        Card[] deck = new Card[MAX_CARDS];
        int count = 0;
        for (Suit suit : Suit.values()) {
            for (int i = 1; i <= MAX_CARD_NUMBERS; i++) {
                deck[count] = new Card(i, suit);
                count++;
            }
        }
        List<Card> deckList = Arrays.asList(deck);
        Collections.shuffle(deckList);
        return deckList.toArray(deck);
    }
}

package com.hussani.freecell;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class App {
    public static final Integer MAX_CARD_NUMBERS = 13;
    public static final int MAX_CARDS = MAX_CARD_NUMBERS * Suit.values().length; // 52 cards

    public static void main(String[] args) {
        boolean exit = false;
        String input;
        Scanner in = new Scanner(System.in);
        MultipleStackInArray<Card> multipleStack = new MultipleStackInArray<>(populateDeck());
        System.out.println("Welcome to Freecell!");
        while (!exit) {
            multipleStack.print();
            System.out.println("Please select an option:");
            input = in.nextLine();

            System.out.println("You choose " + input);

            try {
                multipleStack.pop(MultipleStackInArray.Stacks.GAME_1);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

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

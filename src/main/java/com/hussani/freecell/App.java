package com.hussani.freecell;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.hussani.freecell.ds.MultipleStackInArray;
import com.hussani.freecell.ds.StackInfo;
import com.hussani.freecell.game.Card;
import com.hussani.freecell.game.Suit;

public class App {
    public static final Integer MAX_CARD_NUMBERS = 13;
    public static final int MAX_CARDS = MAX_CARD_NUMBERS * Suit.values().length; // 52 cards

    public static void main(String[] args) {
        boolean exit = false;
        String input;
        Scanner in = new Scanner(System.in);
        MultipleStackInArray<Card> multipleStack = new MultipleStackInArray<>(populateDeck(), defaultStack());
        System.out.println("Welcome to Freecell!");
        while (!exit) {
            multipleStack.print();
            System.out.println("Please select an option:");
            input = in.nextLine();

            System.out.println("You choose " + input);

            try {
                multipleStack.pop(Stacks.GAME_1.toString());
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            if (input.equals("exit")) {
                exit = true;
            }
        }
    }

    private static Map<String, StackInfo> defaultStack() {
        return Map.of(
                Stacks.GAME_1.toString(), new StackInfo(7, 0),
                Stacks.GAME_2.toString(), new StackInfo(7, 7),
                Stacks.GAME_3.toString(), new StackInfo(7, 13),
                Stacks.GAME_4.toString(), new StackInfo(7, 20),
                Stacks.GAME_5.toString(), new StackInfo(6, 27),
                Stacks.GAME_6.toString(), new StackInfo(6, 33),
                Stacks.GAME_7.toString(), new StackInfo(6, 39),
                Stacks.GAME_8.toString(), new StackInfo(6, 45),
                Stacks.OUT.toString(), new StackInfo(0, 51)
        );
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

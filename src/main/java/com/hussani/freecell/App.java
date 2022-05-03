package com.hussani.freecell;

import com.hussani.freecell.ds.MultipleStackInArray;
import com.hussani.freecell.ds.StackInfo;
import com.hussani.freecell.game.Card;
import com.hussani.freecell.game.FreeCellGame;
import com.hussani.freecell.game.Stacks;
import com.hussani.freecell.game.Suit;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import static com.hussani.freecell.game.GamePresenter.presentGame;

public class App {
    public static final Integer MAX_CARD_NUMBERS = 13;
    public static final int MAX_CARDS = MAX_CARD_NUMBERS * Suit.values().length; // 52 cards
    public static final int FREE_CELL_COUNT = 4;

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";

    public static void main(String[] args) {
        boolean exit = false;
        String input;
        int validPlays = 0, invalidPlays = 0;

        MultipleStackInArray<Card> multipleStack = new MultipleStackInArray<>(populateDeck(), defaultStack());
        FreeCellGame freeCellGame = new FreeCellGame(multipleStack, FREE_CELL_COUNT);
        GameCommandController controller = new GameCommandController(freeCellGame);

        Scanner in = new Scanner(System.in);
        System.out.println("Welcome to Freecell!");
        while (!exit) {
            presentGame(freeCellGame);
            System.out.println("Digit the command you want to execute:");
            input = in.nextLine();

            if (input.strip().length() == 0) {
                continue;
            }

            if (input.equals("exit")) {
                exit = true;
                System.out.println("Exiting...");
                System.out.println("Valid plays: " + validPlays);
                System.out.println("Invalid plays: " + invalidPlays);
                System.out.println("Thanks for playing!");
                continue;
            }

            if (input.equals("reset")) {
                System.out.println("Valid plays: " + validPlays);
                System.out.println("Invalid plays: " + invalidPlays);
                System.out.println("Reseting...");
                multipleStack = new MultipleStackInArray<>(populateDeck(), defaultStack());
                controller = new GameCommandController(freeCellGame);
                validPlays = invalidPlays = 0;
                continue;
            }

            try {
                controller.command(input);
                validPlays++;
            } catch (Exception e) {
                System.out.println(ANSI_RED + e.getMessage() + ANSI_RESET);
                invalidPlays++;
            }
        }
    }

    private static Map<String, StackInfo> defaultStack() {
        return Map.of(
                Stacks.GAME_1.toString(), new StackInfo(7, 0),
                Stacks.GAME_2.toString(), new StackInfo(7, 7),
                Stacks.GAME_3.toString(), new StackInfo(7, 14),
                Stacks.GAME_4.toString(), new StackInfo(7, 21),
                Stacks.GAME_5.toString(), new StackInfo(6, 28),
                Stacks.GAME_6.toString(), new StackInfo(6, 34),
                Stacks.GAME_7.toString(), new StackInfo(6, 40),
                Stacks.GAME_8.toString(), new StackInfo(6, 46)
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

package com.hussani.freecell.game;


import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GamePresenter {

    public static void presentGame(final FreeCellGame game) {
        System.out.println("Free cells:");
        for (int i = 0; i < game.getFreeCells().length; i++) {
            System.out.format("%-15s %-15s\n", i , game.getFreeCells()[i]);
        }

        System.out.println("\nFoundation");
        game.getLastDiscardedCard().forEach((suit, number) -> {
            if (number != 0) {
                System.out.format("%-15s %-15s\n", suit, "{" + number + "-" + suit + "}");
                return;
            }
            System.out.format("%-15s %-15s\n", suit, null);
        });

        System.out.println("\nTableau");
        for (Stacks stack : Stacks.values()) {
            String stackCards = Stream.of(game.getStackCards(stack.name()))
                    .map(Card::toString)
                    .collect(Collectors.joining(", "));

            System.out.format("%-15s [%-15s]\n", stack.toString().substring(stack.toString().length() - 1), stackCards);
        }
    }
}

package com.hussani.freecell.game;

import com.hussani.freecell.ds.MultipleStackInArray;

import java.util.Arrays;

public class FreecellGame {
    private final Card[] freeCells;

    private final MultipleStackInArray<Card> stacks;

    public FreecellGame(MultipleStackInArray<Card> stacks, final int freeCellCount) {
        this.stacks = stacks;
        this.freeCells = new Card[freeCellCount];
    }

    public void moveTopCard(String stackNameFrom, String stackNameTo) {
        final Card movingCard = stacks.peek(stackNameFrom);
        final Card targetCard = stacks.peek(stackNameTo);

        validateGameMovement(movingCard, targetCard);

        stacks.push(stackNameTo, stacks.pop(stackNameFrom));
    }

    public void moveToFreeCell(String stackNameFrom) {
        if (stacks.peek(stackNameFrom) == null) {
            throw new GameException("The stack is empty");
        }
        for (int i = 0; i < freeCells.length; i++) {
            if (freeCells[i] == null) {
                freeCells[i] = stacks.pop(stackNameFrom);
                return;
            }
        }
        throw new GameException("No free cell available");
    }

    public Card[] getFreeCells() {
        return Arrays.copyOf(freeCells, freeCells.length);
    }

    private void validateGameMovement(Card movingCard, Card targetCard) {
        if (movingCard.getValue() != targetCard.getValue() - 1) {
            throw new GameException("The moving card must be one number below of the target card");
        }
        if (movingCard.getSuit().getColor().equals(targetCard.getSuit().getColor())) {
            throw new GameException("Cards must be of different colors");
        }
    }
}

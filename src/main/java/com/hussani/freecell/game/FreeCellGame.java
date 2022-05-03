package com.hussani.freecell.game;

import com.hussani.freecell.ds.MultipleStackInArray;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Game implementation for FreeCell game.
 * This class is responsible for game logic.
 */
public class FreeCellGame {
    private final Card[] freeCells;

    private final MultipleStackInArray<Card> stacks;

    private final Map<Suit, Integer> lastDiscardedCard;

    private boolean canMove = true;

    public FreeCellGame(MultipleStackInArray<Card> stacks, final int freeCellCount) {
        this.stacks = stacks;
        this.freeCells = new Card[freeCellCount];
        this.lastDiscardedCard = new HashMap<>(Map.of(Suit.CLUBS, 0, Suit.DIAMONDS,
                0, Suit.HEARTS, 0, Suit.SPADES, 0));
    }

    /**
     * Package-private constructor for testing purposes
     */
    FreeCellGame(final MultipleStackInArray<Card> stacks,
                 final int freeCellCount, Map<Suit, Integer> lastDiscardedCard) {
        this.stacks = stacks;
        this.freeCells = new Card[freeCellCount];
        this.lastDiscardedCard = new HashMap<>(lastDiscardedCard);
    }

    public void moveTopCard(String stackNameFrom, String stackNameTo) {
        final Card movingCard = stacks.peek(stackNameFrom);
        final Card targetCard = stacks.peek(stackNameTo);

        validateGameMovement(movingCard, targetCard);

        stacks.push(stackNameTo, stacks.pop(stackNameFrom));
        generatePossibleMoves();
    }

    public void moveToFreeCell(String stackNameFrom) {
        if (stacks.peek(stackNameFrom) == null) {
            throw new GameException("The stack is empty");
        }
        for (int i = 0; i < freeCells.length; i++) {
            if (freeCells[i] == null) {
                freeCells[i] = stacks.pop(stackNameFrom);
                generatePossibleMoves();
                return;
            }
        }
        throw new GameException("No free cell available");
    }

    public void moveFromGameToFoundation(String stackNameFrom) {
        if (stacks.peek(stackNameFrom) == null) {
            throw new GameException("The stack is empty");
        }

        final Card movingCard = stacks.peek(stackNameFrom);
        validateOutMovement(movingCard);
        stacks.pop(stackNameFrom);
        lastDiscardedCard.replace(movingCard.getSuit(), movingCard.getValue());
        generatePossibleMoves();
    }

    public void moveFreeCellToGame(int i, String destinationStackName) {
        checkFreeCell(i);

        final Card targetCard = stacks.peek(destinationStackName);
        validateGameMovement(freeCells[i], targetCard);
        stacks.push(destinationStackName, freeCells[i]);
        freeCells[i] = null;
        generatePossibleMoves();
    }

    public void moveFreeCellToFoundation(int i) {
        checkFreeCell(i);

        final Card movingCard = freeCells[i];
        validateOutMovement(movingCard);
        lastDiscardedCard.replace(movingCard.getSuit(), movingCard.getValue());
        freeCells[i] = null;
        generatePossibleMoves();
    }

    public Card[] getFreeCells() {
        return Arrays.copyOf(freeCells, freeCells.length);
    }

    public Map<Suit, Integer> getLastDiscardedCard() {
        return Map.copyOf(lastDiscardedCard);
    }

    public Card[] getStackCards(String stackName) {
        return stacks.getStackItems(stackName);
    }

    public boolean isGameOver() {
        return !canMove;
    }

    public boolean isWon() {
        return this.stacks.getItems().length == this.stacks.getEmptyPositionsCount();
    }

    private void generatePossibleMoves() {
        if (this.stacks.getItems().length == this.stacks.getEmptyPositionsCount()) { // if all stacks are empty
            this.canMove = false;
            return;
        }
        for (Card freeCell : freeCells) {
            if (freeCell == null) {
                this.canMove = true;
                return;
            }
            for (String stackName : stacks.getStackNames()) {
                Card stackCard = stacks.peek(stackName);
                if (testOutMovement(freeCell) ||
                        testOutMovement(stackCard) ||
                        testGameMovement(freeCell, stackCard)) {
                    this.canMove = true;
                    return;
                }
            }
        }
        for (String stackNameFrom : stacks.getStackNames()) {
            for (String stackNameTo : stacks.getStackNames()) {
                if (testGameMovement(stacks.peek(stackNameFrom), stacks.peek(stackNameTo))) {
                    this.canMove = true;
                    return;
                }
            }
        }
        this.canMove = false;
    }

    private void checkFreeCell(int i) {
        if (freeCells[i] == null) {
            throw new GameException("The free cell is empty");
        }
    }

    private boolean testOutMovement(Card movingCard) {
        try {
            validateOutMovement(movingCard);
            return true;
        } catch (GameException e) {
            return false;
        }
    }

    private void validateOutMovement(Card movingCard) {
        if (movingCard.getValue() != lastDiscardedCard.get(movingCard.getSuit()) + 1) {
            throw new GameException("The moving card must be one number greater of the last discarded card");
        }
    }

    private boolean testGameMovement(Card movingCard, Card targetCard) {
        try {
            validateGameMovement(movingCard, targetCard);
            return true;
        } catch (GameException e) {
            return false;
        }
    }

    private void validateGameMovement(Card movingCard, Card targetCard) {
        if (targetCard == null) {
            return;
        }
        if (movingCard.getValue() != targetCard.getValue() - 1) {
            throw new GameException("The moving card must be one number below of the target card");
        }
        if (movingCard.getSuit().getColor().equals(targetCard.getSuit().getColor())) {
            throw new GameException("Cards must be of different colors");
        }
    }
}

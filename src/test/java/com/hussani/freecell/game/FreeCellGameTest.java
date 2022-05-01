package com.hussani.freecell.game;

import com.hussani.freecell.ds.MultipleStackInArray;
import com.hussani.freecell.ds.StackInfo;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FreeCellGameTest {

    @Test
    void moveTopCard() {
        Map<String, StackInfo> stacks = Map.of(
                "first", new StackInfo(2, 0),
                "second", new StackInfo(2, 2)
        );
        Card[] cards = {
                new Card(1, Suit.CLUBS), new Card(3, Suit.HEARTS),
                new Card( 11, Suit.DIAMONDS), new Card(4, Suit.SPADES)};
        MultipleStackInArray<Card> multipleStack = new MultipleStackInArray<>(cards, stacks);

        FreeCellGame game = new FreeCellGame(multipleStack, 4);
        game.moveTopCard("first", "second");
        assertEquals(1, multipleStack.getStackItemCount("first"));
        assertEquals(3, multipleStack.getStackItemCount("second"));
    }

    @Test
    void moveTopShouldNotWorkWhenColorIsSameCard() {
        Map<String, StackInfo> stacks = Map.of(
                "first", new StackInfo(2, 0),
                "second", new StackInfo(2, 2)
        );
        Card[] cards = {
                new Card(1, Suit.CLUBS), new Card(3, Suit.HEARTS),
                new Card( 11, Suit.DIAMONDS), new Card(4, Suit.DIAMONDS)};
        MultipleStackInArray<Card> multipleStack = new MultipleStackInArray<>(cards, stacks);

        FreeCellGame game = new FreeCellGame(multipleStack, 4);
        assertThrows(GameException.class,
                () -> game.moveTopCard("first", "second"),
                "Cards must be of different colors");
    }

    @Test
    void moveTopShouldNotWorkWhenFromIsNotOneValueLesserThanTo() {
        Map<String, StackInfo> stacks = Map.of(
                "first", new StackInfo(2, 0),
                "second", new StackInfo(2, 2)
        );
        Card[] cards = {
                new Card(1, Suit.CLUBS), new Card(5, Suit.HEARTS),
                new Card( 11, Suit.DIAMONDS), new Card(4, Suit.SPADES)};
        MultipleStackInArray<Card> multipleStack = new MultipleStackInArray<>(cards, stacks);

        FreeCellGame game = new FreeCellGame(multipleStack, 4);
        assertThrows(GameException.class,
                () -> game.moveTopCard("first", "second"),
                "The moving card must be one number below of the target card");
    }

    @Test
    void moveToFreeCell() {
        Map<String, StackInfo> stacks = Map.of(
                "first", new StackInfo(2, 0),
                "second", new StackInfo(2, 2)
        );
        final Card movingCard = new Card(5, Suit.HEARTS);
        Card[] cards = {
                new Card(1, Suit.CLUBS), movingCard,
                new Card(11, Suit.DIAMONDS), new Card(4, Suit.SPADES)};
        MultipleStackInArray<Card> multipleStack = new MultipleStackInArray<>(cards, stacks);

        FreeCellGame game = new FreeCellGame(multipleStack, 4);
        game.moveToFreeCell("first");
        assertEquals(1, multipleStack.getStackItemCount("first"));
        assertEquals(2, multipleStack.getStackItemCount("second"));
        assertEquals(movingCard, game.getFreeCells()[0]);
    }

    @Test
    void moveToFreeCellShouldNotWorkWhenThereNoSpace() {
        Map<String, StackInfo> stacks = Map.of(
                "first", new StackInfo(2, 0),
                "second", new StackInfo(2, 2)
        );
        Card[] cards = {
                new Card(1, Suit.CLUBS), new Card(5, Suit.HEARTS),
                new Card(11, Suit.DIAMONDS), new Card(4, Suit.SPADES)};
        MultipleStackInArray<Card> multipleStack = new MultipleStackInArray<>(cards, stacks);

        FreeCellGame game = new FreeCellGame(multipleStack, 1);
        game.moveToFreeCell("first");
        assertThrows(GameException.class, () -> game.moveToFreeCell("first"));
    }

    @Test
    void moveFromFreeCellToFoundation() {
        Map<String, StackInfo> stacks = Map.of(
                "first", new StackInfo(3, 0),
                "second", new StackInfo(2, 3)
        );
        Card[] cards = {
                new Card(1, Suit.CLUBS), new Card(5, Suit.HEARTS), new Card(2, Suit.HEARTS),
                new Card(11, Suit.DIAMONDS), new Card(4, Suit.SPADES)};

        final Map<Suit, Integer> lastDiscarded = Map.of(Suit.CLUBS, 0, Suit.DIAMONDS,
                0, Suit.HEARTS, 1, Suit.SPADES, 0);

        MultipleStackInArray<Card> multipleStack = new MultipleStackInArray<>(cards, stacks);

        FreeCellGame game = new FreeCellGame(multipleStack, 4, lastDiscarded);
        game.moveToFreeCell("first");
        game.moveFreeCellToFoundation(0);
        assertEquals(2, game.getLastDiscardedCard().get(Suit.HEARTS));
    }

    @Test
    void moveFromFreeCellToGame() {
        Map<String, StackInfo> stacks = Map.of(
                "first", new StackInfo(3, 0),
                "second", new StackInfo(2, 3)
        );
        Card movingCard = new Card(3, Suit.HEARTS);
        Card[] cards = {
                new Card(1, Suit.CLUBS), new Card(5, Suit.HEARTS), movingCard,
                new Card(11, Suit.DIAMONDS), new Card(4, Suit.SPADES)};

        MultipleStackInArray<Card> multipleStack = new MultipleStackInArray<>(cards, stacks);

        FreeCellGame game = new FreeCellGame(multipleStack, 4);
        game.moveToFreeCell("first");
        game.moveFreeCellToGame(0, "second");
        assertEquals(3, multipleStack.getStackItemCount("second"));
        assertEquals(movingCard, multipleStack.peek("second"));
    }

    @Test
    void moveFromGameToFoundation() {
        Map<String, StackInfo> stacks = Map.of(
                "first", new StackInfo(2, 0),
                "second", new StackInfo(2, 2)
        );
        final Card movingCard = new Card(1, Suit.HEARTS);
        Card[] cards = {
                new Card(1, Suit.CLUBS), movingCard,
                new Card(11, Suit.DIAMONDS), new Card(4, Suit.SPADES)};
        MultipleStackInArray<Card> multipleStack = new MultipleStackInArray<>(cards, stacks);

        FreeCellGame game = new FreeCellGame(multipleStack, 4);
        game.moveFromGameToFoundation("first");

        Map<Suit, Integer> lastDiscarded = Map.of(Suit.CLUBS, 0, Suit.DIAMONDS,
                0, Suit.HEARTS, 1, Suit.SPADES, 0);
        assertEquals(1, multipleStack.getStackItemCount("first"));
        assertEquals(2, multipleStack.getStackItemCount("second"));
        assertEquals(2, multipleStack.getStackItemCount("second"));
        assertEquals(lastDiscarded, game.getLastDiscardedCard());
    }

    @Test
    void moveFromGameToFoundationShouldNotWorkWhenTheLastDiscardedIsOneNumberLesserThanMovingCard() {
        Map<String, StackInfo> stacks = Map.of(
                "first", new StackInfo(2, 0),
                "second", new StackInfo(2, 2)
        );
        final Card movingCard = new Card(6, Suit.HEARTS);
        Card[] cards = {
                new Card(1, Suit.CLUBS), movingCard,
                new Card(11, Suit.DIAMONDS), new Card(4, Suit.SPADES)};
        MultipleStackInArray<Card> multipleStack = new MultipleStackInArray<>(cards, stacks);

        final Map<Suit, Integer> lastDiscarded = Map.of(Suit.CLUBS, 0, Suit.DIAMONDS,
                0, Suit.HEARTS, 1, Suit.SPADES, 0);

        FreeCellGame game = new FreeCellGame(multipleStack, 4, lastDiscarded);

        assertThrows(GameException.class, () -> game.moveFromGameToFoundation("first"));

        assertEquals(2, multipleStack.getStackItemCount("first"));
        assertEquals(2, multipleStack.getStackItemCount("second"));
        assertEquals(lastDiscarded, game.getLastDiscardedCard());
    }
}
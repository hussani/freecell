package com.hussani.freecell.game;

import com.hussani.freecell.ds.MultipleStackInArray;
import com.hussani.freecell.ds.StackInfo;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class FreecellGameTest {

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

        FreecellGame game = new FreecellGame(multipleStack, 4);
        game.moveTopCard("first", "second");
        assertEquals(1, multipleStack.getStackCount("first"));
        assertEquals(3, multipleStack.getStackCount("second"));
    }

    @Test
    void moveTopShoudNotWorkWhenColorIsSameCard() {
        Map<String, StackInfo> stacks = Map.of(
                "first", new StackInfo(2, 0),
                "second", new StackInfo(2, 2)
        );
        Card[] cards = {
                new Card(1, Suit.CLUBS), new Card(3, Suit.HEARTS),
                new Card( 11, Suit.DIAMONDS), new Card(4, Suit.DIAMONDS)};
        MultipleStackInArray<Card> multipleStack = new MultipleStackInArray<>(cards, stacks);

        FreecellGame game = new FreecellGame(multipleStack, 4);
        assertThrows(GameException.class,
                () -> game.moveTopCard("first", "second"),
                "Cards must be of different colors");
    }

    @Test
    void moveTopShoudNotWorkWhenFromIsNotOneValueLesserThanTo() {
        Map<String, StackInfo> stacks = Map.of(
                "first", new StackInfo(2, 0),
                "second", new StackInfo(2, 2)
        );
        Card[] cards = {
                new Card(1, Suit.CLUBS), new Card(5, Suit.HEARTS),
                new Card( 11, Suit.DIAMONDS), new Card(4, Suit.SPADES)};
        MultipleStackInArray<Card> multipleStack = new MultipleStackInArray<>(cards, stacks);

        FreecellGame game = new FreecellGame(multipleStack, 4);
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

        FreecellGame game = new FreecellGame(multipleStack, 4);
        game.moveToFreeCell("first");
        assertEquals(1, multipleStack.getStackCount("first"));
        assertEquals(2, multipleStack.getStackCount("second"));
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

        FreecellGame game = new FreecellGame(multipleStack, 1);
        game.moveToFreeCell("first");
        assertThrows(GameException.class, () -> game.moveToFreeCell("first"));
    }
}
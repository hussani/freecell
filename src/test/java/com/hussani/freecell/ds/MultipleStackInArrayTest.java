package com.hussani.freecell.ds;

import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MultipleStackInArrayTest {

    @Test
    void testStructureInitialization() {
        Map<String, StackInfo> stacks = Map.of(
                "first" , new StackInfo(5, 0),
                "second" , new StackInfo(5, 5)
        );
        Integer[] items = IntStream.range(0, 10).boxed().toArray(Integer[]::new);
        MultipleStackInArray<Integer> multipleStackInArray = new MultipleStackInArray<>(items, stacks);

        assertEquals(2, multipleStackInArray.getStacksCount());
        assertEquals(10, multipleStackInArray.getItems().length);
    }

    @Test
    void testGetStackCount() {
        Map<String, StackInfo> stacks = Map.of(
                "first" , new StackInfo(5, 0),
                "second" , new StackInfo(5, 5)
        );
        Integer[] items = IntStream.rangeClosed(1, 10).boxed().toArray(Integer[]::new);
        MultipleStackInArray<Integer> multipleStack = new MultipleStackInArray<>(items, stacks);

        assertEquals(5, multipleStack.getStackCount("first"));
    }

    @Test
    void testPopItem() {
        Map<String, StackInfo> stacks = Map.of(
                "first" , new StackInfo(5, 0),
                "second" , new StackInfo(5, 5)
        );
        Integer[] items = IntStream.rangeClosed(1, 10).boxed().toArray(Integer[]::new);
        MultipleStackInArray<Integer> multipleStack = new MultipleStackInArray<>(items, stacks);

        final Integer poppedItem = multipleStack.pop("first");
        assertEquals(5, poppedItem);
        assertEquals(4, multipleStack.getStackCount("first"));
        assertEquals(6, multipleStack.getItems()[4]);
    }

    @Test
    void testPushItem() {
        Map<String, StackInfo> stacks = Map.of(
                "first" , new StackInfo(4, 0),
                "second" , new StackInfo(5, 4)
        );
        Integer[] items = IntStream.rangeClosed(1, 9).boxed().toArray(Integer[]::new);
        MultipleStackInArray<Integer> multipleStack = new MultipleStackInArray<>(items, stacks, 10);

        final int newItem = 1000;
        multipleStack.push("first", newItem);
        assertEquals(5, multipleStack.getStackCount("first"));
        assertEquals(1000, multipleStack.getItems()[4]);
        assertEquals(5, multipleStack.getItems()[5]);
    }

    @Test
    void testPeekItem() {
        Map<String, StackInfo> stacks = Map.of(
                "first", new StackInfo(4, 0),
                "second", new StackInfo(5, 4)
        );
        Integer[] items = IntStream.rangeClosed(1, 9).boxed().toArray(Integer[]::new);
        MultipleStackInArray<Integer> multipleStack = new MultipleStackInArray<>(items, stacks, 10);

        assertEquals(4, multipleStack.peek("first"));
        assertEquals(9, multipleStack.peek("second"));
    }

    @Test
    void testWithEmptyFirstStack() {
        Map<String, StackInfo> stacks = Map.of(
                "first", new StackInfo(1, 0),
                "second", new StackInfo(5, 1)
        );
        Integer[] items = IntStream.rangeClosed(1, 6).boxed().toArray(Integer[]::new);
        MultipleStackInArray<Integer> multipleStack = new MultipleStackInArray<>(items, stacks, 10);

        assertEquals(1, multipleStack.getStackCount("first"));
        assertEquals(5, multipleStack.getStackCount("second"));

        multipleStack.pop("first");
        assertEquals(0, multipleStack.getStackInfo("first").getStackStartIndex());
        assertEquals(0, multipleStack.getStackCount("first"));

        multipleStack.push("first", 1);
        assertEquals(1, multipleStack.getStackCount("first"));
        assertEquals(1, multipleStack.getItems()[0]);
        assertEquals(0, multipleStack.getStackInfo("first").getStackStartIndex());
        assertEquals(1, multipleStack.getStackInfo("second").getStackStartIndex());
    }
}
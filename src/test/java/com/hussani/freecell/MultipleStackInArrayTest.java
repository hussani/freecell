package com.hussani.freecell;

import com.hussani.freecell.ds.MultipleStackInArray;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;


class MultipleStackInArrayTest {

    @Test
    void testStructureInitialization() {
        Map<String, com.hussani.freecell.ds.StackInfo> stacks = Map.of(
                "first" , new com.hussani.freecell.ds.StackInfo(5, 0),
                "second" , new com.hussani.freecell.ds.StackInfo(5, 5)
        );
        Integer[] items = IntStream.range(0, 10).boxed().toArray(Integer[]::new);
        MultipleStackInArray<Integer> multipleStackInArray = new MultipleStackInArray<>(items, stacks);

        assertEquals(2, multipleStackInArray.getStacksCount());
        assertEquals(10, multipleStackInArray.getItems().length);
    }
}
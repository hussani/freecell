package com.hussani.freecell.ds;

import java.util.Arrays;
import java.util.Map;

public class MultipleStackInArray<T> {

    public enum Stacks { GAME_1, GAME_2, GAME_3, GAME_4, GAME_5, GAME_6, GAME_7, GAME_8, OUT }

    private final T[] items;

    private final Map<String, StackInfo> stacks;

    public MultipleStackInArray(T[] items, Map<String, StackInfo> stacks) {
        this.items = items;

        this.stacks = stacks;
    }

    public T[] getItems() {
        // prevent modification of the array
        return Arrays.copyOf(this.items, this.items.length);
    }

    public int getStacksCount() {
        return this.stacks.size();
    }

    public T pop(String stack) {
        StackInfo info = stacks.get(stack);
        if (info.getStackSize() == 0) {
            throw new IllegalStateException("Stack is empty");
        }
        int position = info.getStackStartIndex() + info.getStackSize() - 1;
        final T card = this.items[position];
        this.items[position] = null;
        info.decrementStackSize();
        // todo: shift cards to the left
        return card;
    }

    public void print() {
        this.stacks.forEach((key, value) -> System.out.println(getStackWithItems(key.toString())));
    }

    private String getStackWithItems(String stack) {
        StackInfo info = stacks.get(stack);
        return stack + ": " + Arrays.toString(Arrays.copyOfRange(
                this.getItems(), info.getStackStartIndex(), info.getStackStartIndex() + info.getStackSize()));
    }
}

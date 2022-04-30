package com.hussani.freecell;

import java.util.Arrays;
import java.util.Map;

public class MultipleStackInArray<T> {

    public enum Stacks { GAME_1, GAME_2, GAME_3, GAME_4, GAME_5, GAME_6, GAME_7, GAME_8, OUT }

    private final T[] items;

    private final Map<Stacks, StackInfo> stacks;

    public MultipleStackInArray(T[] items) {
        this.items = items;

        this.stacks = Map.of(
                Stacks.GAME_1, new StackInfo(7, 0),
                Stacks.GAME_2, new StackInfo(7, 7),
                Stacks.GAME_3, new StackInfo(7, 13),
                Stacks.GAME_4, new StackInfo(7, 20),
                Stacks.GAME_5, new StackInfo(6, 27),
                Stacks.GAME_6, new StackInfo(6, 33),
                Stacks.GAME_7, new StackInfo(6, 39),
                Stacks.GAME_8, new StackInfo(6, 45),
                Stacks.OUT, new StackInfo(0, 51)
        );
    }

    public T[] getItems() {
        return this.items;
    }

    public T pop(Stacks stack) {
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
        this.stacks.forEach((key, value) -> System.out.println(getStackWithItems(key)));
    }

    private String getStackWithItems(Stacks stack) {
        StackInfo info = stacks.get(stack);
        return stack.toString() + ": " + Arrays.toString(Arrays.copyOfRange(
                this.getItems(), info.getStackStartIndex(), info.getStackStartIndex() + info.getStackSize()));
    }


    private class StackInfo {
        private int stackSize;
        private int stackStartIndex;

        public StackInfo(int stackSize, int stackStartIndex) {
            this.stackSize = stackSize;
            this.stackStartIndex = stackStartIndex;
        }

        public int getStackSize() {
            return stackSize;
        }

        public int getStackStartIndex() {
            return stackStartIndex;
        }

        public void decrementStackSize() {
            stackSize--;
        }

        @Override
        public String toString() {
            return "StackInfo{" +
                    "stackSize=" + stackSize +
                    ", stackStartIndex=" + stackStartIndex +
                    '}';
        }
    }
}

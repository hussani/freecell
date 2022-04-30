package com.hussani.freecell.ds;

public class StackInfo {
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

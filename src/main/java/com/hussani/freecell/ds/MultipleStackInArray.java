package com.hussani.freecell.ds;

import java.util.Arrays;
import java.util.Map;

public class MultipleStackInArray<T> {


    private final T[] items;

    private final int itemCapacity;

    private int emptyPositionsCount;

    private final Map<String, StackInfo> stacks;

    public MultipleStackInArray(T[] items, Map<String, StackInfo> stacks) {
        this.items = items;
        this.itemCapacity = items.length;
        this.emptyPositionsCount = 0;
        this.stacks = stacks;
    }

    public MultipleStackInArray(T[] items, Map<String, StackInfo> stacks, int itemCapacity) {
        this.items = Arrays.copyOf(items, itemCapacity); // creates a new array with the same elements and new capacity
        this.itemCapacity = itemCapacity;
        this.emptyPositionsCount = itemCapacity - items.length;
        this.stacks = stacks;
    }

    public T[] getItems() {
        // prevent modification of the array
        return Arrays.copyOf(this.items, this.items.length);
    }

    public int getStacksCount() {
        return this.stacks.size();
    }

    public int getStackCount(String stack) {
        return getStackInfo(stack).getStackSize();
    }


    public T pop(String stack) {
        StackInfo info = getStackInfo(stack);
        if (info.getStackSize() == 0) {
            throw new IllegalStateException("Stack is empty");
        }
        int position = info.getStackStartIndex() + info.getStackSize() - 1;
        final T item = this.items[position];
        this.items[position] = null;
        info.decrementStackSize();
        moveItemsToTheLeft(position);
        decrementIndexOfAffectedStacks(position, stack);
        this.emptyPositionsCount++;
        return item;
    }

    public void push(String stack, T newItem) {
        checkItemCapacity();
        StackInfo info = getStackInfo(stack);
        int position = info.getStackStartIndex() + info.getStackSize();
        moveItemsToTheRight(position);
        incrementIndexOfAffectedStacks(position, stack);
        this.items[position] = newItem;
        info.incrementStackSize();
        this.emptyPositionsCount--;
    }

    public T peek(String stack) {
        StackInfo info = getStackInfo(stack);
        return this.items[info.getStackStartIndex() + info.getStackSize() - 1];
    }

    private void incrementIndexOfAffectedStacks(final int position, final String triggeringStack) {
        stacks.forEach((stackName, stackInfo) -> {
            if (stackInfo.getStackStartIndex() >= position && !stackName.equals(triggeringStack)) {  // if the stack is affected
                stackInfo.incrementStackStartIndex();
        }});
    }

    private void decrementIndexOfAffectedStacks(final int position, final String triggeringStack) {
        stacks.forEach((stackName, stackInfo) -> {
            if (stackInfo.getStackStartIndex() >= position && !stackName.equals(triggeringStack)) {  // if the stack is affected
                stackInfo.decrementStackStartIndex();
            }});
    }

    private void moveItemsToTheRight(int position) {
        for (int i = this.items.length - 1; i > position; i--) {
            exchangeItems(i, i - 1);
        }
    }

    private void moveItemsToTheLeft(int position) {
        for (int i = position; i < this.items.length - 1; i++) {
            exchangeItems(i, i + 1);
        }
    }

    private void exchangeItems(int position, int position2) {
        T temp = this.items[position];
        this.items[position] = this.items[position2];
        this.items[position2] = temp;
    }

    public void print() {
        this.stacks.forEach((key, value) -> System.out.println(getStackWithItems(key)));
    }

    StackInfo getStackInfo(String stack) {
        checkStackExists(stack);
        return stacks.get(stack);
    }

    private void checkItemCapacity() {
        if (emptyPositionsCount == 0) {
            throw new IllegalStateException("No more space to add items");
        }
    }
    private void checkStackExists(String stack) {
        if (!stacks.containsKey(stack)) {
            throw new IllegalArgumentException("Stack does not exist");
        }
    }

    private String getStackWithItems(String stack) {
        StackInfo info = stacks.get(stack);
        return stack + ": " + Arrays.toString(Arrays.copyOfRange(
                this.getItems(), info.getStackStartIndex(), info.getStackStartIndex() + info.getStackSize()));
    }
}

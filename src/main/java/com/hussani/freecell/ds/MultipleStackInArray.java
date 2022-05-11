package com.hussani.freecell.ds;

import java.util.Arrays;
import java.util.Map;

public class MultipleStackInArray<T> {


    private final T[] items;

    private int emptyPositionsCount;

    private final Map<String, StackInfo> stacks;

    public MultipleStackInArray(T[] items, Map<String, StackInfo> stacks) {
        this.items = items;
        this.emptyPositionsCount = 0;
        this.stacks = stacks;
    }

    public MultipleStackInArray(T[] items, Map<String, StackInfo> stacks, int itemCapacity) {
        this.items = Arrays.copyOf(items, itemCapacity); // creates a new array with the same elements and new capacity
        this.emptyPositionsCount = itemCapacity - items.length;
        this.stacks = stacks;
    }

    public T[] getItems() {
        // prevent modification of the array
        return Arrays.copyOf(this.items, this.items.length);
    }

    public int getEmptyPositionsCount() {
        return emptyPositionsCount;
    }

    public int getStacksCount() {
        return this.stacks.size();
    }

    public String[] getStackNames() {
        return this.stacks.keySet().toArray(new String[this.stacks.size()]);
    }

    public int getStackItemCount(String stack) {
        return getStackInfo(stack).getStackSize();
    }

    /**
     * Similar to iterate over items [stackStartIndex..(stackStartIndex + stackSize)
     *   then copy to a new array.
     * @param stack the stack name
     * @return array of items in the stack
     */
    public T[] getStackItems(String stack) {
        StackInfo info = getStackInfo(stack);
        return Arrays.copyOfRange(this.items, info.getStackStartIndex(),
                info.getStackStartIndex() + info.getStackSize());
    }

    /**
     * Remove and return the item of the top of the stack.
     * This method changes the array of all items. So move items from to the left (current position - 1).
     * All the empty positions are filled with null at the end of array.
     * @param stack Stack name
     * @return T
     */
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

    /**
     * And an item on the top of the stack.
     * This method changes the array of all items. So move items from to the right (current position + 1)
     *   to get the space to the new item.
     * All the empty positions are filled with null at the end of array.
     * @param stack Stack name
     * @return T
     */
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

    public boolean isFull() {
        return this.emptyPositionsCount == 0;
    }

    public boolean isEmpty() {
        return this.emptyPositionsCount == this.items.length;
    }

    public boolean isStackEmpty(String stack) {
        return getStackInfo(stack).getStackSize() == 0;
    }

    /**
     * Return the item of the top of the stack without removing it.
     */
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

    /**
     * TODO: move from the first empty position to the current position.
     */
    private void moveItemsToTheRight(int position) {
        for (int i = this.items.length - 1; i > position; i--) {
            exchangeItems(i, i - 1);
        }
    }

    /**
     * TODO: move from the current position until the first empty position.
     */
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

    StackInfo getStackInfo(String stack) {
        checkStackExists(stack);
        return stacks.get(stack);
    }

    private void checkItemCapacity() {
        if (isFull()) {
            throw new IllegalStateException("No more space to add items");
        }
    }
    private void checkStackExists(String stack) {
        if (!stacks.containsKey(stack)) {
            throw new IllegalStateException("Stack does not exist");
        }
    }
}

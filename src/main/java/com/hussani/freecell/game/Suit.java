package com.hussani.freecell.game;

public enum Suit {
    CLUBS, DIAMONDS, HEARTS, SPADES;
    public enum Color { RED, BLACK}

    public Color getColor() {
        if (this.isRed()) {
            return Color.RED;
        }
        return Color.BLACK;
    }

    public Boolean isBlack() {
        return this.equals(CLUBS) || this.equals(SPADES);
    }

    public Boolean isRed() {
        return !this.isBlack();
    }
}

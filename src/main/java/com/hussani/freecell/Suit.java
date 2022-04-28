package com.hussani.freecell;

public enum Suit {
    CLUBS, DIAMONDS, HEARTS, SPADES;

    public Boolean isBlack() {
        return this.equals(CLUBS) || this.equals(DIAMONDS);
    }

    public Boolean isRed() {
        return !this.isBlack();
    }
}

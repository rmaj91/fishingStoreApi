package com.rmaj91.fishingstoreapi.store.model;

public enum ItemRate {
    BAD(1),
    POOR(2),
    OK(3),
    COOL(4),
    EXCELLENT(5);

    private final int rate;

    ItemRate(int rate) {
        this.rate = rate;
    }

    public int getRate() {
        return rate;
    }
}

package com.coffee.magic_machine.domain.entity;

public enum CoffeeType {
    ESPRESSO(8, 5, 0),
    AMERICANO(8, 10, 0),
    CAPPUCCINO(8, 10, 16);

    private final int waterPercentage;
    private final int beansPercentage;
    private final int milkPercentage;

    CoffeeType(int waterPercentage, int beansPercentage, int milkPercentage) {
        this.waterPercentage = waterPercentage;
        this.beansPercentage = beansPercentage;
        this.milkPercentage = milkPercentage;
    }

    public int getWaterPercentage() {
        return waterPercentage;
    }

    public int getBeansPercentage() {
        return beansPercentage;
    }

    public int getMilkPercentage() {
        return milkPercentage;
    }
}


package com.coffee.magic_machine.domain.dto;

import lombok.Data;

@Data
public class CoffeeMachineResponse {
    private int waterPercentage;
    private int beansPercentage;
    private int milkPercentage;
}

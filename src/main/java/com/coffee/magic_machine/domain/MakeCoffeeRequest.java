package com.coffee.magic_machine.domain;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class MakeCoffeeRequest {
    private Long coffeeMachineId;
    private CoffeeType coffeeType;
}

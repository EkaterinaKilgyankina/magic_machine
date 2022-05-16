package com.coffee.magic_machine.dto;

import com.coffee.magic_machine.domain.CoffeeType;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class MakeCoffeeRequest {
    private Long coffeeMachineId;
    private CoffeeType coffeeType;
}

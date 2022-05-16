package com.coffee.magic_machine.service;

import com.coffee.magic_machine.domain.CoffeeMachine;
import com.coffee.magic_machine.domain.IngredientType;

public interface CoffeeMachinesService {

    CoffeeMachine addIngredient(Long id, IngredientType type);

}

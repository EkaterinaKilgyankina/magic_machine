package com.coffee.magic_machine.service;

import com.coffee.magic_machine.domain.entity.CoffeeMachine;
import com.coffee.magic_machine.domain.entity.IngredientType;

public interface CoffeeMachinesService {

    CoffeeMachine addIngredient(Long id, IngredientType type);

}

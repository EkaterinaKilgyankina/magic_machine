package com.coffee.magic_machine.controller;

import com.coffee.magic_machine.domain.entity.CoffeeMachine;
import com.coffee.magic_machine.domain.entity.IngredientType;
import com.coffee.magic_machine.domain.dto.CoffeeMachineResponse;
import com.coffee.magic_machine.mapper.CoffeeMachineMapper;
import com.coffee.magic_machine.service.CoffeeMachinesService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/coffee-machines")
public class CoffeeMachinesController {
    private final CoffeeMachinesService maintainService;
    private final CoffeeMachineMapper mapper;

    @PutMapping("/{id}/ingredients")
    public CoffeeMachineResponse addIngredient(@PathVariable Long id,
                                               @RequestParam IngredientType ingredientType) {
        final CoffeeMachine coffeeMachine = maintainService.addIngredient(id, ingredientType);
        return mapper.toDto(coffeeMachine);
    }
}
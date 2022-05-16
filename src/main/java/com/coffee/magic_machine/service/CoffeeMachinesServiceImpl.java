package com.coffee.magic_machine.service;

import com.coffee.magic_machine.domain.CoffeeMachine;
import com.coffee.magic_machine.domain.IngredientType;
import com.coffee.magic_machine.exception.NotFoundException;
import com.coffee.magic_machine.repository.CoffeeMachineRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CoffeeMachinesServiceImpl implements CoffeeMachinesService {
    private static final int FULL_AMOUNT = 100;
    private final CoffeeMachineRepository repository;

    @Override
    public CoffeeMachine addIngredient(Long id, IngredientType type) {
        CoffeeMachine coffeeMachine = repository.findById(id)
                    .orElseThrow(() -> new NotFoundException("coffee machine not found with id - " + id));

        switch (type) {
            case WATER:
                coffeeMachine.setWaterPercentage(FULL_AMOUNT);
                break;

            case BEANS:
                coffeeMachine.setBeansPercentage(FULL_AMOUNT);
                break;

            case MILK:
                coffeeMachine.setMilkPercentage(FULL_AMOUNT);
                break;
        }
        return repository.save(coffeeMachine);
    }
}
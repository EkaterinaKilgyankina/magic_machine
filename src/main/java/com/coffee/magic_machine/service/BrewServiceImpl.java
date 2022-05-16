package com.coffee.magic_machine.service;

import com.coffee.magic_machine.domain.*;
import com.coffee.magic_machine.dto.MakeCoffeeRequest;
import com.coffee.magic_machine.exception.IngredientLackException;
import com.coffee.magic_machine.exception.NotAvailableException;
import com.coffee.magic_machine.exception.NotFoundException;
import com.coffee.magic_machine.repository.CoffeeMachineRepository;
import com.coffee.magic_machine.repository.CoffeeStatisticsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.concurrent.locks.ReentrantLock;

@Service
@AllArgsConstructor
public class BrewServiceImpl implements BrewingService {
    private final static ReentrantLock LOCK = new ReentrantLock();
    private final CoffeeMachineRepository coffeeMachineRepository;
    private final CoffeeStatisticsRepository coffeeStatisticsRepository;

    @Override
    public CoffeeStatistic brew(MakeCoffeeRequest request) {
        final boolean isSuccess = LOCK.tryLock();
        if (!isSuccess) {
            throw new NotAvailableException("machine is working now");
        }

        final CoffeeType coffeeType = request.getCoffeeType();
        try {
            CoffeeMachine coffeeMachine = coffeeMachineRepository.findById(request.getCoffeeMachineId())
                    .orElseThrow(() -> new NotFoundException("coffee machine not found with id - " + request.getCoffeeMachineId()));

            checkIngredients(coffeeMachine, coffeeType);

            coffeeMachine.setWaterPercentage(coffeeMachine.getWaterPercentage() - coffeeType.getWaterPercentage());
            coffeeMachine.setBeansPercentage(coffeeMachine.getBeansPercentage() - coffeeType.getBeansPercentage());
            coffeeMachine.setMilkPercentage(coffeeMachine.getMilkPercentage() - coffeeType.getMilkPercentage());
            coffeeMachineRepository.save(coffeeMachine);

            CoffeeStatistic coffeeStatistic = new CoffeeStatistic();
            coffeeStatistic.setType(coffeeType);
            coffeeStatistic.setCoffeeMachineId(coffeeMachine.getId());
            return coffeeStatisticsRepository.save(coffeeStatistic);
        } finally {
            LOCK.unlock();
        }
    }

    private void checkIngredients(CoffeeMachine coffeeMachine, CoffeeType coffeeType) {
        if (coffeeMachine.getWaterPercentage() < coffeeType.getWaterPercentage()) {
            throw new IngredientLackException(IngredientType.WATER);
        }

        if (coffeeMachine.getBeansPercentage() < coffeeType.getBeansPercentage()) {
            throw new IngredientLackException(IngredientType.BEANS);
        }

        if (coffeeMachine.getMilkPercentage() < coffeeType.getMilkPercentage()) {
            throw new IngredientLackException(IngredientType.MILK);
        }
    }
}
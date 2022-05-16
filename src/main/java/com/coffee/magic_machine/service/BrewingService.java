package com.coffee.magic_machine.service;

import com.coffee.magic_machine.domain.CoffeeStatistic;
import com.coffee.magic_machine.domain.MakeCoffeeRequest;

public interface BrewingService {

     CoffeeStatistic brew(MakeCoffeeRequest request);

}

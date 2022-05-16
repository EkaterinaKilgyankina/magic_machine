package com.coffee.magic_machine.service;

import com.coffee.magic_machine.domain.CoffeeStatistic;
import com.coffee.magic_machine.dto.MakeCoffeeRequest;

public interface BrewingService {

     CoffeeStatistic brew(MakeCoffeeRequest request);

}

package com.coffee.magic_machine.service;

import com.coffee.magic_machine.domain.entity.CoffeeStatistic;
import com.coffee.magic_machine.domain.dto.MakeCoffeeRequest;

public interface BrewService {

     CoffeeStatistic brew(MakeCoffeeRequest request);

}

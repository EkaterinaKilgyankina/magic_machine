package com.coffee.magic_machine.controller;

import com.coffee.magic_machine.domain.CoffeeStatistic;
import com.coffee.magic_machine.domain.MakeCoffeeRequest;
import com.coffee.magic_machine.dto.CoffeeStatisticResponse;
import com.coffee.magic_machine.mapper.CoffeeStatisticMapper;
import com.coffee.magic_machine.service.BrewingService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/coffees")
public class BrewingController {
    BrewingService brewingService;
    CoffeeStatisticMapper mapper;

    @PostMapping
    public CoffeeStatisticResponse getCupOfCoffee(@RequestBody MakeCoffeeRequest request) {
        final CoffeeStatistic coffeeStatistic = brewingService.brew(request);
        return mapper.toDto(coffeeStatistic);
    }
}

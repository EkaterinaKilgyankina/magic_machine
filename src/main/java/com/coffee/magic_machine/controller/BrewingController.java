package com.coffee.magic_machine.controller;

import com.coffee.magic_machine.domain.entity.CoffeeStatistic;
import com.coffee.magic_machine.domain.dto.MakeCoffeeRequest;
import com.coffee.magic_machine.domain.dto.CoffeeStatisticResponse;
import com.coffee.magic_machine.mapper.CoffeeStatisticMapper;
import com.coffee.magic_machine.service.BrewService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/coffees")
public class BrewingController {
    private final BrewService brewService;
    private final CoffeeStatisticMapper mapper;

    @PostMapping
    public CoffeeStatisticResponse getCupOfCoffee(@RequestBody MakeCoffeeRequest request) {
        final CoffeeStatistic coffeeStatistic = brewService.brew(request);
        return mapper.toDto(coffeeStatistic);
    }
}

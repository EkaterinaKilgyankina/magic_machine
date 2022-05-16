package com.coffee.magic_machine.mapper;

import com.coffee.magic_machine.domain.entity.CoffeeMachine;
import com.coffee.magic_machine.domain.dto.CoffeeMachineResponse;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface CoffeeMachineMapper {

    CoffeeMachineResponse toDto (CoffeeMachine coffeeMachine);
}

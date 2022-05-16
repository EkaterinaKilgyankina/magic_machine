package com.coffee.magic_machine.mapper;

import com.coffee.magic_machine.domain.CoffeeMachine;
import com.coffee.magic_machine.dto.CoffeeMachineResponse;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface CoffeeMachineMapper {

    CoffeeMachineResponse toDto (CoffeeMachine coffeeMachine);

}

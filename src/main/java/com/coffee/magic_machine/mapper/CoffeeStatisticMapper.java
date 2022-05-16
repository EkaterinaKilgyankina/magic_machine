package com.coffee.magic_machine.mapper;

import com.coffee.magic_machine.domain.CoffeeStatistic;
import com.coffee.magic_machine.dto.CoffeeStatisticResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CoffeeStatisticMapper {

    CoffeeStatisticResponse toDto(CoffeeStatistic coffeeStatistic);
}

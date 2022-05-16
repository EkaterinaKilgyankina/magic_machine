package com.coffee.magic_machine.mapper;

import com.coffee.magic_machine.domain.entity.CoffeeStatistic;
import com.coffee.magic_machine.domain.dto.CoffeeStatisticResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CoffeeStatisticMapper {

    CoffeeStatisticResponse toDto(CoffeeStatistic coffeeStatistic);
}

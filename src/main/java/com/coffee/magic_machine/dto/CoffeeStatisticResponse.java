package com.coffee.magic_machine.dto;

import com.coffee.magic_machine.domain.CoffeeType;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class CoffeeStatisticResponse {
    private LocalDateTime date;
    private CoffeeType type;
}

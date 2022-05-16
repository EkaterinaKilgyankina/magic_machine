package com.coffee.magic_machine.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Data
@Accessors(chain = true)
public class CoffeeStatistic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private long coffeeMachineId;
    private LocalDateTime date = LocalDateTime.now();
    private CoffeeType type;
}

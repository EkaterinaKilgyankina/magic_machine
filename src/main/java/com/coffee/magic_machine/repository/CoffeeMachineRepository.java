package com.coffee.magic_machine.repository;

import com.coffee.magic_machine.domain.CoffeeMachine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoffeeMachineRepository extends JpaRepository<CoffeeMachine, Long> {
}

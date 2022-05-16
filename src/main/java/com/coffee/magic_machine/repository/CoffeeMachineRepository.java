package com.coffee.magic_machine.repository;

import com.coffee.magic_machine.domain.entity.CoffeeMachine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoffeeMachineRepository extends JpaRepository<CoffeeMachine, Long> {
}

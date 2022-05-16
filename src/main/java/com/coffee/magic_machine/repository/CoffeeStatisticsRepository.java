package com.coffee.magic_machine.repository;

import com.coffee.magic_machine.domain.CoffeeStatistic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoffeeStatisticsRepository extends JpaRepository<CoffeeStatistic, Long> {
}

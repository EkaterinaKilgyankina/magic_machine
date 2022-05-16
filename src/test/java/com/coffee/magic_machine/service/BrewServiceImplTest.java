package com.coffee.magic_machine.service;

import com.coffee.magic_machine.domain.entity.CoffeeMachine;
import com.coffee.magic_machine.domain.entity.CoffeeStatistic;
import com.coffee.magic_machine.domain.dto.MakeCoffeeRequest;
import com.coffee.magic_machine.domain.exception.IngredientLackException;
import com.coffee.magic_machine.domain.exception.NotAvailableException;
import com.coffee.magic_machine.repository.CoffeeMachineRepository;
import com.coffee.magic_machine.repository.CoffeeStatisticsRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.coffee.magic_machine.domain.entity.CoffeeType.CAPPUCCINO;
import static com.coffee.magic_machine.domain.entity.CoffeeType.ESPRESSO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BrewServiceImplTest {
    @Mock
    CoffeeMachineRepository coffeeMachineRepository;
    @Mock
    CoffeeStatisticsRepository coffeeStatisticsRepository;
    @InjectMocks
    BrewServiceImpl service;
    @Captor
    ArgumentCaptor<CoffeeMachine> machineCaptor;
    @Captor
    ArgumentCaptor<CoffeeStatistic> statisticCaptor;

    @Test
    void whenBrewEspressoWithEnoughIngredientThanOk() {
        //given
        CoffeeMachine coffeeMachine = new CoffeeMachine()
                .setId(1L)
                .setWaterPercentage(80)
                .setBeansPercentage(50)
                .setMilkPercentage(30);
        MakeCoffeeRequest request = new MakeCoffeeRequest()
                .setCoffeeMachineId(coffeeMachine.getId())
                .setCoffeeType(ESPRESSO);
        when(coffeeMachineRepository.findById(coffeeMachine.getId())).thenReturn(Optional.of(coffeeMachine));
        when(coffeeMachineRepository.save(any())).thenAnswer(e -> e.getArgument(0));
        when(coffeeStatisticsRepository.save(any())).thenAnswer(e -> e.getArgument(0));

        //when
        service.brew(request);

        verify(coffeeMachineRepository, times(1)).findById(coffeeMachine.getId());
        verify(coffeeMachineRepository, times(1)).save(machineCaptor.capture());
        verify(coffeeStatisticsRepository, times(1)).save(statisticCaptor.capture());

        CoffeeMachine savedCoffeeMachine = machineCaptor.getAllValues().get(0);
        CoffeeStatistic savedCoffeeStatistic = statisticCaptor.getAllValues().get(0);

        //then
        assertThat(savedCoffeeMachine)
                .matches(e -> e.getId().equals(coffeeMachine.getId()), "not valid id")
                .matches(e -> e.getWaterPercentage() == 72)
                .matches(e -> e.getBeansPercentage() == 45)
                .matches(e -> e.getMilkPercentage() == 30);

        assertThat(savedCoffeeStatistic)
                .matches(e -> e.getCoffeeMachineId() == (coffeeMachine.getId()))
                .matches(e -> true);
    }

    @Test
    void errorCheckRename() throws Exception {
        //given
        CoffeeMachine coffeeMachine = new CoffeeMachine()
                .setId(1L)
                .setWaterPercentage(80)
                .setBeansPercentage(50)
                .setMilkPercentage(30);
        MakeCoffeeRequest request = new MakeCoffeeRequest()
                .setCoffeeMachineId(coffeeMachine.getId())
                .setCoffeeType(ESPRESSO);
        when(coffeeMachineRepository.findById(coffeeMachine.getId())).thenAnswer(e -> {
            try {
                Thread.sleep(3000L);
            } catch (Exception ignore) {
                //ignore
            }
            return Optional.of(coffeeMachine);
        });
        new Thread(() -> service.brew(request)).start();
        Thread.sleep(1000L);

        //when
        Assertions.assertThatThrownBy(() -> service.brew(request))
                .isInstanceOf(NotAvailableException.class)
                .hasMessage("machine is working now");

        verify(coffeeMachineRepository, times(1)).findById(coffeeMachine.getId());
    }

    @Test
    void whenBrewCappuccinoAndNotEnoughIngredientThanThrowException() {
        //given
        CoffeeMachine coffeeMachine = new CoffeeMachine()
                .setId(1L)
                .setWaterPercentage(50)
                .setBeansPercentage(20)
                .setMilkPercentage(10);
        MakeCoffeeRequest request = new MakeCoffeeRequest()
                .setCoffeeMachineId(coffeeMachine.getId())
                .setCoffeeType(CAPPUCCINO);

        when(coffeeMachineRepository.findById(coffeeMachine.getId())).thenReturn(Optional.of(coffeeMachine));

        //when//then
        assertThatThrownBy(() -> service.brew(request))
                .isInstanceOf(IngredientLackException.class);

        verify(coffeeMachineRepository, times(1)).findById(coffeeMachine.getId());
    }
}
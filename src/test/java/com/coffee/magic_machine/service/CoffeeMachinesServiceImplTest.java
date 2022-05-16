package com.coffee.magic_machine.service;

import com.coffee.magic_machine.domain.entity.CoffeeMachine;
import com.coffee.magic_machine.domain.exception.NotFoundException;
import com.coffee.magic_machine.repository.CoffeeMachineRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.coffee.magic_machine.domain.entity.IngredientType.WATER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CoffeeMachinesServiceImplTest {
    @Mock
    CoffeeMachineRepository repository;
    @InjectMocks
    CoffeeMachinesServiceImpl service;
    @Captor
    ArgumentCaptor<CoffeeMachine> argumentCaptor;

    @Test
    void whenAddIngredientAndMachineIsNotFoundThenThrowException() {
        //given
        when(repository.findById(1L)).thenReturn(Optional.empty());

        //when//then
        assertThatThrownBy(() -> service.addIngredient(1L, WATER))
                .isInstanceOf(NotFoundException.class)
                .hasMessageMatching("coffee machine not found with id - 1");

        verify(repository, times(1)).findById(1L);
    }

    @Test
    void whenAddIngredientAndLimitIsNotExceedThenOk() {
        //given
        CoffeeMachine coffeeMachine = new CoffeeMachine()
                .setId(1L)
                .setWaterPercentage(30);

        when(repository.findById(1L)).thenReturn(Optional.of(coffeeMachine));

        //when
        service.addIngredient(coffeeMachine.getId(), WATER);

        verify(repository, times(1)).findById(coffeeMachine.getId());
        verify(repository, times(1)).save(argumentCaptor.capture());

        CoffeeMachine savedCoffeeMachine = argumentCaptor.getAllValues().get(0);

        //then
        assertThat(savedCoffeeMachine)
                .matches(e -> e.getId().equals(coffeeMachine.getId()))
                .matches(e -> e.getWaterPercentage() == 100);
    }
}
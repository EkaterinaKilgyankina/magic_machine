package com.coffee.magic_machine.controller;

import com.coffee.magic_machine.domain.entity.CoffeeMachine;
import com.coffee.magic_machine.domain.entity.IngredientType;
import com.coffee.magic_machine.mapper.CoffeeMachineMapper;
import com.coffee.magic_machine.service.CoffeeMachinesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        CoffeeMachinesControllerTest.Config.class,
        CoffeeMachinesController.class
})
public class CoffeeMachinesControllerTest {
    @Autowired
    CoffeeMachinesController controller;
    private MockMvc mockMvc;
    @MockBean
    private CoffeeMachinesService coffeeMachinesService;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .setControllerAdvice(new RestResponseExceptionHandler())
                .build();
    }

    @Test
    void whenAddIngredientThenOk() throws Exception {
        //given
        CoffeeMachine coffeeMachine = new CoffeeMachine()
                .setId(1L)
                .setBeansPercentage(100)
                .setWaterPercentage(70)
                .setMilkPercentage(50);
        //when
        when(coffeeMachinesService.addIngredient(1l, IngredientType.BEANS)).thenReturn(coffeeMachine);

        //then
        mockMvc.perform(MockMvcRequestBuilders.put("/coffee-machines/1/ingredients?ingredientType=BEANS")
                .contentType(APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.beansPercentage").value(coffeeMachine.getBeansPercentage()))
                .andExpect(jsonPath("$.milkPercentage").value(coffeeMachine.getMilkPercentage()))
                .andExpect(jsonPath("$.waterPercentage").value(coffeeMachine.getWaterPercentage()));

    }

    @Test
    void whenAddIngredientAndInvalidParamThenBadRequest() throws Exception {
        //given//when//then
        mockMvc.perform(MockMvcRequestBuilders.put("/coffee-machines/1/ingredients?ingredientType=BEANS1")
                .contentType(APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    static class Config {
        @Bean
        CoffeeMachineMapper mapper() {
            return Mappers.getMapper(CoffeeMachineMapper.class);
        }

    }
}
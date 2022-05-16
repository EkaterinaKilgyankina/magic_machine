package com.coffee.magic_machine.controller;

import com.coffee.magic_machine.domain.CoffeeStatistic;
import com.coffee.magic_machine.domain.CoffeeType;
import com.coffee.magic_machine.domain.MakeCoffeeRequest;
import com.coffee.magic_machine.mapper.CoffeeStatisticMapper;
import com.coffee.magic_machine.service.BrewingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        BrewingControllerTest.Config.class,
        BrewingController.class
})
public class BrewingControllerTest {
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    BrewingController controller;
    private MockMvc mockMvc;
    @MockBean
    private BrewingService brewingService;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .setControllerAdvice(new RestResponseExceptionHandler())
                .build();
    }

    @Test
    void whenCreateCoffeeCupThenOk() throws Exception {
        //given
        MakeCoffeeRequest request = new MakeCoffeeRequest()
                .setCoffeeType(CoffeeType.ESPRESSO)
                .setCoffeeMachineId(1L);

        CoffeeStatistic coffeeStatistic = new CoffeeStatistic()
                .setType(CoffeeType.ESPRESSO);

        //when
        when(brewingService.brew(request)).thenReturn(coffeeStatistic);

        //then
        mockMvc.perform(post("/coffees")
                .contentType(APPLICATION_JSON_VALUE)
                .content(asJsonString(request)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.date").isNotEmpty())
                .andExpect(jsonPath("$.type").value("ESPRESSO"));
    }

    public String asJsonString(final Object obj) {
        try {
            return objectMapper.registerModule(new JavaTimeModule()).writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    static class Config {
        @Bean
        CoffeeStatisticMapper mapper() {
            return Mappers.getMapper(CoffeeStatisticMapper.class);
        }

    }
}


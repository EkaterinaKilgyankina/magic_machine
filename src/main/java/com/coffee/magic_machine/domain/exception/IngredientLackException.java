package com.coffee.magic_machine.domain.exception;

import com.coffee.magic_machine.domain.entity.IngredientType;
import lombok.Getter;

@Getter
public class IngredientLackException extends RuntimeException {
    private final IngredientType ingredientType;

    public IngredientLackException(IngredientType ingredientType) {
        super();
        this.ingredientType = ingredientType;
    }
}

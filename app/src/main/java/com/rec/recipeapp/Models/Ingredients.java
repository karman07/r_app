package com.rec.recipeapp.Models;

public class Ingredients {
    private String nameOfIngredient;
    private String quantityOfIngredient;


    public String getNameOfIngredient() {
        return nameOfIngredient;
    }

    public void setNameOfIngredient(String nameOfIngredient) {
        this.nameOfIngredient = nameOfIngredient;
    }

    public String getQuantityOfIngredient() {
        return quantityOfIngredient;
    }

    public void setQuantityOfIngredient(String quantityOfIngredient) {
        this.quantityOfIngredient = quantityOfIngredient;
    }

    @Override
    public String toString() {
        return "Ingredients{" +
                "nameOfIngredient='" + nameOfIngredient + '\'' +
                ", quantityOfIngredient='" + quantityOfIngredient + '\'' +
                '}';
    }
}


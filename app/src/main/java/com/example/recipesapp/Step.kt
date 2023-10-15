package com.example.recipesapp

//Step class allows to get the step description, ingredients and equipment.
data class Step(
    val equipment: List<Equipment>,
    val ingredients: List<Ingredient>,
    val length: Length,
    val number: Int,
    val step: String
)
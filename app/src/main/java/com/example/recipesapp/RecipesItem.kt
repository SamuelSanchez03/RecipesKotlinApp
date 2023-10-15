package com.example.recipesapp

//RecipesItem class for managing recipes search by ingredients API call
//The most useful atribute here is the ID, for later API requests
//Title and image can be also be use when the recipe is display
data class RecipesItem(
    val id: Int,
    val image: String,
    val imageType: String,
    val likes: Int,
    val missedIngredientCount: Int,
    val missedIngredients: List<MissedIngredient>,
    val title: String,
    val unusedIngredients: List<Any>,
    val usedIngredientCount: Int,
    val usedIngredients: List<UsedIngredient>
)
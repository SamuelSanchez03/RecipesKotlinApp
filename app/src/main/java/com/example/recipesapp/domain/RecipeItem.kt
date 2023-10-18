package com.example.recipesapp.domain

//RecipesItem class for managing recipes search by ingredients API call
//The most useful atribute here is the ID, for later API requests
//Title and image can be also be use when the recipe is display
data class RecipeItem(
	val id: Int,
	val title: String,
	val image: String,
	val imageType: String,
	val usedIngredientCount: Int,
	val missedIngredientCount: Int,
	val missedIngredients: List<Ingredient>,
	val unusedIngredients: List<Ingredient>,
	val usedIngredients: List<Ingredient>,
	val likes: Int,
)
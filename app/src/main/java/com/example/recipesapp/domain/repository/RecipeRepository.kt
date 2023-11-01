package com.example.recipesapp.domain.repository

import com.example.recipesapp.domain.RecipeItem
import com.example.recipesapp.domain.SearchedIngredient

interface RecipeRepository {
	suspend fun getRecipesByIngredients(ingredients: Map<String, String>): List<RecipeItem>
	suspend fun autocompleteIngredientSearch(query: String): List<SearchedIngredient>
}
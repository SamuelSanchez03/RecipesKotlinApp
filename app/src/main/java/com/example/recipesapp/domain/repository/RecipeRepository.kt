package com.example.recipesapp.domain.repository

import com.example.recipesapp.domain.RecipeItem

interface RecipeRepository {
	suspend fun getRecipesByIngredients(ingredients: Map<String, String>): List<RecipeItem>
}
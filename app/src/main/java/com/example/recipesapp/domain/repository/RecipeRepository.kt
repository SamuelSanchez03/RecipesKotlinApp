package com.example.recipesapp.domain.repository

import com.example.recipesapp.domain.AnalyzedStepsItem
import com.example.recipesapp.domain.RecipeItem
import com.example.recipesapp.domain.SearchedIngredient
import com.example.recipesapp.domain.Summary

interface RecipeRepository {
	suspend fun getRecipesByIngredients(ingredients: Map<String, String>): List<RecipeItem>
	suspend fun autocompleteIngredientSearch(query: String): List<SearchedIngredient>
	suspend fun getSummaryFromRecipeId(id: Int): Summary
	suspend fun getStepsToPrepareRecipeById(id: Int): List<AnalyzedStepsItem>
}
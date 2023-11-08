package com.example.recipesapp.network

import com.example.recipesapp.domain.AnalyzedStepsItem
import com.example.recipesapp.domain.RecipeItem
import com.example.recipesapp.domain.SearchedIngredient
import com.example.recipesapp.domain.Summary
import com.example.recipesapp.domain.api.ApiKey
import com.example.recipesapp.domain.repository.RecipeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RecipeApiRepository @Inject constructor(
	private val api: ApiInterface,
	apiKey: ApiKey
) : RecipeRepository {
	private val key by apiKey
	
	//Function to get a list of recipes that include a set of ingredients
	override suspend fun getRecipesByIngredients(ingredients: Map<String, String>): List<RecipeItem> =
		withContext(Dispatchers.IO) {
			api.getRecipes(key, ingredients).body() ?: emptyList()
		}
	
	/*Function to get a ingredients by complete or partial name it*/
	override suspend fun autocompleteIngredientSearch(query: String): List<SearchedIngredient> =
		withContext(Dispatchers.IO) {
			api.autocompleteIngredientSearch(key, query).body() ?: emptyList()
		}
	
	//Function to get the summary of a specific recipe
	override suspend fun getSummaryFromRecipeId(id: Int): Summary = withContext(Dispatchers.IO) {
		api.getSummary(apiKey = key, id = id).body()!!
	}
	
	//Function to get instructions for a specific recipe
	override suspend fun getStepsToPrepareRecipeById(id: Int): List<AnalyzedStepsItem> =
		withContext(Dispatchers.IO) {
			api.getSteps(apiKey = key, id = id).body() ?: emptyList()
		}
}
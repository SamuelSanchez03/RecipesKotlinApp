package com.example.recipesapp.network

import com.example.recipesapp.domain.AnalyzedStepsItem
import com.example.recipesapp.domain.RecipeItem
import com.example.recipesapp.domain.SearchedIngredient
import com.example.recipesapp.domain.Summary
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface ApiInterface {
	//API key = 42c44861dc284ba5b6cfffb0867a72c5
	//API call for getting recipes according to a list of ingredients
	//queries map requires a string of ingredients separated by comas
	//and a number as string, representing the maximum number of recipes the call will return
	//Visit https://spoonacular.com/food-api/docs#Search-Recipes-by-Ingredients for full documentation
	@GET("recipes/findByIngredients")
	suspend fun getRecipes(
		@Query("apiKey") apiKey: String,
		@QueryMap queries: Map<String, String>
	): Response<List<RecipeItem>>
	
	//API call for getting the summary of a specific recipe
	//ID can be gotten by the previous API call
	//Visit https://spoonacular.com/food-api/docs#Summarize-Recipe for full documentation
	@GET("recipes/{id}/summary")
	suspend fun getSummary(@Path("id") id: Int, @Query("apiKey") apiKey: String): Response<Summary>
	
	//API call for getting the instructions from a specific recipe
	//The instructions include description, equipment and ingredient
	//ID can be gotten by the first API call
	//Visit https://spoonacular.com/food-api/docs#Get-Analyzed-Recipe-Instructions for full documentation
	@GET("recipes/{id}/analyzedInstructions")
	suspend fun getSteps(
		@Path("id") id: Int,
		@Query("apiKey") apiKey: String
	): Response<List<AnalyzedStepsItem>>
	
	@GET("food/ingredients/autocomplete")
	suspend fun autocompleteIngredientSearch(
		@Query("apiKey") key: String,
		@Query("query") ingredient: String
	): Response<List<SearchedIngredient>>
}
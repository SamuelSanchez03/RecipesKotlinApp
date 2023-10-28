package com.example.recipesapp.network

import android.util.Log
import com.example.recipesapp.domain.RecipeItem
import com.example.recipesapp.domain.api.ApiKey
import com.example.recipesapp.domain.repository.RecipeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
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
	
	/*TODO(Remove run blocking statements, should be called from ViewModel coroutine)*/
	//Function to get the summary of a specific recipe
	fun getSummaryByID(id: Int) {
		val response = runBlocking { api.getSummary(apiKey = key, id = id) }
		Log.d("MainActivity", response.body().toString())
	}
	
	//Function to get instructions for a specific recipe
	fun getInstructionsByID(id: Int) {
		val response = runBlocking { api.getSteps(apiKey = key, id = id) }
		Log.d("MainActivity", response.body().toString())
	}
	
	//Function to remove the tags and unnecessary information included in the summary by default
	fun cleanSummary(summary: String): String {
		var output = summary.replace("<b>", "")
		output = output.replace("</b>", "")
		output = output.replace("\\.[^\\.]+<a href=[\\S\\s]+".toRegex(), "")
		output += "."
		return output
	}
}
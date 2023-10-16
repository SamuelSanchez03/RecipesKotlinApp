package com.example.recipesapp.network

import android.util.Log
import com.example.recipesapp.domain.RecipesItem
import com.example.recipesapp.domain.api.ApiKey
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiCalls @Inject constructor(private val api: ApiInterface, private val apiKey: ApiKey) {
	/*TODO(Remove run blocking statements, should be called from ViewModel coroutine)*/
	//Function to get a list of recipes that include a set of ingredients
	fun getRecipesByIngredients(queries: HashMap<String, String>): List<RecipesItem> {
		return runBlocking { api.getRecipes(apiKey.key, queries).body() ?: emptyList() }
	}
	
	//Function to get the summary of a specific recipe
	fun getSummaryByID(id: Int) {
		val response = runBlocking { api.getSummary(apiKey = apiKey.key, id = id) }
		Log.d("MainActivity", response.body().toString())
	}
	
	//Function to get instructions for a specific recipe
	fun getInstructionsByID(id: Int) {
		val response = runBlocking { api.getSteps(apiKey = apiKey.key, id = id) }
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
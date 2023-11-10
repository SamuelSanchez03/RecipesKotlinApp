package com.example.recipesapp.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipesapp.domain.AnalyzedStepsItem
import com.example.recipesapp.domain.RecipeItem
import com.example.recipesapp.domain.SelectableIngredient
import com.example.recipesapp.domain.Summary
import com.example.recipesapp.domain.repository.RecipeRepository
import com.example.recipesapp.ui.state.RecipeSearchState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
typealias RecipeInformation = Pair<Summary, List<AnalyzedStepsItem>>
@HiltViewModel
class RecipesViewModel @Inject constructor(private val recipeRepository: RecipeRepository) :
	ViewModel() {
	
	companion object {
		const val INGREDIENTS = "ingredients"
		const val NUMBER = "number"
	}
	
	private val recipesInformation = mutableMapOf<Int, RecipeInformation>()
	var currentRecipeInformation: RecipeInformation? by mutableStateOf(null)
		private set
	fun clearInformation() {
		currentRecipeInformation = null
	}
	
	private val _currentRecipes: MutableStateFlow<List<RecipeItem>> =
		MutableStateFlow(value = emptyList())
	var searchRecipesState by mutableStateOf(RecipeSearchState.GettingRecipes)
		private set
	val currentRecipes: StateFlow<List<RecipeItem>> = _currentRecipes.asStateFlow()
	val searchedIngredients = mutableStateListOf<SelectableIngredient>()
	val selectedIngredients = mutableStateListOf<SelectableIngredient>()
	var isSearching by mutableStateOf(false)
		private set
	
	init {
		val queries = mapOf(INGREDIENTS to "apples,sugar,flour", NUMBER to "50")
		getRecipesByIngredients(queries)
	}
	
	fun getRecipesByIngredients(ingredients: Map<String, String>) {
		searchRecipesState = RecipeSearchState.GettingRecipes
		viewModelScope.launch {
			_currentRecipes.value = recipeRepository.getRecipesByIngredients(ingredients)
			searchRecipesState = if (currentRecipes.value.isEmpty())
				RecipeSearchState.NotFound
			else
				RecipeSearchState.Success
		}
	}
	
	fun onSearchIngredientQuery(newQuery: String) {
		isSearching = true
		viewModelScope.launch {
			val result = recipeRepository.autocompleteIngredientSearch(newQuery)
			if (searchedIngredients.isNotEmpty()) searchedIngredients.clear()
			searchedIngredients.addAll(result.map { it.toSelectableIngredient() })
			isSearching = false
		}
	}
	
	fun getRecipeInformationById(id: Int) {
		if (recipesInformation.containsKey(id)) {
			currentRecipeInformation = recipesInformation.getValue(id)
			return
		}
		viewModelScope.launch {
			recipesInformation[id] = recipeRepository.run {
				Pair(getSummaryFromRecipeId(id), getStepsToPrepareRecipeById(id)).also{
					currentRecipeInformation = it
				}
			}
		}
	}
}
package com.example.recipesapp.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipesapp.domain.RecipeItem
import com.example.recipesapp.domain.SelectableIngredient
import com.example.recipesapp.domain.repository.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipesViewModel @Inject constructor(private val recipeRepository: RecipeRepository) :
	ViewModel() {
	
	companion object {
		const val INGREDIENTS = "ingredients"
	}
	
	private val _currentRecipes: MutableStateFlow<List<RecipeItem>> =
		MutableStateFlow(value = emptyList())
	val currentRecipes: StateFlow<List<RecipeItem>> = _currentRecipes.asStateFlow()
	val searchedIngredients = mutableStateListOf<SelectableIngredient>()
	var isSearching by mutableStateOf(false)
		private set
	
	init {
		val queries = mapOf(INGREDIENTS to "apples,sugar,flour", "number" to "25")
		getRecipesByIngredients(queries)
	}
	
	/*TODO(Error Handling)*/
	fun getRecipesByIngredients(ingredients: Map<String, String>) {
		viewModelScope.launch {
			_currentRecipes.value = recipeRepository.getRecipesByIngredients(ingredients)
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
}
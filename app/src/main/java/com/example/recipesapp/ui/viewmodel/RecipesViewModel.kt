package com.example.recipesapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipesapp.domain.RecipeItem
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
	
	private val _currentRecipes: MutableStateFlow<List<RecipeItem>> =
		MutableStateFlow(value = emptyList())
	val currentRecipes: StateFlow<List<RecipeItem>> = _currentRecipes.asStateFlow()
	
	init {
		val queries = mapOf("ingredients" to "apples,sugar,flour", "number" to "5")
		viewModelScope.launch {
			_currentRecipes.value = recipeRepository.getRecipesByIngredients(ingredients = queries)
		}
	}
	
	/*TODO(Error Handling)*/
	fun getRecipesBiIngredients(ingredients: Map<String, String>) {
		viewModelScope.launch {
			_currentRecipes.value = recipeRepository.getRecipesByIngredients(ingredients)
		}
	}
}
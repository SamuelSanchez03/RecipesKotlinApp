package com.example.recipesapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.recipesapp.ui.screen.FIND_RECIPE_SCREEN
import com.example.recipesapp.ui.screen.RecipeScreen
import com.example.recipesapp.ui.viewmodel.RecipesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
			val navController = rememberNavController()
			val recipesViewModel = hiltViewModel<RecipesViewModel>()
			var active by rememberSaveable { mutableStateOf(false) }
			var query by rememberSaveable { mutableStateOf(String()) }
			Scaffold(
				topBar = {
					TopSearchBar(
						query = query,
						active = active,
						isSearching = recipesViewModel.isSearching,
						selectedIngredients = recipesViewModel.selectedIngredients,
						ingredientsResultSearch = recipesViewModel.searchedIngredients,
						onSelectIngredient = {
							if (!it.isSelected)
								recipesViewModel.selectedIngredients.add(it)
							it.isSelected = true
						},
						onRemoveSelectedIngredient = {
							recipesViewModel.selectedIngredients.remove(it)
							it.isSelected = false
						},
						onQueryChange = {
							query = it
						},
						onSearch = recipesViewModel::onSearchIngredientQuery,
						onSearchRecipes = {
							recipesViewModel.getRecipesByIngredients(
								mapOf(
									RecipesViewModel.INGREDIENTS
											to recipesViewModel.selectedIngredients.joinToString(
										separator = ","
									) { it.name }
								)
							)
							active = false
						},
						onActiveChange = {
							active = it
						}
					)
				}
			) { paddingValues ->
				NavHost(
					navController = navController,
					startDestination = FIND_RECIPE_SCREEN,
					modifier = Modifier
						.padding(paddingValues)
						.fillMaxSize()
				) {
					composable(FIND_RECIPE_SCREEN) {
						val recipes by recipesViewModel.currentRecipes.collectAsState()
						RecipeScreen(recipes = recipes)
					}
				}
			}
		}
	}
}
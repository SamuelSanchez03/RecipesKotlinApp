package com.example.recipesapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
			NavHost(
				navController = navController,
				startDestination = FIND_RECIPE_SCREEN,
				modifier = Modifier.fillMaxSize()
			) {
				composable(FIND_RECIPE_SCREEN) {
					val recipesViewModel = hiltViewModel<RecipesViewModel>()
					val recipes by recipesViewModel.currentRecipes.collectAsState()
					RecipeScreen(recipes = recipes)
				}
			}
		}
	}
}
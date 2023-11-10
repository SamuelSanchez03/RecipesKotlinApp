package com.example.recipesapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.recipesapp.ui.screen.DETAIL_RECIPE_SCREEN
import com.example.recipesapp.ui.screen.DetailRecipeScreen
import com.example.recipesapp.ui.screen.FIND_RECIPE_SCREEN
import com.example.recipesapp.ui.screen.GettingRecipesScreen
import com.example.recipesapp.ui.screen.NotFoundRecipesScreen
import com.example.recipesapp.ui.screen.RECIPE_IMAGE
import com.example.recipesapp.ui.screen.RecipeScreen
import com.example.recipesapp.ui.screen.TopSearchBar
import com.example.recipesapp.ui.state.RecipeSearchState
import com.example.recipesapp.ui.viewmodel.RecipesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
	@OptIn(ExperimentalComposeUiApi::class)
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
			val navController = rememberNavController()
			val recipesViewModel = hiltViewModel<RecipesViewModel>()
			var active by rememberSaveable { mutableStateOf(false) }
			var recipesToFind by rememberSaveable { mutableIntStateOf(10) }
			var query by rememberSaveable { mutableStateOf(String()) }
			Scaffold(
				topBar = {
					AnimatedVisibility(visible = recipesViewModel.currentRecipeInformation == null)
					{
						val keyboardController = LocalSoftwareKeyboardController.current
						val focusManager = LocalFocusManager.current
						TopSearchBar(
							query = query,
							onClearQuery = { query = String() },
							active = active,
							isSearching = recipesViewModel.isSearching,
							recipesToFind = recipesToFind,
							onChangeRecipesToFind = {
								recipesToFind = it
							},
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
							onSearch = {
								recipesViewModel.onSearchIngredientQuery(it)
								focusManager.clearFocus(force = true)
								keyboardController?.hide()
							},
							onSearchRecipes = {
								recipesViewModel.getRecipesByIngredients(
									mapOf(
										RecipesViewModel.INGREDIENTS
												to recipesViewModel.selectedIngredients.joinToString(
											separator = ","
										) { it.name },
										RecipesViewModel.NUMBER to "$recipesToFind"
									)
								)
								active = false
							},
							onActiveChange = {
								active = it
							}
						)
					}
				}
			) { paddingValues ->
				NavHost(
					navController = navController,
					startDestination = FIND_RECIPE_SCREEN,
					modifier = Modifier
						.padding(paddingValues)
						.fillMaxSize()
				) {
					composable(
						route = "$DETAIL_RECIPE_SCREEN/{$RECIPE_IMAGE}",
						arguments = listOf(
							navArgument(name = RECIPE_IMAGE) {
								type = NavType.StringType
							}
						),
						enterTransition = {
							slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right)
						},
						exitTransition = {
							slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left)
						}
					) { backStackEntry ->
						val arguments = checkNotNull(backStackEntry.arguments)
						DetailRecipeScreen(
							recipeImage = arguments.getString(RECIPE_IMAGE).orEmpty(),
							recipeInformation = recipesViewModel.currentRecipeInformation,
							onBack = {
								navController.popBackStack()
								recipesViewModel.clearInformation()
							}
						)
					}
					composable(
						route = FIND_RECIPE_SCREEN,
						enterTransition = {
							slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left)
						},
						exitTransition = {
							slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right)
						}
					) {
						val recipes by recipesViewModel.currentRecipes.collectAsState()
						when (val state = recipesViewModel.searchRecipesState) {
							RecipeSearchState.NotFound -> NotFoundRecipesScreen()
							else -> RecipeScreen(
								recipes = recipes,
								onClickRecipeItem = {
									recipesViewModel.getRecipeInformationById(it.id)
									navController.navigate(
										"$DETAIL_RECIPE_SCREEN/${
											it.image.replace(
												'/',
												'*'
											)
										}"
									)
								},
								showLoading = state == RecipeSearchState.GettingRecipes
							) {
								GettingRecipesScreen()
							}
						}
					}
				}
			}
		}
	}
}
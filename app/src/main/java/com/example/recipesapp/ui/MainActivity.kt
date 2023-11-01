package com.example.recipesapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.recipesapp.R
import com.example.recipesapp.ui.screen.FIND_RECIPE_SCREEN
import com.example.recipesapp.ui.screen.RecipeScreen
import com.example.recipesapp.ui.viewmodel.RecipesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
	@OptIn(ExperimentalMaterial3Api::class)
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
			val navController = rememberNavController()
			val recipesViewModel = hiltViewModel<RecipesViewModel>()
			var active by rememberSaveable { mutableStateOf(false) }
			var query by rememberSaveable { mutableStateOf(String()) }
			val selectedIngredients = remember {
				mutableStateListOf<String>()
			}
			Scaffold(
				topBar = {
					SearchBar(
						query = query,
						onQueryChange = {
							query = it
						},
						onSearch = recipesViewModel::onSearchIngredientQuery,
						active = active,
						onActiveChange = {
							active = it
						},
						modifier = Modifier.fillMaxWidth(),
						placeholder = {
							Text(text = stringResource(id = R.string.search_by_ingredient))
						}
					) {
						LazyRow(
							modifier = Modifier
								.fillMaxWidth()
								.padding(all = 4.dp)
						) {
							items(selectedIngredients) {
								Text(text = it, modifier = Modifier.padding(horizontal = 4.dp))
							}
						}
						if (recipesViewModel.isSearching)
							CircularProgressIndicator(
								modifier = Modifier
									.align(Alignment.CenterHorizontally)
									.padding(top = 25.dp)
							)
						else {
							LazyColumn {
								items(recipesViewModel.searchedIngredients) {
									Row(modifier = Modifier.padding(all = 4.dp)) {
										Text(text = it, modifier = Modifier.clickable {
											selectedIngredients.add(it)
										})
									}
								}
							}
						}
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
					composable(FIND_RECIPE_SCREEN) {
						val recipes by recipesViewModel.currentRecipes.collectAsState()
						RecipeScreen(recipes = recipes)
					}
				}
			}
		}
	}
}
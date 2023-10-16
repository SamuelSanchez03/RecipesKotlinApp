package com.example.recipesapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.recipesapp.network.ApiCalls
import com.example.recipesapp.ui.screen.FIND_RECIPE_SCREEN
import com.example.recipesapp.ui.screen.FindRecipeScreen
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
	@Inject
	lateinit var caller: ApiCalls
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
					FindRecipeScreen()
					val queries = HashMap<String, String>()
					queries["ingredients"] = "apples,sugar,flour"
					queries["number"] = "5"
					caller.getRecipesByIngredients(queries)
					caller.getSummaryByID(4632)
					caller.getInstructionsByID(4632)
				}
			}
		}
		//setContentView(R.layout.activity_main)
		
		//Variable that allows us to make API calls
		//val caller = ApiCalls()
		
		//Function testing
		/*var queries = HashMap<String, String>()
		queries["ingredients"] = "apples,sugar,flour"
		queries["number"] = "5"

		caller.getRecipesByIngredients(queries)
		caller.getSummaryByID(4632)
		caller.getInstructionsByID(4632)*/
	}
}
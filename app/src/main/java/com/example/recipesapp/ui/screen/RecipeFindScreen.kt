package com.example.recipesapp.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import com.example.recipesapp.R
import com.example.recipesapp.domain.RecipeItem

const val FIND_RECIPE_SCREEN = "FindRecipeScreen"

@Composable
fun RecipeScreen(modifier: Modifier = Modifier, recipes: List<RecipeItem>) {
	LazyVerticalGrid(
		modifier = modifier, contentPadding = PaddingValues(all = 4.dp),
		columns = GridCells.Fixed(count = 3),
		horizontalArrangement = Arrangement.spacedBy(4.dp),
	) {
		items(recipes, key = { it.id }) {
			RecipeItemView(modifier = Modifier, recipeItem = it)
		}
	}
}

@Composable
fun RecipeItemView(
	modifier: Modifier = Modifier,
	recipeItem: RecipeItem,
	image: @Composable (Modifier) -> Unit = {
		SubcomposeAsyncImage(
			model = recipeItem.image,
			loading = {
				LinearProgressIndicator(modifier = Modifier.padding(2.dp))
			},
			contentDescription = recipeItem.title,
			modifier = it,
			contentScale = ContentScale.Crop
			
		)
	}
) {
	val padding = 8.dp //This should be a dimen resource
	Card(
		modifier = modifier
			.fillMaxWidth()
			.padding(vertical = padding / 2)
	) {
		Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
			Text(
				text = recipeItem.title,
				fontSize = 16.sp,
				fontWeight = FontWeight.Bold,
				modifier = Modifier
					.fillMaxWidth()
					.padding(all = 4.dp),
				maxLines = 2,
				textAlign = TextAlign.Center
			)
			image(
				Modifier.fillMaxSize()
			)
		}
	}
}

@Composable
@Preview
fun RecipeItemPreview() {
	val data = listOf(
		RecipeItem(
			id = 640352,
			title = "Cranberry Apple Crisp",
			image = "https://spoonacular.com/recipeImages/632583-312x231.jpg",
			usedIngredientCount = 1,
			imageType = "jpg",
			missedIngredientCount = 3,
			missedIngredients = emptyList(),
			usedIngredients = emptyList(),
			likes = 2,
			unusedIngredients = emptyList()
		),
		RecipeItem(
			id = 640352,
			title = "Cranberry Apple Crisp",
			image = "https://spoonacular.com/recipeImages/632583-312x231.jpg",
			usedIngredientCount = 1,
			imageType = "jpg",
			missedIngredientCount = 3,
			missedIngredients = emptyList(),
			usedIngredients = emptyList(),
			likes = 2,
			unusedIngredients = emptyList()
		),
		RecipeItem(
			id = 640352,
			title = "Cranberry Apple Crisp",
			image = "https://spoonacular.com/recipeImages/632583-312x231.jpg",
			usedIngredientCount = 1,
			imageType = "jpg",
			missedIngredientCount = 3,
			missedIngredients = emptyList(),
			usedIngredients = emptyList(),
			likes = 2,
			unusedIngredients = emptyList()
		),
		RecipeItem(
			id = 640352,
			title = "Cranberry Apple Crisp",
			image = "https://spoonacular.com/recipeImages/632583-312x231.jpg",
			usedIngredientCount = 1,
			imageType = "jpg",
			missedIngredientCount = 3,
			missedIngredients = emptyList(),
			usedIngredients = emptyList(),
			likes = 2,
			unusedIngredients = emptyList()
		),
		RecipeItem(
			id = 640352,
			title = "Cranberry Apple Crisp",
			image = "https://spoonacular.com/recipeImages/632583-312x231.jpg",
			usedIngredientCount = 1,
			imageType = "jpg",
			missedIngredientCount = 3,
			missedIngredients = emptyList(),
			usedIngredients = emptyList(),
			likes = 2,
			unusedIngredients = emptyList()
		)
	)
	LazyVerticalGrid(
		modifier = Modifier.fillMaxSize(), contentPadding = PaddingValues(all = 4.dp),
		columns = GridCells.Fixed(count = 3),
		horizontalArrangement = Arrangement.spacedBy(4.dp),
	) {
		items(data) {
			RecipeItemView(
				recipeItem = it, image = { modifier ->
					Image(
						painter = painterResource(id = R.drawable.apple_crisp),
						contentDescription = null,
						modifier = modifier,
						contentScale = ContentScale.FillWidth
					)
				}
			)
		}
	}
}
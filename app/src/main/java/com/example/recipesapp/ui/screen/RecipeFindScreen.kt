package com.example.recipesapp.ui.screen

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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
	val padding = dimensionResource(id = R.dimen.padding_small)
	LazyVerticalGrid(
		modifier = modifier,
		contentPadding = PaddingValues(all = padding),
		columns = GridCells.Fixed(
			count =
			if (LocalConfiguration.current.orientation == Configuration.ORIENTATION_PORTRAIT)
				2
			else
				3
		),
		horizontalArrangement = Arrangement.spacedBy(padding),
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
	val padding = dimensionResource(id = R.dimen.padding_small)
	Card(
		modifier = modifier
			.fillMaxWidth()
			.padding(vertical = padding / 2)
	) {
		Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
			Text(
				text = recipeItem.title,
				fontSize = 18.sp,
				fontWeight = FontWeight.Bold,
				modifier = Modifier
					.fillMaxWidth()
					.padding(all = padding),
				maxLines = 2,
				textAlign = TextAlign.Center
			)
			Row(
				modifier = Modifier
					.fillMaxWidth()
					.padding(vertical = padding),
				horizontalArrangement = Arrangement.Center,
				verticalAlignment = Alignment.CenterVertically
			) {
				Icon(
					painter = painterResource(id = R.drawable.grocery),
					contentDescription = null
				)
				Spacer(
					modifier = Modifier
						.fillMaxHeight()
						.width(padding)
				)
				Column {
					Text(
						text = stringResource(
							id = R.string.used_ingredients,
							recipeItem.usedIngredientCount
						),
						textAlign = TextAlign.Center
					)
					Text(
						text = stringResource(
							id = R.string.missed_ingredients,
							recipeItem.missedIngredientCount
						),
						textAlign = TextAlign.Center
					)
				}
			}
			
			image(Modifier.fillMaxSize())
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
		columns = GridCells.Fixed(count = 2),
		horizontalArrangement = Arrangement.spacedBy(4.dp),
	) {
		items(data) {
			RecipeItemView(
				recipeItem = it, image = { modifierImage ->
					Image(
						painter = painterResource(id = R.drawable.apple_crisp),
						contentDescription = null,
						modifier = modifierImage,
						contentScale = ContentScale.Crop
					)
				}
			)
		}
	}
}
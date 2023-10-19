package com.example.recipesapp.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.List
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import com.example.recipesapp.R
import com.example.recipesapp.domain.RecipeItem

const val FIND_RECIPE_SCREEN = "FindRecipeScreen"

@Composable
fun RecipeScreen(modifier: Modifier = Modifier, recipes: List<RecipeItem>) {
	LazyColumn(modifier = modifier, contentPadding = PaddingValues(all = 8.dp)) {
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
				CircularProgressIndicator()
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
		Column(verticalArrangement = Arrangement.Center) {
			Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
				val modifierRowContent = Modifier.padding(start = padding, top = padding)
				Icon(
					imageVector = Icons.Outlined.List,
					contentDescription = null,
					modifier = modifierRowContent.align(Alignment.CenterVertically)
				)
				Text(
					text = recipeItem.title,
					fontSize = 20.sp,
					fontWeight = FontWeight.Bold,
					modifier = modifierRowContent,
					maxLines = 2
				)
			}
			image(
				Modifier
					.size(100.dp)
					.padding(start = padding, top = padding, bottom = padding)
					.clip(CircleShape)
			)
		}
	}
}

@Composable
@Preview
fun RecipeItemPreview() {
	RecipeItemView(
		recipeItem = RecipeItem(
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
		), image = {
			Image(
				painter = painterResource(id = R.drawable.apple_crisp),
				contentDescription = null,
				modifier = it
			)
		}
	)
}

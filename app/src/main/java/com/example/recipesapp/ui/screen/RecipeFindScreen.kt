package com.example.recipesapp.ui.screen

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import com.example.recipesapp.domain.RecipeItem

const val FIND_RECIPE_SCREEN = "FindRecipeScreen"

/*TODO(Create view from recipe)*/
@Composable
fun RecipeScreen(modifier: Modifier = Modifier, recipes: List<RecipeItem>) {
	LazyColumn(modifier = modifier, contentPadding = PaddingValues(all = 8.dp)) {
		items(recipes) {
			RecipeItemView(recipeItem = it)
		}
	}
}

@Composable
fun RecipeItemView(modifier: Modifier = Modifier, recipeItem: RecipeItem) {
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
			SubcomposeAsyncImage(
				model = recipeItem.image,
				loading = {
					CircularProgressIndicator()
				},
				contentDescription = recipeItem.title,
				modifier = Modifier
					.size(100.dp)
					.padding(start = padding, top = padding, bottom = padding)
					.clip(CircleShape),
				contentScale = ContentScale.Crop
			)
		}
	}
}

@Composable
@Preview
fun RecipeItemPreview() {
	Column {
		RecipeItemView(
			recipeItem = RecipeItem(
				id = 0,
				title = "Title",
				image = "https://spoonacular.com/recipeImages/632583-312x231.jpg",
				usedIngredientCount = 1,
				imageType = "",
				missedIngredientCount = 1,
				missedIngredients = emptyList(),
				usedIngredients = emptyList(),
				likes = 2,
				unusedIngredients = emptyList()
			)
		)
	}
}

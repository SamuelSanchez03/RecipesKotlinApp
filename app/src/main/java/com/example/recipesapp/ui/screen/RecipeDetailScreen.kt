package com.example.recipesapp.ui.screen

import android.text.Html
import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.recipesapp.R
import com.example.recipesapp.domain.AnalyzedStepsItem
import com.example.recipesapp.domain.Equipment
import com.example.recipesapp.domain.IngredientStep
import com.example.recipesapp.domain.Step
import com.example.recipesapp.domain.Summary
import com.example.recipesapp.domain.Temperature
import com.example.recipesapp.ui.viewmodel.RecipeInformation

const val RECIPE_IMAGE = "recipeImage"
const val DETAIL_RECIPE_SCREEN = "DetailRecipeScreen"

@Composable
fun DetailRecipeScreen(
	modifier: Modifier = Modifier, recipeImage: String, image: @Composable (Modifier) -> Unit = {
		AsyncImage(
			model = recipeImage.replace('*', '/'),
			contentDescription = null,
			modifier = it,
			contentScale = ContentScale.Crop
		)
	}, recipeInformation: RecipeInformation?, onBack: () -> Unit
) {
	BackHandler(enabled = true, onBack = onBack)
	val smallPadding = dimensionResource(id = R.dimen.padding_small)
	Column(
		modifier = modifier
			.fillMaxSize()
			.padding(smallPadding)
			.verticalScroll(state = rememberScrollState()),
		verticalArrangement = Arrangement.SpaceEvenly
	) {
		recipeInformation?.let { recipeInformation ->
			val (summaryRecipe, analyzedSteps) = recipeInformation
			Card(
				modifier = Modifier.fillMaxWidth()
			) {
				val modifierAlignment = Modifier.align(Alignment.CenterHorizontally)
				val modifierPadding = Modifier.padding(
					all = dimensionResource(
						id = R.dimen.medium_padding
					)
				)
				Text(
					text = summaryRecipe.title,
					modifier = modifierAlignment.then(modifierPadding),
					fontSize = dimensionResource(id = R.dimen.titles).value.sp,
					fontWeight = FontWeight.Bold,
					textAlign = TextAlign.Center
				)
				image(
					modifierAlignment
						.width(dimensionResource(id = R.dimen.width_image))
						.height(dimensionResource(id = R.dimen.height_image))
						.clip(CircleShape)
						.border(
							border = BorderStroke(
								width = smallPadding, color = MaterialTheme.colorScheme.primary
							), shape = CircleShape
						)
				)
				Box(modifier = modifierPadding) {
					val scrollState = rememberScrollState()
					Text(
						text = "${
							Html.fromHtml(
								summaryRecipe.summary, Html.FROM_HTML_MODE_COMPACT
							)
						}",
						modifier = Modifier
							.align(Alignment.Center)
							.height(dimensionResource(id = R.dimen.text_summary_recipe_height))
							.verticalScroll(scrollState),
						fontSize = dimensionResource(
							id = R.dimen.subtitles
						).value.sp,
						textAlign = TextAlign.Center
					)
					val scaleDownIndicator by animateFloatAsState(
						label = "Scale",
						targetValue = if (scrollState.canScrollForward) 1f else 0f,
						animationSpec = tween(durationMillis = 400)
					)
					Icon(
						imageVector = Icons.Default.ArrowDropDown,
						contentDescription = null,
						modifier = Modifier
							.padding(bottom = smallPadding)
							.align(Alignment.BottomCenter)
							.scale(scaleDownIndicator)
							.clip(CircleShape)
							.background(color = MaterialTheme.colorScheme.primary),
						tint = Color.White
					)
				}
			}
			analyzedSteps.forEach {
				StepsHorizontalPage(analyzedStepsItem = it)
			}
		} ?: LinearProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
	}
}

@Preview(showBackground = true)
@Composable
fun DetailPreview() {
	DetailRecipeScreen(recipeImage = "URL", image = {
		Image(
			painter = painterResource(id = R.drawable.apple_crisp),
			contentDescription = null,
			modifier = it
		)
	}, recipeInformation = Summary(
		id = 1, summary = Html.fromHtml(
			"The recipe Soy-and-Ginger-Glazed Salmon with Udon Noodles can be made <b>in approximately 1 hour and 35 minutes </b>. One portion of this dish contains about <b>48g of protein</b>, <b>17g of fat </b>, and a total of <b>552 calories</b>. This recipe serves 4. For <b>$5.91 per serving </b>, this recipe <b>covers 47% </b> of your daily requirements of vitamins and minerals. It works well as a main course. 1 person has tried and liked this recipe. It is brought to you by Food and Wine. If you have fresh ginger, udon noodles, salmon fillets, and a few other ingredients on hand, you can make it. It is a good option if you're following a  <b>dairy free and pescatarian </b> diet. All things considered, we decided this recipe  <b>deserves a spoonacular score of 92% </b>. This score is great. If you like this recipe, take a look at these similar recipes: Salmon With Soy-ginger Noodles, Ginger-Soy Salmon With Soba Noodles, and Soy & ginger salmon with soba noodles.",
			Html.FROM_HTML_MODE_COMPACT
		).toString(), title = "Soy-and-Ginger-Glazed Salmon with Udon Noodles"
	) to listOf(
		AnalyzedStepsItem(
			name = "Sample Name", steps = listOf(
				Step(
					equipment = listOf(
						Equipment(
							id = 404784,
							image = "",
							name = "oven",
							localizedName = null,
							temperature = Temperature(
								number = 200.0, unit = "Fahrenheit"
							)
						)
					),
					ingredients = emptyList(),
					length = null,
					number = 1,
					step = "Preheat the oven to 200 degrees F."
				), Step(
					equipment = listOf(
						Equipment(
							id = 404661,
							image = "whisk.png",
							name = "whisk",
							localizedName = null,
							temperature = null
						), Equipment(
							id = 404783,
							image = "bowl.jpg",
							name = "bowl",
							localizedName = null,
							temperature = null
						)
					),
					ingredients = listOf(
						IngredientStep(
							id = 19334, name = "light brown sugar"
						), IngredientStep(
							id = 19335, name = "granulated sugar"
						), IngredientStep(
							id = 18371, name = "baking powder"
						), IngredientStep(
							id = 12142, name = "pecans"
						), IngredientStep(
							id = 20081, name = "all purpose flour"
						), IngredientStep(
							id = 2047, name = "salt"
						)
					),
					length = null,
					number = 2,
					step = "Whisk together the flour, pecans, granulated sugar, light brown sugar, baking powder, baking soda, and salt in a medium bowl."
				)
			)
		)
	), onBack = {})
}
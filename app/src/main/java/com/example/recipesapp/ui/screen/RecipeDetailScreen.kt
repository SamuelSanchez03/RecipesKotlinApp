package com.example.recipesapp.ui.screen

import android.content.res.Configuration
import android.text.Html
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.recipesapp.R
import com.example.recipesapp.domain.Summary
import com.example.recipesapp.ui.viewmodel.RecipeInformation

const val RECIPE_IMAGE = "recipeImage"
const val DETAIL_RECIPE_SCREEN = "DetailRecipeScreen"

@Composable
fun DetailRecipeScreen(
	modifier: Modifier = Modifier,
	recipeImage: String,
	image: @Composable (Modifier) -> Unit = {
		AsyncImage(
			model = recipeImage.replace('*', '/'),
			contentDescription = null,
			modifier = it,
			contentScale = ContentScale.Crop
		)
	},
	recipeInformation: RecipeInformation?,
	onBack: () -> Unit
) {
	BackHandler(enabled = true, onBack = onBack)
	Column(
		modifier = Modifier
		.fillMaxSize()
		.verticalScroll(state = rememberScrollState()),
		verticalArrangement = Arrangement.Center
	) {
		Column(
			modifier = modifier
				.fillMaxSize()
				.padding(
					dimensionResource(id = R.dimen.padding_small)
				),
			verticalArrangement = Arrangement.Center
		) {
			recipeInformation?.let {
				val (summaryRecipe, steps) = it
				Card(modifier = Modifier.fillMaxWidth()) {
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
					val scope: (@Composable (@Composable () -> Unit) -> Unit) =
						if (LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE)
							({
								Row { it() }
							}) else
							({
								it()
							})
					scope.invoke {
						image(
							modifierAlignment
								.width(dimensionResource(id = R.dimen.width_image))
								.height(dimensionResource(id = R.dimen.height_image))
								.clip(CircleShape)
								.border(
									border = BorderStroke(
										width = 2.dp,
										color = MaterialTheme.colorScheme.primary
									),
									shape = CircleShape
								)
						)
						Text(
							text = "${
								Html.fromHtml(
									summaryRecipe.summary,
									Html.FROM_HTML_MODE_COMPACT
								)
							}", modifier = modifierPadding,
							fontSize = dimensionResource(
								id = R.dimen.subtitles
							).value.sp
						)
					}
				}
			} ?: LinearProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
		}
	}
	
}

@Preview(showBackground = true)
@Composable
fun DetailPreview() {
	DetailRecipeScreen(
		recipeImage = "URL",
		image = {
			Image(
				painter = painterResource(id = R.drawable.apple_crisp),
				contentDescription = null,
				modifier = it
			)
		},
		recipeInformation = Summary(
			id = 1,
			summary = Html.fromHtml(
				"The recipe Soy-and-Ginger-Glazed Salmon with Udon Noodles can be made <b>in approximately 1 hour and 35 minutes </b>. One portion of this dish contains about <b>48g of protein</b>, <b>17g of fat </b>, and a total of <b>552 calories</b>. This recipe serves 4. For <b>$5.91 per serving </b>, this recipe <b>covers 47% </b> of your daily requirements of vitamins and minerals. It works well as a main course. 1 person has tried and liked this recipe. It is brought to you by Food and Wine. If you have fresh ginger, udon noodles, salmon fillets, and a few other ingredients on hand, you can make it. It is a good option if you're following a  <b>dairy free and pescatarian </b> diet. All things considered, we decided this recipe  <b>deserves a spoonacular score of 92% </b>. This score is great. If you like this recipe, take a look at these similar recipes: Salmon With Soy-ginger Noodles, Ginger-Soy Salmon With Soba Noodles, and Soy & ginger salmon with soba noodles.",
				Html.FROM_HTML_MODE_COMPACT
			).toString(),
			title = "Soy-and-Ginger-Glazed Salmon with Udon Noodles"
		) to emptyList(),
		onBack = {}
	)
}
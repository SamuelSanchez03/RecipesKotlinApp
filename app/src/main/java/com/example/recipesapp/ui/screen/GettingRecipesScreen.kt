package com.example.recipesapp.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.recipesapp.R

@Composable
fun GettingRecipesScreen(modifier: Modifier = Modifier) {
	val emojis = arrayOf("😋", "👀", "🥗", "🍴", "🍽", "🥧")
	val messages = stringArrayResource(id = R.array.loading_messages)
	Column(modifier = modifier, verticalArrangement = Arrangement.Center) {
		val modifierAlignment = Modifier.align(Alignment.CenterHorizontally)
		Text(
			text = messages.random() + emojis.random(),
			fontSize = dimensionResource(id = R.dimen.titles).value.sp,
			modifier = modifierAlignment,
			textAlign = TextAlign.Center
		)
		Spacer(modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.medium_padding)))
		LinearProgressIndicator(
			modifier = modifierAlignment
		)
	}
}

@Preview(showBackground = true)
@Composable
fun GettingRecipesPreview() = GettingRecipesScreen()
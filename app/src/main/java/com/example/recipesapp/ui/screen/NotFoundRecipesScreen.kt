package com.example.recipesapp.ui.screen

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.recipesapp.R

@Composable
fun NotFoundRecipesScreen(modifier: Modifier = Modifier) {
	Column(modifier = modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
		val modifierCenter = Modifier.align(Alignment.CenterHorizontally)
		val infiniteTransition = rememberInfiniteTransition(label = "Infinite Rotate Animation")
		val rotation by infiniteTransition.animateFloat(
			initialValue = -10f,
			targetValue = 10f,
			animationSpec = infiniteRepeatable(
				animation = tween(durationMillis = 500),
				repeatMode = RepeatMode.Reverse
			),
			label = "Infinite Rotation"
		)
		Image(
			painter = painterResource(id = R.drawable.not_found),
			contentDescription = null,
			modifier = modifierCenter.rotate(rotation)
		)
		Text(
			text = stringResource(id = R.string.not_fund_recipes),
			textAlign = TextAlign.Center,
			modifier = modifierCenter,
			fontSize = dimensionResource(id = R.dimen.titles).value.sp
		)
	}
}

@Preview(showBackground = true)
@Composable
fun NotFoundPreview() = NotFoundRecipesScreen()
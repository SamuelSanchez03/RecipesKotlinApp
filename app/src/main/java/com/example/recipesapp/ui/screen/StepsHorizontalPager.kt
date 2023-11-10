package com.example.recipesapp.ui.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.recipesapp.R
import com.example.recipesapp.domain.AnalyzedStepsItem

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun StepsHorizontalPage(analyzedStepsItem: AnalyzedStepsItem) {
	val smallPadding = dimensionResource(id = R.dimen.padding_small)
	val mediumPadding = dimensionResource(id = R.dimen.medium_padding)
	if (analyzedStepsItem.name.isNotEmpty()) Text(
		text = analyzedStepsItem.name,
		fontSize = dimensionResource(id = R.dimen.subtitles).value.sp,
		modifier = Modifier
			.fillMaxWidth()
			.padding(horizontal = smallPadding),
		textAlign = TextAlign.Center
	)
	val pagerState = rememberPagerState(initialPage = 0) {
		analyzedStepsItem.steps.size
	}
	HorizontalPager(
		state = pagerState,
		contentPadding = PaddingValues(all = mediumPadding),
		pageSpacing = smallPadding,
		beyondBoundsPageCount = analyzedStepsItem.steps.size
	) { page ->
		Card(
			modifier = Modifier.fillMaxWidth()
		) {
			val paddingTitle = Modifier.padding(
				PaddingValues(
					vertical = mediumPadding
				)
			)
			Row(
				horizontalArrangement = Arrangement.SpaceEvenly,
				modifier = Modifier.fillMaxWidth(),
			) {
				if (analyzedStepsItem.steps[page].ingredients.isNotEmpty())
					ItemsList(
						modifier = paddingTitle,
						title = stringResource(id = R.string.ingredients),
						items = analyzedStepsItem.steps[page].ingredients,
						text = { it.name }
					)
				if (analyzedStepsItem.steps[page].equipment.isNotEmpty())
					ItemsList(
						modifier = paddingTitle,
						title = stringResource(id = R.string.equipment),
						items = analyzedStepsItem.steps[page].equipment,
						text = { it.name }
					)
			}
			Text(
				text = analyzedStepsItem.steps[page].step,
				modifier = Modifier
					.fillMaxWidth()
					.padding(all = mediumPadding),
				textAlign = TextAlign.Center,
				fontSize = dimensionResource(id = R.dimen.subtitles).value.sp
			)
			Text(
				text = stringResource(id = R.string.step, analyzedStepsItem.steps[page].number),
				modifier = Modifier
					.align(Alignment.End)
					.padding(end = smallPadding, bottom = smallPadding),
				fontWeight = FontWeight.ExtraBold
			)
		}
	}
}

@Composable
private fun TextItemList(text: String) = Row(horizontalArrangement = Arrangement.Center) {
	val modifierAlign = Modifier.align(Alignment.CenterVertically)
	Icon(
		imageVector = Icons.Default.List,
		contentDescription = null,
		modifier = modifierAlign
	)
	Text(
		text = text,
		fontSize = dimensionResource(id = R.dimen.subtitles).value.sp,
		modifier = modifierAlign
	)
}

@Composable
private fun <T> ItemsList(modifier: Modifier, title: String, items: List<T>, text: (T) -> String) {
	Column {
		Text(
			text = title,
			fontSize = dimensionResource(
				id = R.dimen.titles
			).value.sp,
			textAlign = TextAlign.Center,
			modifier = modifier
		)
		items.forEach {
			TextItemList(text = text(it))
		}
	}
}
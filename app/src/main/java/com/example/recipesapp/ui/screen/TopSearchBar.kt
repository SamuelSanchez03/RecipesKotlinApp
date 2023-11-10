package com.example.recipesapp.ui.screen

import android.widget.NumberPicker
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.recipesapp.R
import com.example.recipesapp.domain.SelectableIngredient

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun TopSearchBar(
	modifier: Modifier = Modifier,
	query: String,
	onClearQuery: () -> Unit,
	active: Boolean,
	isSearching: Boolean,
	recipesToFind: Int,
	onChangeRecipesToFind: (Int) -> Unit,
	selectedIngredients: List<SelectableIngredient>,
	ingredientsResultSearch: List<SelectableIngredient>,
	onSelectIngredient: (SelectableIngredient) -> Unit,
	onRemoveSelectedIngredient: (SelectableIngredient) -> Unit,
	onQueryChange: (String) -> Unit,
	onSearch: (String) -> Unit,
	onSearchRecipes: () -> Unit,
	onActiveChange: (Boolean) -> Unit
) {
	val padding = dimensionResource(id = R.dimen.padding_small)
	SearchBar(
		query = query,
		onQueryChange = onQueryChange,
		onSearch = onSearch,
		active = active,
		onActiveChange = onActiveChange,
		modifier = modifier
			.padding(all = padding)
			.fillMaxWidth(),
		placeholder = {
			Text(text = stringResource(id = R.string.search_by_ingredient))
		},
		trailingIcon = if (query.isNotEmpty()) ({
			IconButton(onClick = onClearQuery) {
				Icon(
					imageVector = Icons.Default.Close,
					contentDescription = stringResource(id = R.string.clear_query)
				)
			}
		}) else null
	) {
		AnimatedVisibility(
			visible = selectedIngredients.isNotEmpty(),
			modifier = Modifier.fillMaxWidth()
		) {
			Column {
				LazyRow(
					modifier = Modifier
						.padding(all = padding)
						.fillMaxWidth()
				) {
					items(
						selectedIngredients,
						key = {
							it.name
						}
					) {
						TextButton(
							onClick = {
								onRemoveSelectedIngredient(it)
							},
							modifier = Modifier
								.animateItemPlacement()
								.padding(horizontal = padding / 2)
								.border(
									border = BorderStroke(
										width = 1.dp,
										color = MaterialTheme.colorScheme.primary
									),
									shape = CircleShape
								)
						) {
							Icon(
								imageVector = Icons.Default.Close, contentDescription = null
							)
							Text(text = it.name)
						}
					}
				}
				TextButton(
					onClick = onSearchRecipes,
					modifier = Modifier.align(Alignment.CenterHorizontally),
				) {
					Icon(
						imageVector = Icons.Default.Search, contentDescription = null
					)
					Spacer(modifier = Modifier.width(padding))
					Text(
						text = stringResource(
							id = R.string.search_recipes, selectedIngredients.size
						)
					)
				}
				Divider(thickness = padding)
				Row(
					modifier = Modifier
						.fillMaxWidth()
						.padding(vertical = dimensionResource(id = R.dimen.medium_padding))
				) {
					Text(
						text = stringResource(id = R.string.how_many_recipes, "👀"),
						fontSize = dimensionResource(id = R.dimen.subtitles).value.sp,
						modifier = Modifier
							.align(Alignment.CenterVertically)
							.weight(2f),
						textAlign = TextAlign.Center,
						minLines = 2
					)
					Spacer(modifier = Modifier.width(padding))
					AndroidView(
						factory = {
							NumberPicker(it).apply {
								minValue = 1
								maxValue = 100
								setOnValueChangedListener { _, _, newValue ->
									onChangeRecipesToFind(newValue)
								}
							}
						}, modifier = Modifier
							.weight(1f)
					) {
						it.value = recipesToFind
					}
				}
			}
		}
		Divider(thickness = padding)
		if (isSearching)
			Box(modifier = Modifier.fillMaxSize()) {
				CircularProgressIndicator(
					modifier = Modifier
						.height(IntrinsicSize.Max)
						.align(Alignment.Center)
				)
			}
		else {
			LazyColumn(
				modifier = Modifier.fillMaxWidth(),
				contentPadding = PaddingValues(all = padding)
			) {
				items(ingredientsResultSearch) {
					Row(
						modifier = Modifier
							.padding(vertical = padding * 2)
							.fillMaxWidth()
					) {
						Icon(
							imageVector = Icons.Default.List,
							contentDescription = null,
							modifier = Modifier.align(Alignment.CenterVertically)
						)
						Text(
							text = it.name,
							modifier = Modifier
								.clickable {
									onSelectIngredient(it)
								}
								.align(Alignment.CenterVertically),
							fontSize = dimensionResource(
								id = R.dimen.subtitles
							).value.sp
						)
					}
				}
			}
		}
	}
}

@Preview
@Composable
fun PreviewSearchBar() {
	TopSearchBar(
		query = "",
		onClearQuery = {},
		active = true,
		isSearching = false,
		recipesToFind = 10,
		onChangeRecipesToFind = {},
		selectedIngredients = listOf(
			SelectableIngredient(name = "Chocolate", isSelected = true),
			SelectableIngredient(name = "Nuts", isSelected = true)
		),
		ingredientsResultSearch = listOf(
			SelectableIngredient(name = "Apple"),
			SelectableIngredient(name = "Orange"),
			SelectableIngredient(name = "Chocolate", isSelected = true),
			SelectableIngredient(name = "Milk"),
			SelectableIngredient(name = "Nuts", isSelected = true)
		),
		onSelectIngredient = {},
		onRemoveSelectedIngredient = {},
		onQueryChange = {},
		onSearch = {},
		onSearchRecipes = {},
		onActiveChange = {}
	)
}
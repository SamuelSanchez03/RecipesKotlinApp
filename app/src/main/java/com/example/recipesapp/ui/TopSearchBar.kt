package com.example.recipesapp.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.recipesapp.R
import com.example.recipesapp.domain.SelectableIngredient

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopSearchBar(
	modifier: Modifier = Modifier,
	query: String,
	active: Boolean,
	isSearching: Boolean,
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
	SearchBar(query = query,
		onQueryChange = onQueryChange,
		onSearch = onSearch,
		active = active,
		onActiveChange = onActiveChange,
		modifier = modifier
			.padding(all = padding)
			.fillMaxWidth(),
		placeholder = {
			Text(text = stringResource(id = R.string.search_by_ingredient))
		}) {
		LazyRow(
			modifier = Modifier
				.fillMaxWidth()
				.padding(all = padding / 2)
		) {
			items(selectedIngredients) {
				TextButton(
					onClick = {
						onRemoveSelectedIngredient(it)
					},
					modifier = Modifier
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
			enabled = selectedIngredients.isNotEmpty(),
			modifier = Modifier.align(Alignment.CenterHorizontally)
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
		if (isSearching)
			CircularProgressIndicator(
				modifier = Modifier
					.align(Alignment.CenterHorizontally)
					.padding(top = LocalConfiguration.current.screenHeightDp.dp / 4)
			)
		else {
			LazyColumn(
				modifier = Modifier.fillMaxWidth(),
				contentPadding = PaddingValues(all = padding)
			) {
				item {
					Divider(thickness = padding)
				}
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
								id = R.dimen.text_card_ingredient
							).value.sp
						)
					}
				}
			}
		}
	}
}
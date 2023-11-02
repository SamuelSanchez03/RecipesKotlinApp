package com.example.recipesapp.domain

data class SearchedIngredient(
	val name: String
) {
	fun toSelectableIngredient() = SelectableIngredient(name)
}

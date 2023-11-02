package com.example.recipesapp.domain

data class SelectableIngredient(
	val name: String,
	var isSelected: Boolean = false
)

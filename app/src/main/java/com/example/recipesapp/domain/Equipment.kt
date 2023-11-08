package com.example.recipesapp.domain

data class Equipment(
    val id: Int,
    val image: String,
    val name: String,
    val localizedName: String?,
    val temperature: Temperature?
)
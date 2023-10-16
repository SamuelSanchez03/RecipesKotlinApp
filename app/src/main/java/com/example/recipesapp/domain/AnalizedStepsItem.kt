package com.example.recipesapp.domain

//AnalizedStepsItem for managing analized steps API call
data class AnalizedStepsItem(
    val name: String,
    val steps: List<Step>
)
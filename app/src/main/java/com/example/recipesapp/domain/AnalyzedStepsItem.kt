package com.example.recipesapp.domain

//AnalyzedStepsItem for managing analyzed steps API call
data class AnalyzedStepsItem(
    val name: String,
    val steps: List<Step>
)
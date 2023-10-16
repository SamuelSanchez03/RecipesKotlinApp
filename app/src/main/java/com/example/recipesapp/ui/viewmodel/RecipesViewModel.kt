package com.example.recipesapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.recipesapp.network.ApiCalls
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RecipesViewModel @Inject constructor(private val apiCalls: ApiCalls): ViewModel() {
}
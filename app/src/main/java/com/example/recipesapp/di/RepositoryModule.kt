package com.example.recipesapp.di

import com.example.recipesapp.domain.repository.RecipeRepository
import com.example.recipesapp.network.RecipeApiRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {
	@Binds
	abstract fun bindApiRepository(recipeApiRepository: RecipeApiRepository): RecipeRepository
}
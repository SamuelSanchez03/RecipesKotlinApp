package com.example.recipesapp.di

import android.content.Context
import com.example.recipesapp.R
import com.example.recipesapp.domain.api.ApiKey
import com.example.recipesapp.network.ApiInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
	private const val BASE_URL = "https://api.spoonacular.com/recipes/"
	
	//RetrofitBuilder for making API calls from the ApiInterface class
	@Provides
	fun provideRetrofit(): ApiInterface = Retrofit.Builder()
		.addConverterFactory(GsonConverterFactory.create())
		.baseUrl(BASE_URL)
		.build()
		.create(ApiInterface::class.java)
	
	@Provides
	fun provideApiKey(@ApplicationContext context: Context) =
		ApiKey(key = context.getString(R.string.api_key))
}